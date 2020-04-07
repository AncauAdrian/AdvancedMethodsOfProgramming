package Domain.Statements;

import Domain.*;
import Domain.Expressions.Exp;
import Domain.Types.BoolType;
import Domain.Types.BoolValue;
import Domain.Types.Type;
import Domain.Utils.MyDict;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIStack;

public class IfStmt implements IStmt{
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}
    public String toString(){ return "IF("+ exp.toString()+") THEN(" +thenS.toString()
            +")ELSE("+elseS.toString()+")";}

    public PrgState execute(PrgState state) throws MyException {
        Value cond = exp.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getStk();
        if(cond.getType() instanceof BoolType)
        {
            BoolValue v = (BoolValue)cond;
            if (v.getVal())
            {
                stack.push(thenS);
            }
            else stack.push(elseS);
        }
        else throw new MyException("conditional expr is not a boolean");

        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(new MyDict<String, Type>((MyDict<String, Type>) typeEnv));
            elseS.typecheck(new MyDict<String, Type>((MyDict<String, Type>) typeEnv));
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}