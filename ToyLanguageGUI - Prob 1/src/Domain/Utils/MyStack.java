package Domain.Utils;
import Domain.Statements.IStmt;

import java.time.chrono.IsoChronology;
import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> s;

    public MyStack()
    {
        s = new Stack<T>();
    }

    //copy constructor
    public MyStack(MyStack<T> a)
    {
        this.s = (Stack<T>) a.s.clone();
    }

    public synchronized T pop()
    {
        return s.pop();
    }

    public synchronized T top()
    {
        return s.peek();
    }
    public synchronized void push(T v)
    {
        s.push(v);
    }

    public synchronized Stack<T> getStack()
    {
        return this.s;
    }

    public synchronized boolean isEmpty()
    {
        return s.isEmpty();
    }

    public synchronized String toString()
    {
        return s.toString();
    }
}
