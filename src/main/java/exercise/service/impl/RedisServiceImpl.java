package exercise.service.impl;

import exercise.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by feixiaobo on 2016/11/25.
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 缓存失效时间（单位：秒）
     */
    private final int EXPIRE = 3 * 60 * 60;
    private Logger logger = LogManager.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <K,V> void setObject(K key, V value, Integer timeout){
        if(timeout == null){
            timeout = EXPIRE;
        }
        redisTemplate.opsForValue().set(key,value, timeout, TimeUnit.SECONDS);
        logger.info("set to redis success!");
    }

    @Override
    public <K,V> V getObject(K key){
        //get方法返回的应该是范型，为什么是object?
        V value = (V)redisTemplate.opsForValue().get(key);
        logger.info("get value from redis result: {}",value);
        return value;
    }

    @Override
    public <K> void remove(K key){
        redisTemplate.delete(key);
        logger.info("delete from redis success!");
    }

    @Override
    public <K> void removeAll(K ... keys){
        redisTemplate.delete(Arrays.asList(keys));
        logger.info("delete all from redis success!");
    }
}
