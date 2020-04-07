package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.IntType;
import Domain.Types.IntValue;
import Domain.Types.Type;
import Domain.Utils.MyDict;
import Domain.Utils.MyIDictionary;
import Domain.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class createSemaphore implements IStmt {

    private String var;
    private Exp expression;
    private static Lock lock = new ReentrantLock();
    private static int newFreeLocation = 0;

    public createSemaphore(String var, Exp expression){
        this.var = var;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + ", " + this.expression + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIDictionary<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        Value value = expression.eval(symbolTable, state.getHeap());


        if (!value.getType().equals(new IntType()))
            throw new MyException("The result of the evaluation is not an int");

        int res = ((IntValue)value).getVal();

        semaphoreTable.add(newFreeLocation, new Pair<Integer, List<Integer>>(res, new ArrayList<>()));
        symbolTable.add(var, new IntValue(newFreeLocation));
        newFreeLocation++;

        lock.unlock();
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ1;
        typ1 = this.expression.typecheck(typeEnv);

        if(!typ1.equals(new IntType()))
            throw new MyException("The expression must eval to integer type");

        typ1 = typeEnv.get(this.var);

        if(!typ1.equals(new IntType()))
            throw new MyException("The var must be of integer type");

        return typeEnv;
    }
}
