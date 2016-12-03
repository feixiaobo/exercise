package exercise.service;

/**
 * Created by feixiaobo on 2016/11/25.
 */
public interface RabbitMQService {

    <V> void convertAndSend(String exchange, String queue, V value);
}
