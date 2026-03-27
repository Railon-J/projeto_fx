module projeto_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires java.sql;
	requires javafx.base;

    exports application;
    opens application to javafx.base,javafx.graphics,javafx.fxml;
    opens application.view to javafx.fxml,javafx.base,javafx.graphics;
    opens application.model to javafx.base;
}
