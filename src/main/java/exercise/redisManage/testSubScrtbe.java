package exercise.redisManage;

import exercise.redisPubSub.PubSubScriber;
import exercise.utils.Constants;
import org.springframework.stereotype.Component;

/**
 * Created by feixiaobo on 2017/1/7.
 */
@Component
public class TestSubScrtbe extends PubSubScriber{

    @Override
    public void process(String channel, String message){
        channel = Constants.REDIS_PUBLISH;
        System.out.println("++++++++"+message);
    }
}
