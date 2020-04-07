module nettest {
    requires javafx.fxml;
    requires javafx.controls;

    opens UI;
    opens UI.Controllers;
    opens Utilities;
}