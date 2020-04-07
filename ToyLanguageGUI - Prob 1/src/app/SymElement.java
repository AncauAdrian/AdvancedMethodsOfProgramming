package app;

import Domain.Value;

public class SymElement {
    String name;
    Value value;

    public SymElement(String name, Value value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }
}
