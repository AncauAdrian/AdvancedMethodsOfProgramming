package View;

import Domain.*;
import Domain.Statements.IStmt;
import Domain.Types.Type;
import Domain.Utils.MyDict;
import Domain.Utils.MyIDictionary;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo)
    {
        this.repo = repo;
    }


    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void initialiseExecutor()
    {
        executor = Executors.newFixedThreadPool(2);
    }

    public void shutdownExecutor()
    {
        executor.shutdownNow();
    }

    public void oneStepForAllPrg(List<PrgState> prgList)
    {
        prgList.forEach(prg ->repo.logPrgState(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newPrgList;
        List<Future<PrgState>> futures;
        try {
            futures = executor.invokeAll(callList);
            newPrgList = futures.stream()
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.toString());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());


        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        prgList.addAll(newPrgList);
        prgList.forEach(prg ->repo.logPrgState(prg));
    }

    /*Map<Integer, Value> newHeap = PrgState.garbageCollector(PrgState.getAddrFromSymTable(cur.getSymTable().getCollection()),
            cur.getHeap().getContent());
                cur.getHeap().setContent(newHeap);*/

    public void allStep() {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            Map<Integer, Value> newHeap = PrgState.garbageCollector(PrgState.getAllAddrFromSymTable(prgList),
                    prgList.get(0).getHeap().getContent());
            prgList.get(0).getHeap().setContent(newHeap);

            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(prgList);
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public static void typecheck(IStmt stmt) throws MyException
    {
        MyIDictionary<String, Type> typeEnv= new MyDict<String, Type>();

        stmt.typecheck(typeEnv);

    }
}
