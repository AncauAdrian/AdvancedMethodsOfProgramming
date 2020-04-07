package Domain.Statements;

import Domain.MyException;
import Domain.Utils.MyIStack;
import Domain.PrgState;

import java.time.chrono.IsoChronology;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt f, IStmt s) {
        this.first = f;
        this.snd = s;
    }

    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return state;
    }

    public IStmt getFirst() { return this.first; }

    public IStmt getSecond() { return this.snd; }
}
