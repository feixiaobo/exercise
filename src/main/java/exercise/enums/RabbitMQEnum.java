package exercise.enums;

/**
 * Created by feixiaobo on 2016/11/11.
 */
public enum RabbitMQEnum {

    /**
     * test exchange
     */
    EXCHANGE_TEST("testExchange"),

    /**
     * test queue
     */
    QUEUE_TEST("test.queue");

    private String value;

    RabbitMQEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
