import Domain.*;
import Domain.Expressions.*;
import Domain.Statements.*;
import Domain.Types.*;
import Domain.Utils.*;
import View.*;

import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        System.out.print("Enter file to save PrgState: ");
        Scanner in = new Scanner(System.in);
        String path = in.next();

        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        MyIStack<IStmt> stck1 = new MyStack<IStmt>();
        stck1.push(ex1);

        PrgState p1 = new PrgState(stck1);
        Repository repo1 = new Repository(p1, path);

        Controller cont1 = new Controller(repo1);


        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),new
                                ArithExp(3,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        MyIStack<IStmt> stck2 = new MyStack<IStmt>();
        stck2.push(ex2);

        PrgState p2 = new PrgState(stck2);
        Repository repo2 = new Repository(p2, path);

        Controller cont2 = new Controller(repo2);


        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new ArithExp(9, new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        MyIStack<IStmt> stck3 = new MyStack<IStmt>();
        stck3.push(ex3);

        PrgState p3 = new PrgState(stck3);
        Repository repo3 = new Repository(p3, path);

        Controller cont3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenFileStatement(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()),
                new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                new CompStmt(new PrintStmt(new VarExp("varc")),
                new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseFileStatement(new VarExp("varf"))))))))));

        MyIStack<IStmt> stck4 = new MyStack<IStmt>();
        stck4.push(ex4);

        PrgState p4 = new PrgState(stck4);
        Repository repo4 = new Repository(p4, path);

        Controller cont4 = new Controller(repo4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenFileStatement(new VarExp("varf")), new OpenFileStatement(new VarExp("varf")))));

        MyIStack<IStmt> stck5 = new MyStack<IStmt>();
        stck5.push(ex5);

        PrgState p5 = new PrgState(stck5);
        Repository repo5 = new Repository(p5, path);

        Controller cont5 = new Controller(repo5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new NewStmt("a", new VarExp("v")),
                new CompStmt(new PrintStmt(new VarExp("v")),
                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))))));

        MyIStack<IStmt> stck6 = new MyStack<IStmt>();
        stck6.push(ex6);

        PrgState p6 = new PrgState(stck6);
        Repository repo6 = new Repository(p6, path);

        Controller cont6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
            new CompStmt(new WhileStmt(new ArithExp(5, new VarExp("v"), new ValueExp(new IntValue(10))),
                    new AssignStmt("v", new ArithExp(1, new VarExp("v"), new ValueExp(new IntValue(1))))),
                    new PrintStmt(new VarExp("v")))));


        MyIStack<IStmt> stck7 = new MyStack<IStmt>();
        stck7.push(ex7);

        PrgState p7 = new PrgState(stck7);
        Repository repo7 = new Repository(p7, path);

        Controller cont7 = new Controller(repo7);


        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))), a = v, print(rH(rH(a)))
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new NewStmt("a", new VarExp("v")),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                new CompStmt(new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))),
                new CompStmt(new WriteHeapStmt("a", new VarExp("v")),
                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))))));


        MyIStack<IStmt> stck8 = new MyStack<IStmt>();
        stck8.push(ex8);

        PrgState p8 = new PrgState(stck8);
        Repository repo8 = new Repository(p8, path);

        Controller cont8 = new Controller(repo8);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),cont1));
        menu.addCommand(new RunExample("2",ex2.toString(),cont2));
        menu.addCommand(new RunExample("3",ex3.toString(),cont3));
        menu.addCommand(new RunExample("4",ex4.toString(),cont4));
        menu.addCommand(new RunExample("5",ex5.toString(),cont5));
        menu.addCommand(new RunExample("6",ex6.toString(),cont6));
        menu.addCommand(new RunExample("7",ex7.toString(),cont7));
        menu.addCommand(new RunExample("8",ex8.toString(),cont8));
        menu.show();


        //cont1.allStep();
        //cont2.allStep();
        //cont3.allStep();
        //cont4.allStep();

        //cont3.printState();

    }
}


/*//int v; v=2;Print(v) is represented as:

        System.out.print("Enter file to save PrgState: ");
        Scanner in = new Scanner(System.in);
        String path = in.next();
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        MyIStack<IStmt> stck1 = new MyStack<IStmt>();
        stck1.push(ex1);

        PrgState p1 = new PrgState(stck1);
        Repository repo1 = new Repository(p1, path);

        Controller cont1 = new Controller(repo1);




        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        MyIStack<IStmt> stck2 = new MyStack<IStmt>();
        stck2.push(ex2);

        PrgState p2 = new PrgState(stck2);
        Repository repo2 = new Repository(p2, path);

        Controller cont2 = new Controller(repo2);


        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        MyIStack<IStmt> stck3 = new MyStack<IStmt>();
        stck3.push(ex3);

        PrgState p3 = new PrgState(stck3);
        Repository repo3 = new Repository(p3, path);

        Controller cont3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenFileStatement(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()),
                new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                new CompStmt(new PrintStmt(new VarExp("varc")),
                new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseFileStatement(new VarExp("varf"))))))))));

        MyIStack<IStmt> stck4 = new MyStack<IStmt>();
        stck4.push(ex4);

        PrgState p4 = new PrgState(stck4);
        Repository repo4 = new Repository(p4, path);

        Controller cont4 = new Controller(repo4);
        //cont1.allStep();
        //cont2.allStep();
        //cont3.allStep();
        cont4.allStep();

        //cont3.printState();*/