package exercise.service.impl;

import exercise.redisPubSub.PubClient;
import exercise.redisPubSub.SubScriber;
import exercise.service.RedisPubService;
import exercise.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by feixiaobo on 2017/1/7.
 */
@Service
public class RedisPubServiceImpl implements RedisPubService {

    @Autowired
    private JedisPool jedisPool;
    private PubClient pubClient;
    private SubScriber subscribe;

    @Override
    public void publishMessage(JedisPubSub jedisPubSub, String message) {
        //发布者和订阅者需要两个不同的jedis Bean
        Jedis jedis = jedisPool.getResource();
        subscribe = new SubScriber(jedis);
        //先将订阅者持久化一次
        subscribe.subScriber(Constants.SUBSCRIBE_CENTER, Constants.REDIS_PUBLISH);
        //订阅
        jedis.subscribe(jedisPubSub, Constants.REDIS_PUBLISH);

        Jedis jedisPub = jedisPool.getResource();
        pubClient = new PubClient(jedisPub);
        //发布
        pubClient.pub(Constants.REDIS_PUBLISH, message);
    }
}
