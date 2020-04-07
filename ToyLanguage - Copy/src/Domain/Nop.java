package Domain;

import Domain.Statements.IStmt;

public class Nop implements IStmt {
    public Nop() {}

    @Override
    public String toString() {return "NOP;";}

    public PrgState execute(PrgState state) throws MyException{
        return state;
    }
}
