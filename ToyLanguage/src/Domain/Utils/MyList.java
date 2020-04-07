package Domain.Utils;
import Domain.Utils.MyIList;

import java.util.*;

public class MyList<T> implements MyIList<T> {
    private List<T> l;

    public MyList()
    {
        l = new ArrayList<T>();
    }

    public void add(int index, T value)
    {
        l.add(index, value);
    }

    public T get(int index)
    {
        return l.get(index);
    }

    public void set(int index, T newValue)
    {
        l.set(index, newValue);
    }

    public void remove(int index)
    {
        l.remove(index);
    }

    public void addToEnd(T newValue) {
        l.add(newValue);
    }

    public String toString()
    {
        //return l.toString();

        StringBuilder s = new StringBuilder();

        for (T item : this.l) {
            s.append(item.toString()).append("\n");
        }

        return s.toString();
    }
}
