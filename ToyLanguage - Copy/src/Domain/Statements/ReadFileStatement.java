package Domain.Statements;

import Domain.Expressions.Exp;
import Domain.Expressions.VarExp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.IntType;
import Domain.Types.IntValue;
import Domain.Types.StringType;
import Domain.Types.StringValue;
import Domain.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ReadFileStatement implements IStmt{
    private Exp path;
    private String var;

    public ReadFileStatement(Exp a, String var) { this.path = a; this.var = var; };

    @Override
    public String toString() {
        return "readFile(" + path.toString() + ", " + var.toString() + ");";
    }

    public PrgState execute(PrgState state) throws MyException {
        Value evaluation = new VarExp(this.var).eval(state.getSymTable(), state.getHeap());

        if(!(evaluation.getType().equals(new IntType())))
            throw new MyException("The variable must be an INT!");

        IntValue i = (IntValue) evaluation;

        evaluation = this.path.eval(state.getSymTable(), state.getHeap());

        if(!(evaluation.getType().equals(new StringType())))
            throw new MyException("Path name must be a string ");

        StringValue str = (StringValue) evaluation;

        if(!state.getFileTable().contains(str.getVal()))
            throw new MyException("That file is not opened yet!");

        BufferedReader rdr = state.getFileTable().get(str.getVal());

        try
        {
            String line = rdr.readLine();

            IntValue readInt = new IntValue(0);

            if (!line.isEmpty())
                readInt = new IntValue(Integer.parseInt(line));

            state.getSymTable().set(this.var, readInt);
        }
        catch (IOException e)
        {
            throw new MyException(e.toString());
        }


        return state;
    }
}