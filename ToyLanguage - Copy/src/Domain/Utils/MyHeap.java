package Domain.Utils;

import java.util.*;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    private Map<K, V> heap;

    public MyHeap () { heap = new HashMap<K, V>(); }
    public void add(K key, V val) {
        heap.put(key, val);
    }
    public void set(K key, V newValue)
    {
        if(heap.containsKey(key))
        {
            heap.remove(key);
            heap.put(key, newValue);
        }
    }

    public void remove(K key)
    {
        heap.remove(key);
    }

    public V get(K key)
    {
        return heap.get(key);
    }

    public boolean contains(K key)
    {
        return heap.containsKey(key);
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for(K key : this.heap.keySet())
        {
            s.append(key.toString()).append(" -> ").append(this.heap.get(key).toString()).append("\n");
        }

        return s.toString();
    }

    public Collection<V> getCollection()
    {
        return this.heap.values();
    }

    public void setContent(Map<K, V> old)
    {
        this.heap = new HashMap<K, V>(old);
    }

    public Map<K,V> getContent()
    {
        return this.heap;
    }
}