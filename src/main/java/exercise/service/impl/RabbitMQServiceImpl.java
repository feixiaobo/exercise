package exercise.service.impl;

import exercise.utils.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by feixiaobo on 2016/11/25.
 */
public class RabbitMQServiceImpl implements exercise.service.RabbitMQService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private Logger logger = LogManager.getLogger(RabbitMQServiceImpl.class);

    @Override
    public <V> void convertAndSend(String exchange, String queue, V value){
        Assert.notNull(Arrays.asList(exchange,queue));
        Assert.notBlank(value);
        try {
            amqpTemplate.convertAndSend(exchange, queue, value);
            logger.info("sent to AMQ : exchange:{}, queue:{}, value:{}",exchange,queue,value);
        }catch (AmqpException e){
            logger.error("error when send to AMQ!");
        }
    }
}
