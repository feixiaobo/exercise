//package exercise;
//
//import exercise.redis.RedisUtils;
//import org.redisson.Config;
//import org.redisson.RedissonClient;
//import org.redisson.SingleServerConfig;
//import org.springframework.beans.factory.annotation.Value;
//
///**
// * Created by feixiaobo on 2016/10/17.
// */
//public class test {
//
//    @Value("${redis.host}:${redis.port}") private static String address;
//    @Value("${redis.password}") private static String pass;
//
//    private static RedissonClient redissonClient;
//
//    public static RedissonClient createClient() {
//        if(redissonClient == null) {
//            synchronized (test.class) {
//                if(redissonClient == null) {
//                    Config config = new Config();
//                    SingleServerConfig singleSerververConfig = config.useSingleServer();
//                    singleSerververConfig.setAddress(address);
//                    singleSerververConfig.setPassword(pass);
//                    redissonClient = RedisUtils.getInstance().getRedisson(config);
//                }
//            }
//        }
//        return redissonClient;
//    }
//
//}
