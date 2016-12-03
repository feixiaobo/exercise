package exercise.config;

import exercise.enums.RabbitMQEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by feixiaobo on 2016/11/11.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 声明exchange
     */
    @Bean
    public DirectExchange testExchange(){
        return new DirectExchange(RabbitMQEnum.EXCHANGE_TEST.getValue());
    }

    /**
     * test queue
     * @return
     */
    @Bean
    Queue testQueue(){
       return   new Queue(RabbitMQEnum.QUEUE_TEST.getValue());
    }

    /**
     * queue 绑定到exchange
     */
    @Bean
    public List<Binding> bindingQueue(){
        return Arrays.asList(BindingBuilder.bind(testQueue()).to(testExchange())
            .with(RabbitMQEnum.QUEUE_TEST.getValue()));
    }
}
