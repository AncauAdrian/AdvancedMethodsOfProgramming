package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.BoolValue;
import Domain.Value;

public class WhileStmt implements IStmt {
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) { this.exp = exp; this.stmt = stmt; }

    @Override
    public String toString() {
        return "while(" + exp.toString() + ") { " + stmt.toString() + " }";
    }

    public PrgState execute(PrgState state) throws MyException
    {
        Value evaluation = exp.eval(state.getSymTable(), state.getHeap());

        if (! (evaluation instanceof BoolValue))
            throw new MyException("Expression must evaluate to a Bool Value!");

        BoolValue res = (BoolValue) evaluation;

        if(!res.getVal())
            return state;

        state.getStk().push(this);
        state.getStk().push(this.stmt);

        return state;
    }
}
