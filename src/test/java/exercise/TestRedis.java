//package exercise;
//
//import exercise.redis.RedisLock;
//import exercise.redis.RedisLockKey;
//import exercise.redis.RedisUtils;
//import exercise.service.TestService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.redisson.Config;
//import org.redisson.RedissonClient;
//import org.redisson.SingleServerConfig;
//import org.redisson.core.RBucket;
//import org.redisson.core.RLock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by feixiaobo on 2016/10/17.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
//public class TestRedis {
//
//    @Autowired
//    TestService testService;
//
//    @Test
//    public void test() throws InterruptedException {
//        //redisson配置
//        Config config = new Config();
//        SingleServerConfig singleSerververConfig = config.useSingleServer();
//        //singleSerververConfig.setAddress("172.16.2.2:6379");
//        singleSerververConfig.setAddress("127.0.0.1:6379");
//        //singleSerververConfig.setPassword("dev@3YnUcR7V");
//        //redisson客户端
//        //RedissonClient redissonClient = RedisUtils.getInstance().getRedisson(config);
//        RedissonClient redissonClient = RedisUtils.getInstance().createClient();
////        RBucket<Object> rBucket = RedisUtils.getInstance().getRBucket(redissonClient, "key");
////        System.out.println(rBucket.get());
//
//        while (true) {
//            RLock lock = redissonClient.getLock("lock");
//            lock.tryLock(0, 1, TimeUnit.SECONDS);//第一个参数代表等待时间，第二是代表超过时间释放锁，第三个代表设置的时间制
//                    try {
//                        System.out.println("执行");
//                    } finally {
//                        lock.unlock();
//                    }
//                }
////        for (int i = 0;i < 10 ;i++ ){
////            String key = "lock";
////            print(key);
////        }
//    }
//
//    public void print( String key ){
//        try {
//            System.out.println("执行，休眠");
//            Thread.sleep(1000);
//            System.out.println("休眠结束");
//        }catch (Exception e){
//            //
//        }
//    }
//}
