package Domain.Statements;

import Domain.*;
import Domain.Expressions.Exp;
import Domain.Types.BoolType;
import Domain.Types.BoolValue;
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

        return state;
    }
}