package Domain.Utils;

import Domain.Statements.CompStmt;
import Domain.Statements.IStmt;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T>{
    T pop();
    T top();
    void push(T v);
    boolean isEmpty();

    static String infix(MyIStack<IStmt> a) {
        if (a.isEmpty())
            return "";

        IStmt first = a.pop();

        if (a.isEmpty())
        {
            if (first instanceof CompStmt) {
                a.push(((CompStmt) first).getSecond());
                a.push(((CompStmt) first).getFirst());

                return infix(a);
            } else return first.toString() + '\n';
        }

        IStmt snd = a.pop();

        if(snd instanceof CompStmt) {
            CompStmt second = (CompStmt) snd;

            a.push(second.getSecond());
            a.push(second.getFirst());

            return first.toString() + "\n" + infix(a);
        }
        else return first.toString() + "\n" + snd.toString() + "\n";
    }

}
