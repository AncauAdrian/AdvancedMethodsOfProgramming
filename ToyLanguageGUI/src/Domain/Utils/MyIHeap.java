package Domain.Utils;

import java.util.Collection;
import java.util.Map;

public interface MyIHeap<K, V> {
    void add(K key, V value);
    void set(K key, V newValue);
    void remove(K key);
    V get(K key);
    boolean contains(K key);
    Collection<V> getCollection();
    void setContent(Map<K,V> old);
    Map<K,V> getContent();
}
