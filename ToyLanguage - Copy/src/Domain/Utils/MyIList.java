package Domain.Utils;

public interface MyIList<T>
{
    void add(int index, T value);
    T get(int index);
    void set(int index, T newValue);
    void remove(int index);
    void addToEnd(T newValue);
}
