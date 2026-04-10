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

public class ProcessarEstoqueController {

    @FXML private Button btnBuscar;
    @FXML private Button btnProcessar;
    @FXML private TextField txtCod;
    @FXML private TextField txtId;
    @FXML private TextField txtPesquisar;
    @FXML private TextField txtProduto;
    @FXML private TextField txtQtd;

    @FXML private TableView<ProdutoModel> tabProdutos;
    @FXML private TableColumn<ProdutoModel, Integer> colId; 
    @FXML private TableColumn<ProdutoModel, String> colNome;
    @FXML private TableColumn<ProdutoModel, String> colCod; 
    @FXML private TableColumn<ProdutoModel, String> colDescricao;
    @FXML private TableColumn<ProdutoModel, String> colCategoria;
    @FXML private TableColumn<ProdutoModel, Integer> colQuantidade; 

    @FXML private ToggleGroup rdOperacao;
    @FXML private RadioButton rbEntrada;
    @FXML private RadioButton rbSaida;

    private ObservableList<ProdutoModel> listaProdutos;
    private ProdutoModel produto = new ProdutoModel(0, null, null, null, null, 0, 0);
    
    @FXML
    private void abrirHistorico() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/HistoricoProcessamento.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Produtos");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Configura colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCod.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Carrega produtos
        ListarProdutosTab(null);

        // Seleção na tabela
        tabProdutos.getSelectionModel().selectedItemProperty().addListener((obs, selecao, novaSelecao) -> {
            if (novaSelecao != null) {
                produto = novaSelecao;
                txtId.setText(String.format("%06d", produto.getId()));
                txtProduto.setText(produto.getNome());
                txtCod.setText(produto.getCodBarras());
                txtQtd.setText("0");
            }
        });

        // Botão Processar
        btnProcessar.setOnAction(e -> {
            produto.setQuantidade(Integer.parseInt(txtQtd.getText()));
            RadioButton operacao = (RadioButton) rdOperacao.getSelectedToggle();
            produto.processaEstoque(operacao.getText());

            limparCampos();
            ListarProdutosTab(null);
        });

        // Botão Buscar
        btnBuscar.setOnAction(e -> Pesquisar());
    }

    public void Pesquisar() {
        if (!txtPesquisar.getText().isEmpty()) {
            produto.buscar(txtPesquisar.getText());
            ListarProdutosTab(txtPesquisar.getText());
            txtId.setText(String.format("%06d", produto.getId()));
            txtProduto.setText(produto.getNome());
            txtCod.setText(produto.getCodBarras());
            txtQtd.setText("0");
        } else {
            ListarProdutosTab(null);
        }
    }

    public void ListarProdutosTab(String valor) {
        List<ProdutoModel> produtos = produto.listarProdutos(valor);
        listaProdutos = FXCollections.observableArrayList(produtos);
        tabProdutos.setItems(listaProdutos);
    }

    private void limparCampos() {
        txtPesquisar.clear();
        txtId.clear();
        txtCod.clear();
        txtProduto.clear();
        txtQtd.clear();
    }
}
