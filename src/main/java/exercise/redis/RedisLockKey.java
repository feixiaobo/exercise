package exercise.redis;

import java.lang.annotation.*;

/**
 * Created by feixiaobo on 2016/10/19.
 */
@Target({ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME) @Inherited
public @interface RedisLockKey {
    /**
     * key的拼接顺序规则
     */
    int order() default 0;
}
