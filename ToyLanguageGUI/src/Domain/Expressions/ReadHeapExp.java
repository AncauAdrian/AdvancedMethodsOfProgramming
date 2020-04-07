package Domain.Expressions;

import Domain.MyException;
import Domain.Types.RefType;
import Domain.Types.RefValue;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

public class ReadHeapExp implements Exp {
    private Exp exp;

    public ReadHeapExp(Exp exp) { this.exp = exp; }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value evaluation = this.exp.eval(tbl, hp);

        if(!(evaluation instanceof RefValue))
            throw new MyException("The argument must be of type RefValue");

        int address = ((RefValue) evaluation).getAddr();

        if(! hp.contains(address))
            throw new MyException("Address is not a key in the heap!");

        return hp.get(address);
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ = exp.typecheck(typeEnv);

        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }
}
