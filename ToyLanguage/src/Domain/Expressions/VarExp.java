package Domain.Expressions;

import Domain.MyException;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

public class VarExp implements Exp {
    private String id;

    public VarExp(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return id;
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        if(tbl.contains(id))
        {
            return tbl.get(id);
        }
        else throw new MyException("Variable " + id + " is not defined!");
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return typeEnv.get(this.id);
    }
}