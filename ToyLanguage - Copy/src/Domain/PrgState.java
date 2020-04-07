package Domain;

import Domain.Statements.IStmt;
import Domain.Statements.NewStmt;
import Domain.Types.RefValue;
import Domain.Utils.*;

import java.io.BufferedReader;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;

    public PrgState(MyIStack<IStmt> stack) {
        this.exeStack = stack;
        this.symTable = new MyDict<String, Value>();
        this.out = new MyList<Value>();
        this.fileTable = new MyDict<String, BufferedReader>();
        this.heap = new MyHeap<Integer, Value>();
    }

    public MyIStack<IStmt> getStk() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return this.symTable;
    }

    public MyIList<Value> getOut() {
        return this.out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIHeap<Integer, Value> getHeap() {
        return this.heap;
    }

    public String formatLog()
    {

        MyIStack<IStmt> copy = new MyStack<IStmt>((MyStack<IStmt>) exeStack);
        String stack = MyIStack.infix(copy);

        String _symTable = this.symTable.toString();

        String _out = this.out.toString();

        String _fileTable = this.fileTable.toString();

        String _heap = this.heap.toString();

        String delimiter = "-";
        delimiter = delimiter.repeat(30);

        return delimiter + "\nStack:\n" + stack + delimiter +
                "\nSymTable:\n" + _symTable + delimiter +
                "\nOut:\n" + _out + delimiter +
                "\nFile Table:\n" + _fileTable + delimiter +
                "\nHeap:\n" + _heap + delimiter + "\n\n";
    }

    public String toString() {
        return "ExeStack: " + exeStack.toString() + "\nSymTable: " + symTable.toString() + "\nOut: " + out.toString()
                + "\nHeap: " + heap.toString();
        //return this.formatLog();
    }

    public static Map<Integer,Value> garbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap)
    {
        Map<Integer, Value> newHeap = new HashMap<Integer, Value>();

        for(int addr : symTableAddr)
        {
            if(addr == 0)
                continue;

            Value next = heap.get(addr);
            int ad = addr;
            while (!newHeap.containsKey(ad))
            {
                newHeap.put(ad, next);
                if(!(next instanceof RefValue))
                {
                    //newHeap.put(ad, next);
                    break;
                }

                ad = ((RefValue) next).getAddr();
                next = heap.get(ad);
            }

        }
        NewStmt.nextFree = 0;
        return newHeap;
    }

    public static List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

}
