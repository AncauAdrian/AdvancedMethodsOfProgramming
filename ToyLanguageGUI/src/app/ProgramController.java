package app;


import Domain.PrgState;
import Domain.Statements.IStmt;
import Domain.Utils.MyIStack;
import Domain.Utils.MyStack;
import Domain.Value;
import View.Controller;
import View.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ProgramController {
    @FXML
    private TableView<HeapElement> tableviewHeap;

    @FXML
    private TableColumn<HeapElement, Integer> columnHeapAddress;

    @FXML
    private TableColumn<HeapElement, Value> columnHeapValue;

    @FXML
    private ListView<String> listviewFileTable;

    @FXML
    private TextField textfieldNrStates;

    @FXML
    private ListView<PrgState> listviewSelectedPrg;

    @FXML
    private TableView<SymElement> tableviewSymTable;

    @FXML
    private TableColumn<SymElement, String> columnSymName;

    @FXML
    private TableColumn<SymElement, Value> columnSymValue;

    @FXML
    private ListView<String> listviewExeStack;

    @FXML
    private ListView<Value> listviewOut;

    @FXML
    private TableView<SemElement> tableviewSem;

    @FXML
    private TableColumn<SemElement, Integer> columnIndex;

    @FXML
    private TableColumn<SemElement, Integer> columnValue;

    @FXML
    private TableColumn<SemElement, String> columnThreads;

    @FXML
    private Button buttonOneStep;

    private PrgState initialState;
    private Repository repo;
    private Controller cont;

    ProgramController(PrgState initialState) {
        this.initialState = initialState;
        this.repo = new Repository(initialState, "log.txt");
        this.cont = new Controller(this.repo);

        this.cont.initialiseExecutor();
    }

    @FXML
    public void initialize() {
        assert tableviewHeap != null : "fx:id=\"tableviewHeap\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert columnHeapAddress != null : "fx:id=\"columnHeapAddress\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert columnHeapValue != null : "fx:id=\"columnHeapValue\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert listviewFileTable != null : "fx:id=\"listviewFileTable\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert textfieldNrStates != null : "fx:id=\"textfieldNrStates\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert listviewSelectedPrg != null : "fx:id=\"listviewSelectedPrg\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert tableviewSymTable != null : "fx:id=\"tableviewSymTable\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert columnSymName != null : "fx:id=\"columnSymName\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert columnSymValue != null : "fx:id=\"columnSymValue\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert listviewExeStack != null : "fx:id=\"listviewExeStack\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert listviewOut != null : "fx:id=\"listviewOut\" was not injected: check your FXML file 'ProgramWindow.fxml'.";
        assert buttonOneStep != null : "fx:id=\"buttonOneStep\" was not injected: check your FXML file 'ProgramWindow.fxml'.";

        this.columnHeapAddress.setCellValueFactory(new PropertyValueFactory<HeapElement, Integer>("address"));
        this.columnHeapValue.setCellValueFactory(new PropertyValueFactory<HeapElement, Value>("value"));

        this.columnSymName.setCellValueFactory(new PropertyValueFactory<SymElement, String>("name"));
        this.columnSymValue.setCellValueFactory(new PropertyValueFactory<SymElement, Value>("value"));

        this.columnIndex.setCellValueFactory(new PropertyValueFactory<SemElement, Integer>("index"));
        this.columnValue.setCellValueFactory(new PropertyValueFactory<SemElement, Integer>("value"));
        this.columnThreads.setCellValueFactory(new PropertyValueFactory<SemElement, String>("threads"));

        refreshSelected();

        this.listviewSelectedPrg.getSelectionModel().selectIndices(0);
        this.listviewSelectedPrg.getFocusModel().focus(0);

        refreshExeStack();
        refreshFileTable();
        refreshOut();
        refreshNrStates();
        refreshHeap();
        refreshSymTable();
        refreshSemTable();
    }

    @FXML
    void runOneStep(MouseEvent event) {

        List<PrgState> prgList = repo.getPrgList();
        if(prgList.size() == 0) {
            cont.shutdownExecutor();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There are no longer any available steps");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();
            return;
        }

        Map<Integer, Value> newHeap = PrgState.garbageCollector(PrgState.getAllAddrFromSymTable(prgList),
                prgList.get(0).getHeap().getContent());
        prgList.get(0).getHeap().setContent(newHeap);

        cont.oneStepForAllPrg(prgList);

        repo.setPrgList(prgList);

        refreshSelected();

        this.listviewSelectedPrg.getSelectionModel().selectIndices(0);
        this.listviewSelectedPrg.getFocusModel().focus(0);

        refreshExeStack();
        refreshFileTable();
        refreshOut();
        refreshNrStates();
        refreshHeap();
        refreshSymTable();
        refreshSemTable();

        prgList = cont.removeCompletedPrg(prgList);
        repo.setPrgList(prgList);
    }

    @FXML
    void selectionChanged(MouseEvent event) {
        refreshExeStack();
        refreshFileTable();
        refreshOut();
        refreshNrStates();
    }

    private void refreshSelected() {
        listviewSelectedPrg.getItems().clear();

        for (PrgState s : this.repo.getPrgList()) {
            listviewSelectedPrg.getItems().add(s);
        }
    }

    private void refreshNrStates() {
        textfieldNrStates.setText(Integer.toString(this.repo.getPrgList().size()));
    }

    private void refreshFileTable() {
        listviewFileTable.getItems().clear();

        for (String path : this.getSelectedPrg().getFileTable().getContent().keySet()) {
            listviewFileTable.getItems().add(path);
        }
    }

    private void refreshExeStack() {
        this.listviewExeStack.getItems().clear();

        MyIStack<IStmt> copy = new MyStack<IStmt>((MyStack<IStmt>) this.getSelectedPrg().getStk());
        String stack = MyIStack.infix(copy);

        StringTokenizer multiTokenizer = new StringTokenizer(stack, "\n");

        while (multiTokenizer.hasMoreTokens()) {
            this.listviewExeStack.getItems().add(multiTokenizer.nextToken());
        }
    }

    private void refreshOut()
    {
        this.listviewOut.getItems().clear();

        for(Value a : this.getSelectedPrg().getOut().getContent())
        {
            this.listviewOut.getItems().add(a);
        }
    }

    private void refreshHeap()
    {
        this.tableviewHeap.getItems().clear();

        for(int addr : this.getSelectedPrg().getHeap().getContent().keySet())
        {
            this.tableviewHeap.getItems().add(new HeapElement(addr, this.getSelectedPrg().getHeap().get(addr)));
        }

    }

    private void refreshSemTable()
    {
        tableviewSem.getItems().clear();

        for(int index : this.getSelectedPrg().getSemaphoreTable().getContent().keySet())
        {
            Pair<Integer, List<Integer>> p = this.getSelectedPrg().getSemaphoreTable().get(index);
            this.tableviewSem.getItems().add(new SemElement(index, p.getKey(), p.getValue().toString()));
        }
    }

    private void refreshSymTable()
    {
        this.tableviewSymTable.getItems().clear();

        for(String name : this.getSelectedPrg().getSymTable().getContent().keySet())
        {
            this.tableviewSymTable.getItems().add(new SymElement(name, this.getSelectedPrg().getSymTable().get(name)));
        }

    }

    private PrgState getSelectedPrg()
    {
        return this.listviewSelectedPrg.getSelectionModel().getSelectedItem();
    }

    public void shutdownThreads()
    {
        this.cont.shutdownExecutor();
    }
}
