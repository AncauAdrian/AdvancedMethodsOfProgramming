package Domain.Types;

import Domain.Value;

public class RefValue implements Value {
    private int address;
    private Type locationType;
    public RefValue(int addr, Type locationType) { this.address = addr; this.locationType = locationType;}
    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}
    public Type getLocationType() {return locationType; }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}
