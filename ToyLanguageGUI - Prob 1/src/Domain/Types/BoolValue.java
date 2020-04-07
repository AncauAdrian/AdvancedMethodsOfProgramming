package Domain.Types;

import Domain.Value;

public class BoolValue implements Value {
    private boolean val;
    public BoolValue(boolean v){val=v;}

    public boolean getVal() {return val;}
    public String toString() { return Boolean.toString(val); }
    public Type getType() { return new BoolType();}
    public boolean equals(Object another){
        if (another == this)
            return true;

        if (!(another instanceof BoolValue)) {
            return false;
        }

        BoolValue a = (BoolValue) another;

        return a.val == this.val;
    }
}
