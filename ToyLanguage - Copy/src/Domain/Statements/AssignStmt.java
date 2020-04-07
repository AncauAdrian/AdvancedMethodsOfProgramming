package Domain.Statements;

import Domain.*;
import Domain.Expressions.Exp;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIStack;


public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value val = exp.eval(symTbl, state.getHeap());
        if (symTbl.contains(id)) {
            Type typId = (symTbl.get(id)).getType();
            if ((val.getType()).equals(typId)) {
                symTbl.set(id, val);
            } else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
            return state;
        } else throw new MyException("the used variable " + id + " was not declared before");
    }
}