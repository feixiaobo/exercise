package exercise.redisPubSub;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class PubSubScriber extends JedisPubSub {

    @Autowired
    private Jedis jedis;

    public PubSubScriber() {
    }

    public void onMessage(String channel, String message) {
        System.out.println(String.format("receive redis published message, channel :-> %s, message :-> %s", channel, message));
        this.handle(channel, message);
    }

    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("subscribe redis channel success, channel :> %s, subscribedChannels :> %d",
                channel, subscribedChannels));
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("unsubscribe redis channel, channel :- %s, subscribedChannels :- %d",
                channel, subscribedChannels));
        this.unsubscribe(channel);
    }


    public void handle(String channel,String message){
        int index = message.indexOf("/");
        if(index < 0){
            return;
        }
        Long txid = Long.valueOf(message.substring(0,index));
        String key = channel;
        while (true){
            String lm = jedis.lindex(key,0);  //以同一个key存储的list的第一个value
            if(lm == null){
                break;
            }
            int li = lm.indexOf("/");
            //如果消息不合法，删除并处理
            if(li < 0){
                String result = jedis.lpop(key);//删除当前message
                //为空
                if(result == null){
                    break;
                }
                //message(channel, lm);
                //                process.subProcess(channel,message);
                this.process(channel, message);
                continue;
            }
            Long lxid = Long.valueOf(lm.substring(0,li));//获取消息的txid
            //直接消费txid之前的残留消息
            if(txid >= lxid){
                jedis.lpop(key);//删除当前message
                //message(channel, lm);
                continue;
            }else{
                break;
            }
        }
    }

    public void unsubscribe(String channel){
        String key = channel;
        jedis.srem("test", key);//从“活跃订阅者”集合中删除
        jedis.del(key);//删除“订阅者消息队列”
    }

    public void process(String channel, String message){
        //do nothing
    }
}
