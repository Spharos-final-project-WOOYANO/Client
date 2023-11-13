package spharos.client.global.common.domain;

public interface BaseEnum<K,V> {
    K getKey();
    V getValue();
}
