package View;

import Domain.*;
import Domain.Statements.IStmt;
import Domain.Utils.MyIStack;

import java.util.Collection;
import java.util.Map;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo)
    {
        this.repo = repo;
    }

    public String printState()
    {
        return repo.getCrtPrg().toString();
    }

    public PrgState oneStep(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getStk();
        if(stk.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep(){
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyRepoInterface
        //here you can display the prg state
        repo.logPrgState();
        while (!prg.getStk().isEmpty()) {
            try {
                PrgState cur = this.oneStep(prg);
                //System.out.println((this.printState()));
                Map<Integer, Value> newHeap = PrgState.garbageCollector(PrgState.getAddrFromSymTable(cur.getSymTable().getCollection()),
                        cur.getHeap().getContent());
                cur.getHeap().setContent(newHeap);

                repo.logPrgState();
            } catch (MyException e) {
                System.out.println(e.toString());
                break;
            }
            //here you can display the prg state
        }
    }
}
