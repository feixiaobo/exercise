package exercise.redisPubSub;

import exercise.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

@Component
public class PubSubScriber extends JedisPubSub {

    private Logger logger = LogManager.getLogger(PubSubScriber.class);

    @Autowired
    private JedisPool jedisPool;
    private Jedis jedis;

    public PubSubScriber() {
    }

    public void onMessage(String channel, String message) {
        logger.info("receive redis published message, channel :-> {}, message :-> {}", channel, message);
        this.handle(channel, message);
    }

    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("subscribe redis channel success, channel :> {}, subscribedChannels :> {}", channel, subscribedChannels);
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("unsubscribe redis channel, channel :- {}, subscribedChannels :- {}", channel, subscribedChannels);
        this.unsubscribe(channel);
    }


    public void handle(String channel,String message){
        int index = message.indexOf("/");
        if(index < 0){
            return;
        }
        jedis.lpop(message);//删除当前message
        message = message.substring(index+1);
        this.process(channel, message);
    }

    public void unsubscribe(String channel){
        String key = channel;
        jedis.srem(Constants.SUBSCRIBE_CENTER, key);//从“活跃订阅者”集合中删除
        jedis.del(key);//删除“订阅者消息队列”
    }

    public void process(String channel, String message){
        //do nothing
        logger.info("test redis pubsub-----------{}",channel+message);
    }

    @PostConstruct
    private  void initJedis(){
        jedis = jedisPool.getResource();
    }
}
