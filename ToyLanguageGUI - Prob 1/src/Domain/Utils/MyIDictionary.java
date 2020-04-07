package Domain.Utils;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary<T, V> {
    void add(T key, V value);
    void set(T key, V newValue);
    void remove(T key);
    V get(T key);
    boolean contains(T key);
    Collection<V> getCollection();
    Map<T,V> getContent();
}
