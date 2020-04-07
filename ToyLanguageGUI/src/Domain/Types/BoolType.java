package Domain.Types;


import Domain.Value;

public class BoolType implements Type {
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() { return "bool";}

    public Value defaultValue()
    {
        return new BoolValue(false);
    }

}
