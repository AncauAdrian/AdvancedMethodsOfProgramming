package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.RefType;
import Domain.Types.RefValue;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Value;

public class NewStmt implements IStmt {
    private String id;
    private Exp value;
    public static int nextFree = 1;
    public NewStmt(String id, Exp value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "new(" + id + ", " + value.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String, Value> table = state.getSymTable();
        if (!table.contains(id))
            throw new MyException("The variable " + id + " doesn't exist");

        if(!(table.get(id).getType() instanceof RefType))
            throw new MyException("The variable " + id + " must be a Ref type");

        RefValue ref = (RefValue) table.get(id);
        Value evaluation = value.eval(table, state.getHeap());

        if(!evaluation.getType().equals(ref.getLocationType()))
            throw new MyException("The variable is not the same type as the reference");

        if(nextFree == 0)
        {
            nextFree++;
            while (state.getHeap().contains(nextFree))
                nextFree++;
        }

        state.getHeap().add(nextFree, evaluation);
        table.set(id, new RefValue(nextFree, evaluation.getType()));

        nextFree++;
        while (state.getHeap().contains(nextFree))
            nextFree++;

        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        Type typevar = typeEnv.get(id);
        Type typexp = value.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

}
