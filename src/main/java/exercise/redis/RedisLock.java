package exercise.redis;

import java.lang.annotation.*;

/**
 * Created by feixiaobo on 2016/10/18.
 */
@Target({ElementType.METHOD}) @Retention(RetentionPolicy.RUNTIME) @Inherited
public @interface RedisLock {
    /**
     * 锁的key
     * 如果想添加非固定锁，可以在参数上添加@P4jSynKey注解，但是本参数是必写选项<br/>
     * redis key的拼写规则为 "DistRedisLock+" + lockKey + @RedisLOckKey<br/>
     */
    String lockKey();

    /**
     * 持锁时间，超时时间，持锁超过此时间自动丢弃锁<br/>
     * 单位毫秒,默认5秒<br/>
     * 如果为0表示永远不释放锁，在设置为0的情况下action为continue是没有意义的<br/>
     * 但是没有比较强的业务要求下，不建议设置为0
     */
    long keepMills() default 5 * 1000;

    /**
     * 没有获取到锁时，等待时间
     * @return
     */
    long maxSleepMills() default 120 * 1000;
}
