package application.view;

import application.model.ProdutoModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProcessarEstoqueController {

    @FXML private Button btnBuscar;
    @FXML private Button btnProcessar;
    @FXML private TableView<?> tabEstoque;
    @FXML private TextField txtCod;
    @FXML private TextField txtId;
    @FXML private TextField txtPesquisar;
    @FXML private TextField txtProduto;
    @FXML private TextField txtQtd;
    @FXML private TableColumn<ProdutoModel, String> colCategoria;
    @FXML private TableColumn<ProdutoModel, String> colDescricao;
    @FXML private TableColumn<ProdutoModel, Integer> colId; 
    @FXML private TableColumn<ProdutoModel, String> colNome;
    @FXML private TableColumn<ProdutoModel, Integer> colQuantidade; 
    @FXML private TableColumn<ProdutoModel, Integer> colCod; 
    @FXML private TableView<ProdutoModel> tabProdutos;
    private ObservableList<ProdutoModel> listaProdutos;
    
    ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0, null);

}
