package Domain.Statements;

import Domain.MyException;
import Domain.PrgState;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;

public interface IStmt{
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;

}
