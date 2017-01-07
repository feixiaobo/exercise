package exercise.redisPubSub;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class SubScriber {

    @Autowired
    private Jedis jedis;

    public SubScriber(Jedis jedis){
       this.jedis = jedis;
    }

    /**
     * 将所有订阅者持久化一次
     * @param key
     * @param strings
     */
    public void subScriber(String key, String ...strings){
        jedis.lpush(key,strings);
    }

}
