package exercise;

import java.util.Random;

/**
 * Created by feixiaobo on 2016/11/19.
 */
public class randomTest {

    public static void main(String[] args) {

        Random random = new Random(20);
        for(int i = 0;i < 20 ;i++){
            int num = random.nextInt(20);
            System.out.println(num+ ",");
        }
    }
}
