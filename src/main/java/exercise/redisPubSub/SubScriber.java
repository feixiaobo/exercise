package exercise.redisPubSub;

import exercise.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class SubScriber implements Runnable{

    private Logger logger = LogManager.getLogger(SubScriber.class);

    private Jedis jedis;
    private JedisPubSub jedisPubSub;
    private String channel;

    public SubScriber(Jedis jedis,JedisPubSub jedisPubSub, String channel ){
        this.jedis = jedis;
        this.jedisPubSub = jedisPubSub;
        this.channel = channel;
    }

    /**
     * 将所有订阅者持久化一次
     * @param key
     * @param strings
     */
    public void subScriber(String key, String ...strings){
        jedis.sadd(key,strings);
    }

    @Override
    public void run() {
        logger.info("subscribe redis, channel : {}, thread will be blocked", channel);
        try {
            /**
             * 持久化订阅者到订阅中心
             */
            this.subScriber(Constants.SUBSCRIBE_CENTER,Constants.SUBSCRIBER_ONE);
            /**
             * 订阅者订阅
             */
            jedis.subscribe(jedisPubSub, channel);
        } catch (Exception e) {
            logger.info("subsrcibe channel error, {}", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}

