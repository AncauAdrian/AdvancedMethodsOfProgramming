package Domain.Expressions;

import Domain.MyException;
import Domain.Types.Type;
import Domain.Utils.MyIDictionary;
import Domain.Utils.MyIHeap;
import Domain.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}