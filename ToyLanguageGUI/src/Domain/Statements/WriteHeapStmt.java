package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.*;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

public class WriteHeapStmt implements IStmt {
    private String id;
    private Exp exp;

    public WriteHeapStmt(String id, Exp exp) { this.id = id; this.exp = exp; }

    @Override
    public String toString() {
        return "wH(" + id + ", " + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException
    {
        MyIHeap<Integer, Value> hp = state.getHeap();
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Value evaluation = this.exp.eval(tbl, hp);

        if (!tbl.contains(id))
            throw new MyException("The variable " + id + " doesn't exist");

        if(!(tbl.get(id) instanceof RefValue))
            throw new MyException("The argument must be of type RefValue");

        RefValue ref = ((RefValue) tbl.get(id));

        if(!hp.contains(ref.getAddr()))
            throw new MyException("Address is not a key in the heap!");

        if(!evaluation.getType().equals(ref.getLocationType()))
            throw new MyException("The variable is not the same type as the reference");

        hp.set(ref.getAddr(), evaluation);

        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typevar = typeEnv.get(id);
        Type typexp = exp.typecheck(typeEnv);

        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WRITE HEAP stmt: right hand side and left hand side have different types ");
    }
}
