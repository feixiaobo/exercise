package exercise.service;

import redis.clients.jedis.JedisPubSub;

public interface RedisPubService {

    void publishMessage(JedisPubSub jedisPubSub, String message);
}
