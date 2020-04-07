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

    public T pop()
    {
        return s.pop();
    }

    public T top()
    {
        return s.peek();
    }
    public void push(T v)
    {
        s.push(v);
    }

    public Stack<T> getStack()
    {
        return this.s;
    }

    public boolean isEmpty()
    {
        return s.isEmpty();
    }

    public String toString()
    {
        return s.toString();
    }
}
