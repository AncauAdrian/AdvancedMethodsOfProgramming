package Domain;

import Domain.Expressions.ArithExp;
import Domain.Expressions.ReadHeapExp;
import Domain.Expressions.ValueExp;
import Domain.Expressions.VarExp;
import Domain.Statements.*;
import Domain.Types.*;
import Domain.Utils.*;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;

    private static int nextid = 0;
    private int id;

    public PrgState(MyIStack<IStmt> stack) {
        this.exeStack = stack;
        this.symTable = new MyDict<String, Value>();
        this.out = new MyList<Value>();
        this.fileTable = new MyDict<String, BufferedReader>();
        this.heap = new MyHeap<Integer, Value>();
        this.id = nextid;
        PrgState.nextid++;
    }

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTable, MyIList<Value> out,
                    MyIDictionary<String, BufferedReader> fileTable, MyIHeap<Integer, Value> heap) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = nextid;
        PrgState.nextid++;
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

    public Boolean isNotCompleted() { return !exeStack.isEmpty(); };

    public PrgState oneStep() throws MyException{
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
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

        return delimiter + "\nID = " + this.id +
                "\nStack:\n" + stack + delimiter +
                "\nSymTable:\n" + _symTable + delimiter +
                "\nOut:\n" + _out + delimiter +
                "\nFile Table:\n" + _fileTable + delimiter +
                "\nHeap:\n" + _heap + delimiter + "\n\n";
    }

    public String toString() {
        /*return "ExeStack: " + exeStack.toString() + "\nSymTable: " + symTable.toString() + "\nOut: " + out.toString()
                + "\nHeap: " + heap.toString();*/

        MyIStack<IStmt> copy = new MyStack<IStmt>((MyStack<IStmt>) exeStack);
        return "ID = " + this.id + "\n" + MyIStack.infix(copy);

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

    public static List<Integer> getAllAddrFromSymTable(List<PrgState> prgStateList)
    {
        List<Integer> all = new ArrayList<Integer>();

        for (PrgState p : prgStateList) {
            List<Integer> individual = getAddrFromSymTable(p.getSymTable().getCollection());

            for(int a : individual)
                if(!all.contains(a))
                    all.add(a);
        }

        return all;
    }

    public static List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public static List<IStmt> createExamples()
    {
        List<IStmt> states = new ArrayList<IStmt>();

        IStmt ex7 = concatStatements(new VarDeclStmt("a", new IntType()),
                new VarDeclStmt("b", new IntType()),
                new VarDeclStmt("c", new IntType()),
                new AssignStmt("a", new ValueExp(new IntValue(1))),
                new AssignStmt("b", new ValueExp(new IntValue(2))),
                new AssignStmt("c", new ValueExp(new IntValue(5))),
                new SwitchStmt(new ArithExp(3, new VarExp("a"), new ValueExp(new IntValue(10))),
                        new ArithExp(3, new VarExp("b"), new VarExp("c")),
                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                        new ValueExp(new IntValue(10)),
                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                        new PrintStmt(new ValueExp(new IntValue(300)))),
                new PrintStmt(new ValueExp(new IntValue(300))));

        states.add(ex7);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))), a = v, print(rH(rH(a)))
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new CompStmt(new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))),
                                                        new CompStmt(new WriteHeapStmt("a", new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))))));



        states.add(ex8);

        IStmt ex9 = concatStatements(new VarDeclStmt("v", new IntType()),
                new VarDeclStmt("a", new RefType(new IntType())),
                new AssignStmt("v", new ValueExp(new IntValue(10))),
                new NewStmt("a", new ValueExp(new IntValue(22))),
                new ForkStmt(concatStatements(
                        new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                        new PrintStmt(new VarExp("v")),
                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))),
                new PrintStmt(new VarExp("v")),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))));


        states.add(ex9);


        IStmt ex10 = concatStatements(new VarDeclStmt("v", new IntType()),
                new VarDeclStmt("a", new RefType(new IntType())),
                new AssignStmt("v", new ValueExp(new IntValue(10))),
                new NewStmt("a", new ValueExp(new IntValue(22))),
                new ForkStmt(concatStatements(
                        new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                        new ForkStmt(concatStatements(
                                new AssignStmt("v", new ValueExp(new IntValue(60))),
                                new PrintStmt(new VarExp("v")),
                                new ForkStmt(concatStatements(
                                        new AssignStmt("v", new ValueExp(new IntValue(90))),
                                        new PrintStmt(new VarExp("v")),
                                        new WriteHeapStmt("a", new ValueExp(new IntValue(99)))
                                ))
                        )),
                        new ForkStmt(concatStatements(
                                new AssignStmt("v", new ValueExp(new IntValue(1000))),
                                new PrintStmt(new VarExp("v")))
                        ),
                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                        new PrintStmt(new VarExp("v")),
                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))),
                new PrintStmt(new VarExp("v")),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))));


        states.add(ex10);

        return states;

    }

    private static IStmt concatStatements(IStmt... statements)
    {
        if(statements.length == 1)
            return statements[0];

        return new CompStmt(statements[0], concatStatements(Arrays.copyOfRange(statements, 1, statements.length)));
    }

}
