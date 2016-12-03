//package exercise;
//
//import exercise.redis.RedisLock;
//import exercise.redis.RedisUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.redisson.RedissonClient;
//import org.redisson.core.RAtomicLong;
//import org.redisson.core.RLock;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.lang.reflect.Method;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by feixiaobo on 2016/10/21.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//public class TestRedisMrthod {
//    private RedissonClient redissonClient;
//
//    @Test
//    public void lock() throws Throwable{
//        RLock lock = null;
//        Boolean status = false;
//        Object object = null;
//        try {
//            //RedisLock redisLock = getDistRedisLockInfo(point);
//            redissonClient = RedisUtils.createClient();
//            RAtomicLong atomicLong = redissonClient.getAtomicLong("redis-lock");
//            atomicLong.set(2000);
//            atomicLong.compareAndSet(5,400);
//            RedisUtils redisUtils = new RedisUtils();
//            //String lockKey = redisUtils.getLockKey(point, redisLock.lockKey());
//            long sleep = 1000;
//            long maxWaitTime = System.nanoTime() + sleep;
//            //while (System.nanoTime() <= maxWaitTime) {  //未获取到锁且小于最大等待时间，重试获取锁
//                lock = redisUtils.getRLock(redissonClient, "lockKey");
//                //status = lock.tryLock(redisLock.maxSleepMills(), redisLock.keepMills(), TimeUnit.MILLISECONDS);
//                System.out.println("============================"+lock);
////                if (lock != null) {
//            //                    break;
//            //                }
//            //}
//            if (lock != null) {
//                //status = lock.tryLock(redisLock.maxSleepMills(), redisLock.keepMills(), TimeUnit.MILLISECONDS);
//                lock.tryLock(0, 1, TimeUnit.SECONDS);
//                while (true) {
//                    try {
//                        System.out.println("执行");
//                        Thread.sleep(1000);
//                        System.out.println("休眠结束");
//                    } finally {
//                        lock.unlock();
//                    }
//                }
//            }
//                //object = point.proceed();
//        }finally {
//            if(lock != null){
//                lock.unlock();
//            }
//        }
//        //return object;
//    }
//
//    private RedisLock getDistRedisLockInfo(ProceedingJoinPoint point) {
//        try {
//            MethodSignature methodSignature = (MethodSignature) point.getSignature();
//            Method method = methodSignature.getMethod();
//            return method.getAnnotation(RedisLock.class);
//        } catch (Exception e) {
//            //logger.error("getDistRedisLockInfo error.", e);
//        }
//        return null;
//    }
//}
