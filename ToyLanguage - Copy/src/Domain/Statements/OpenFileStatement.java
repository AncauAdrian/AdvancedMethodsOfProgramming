package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.StringType;
import Domain.Types.StringValue;
import Domain.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OpenFileStatement implements IStmt{
    private Exp path;

    public OpenFileStatement(Exp a) { this.path = a; };

    @Override
    public String toString() {
        return "openRFile(" + path.toString() + ");";
    }

    public PrgState execute(PrgState state) throws MyException {
        Value evaluation = path.eval(state.getSymTable(), state.getHeap());
        if(!(evaluation.getType().equals(new StringType())))
            throw new MyException("Path name must be a string ");

        StringValue str = (StringValue) evaluation;

        if(state.getFileTable().contains(str.getVal()))
            throw new MyException("That file is already opened!");

        try
        {
            BufferedReader wr = new BufferedReader(new FileReader(str.getVal()));
            state.getFileTable().add(str.getVal(), wr);
        }
        catch (IOException e)
        {
            throw new MyException("Error opening file with the path: " + str.getVal());
        }

        return state;
    }
}
