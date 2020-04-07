package app;

import Domain.MyException;
import Domain.PrgState;
import Domain.Statements.IStmt;
import Domain.Utils.MyIStack;
import Domain.Utils.MyStack;
import View.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class MainController {
    @FXML
    private Button ButtonExecute;
    @FXML
    private ListView<IStmt> ListViewMain;


    @FXML
    public void initialize() {
        assert ListViewMain != null : "fx:id=\"ListViewMain\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert ButtonExecute != null : "fx:id=\"ButtonExecute\" was not injected: check your FXML file 'MainWindow.fxml'.";

        this.fillListView();

        this.ListViewMain.getSelectionModel().selectIndices(0);
        this.ListViewMain.getFocusModel().focus(0);
    }


    @FXML
    void clickedButtonExecute(MouseEvent event) throws IOException {
        IStmt selected = this.ListViewMain.getSelectionModel().getSelectedItem();

        try{
            Controller.typecheck(selected);
        }
        catch (MyException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typechecker failed!");
            alert.setContentText(e.toString());

            alert.showAndWait();
            return;
        }

        MyIStack<IStmt> stck = new MyStack<>();
        stck.push(selected);

        ProgramController cont = new ProgramController(new PrgState(stck));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProgramWindow.fxml"));
        fxmlLoader.setController(cont);
        Parent root1 = fxmlLoader.load();

        Stage newWindow = new Stage();
        newWindow.setOnHiding( ev-> {cont.shutdownThreads();} );

        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(this.ButtonExecute.getScene().getWindow());
        newWindow.setTitle("Program window");
        newWindow.setScene(new Scene(root1));
        newWindow.show();
    }


    private void fillListView() {

        List<IStmt> exampleStates = PrgState.createExamples();

        for (IStmt state: exampleStates) {
            this.ListViewMain.getItems().add(state);
        }
    }

}
