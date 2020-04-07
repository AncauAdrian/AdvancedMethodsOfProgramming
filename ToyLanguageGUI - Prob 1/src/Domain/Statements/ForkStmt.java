package Domain.Statements;

import Domain.MyException;
import Domain.PrgState;
import Domain.Types.Type;
import Domain.Utils.MyDict;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIStack;
import Domain.Utils.MyStack;
import Domain.Value;

import java.util.Dictionary;

public class ForkStmt implements IStmt {
    private IStmt stmt;

    public ForkStmt(IStmt stmt) { this.stmt = stmt; }

    @Override
    public String toString() {
        return "Fork(" + stmt.toString() + ");";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        stack.push(this.stmt);

        MyIDictionary<String, Value> symTable = new MyDict<String, Value>((MyDict<String, Value>) state.getSymTable());
        return new PrgState(stack, symTable, state.getOut(), state.getFileTable(), state.getHeap());
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{

        stmt.typecheck(new MyDict<String, Type>((MyDict<String, Type>) typeEnv));

        return typeEnv;
    }
}
