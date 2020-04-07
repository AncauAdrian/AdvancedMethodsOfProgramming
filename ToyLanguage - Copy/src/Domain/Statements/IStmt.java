package Domain.Statements;

import Domain.MyException;
import Domain.PrgState;

public interface IStmt{
    PrgState execute(PrgState state) throws MyException;
}
