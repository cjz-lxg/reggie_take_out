package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basepath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info("文件上传:{}", file.toString());

        String originName = file.getOriginalFilename();
        String suffix = originName.substring(originName.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basepath);

        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basepath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basepath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] temp = new byte[1024];
            while ((len = fileInputStream.read(temp)) != -1) {
                outputStream.write(temp);
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}