package Domain.Statements;

import Domain.Expressions.ArithExp;
import Domain.Expressions.Exp;
import Domain.MyException;
import Domain.PrgState;
import Domain.Types.*;
import Domain.Utils.MyIDictionary;
import Domain.Value;

public class SwitchStmt implements IStmt {

    Exp exp, exp1, exp2;
    IStmt stmt1, stmt2, def;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt def)
    {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.def = def;
    }

    @Override
    public String toString() {
        return "SwitchStmt(" +
                "case " + exp +
                ": " + stmt1 +
                "; case " + exp2 +
                ": " + stmt2 +
                "; default: " + def +
                ')';
    }

    public PrgState execute(PrgState state) throws MyException{
        /*Value cond = exp.eval(state.getSymTable(), state.getHeap());
        Value cond1;

        if(cond.getType().equals(new IntType())) {
            int res = ((IntValue) cond).getVal();

            // evaluate exp1 and see if exp == exp1
            cond1 = exp1.eval(state.getSymTable(), state.getHeap());
            if(cond1.getType().equals(new IntType())) {
                int resExp = ((IntValue) cond1).getVal();

                if(resExp == res)
                    state.getStk().push(this.stmt1);
            }

        }*/


        IStmt first = new IfStmt(new ArithExp(7,this.exp, this.exp2), this.stmt2, this.def);
        IStmt second = new IfStmt(new ArithExp(7,this.exp, this.exp1), this.stmt1, first);

        state.getStk().push(second);

        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException {

        Type typ1, typ2, typ3;
        typ1=exp.typecheck(typeEnv);
        typ2=exp1.typecheck(typeEnv);
        typ3=exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                if(typ3.equals(new IntType())) {
                    this.def.typecheck(typeEnv);
                    this.stmt1.typecheck(typeEnv);
                    this.stmt2.typecheck(typeEnv);

                    return typeEnv;
                } else throw new MyException("third operand is not an integer");
            } else throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}
