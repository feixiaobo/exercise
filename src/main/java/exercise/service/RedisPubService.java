package exercise.service;

public interface RedisPubService {

    void publishMessage(PubSubScriber pubSubScriber, String message);
}
