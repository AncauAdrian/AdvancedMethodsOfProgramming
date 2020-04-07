package Domain.Expressions;

import Domain.Types.BoolType;
import Domain.Types.BoolValue;
import Domain.MyException;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; // 1 - and; 2 - or;

    LogicExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString()
    {
        String[] opToString = { "AND", "OR" };
        return e1.toString() + " " + opToString[op] + " " + e2.toString();
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException {
        Value nr1 = e1.eval(tbl, hp);
        if(nr1.getType() instanceof BoolValue) {
            Value nr2 = e2.eval(tbl, hp);
            if(nr2.getType() instanceof BoolValue) {
                BoolValue a = (BoolValue)nr1;
                BoolValue b = (BoolValue)nr2;

                if(op == 1)
                {
                    return new BoolValue(a.getVal() && b.getVal());
                }
                else if(op == 2) {
                    return new BoolValue(a.getVal() || b.getVal());
                }
                else throw new MyException("Invalid op parameter at logic exp");
            }
            else throw new MyException("Operator 2 is not boolean!");
        } else throw new MyException("Operator 1 is not boolean!");
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ1, typ2;

        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);

        if (typ1.equals(new BoolType()))
        {
            if(typ2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else throw new MyException("Second operand in Logic Expression is not Boolean");
        }
        else throw new MyException("First operand in Logic Expression is not Boolean");
    }
}
