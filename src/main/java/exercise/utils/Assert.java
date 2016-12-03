package exercise.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * Created by feixiaobo on 2016/11/25.
 */
public class Assert extends org.springframework.util.Assert {

    public static <T> void notBlank(T msg){
        if((msg instanceof String && StringUtils.isBlank((String)msg)) || msg == null){
            throw new IllegalArgumentException("param error,can not is null!");
        }
    }

    public static <T> void notNull(Collection<T> msgs){
        msgs.forEach(e -> notBlank(e));
    }

}
