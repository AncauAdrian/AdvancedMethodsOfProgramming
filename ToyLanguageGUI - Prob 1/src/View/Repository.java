package View;

import Domain.*;

import java.io.*;
import java.util.*;

interface IRepository {
    void logPrgState(PrgState log);
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

    public List<PrgState> getPrgList()
    {
        return this.states;
    }

    public void setPrgList(List<PrgState> newList)
    {
        this.states = newList;
    }

    public void logPrgState(PrgState log){
        try (PrintWriter test = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            test.write(log.formatLog());
            System.out.println("written PrgState to log file");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
