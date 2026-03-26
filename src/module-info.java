module projeto_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires java.sql;

    exports application; 
    opens application.view to javafx.fxml;
}
