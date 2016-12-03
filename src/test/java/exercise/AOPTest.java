package exercise;

import exercise.redis.LockTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Created by feixiaobo on 2016/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AOPTest {
    @Autowired LockTest lockTest;
    @Test public void testDistLockAop() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockTest.add("***********testDistLockAop", 99);
            }).start();
        }

        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void testDistLock() throws InterruptedException {
        lockTest.add("============testDistLock", 111111);
        TimeUnit.SECONDS.sleep(10);
    }

}
