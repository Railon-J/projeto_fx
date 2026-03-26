package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class TelaController {

    @FXML
    private MenuItem itemCliente;
    @FXML
    private MenuItem itemProcessaEstoque;
    @FXML
    private MenuItem itemProduto;
    @FXML
    private MenuItem itemSair;
    
    @FXML
    private void initialize() {
        
    }
    public void Sair() {
        System.exit(0);
    }
    @FXML
    private void abrirCadastroProdutos() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/CadastroProduto.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Produtos");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }