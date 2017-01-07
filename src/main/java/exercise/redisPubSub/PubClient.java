package exercise.redisPubSub;

import exercise.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class PubClient {

    @Autowired
    private Jedis jedis;

    public PubClient(Jedis jedis){
        this.jedis = jedis;
    }

    /**
     * 发布的每条消息，都需要在“订阅者消息队列”中持久
     * @param message
     */
    public void put(String message){
        //期望这个集合不要太大,一个发布者对应多个订阅者
        Set<String> subClients = jedis.smembers(Constants.SUBSCRIBE_CENTER);  //获取以key为键的所有value,所有订阅者都存到一个订阅者集合中，此处获取所有订阅者，只有一个的话可以传订阅者
        for(String clientKey : subClients){ //一个message发布到多个key上，也就是多个订阅者上
            jedis.rpush(clientKey, message);
        }
    }

    public void pub(String channel,String message){
        //每个消息，都有具有一个全局唯一的id
        //txid为了防止订阅端在数据处理时“乱序”，这就要求订阅者需要解析message，相同的value会自加计数，我们不能保证value不重复，所以这个操作很有必要
        Long txid = jedis.incr(message);
        String content = txid + "/" + message;
        //非事务
        this.put(content);
        jedis.publish(channel, content);//为每个消息设定id，最终消息格式1000/messageContent

    }

    public void close(String channel){
        jedis.publish(channel, "quit");
        jedis.del(channel);//删除
    }

    public void test(){
        jedis.set("pub-block", "15");
        String tmp = jedis.get("pub-block");
        System.out.println("TEST:" + tmp);
    }
}
