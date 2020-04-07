package Domain;

import Domain.Statements.IStmt;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;

public class Nop implements IStmt {
    public Nop() {}

    @Override
    public String toString() {return "NOP;";}

    public PrgState execute(PrgState state) throws MyException{
        return state;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return typeEnv;
    }
}
