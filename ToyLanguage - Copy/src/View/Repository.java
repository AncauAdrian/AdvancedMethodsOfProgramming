package View;

import Domain.*;

import java.io.*;
import java.util.*;

interface IRepository {
    PrgState getCrtPrg();
    void logPrgState();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
}

public class Repository implements IRepository{
    private List<PrgState> states = new ArrayList<PrgState>();
    private String logFilePath;

    public Repository(PrgState initial, String logFilePath)
    {
        this.states.add(initial);
        this.logFilePath = logFilePath;
    }

    public PrgState getCrtPrg() {
        return states.get(0);
    }

    public List<PrgState> getPrgList()
    {
        return this.states;
    }

    public void setPrgList(List<PrgState> newList)
    {
        this.states = newList;
    }

    public void logPrgState(){
        try (PrintWriter test = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            test.write(this.getCrtPrg().formatLog());
            System.out.println("written PrgState to log file");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
