package Domain.Utils;
import Domain.Utils.MyIDictionary;

import java.util.*;

public class MyDict<T, V> implements MyIDictionary<T, V> {
    private Map<T, V> m;

    public MyDict()
    {
        m = new HashMap<T, V>();
    }

    public MyDict(MyDict<T, V> newDict)
    {
        m = new HashMap<T, V>(newDict.getContent());
    }

    public void add(T key, V value)
    {
        m.put(key, value);
    }

    public void set(T key, V newValue)
    {
        if(m.containsKey(key))
        {
            m.remove(key);
            m.put(key, newValue);
        }
    }

    public void remove(T key)
    {
        m.remove(key);
    }

    public V get(T key)
    {
        return m.get(key);
    }

    public String toString()
    {
        //return m.toString();

        StringBuilder s = new StringBuilder();

        for(T key : this.m.keySet())
        {
            s.append(key.toString()).append("=").append(this.m.get(key).toString()).append("\n");
        }

        return s.toString();
    }

    public boolean contains(T key)
    {
        return m.containsKey(key);
    }

    public Collection<V> getCollection()
    {
        return this.m.values();
    }

    public Map<T,V> getContent()
    {
        return this.m;
    }
}
