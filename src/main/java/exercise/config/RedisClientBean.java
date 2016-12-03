//package exercise.config;
//
//import exercise.redis.RedisUtils;
//import org.redisson.Config;
//import org.redisson.Redisson;
//import org.redisson.RedissonClient;
//import org.redisson.SingleServerConfig;
//import org.redisson.core.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
///**
// * Created by feixiaobo on 2016/10/27.
// */
//@Configuration
//public class RedisClientBean {
//
//    private static Logger logger= LoggerFactory.getLogger(RedisClientBean.class);
//
//    private RedissonClient redissonClient;
//
//    @Value("${spring.redis.host}:${spring.redis.port}") String address;
//    @Value("${spring.redis.password}") String pass;
//
//    @Bean
//    public RedissonClient redissonClient() {
//        if (redissonClient == null) {
//            synchronized (RedisUtils.class) {
//                if (redissonClient == null) {
//                    Config config = new Config();
//                    SingleServerConfig singleSerververConfig = config.useSingleServer();
//                    singleSerververConfig.setAddress(address);
//                    singleSerververConfig.setPassword(pass);
//                    //singleSerververConfig.setAddress("127.0.0.1:6379");
//                    redissonClient = RedisUtils.getRedisson(config);
//                }
//            }
//        }
//        return redissonClient;
//    }
//
//}
