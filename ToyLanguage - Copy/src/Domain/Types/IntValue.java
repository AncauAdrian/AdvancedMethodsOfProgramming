package Domain.Types;

import Domain.Value;

public class IntValue implements Value {
    private int val;
    public IntValue(int v){val=v;}

    public int getVal() {return val;}
    public String toString() { return Integer.toString(val); }
    public Type getType() { return new IntType();}
    public boolean equals(Object another){
        if (another == this)
            return true;

        if (!(another instanceof IntValue)) {
            return false;
        }

        IntValue a = (IntValue) another;

        return a.val == this.val;
    }
}