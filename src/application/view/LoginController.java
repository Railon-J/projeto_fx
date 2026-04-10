package application.view;

import java.util.List;
import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LoginController {

    @FXML private Button btnEntrar;
    @FXML private TextField txtLogin;
    @FXML private TextField txtSenha;

    public void entrar() {
    	txtLogin.getText();
    	
    }
    }