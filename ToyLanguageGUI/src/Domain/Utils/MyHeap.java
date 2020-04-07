package Domain.Utils;

import java.util.*;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    private Map<K, V> heap;

    public MyHeap () { heap = new HashMap<K, V>(); }
    public synchronized void add(K key, V val) {
        heap.put(key, val);
    }
    public synchronized void set(K key, V newValue)
    {
        if(heap.containsKey(key))
        {
            heap.remove(key);
            heap.put(key, newValue);
        }
    }

    public synchronized void remove(K key)
    {
        heap.remove(key);
    }

    public synchronized V get(K key)
    {
        return heap.get(key);
    }

    public synchronized boolean contains(K key)
    {
        return heap.containsKey(key);
    }

    public synchronized String toString()
    {
        StringBuilder s = new StringBuilder();

        for(K key : this.heap.keySet())
        {
            s.append(key.toString()).append(" -> ").append(this.heap.get(key).toString()).append("\n");
        }

        return s.toString();
    }

    public synchronized Collection<V> getCollection()
    {
        return this.heap.values();
    }

    public synchronized void setContent(Map<K, V> old)
    {
        this.heap = new HashMap<K, V>(old);
    }

    public synchronized Map<K,V> getContent()
    {
        return this.heap;
    }
}