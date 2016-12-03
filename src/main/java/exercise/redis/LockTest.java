package exercise.redis;

import org.springframework.stereotype.Component;

/**
 * Created by feixiaobo on 2016/10/25.
 */
@Component
public class LockTest {

    private static int i = 0;

    @RedisLock(lockKey = "lockKey")
    public void add(@RedisLockKey(order = 1) String key,
        @RedisLockKey(order = 0) int key1) {
        i++;
        System.out.println("i=***************************************" + i);
    }
}
