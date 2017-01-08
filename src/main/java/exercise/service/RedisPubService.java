package exercise.service;

import exercise.redisPubSub.PubSubScriber;

public interface RedisPubService {

    void publishMessage(PubSubScriber pubSubScriber, String message);
}
