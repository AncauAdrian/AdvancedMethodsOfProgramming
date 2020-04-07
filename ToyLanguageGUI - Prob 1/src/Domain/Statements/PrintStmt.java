package Domain.Statements;

import Domain.*;
import Domain.Expressions.Exp;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIList;

public class PrintStmt implements IStmt{
    private Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    public String toString(){ return "print(" +exp.toString()+");"; }
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> o = state.getOut();
        o.addToEnd(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        exp.typecheck(typeEnv);
        return typeEnv;
    }

}
