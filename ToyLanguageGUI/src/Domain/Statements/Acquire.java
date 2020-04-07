package Domain.Statements;

import Domain.MyException;
import Domain.PrgState;
import Domain.Types.IntType;
import Domain.Types.IntValue;
import Domain.Types.Type;
import Domain.Utils.MyDict;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIStack;
import Domain.Utils.MyStack;
import Domain.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Acquire implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public Acquire(String var)
    {
        this.var = var;
    }

    @Override
    public String toString() {
        return "Acquire(" + this.var + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        if(!state.getSymTable().contains(var))
            throw new MyException("Aquire: no such variable in sym table");

        Value value = state.getSymTable().get(var);

        if(!value.getType().equals(new IntType()))
            throw new MyException("Aquire: var must be of int type");

        int foundIndex = ((IntValue)value).getVal();

        if(!state.getSemaphoreTable().contains(foundIndex))
            throw new MyException("Aquire: no such key in the semaphor table");

        Pair<Integer, List<Integer>> p = state.getSemaphoreTable().get(foundIndex);

        int N1 = p.getKey();
        int NL = p.getValue().size();

        if(N1 > NL)
        {
            if(!p.getValue().contains(state.getID()))
                p.getValue().add(state.getID());
        }
        else
        {
            state.getStk().push(this);
        }

        lock.unlock();
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ = typeEnv.get(this.var);
        if(!typ.equals(new IntType()))
            throw new MyException("The var must be of integer type");

        return typeEnv;
    }
}
