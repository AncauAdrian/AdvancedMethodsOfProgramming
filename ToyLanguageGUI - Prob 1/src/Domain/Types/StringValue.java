package Domain.Types;

import Domain.Value;

public class StringValue implements Value {
    private String val;
    public StringValue(String v){val=v;}

    public String getVal() {return val;}
    public String toString() { return val; }
    public Type getType() { return new StringType();}
    public boolean equals(Object another){
        if (another == this)
            return true;

        if (!(another instanceof StringValue)) {
            return false;
        }

        StringValue a = (StringValue) another;

        return a.val.compareTo(this.val) == 0;
    }
}
