package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        Long userId = BaseContext.getCurrentId();
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
        if (dishId == null) {
            queryWrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        } else {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart != null) {
            int number = cart.getNumber();
            cart.setNumber(number + 1);
            shoppingCartService.updateById(cart);
        } else {
            cart = shoppingCart;
            cart.setUserId(userId);
            shoppingCartService.save(cart);
        }
        return R.success(cart);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清除成功");
    }
}
