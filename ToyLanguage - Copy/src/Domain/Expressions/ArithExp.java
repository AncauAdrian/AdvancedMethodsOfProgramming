package Domain.Expressions;

import Domain.Types.BoolValue;
import Domain.Types.IntType;
import Domain.Types.IntValue;
import Domain.MyException;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; //1-plus, 2-minus, 3-star, 4-divide, 5 - <; 6 - <=; 7 - ==; 8 - !=; 9 - >=; 10 - >

    public ArithExp(int op, Exp e1, Exp e2)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        String[] opToString = { "+", "-", "*", "/", "<", "<=", "==", "!=", ">=", ">" };
        return e1.toString() + opToString[op - 1] + e2.toString() + ";";
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp)throws MyException
    {
        if (this.op == 0)
            throw new MyException("Invalid operand");

        Value v1,v2;
        v1= e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new IntValue(n1+n2);
                if (op==2) return new IntValue(n1-n2);
                if(op==3) return new IntValue(n1*n2);
                if(op==4)
                    if(n2==0){ throw new MyException("division by zero");} else return new IntValue(n1/n2);
                if(op==5)
                    return new BoolValue(n1<n2);
                if(op==6)
                    return new BoolValue(n1<=n2);
                if(op==7)
                    return new BoolValue(n1==n2);
                if(op==8)
                    return new BoolValue(n1!=n2);
                if(op==9)
                    return new BoolValue(n1>=n2);
                if(op==10)
                    return new BoolValue(n1>n2);
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");

        return new IntValue(0);
    }
}
