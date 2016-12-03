package exercise.service;

/**
 * Created by feixiaobo on 2016/11/24.
 */
public interface RedisService {

    <K,V> void setObject(K key, V value);

    <K,V> V getObject(K key);

    <K> void remove(K key);

    <K> void removeAll(K ... keys);
}
