import com.itheima.reggie.common.R;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;


public class test {
    @Test
    public void get() {
        int[] phone = new int[11];
        for (int i = 0; i < phone.length; i++) {
            phone[i] = new Random().nextInt(9);
        }
        int[] ans = new int[18];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = new Random().nextInt(9);
        }
        System.out.println(Arrays.toString(phone));
        System.out.println(Arrays.toString(ans));
    }
}
