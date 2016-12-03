package exercise.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by feixiaobo on 2016/11/11.
 */
@Component
public class RabbitMQListenerComponent {

    @RabbitListener(queues = "test.queue")
    public void processTest(){}

}
