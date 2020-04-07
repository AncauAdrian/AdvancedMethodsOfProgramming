package Domain.Expressions;

import Domain.MyException;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

public class ValueExp implements Exp {
    private Value e;

    @Override
    public String toString()
    {
        return e.toString();
    }

    public ValueExp(Value e) {
        this.e = e;
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {return e;}
}
