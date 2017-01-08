package exercise.service.impl;

import exercise.redisPubSub.PubClient;
import exercise.redisPubSub.SubScriber;
import exercise.service.RedisPubService;
import exercise.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

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
    public void publishMessage(PubSubScriber pubSubScriber, String message) {
        //发布者和订阅者需要两个不同的jedis Bean
        subscribe = new SubScriber(jedisPool.getResource(),pubSubScriber,Constants.REDIS_PUBLISH);
        new Thread(subscribe).start();

        pubClient = new PubClient(jedisPool.getResource());
        //发布
        pubClient.pub(Constants.REDIS_PUBLISH, message);
        //退订
        pubSubScriber.unsubscribe(Constants.REDIS_PUBLISH);
    }
}
