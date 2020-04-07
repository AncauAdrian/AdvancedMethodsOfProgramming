package Domain.Utils;
import Domain.Utils.MyIList;

import java.util.*;

public class MyList<T> implements MyIList<T> {
    private List<T> l;

    public MyList()
    {
        l = new ArrayList<T>();
    }

    public synchronized void add(int index, T value)
    {
        l.add(index, value);
    }

    public synchronized T get(int index)
    {
        return l.get(index);
    }

    public synchronized void set(int index, T newValue)
    {
        l.set(index, newValue);
    }

    public synchronized void remove(int index)
    {
        l.remove(index);
    }

    public synchronized void addToEnd(T newValue) {
        l.add(newValue);
    }

    public synchronized String toString()
    {
        //return l.toString();

        StringBuilder s = new StringBuilder();

        for (T item : this.l) {
            s.append(item.toString()).append("\n");
        }

        return s.toString();
    }

    public synchronized List<T> getContent()
    {
        return this.l;
    }
}
