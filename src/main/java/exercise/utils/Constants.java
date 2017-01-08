package exercise.utils;

/**
 * Created by feixiaobo on 2016/12/12.
 */
public interface Constants {
    /**
     * 订阅者注册中心，用于持久化订阅者
     */
    String SUBSCRIBE_CENTER = "sub_scriber_center";

    /**
     * 发布渠道
     */
    String REDIS_PUBLISH = "redis-pub-channel";

    /**
     * 单订阅者（暂只做一个）
     */
    String SUBSCRIBER_ONE = "subscriber_one";
}
