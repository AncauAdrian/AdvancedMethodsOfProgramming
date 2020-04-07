package Domain.Statements;

import Domain.*;
import Domain.Types.*;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIStack;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type typ;

    public VarDeclStmt(String name, Type typ)
    {
        this.name = name;
        this.typ = typ;
    }

    public String toString() {
        return typ.toString() + " " + name + ";";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();

        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if(symTbl.contains(name)) {
            throw new MyException("a variable with the name " + name + " is already defined");
        }

        Value val = typ.defaultValue();
        symTbl.add(name, val);

        return state;
    }
}
