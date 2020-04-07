package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.StringType;
import Domain.Types.StringValue;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStmt {
    private Exp path;

    public CloseFileStatement(Exp a) { this.path = a; }

    @Override
    public String toString() {
        return "closeRFile(" + path.toString() + ");";
    }

    public PrgState execute(PrgState state) throws MyException {
        Value evaluation = path.eval(state.getSymTable(), state.getHeap());
        if(!(evaluation.getType().equals(new StringType())))
            throw new MyException("Path name must be a string ");

        StringValue str = (StringValue) evaluation;

        if(!state.getFileTable().contains(str.getVal()))
            throw new MyException("That path does not exist!");

        BufferedReader rdr = state.getFileTable().get(str.getVal());
        try
        {
            rdr.close();
            state.getFileTable().remove(str.getVal());
        }
        catch (IOException e)
        {
            throw new MyException(e.toString());
        }

        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type t = path.typecheck(typeEnv);

        if(!t.equals(new StringType()))
            throw new MyException("The path is not a string value!");

        return typeEnv;
    }
}
