package app;

import Domain.Value;

public class HeapElement {
    int address;
    Value value;

    HeapElement(int address, Value value)
    {
        this.address = address;
        this.value = value;
    }

    public Value getValue()
    {
        return this.value;
    }

    public int getAddress()
    {
        return this.address;
    }
}
