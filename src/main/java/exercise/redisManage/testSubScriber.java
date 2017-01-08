package exercise.redisManage;

import exercise.redisPubSub.PubSubScriber;
import exercise.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by feixiaobo on 2017/1/7.
 */
@Component
public class TestSubScriber extends PubSubScriber{

    private Logger logger = LogManager.getLogger(TestSubScriber.class);

    @Override
    public void process(String channel, String message){
        channel = Constants.REDIS_PUBLISH;
        logger.info("redis pubsub success-------->channel : {}, message : {}!",channel, message);
    }
}
