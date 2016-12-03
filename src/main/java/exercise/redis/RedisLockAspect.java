package exercise.redis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonClient;
import org.redisson.core.RAtomicLong;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by feixiaobo on 2016/10/18.
 */
@Component @Aspect public class RedisLockAspect {

    private static Logger logger= LoggerFactory.getLogger(RedisLockAspect.class);

    @Value("${spring.redis.host}:${spring.redis.port}") String address;
    //@Value("${spring.redis.password}") String pass;

    @Around("execution(* exercise..*(..))&& @annotation(RedisLock)")
    public Object lock(ProceedingJoinPoint point) throws Throwable {
        RLock lock = null;
        Object object = null;
        logger.info("into Aspect!");
        try {
            RedisLock redisLock = getDistRedisLockInfo(point);
            RedisUtils redisUtils = RedisUtils.getInstance();
            RedissonClient redissonClient = RedisUtils.createClient(address, null);
//            RAtomicLong atomicLong = RedisUtils.getRAtomicLong(redissonClient, "redis-lock");
//            atomicLong.set(2000);
//            atomicLong.compareAndSet(100, 400);
            String lockKey = redisUtils.getLockKey(point, redisLock.lockKey());

            lock = redisUtils.getRLock(redissonClient, lockKey);
            if (lock != null) {
                Boolean status = lock.tryLock(redisLock.maxSleepMills(), redisLock.keepMills(), TimeUnit.MILLISECONDS);
                if (status) {
                    object = point.proceed();
                }
            }
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
        return object;
    }

    private RedisLock getDistRedisLockInfo(ProceedingJoinPoint point) {
        try {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            return method.getAnnotation(RedisLock.class);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}
