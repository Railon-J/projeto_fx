package application.view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistoricoController {

    @FXML private Button btnBuscar;
    @FXML private TableColumn<?, ?> colData;
    @FXML private TableColumn<?, ?> colID;
    @FXML private TableColumn<?, ?> colIdProd;
    @FXML private TableColumn<?, ?> colNome;
    @FXML private TableColumn<?, ?> colQtd;
    @FXML private TableColumn<?, ?> colTipo;
    @FXML private DatePicker dtFinal;
    @FXML private DatePicker dtInicio;
    @FXML private TableView<?> tabEstoque;

}
