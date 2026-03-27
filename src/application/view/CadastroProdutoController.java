package application.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import application.model.ProdutoModel;

public class CadastroProdutoController {

    @FXML private TextField txtQuantidade;
    @FXML private Button btnSalvar;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtID;
    @FXML private TableColumn<ProdutoModel, String> colCategoria;
    @FXML private TableColumn<ProdutoModel, String> colDescricao;
    @FXML private TableColumn<ProdutoModel, Integer> colID;
    @FXML private TableColumn<ProdutoModel, String> colNome;
    @FXML private TableColumn<ProdutoModel, Double> colPreco;
    @FXML private TableColumn<ProdutoModel, String> colQuantidade;
    @FXML private TableView<ProdutoModel> tabProdutos;
    private ObservableList<ProdutoModel> listaProdutos;

    ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0);

    // SALVAR
    public void Salvar() {

        if(txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() ||
           txtCategoria.getText().isEmpty() || txtPreco.getText().isEmpty() ||
           txtQuantidade.getText().isEmpty()) {

            String erro = "";
            if(txtNome.getText().isEmpty()) erro += "\nNome vazio";
            if(txtDescricao.getText().isEmpty()) erro += "\nDescrição vazia";
            if(txtCategoria.getText().isEmpty()) erro += "\nCategoria vazia";
            if(txtPreco.getText().isEmpty()) erro += "\nPreço vazio";
            if(txtQuantidade.getText().isEmpty()) erro += "\nQuantidade vazia";

            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setContentText("Preencha os campos:" + erro);
            mensagem.showAndWait();

        } else {

            produto.setNome(txtNome.getText());
            produto.setdescricao(txtDescricao.getText());
            produto.setcategoria(txtCategoria.getText());
            produto.setpreco(Double.parseDouble(txtPreco.getText()));
            produto.setquantidade(Integer.parseInt(txtQuantidade.getText()));

            produto.Salvar();

            // RESETA (ESSENCIAL)
            produto = new ProdutoModel(0, null, null, null, 0, 0);

            txtNome.clear();
            txtDescricao.clear();
            txtCategoria.clear();
            txtPreco.clear();
            txtQuantidade.clear();
        }
    }

    // BUSCAR
    public void Pesquisar() {
        if(!txtBuscar.getText().isEmpty()) {

            produto.Buscar(txtBuscar.getText());

            txtNome.setText(produto.getNome());
            txtDescricao.setText(produto.getdescricao());
            txtCategoria.setText(produto.getcategoria());
            txtPreco.setText(String.valueOf(produto.getpreco()));
            txtQuantidade.setText(String.valueOf(produto.getquantidade()));

        } else {
            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setContentText("Digite algo para buscar!");
            mensagem.showAndWait();
        }
    }

    // EXCLUIR
    public void Excluir() {

        if(produto.getId() > 0) {

            produto.Excluir();

            produto = new ProdutoModel(0, null, null, null, 0, 0);

            txtNome.clear();
            txtDescricao.clear();
            txtCategoria.clear();
            txtPreco.clear();
            txtQuantidade.clear();

        } else {
            Alert mensagem = new Alert(Alert.AlertType.ERROR);
            mensagem.setContentText("Nenhum produto selecionado!");
            mensagem.showAndWait();
        }
    }
    
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        ListarProdutosTab(null);
    }
    
    public void ListarProdutosTab(String valor) {
        List <ProdutoModel> produtos = produto.ListarProdutos(valor);
        listaProdutos=FXCollections.observableArrayList(produtos);
        tabProdutos.setItems(listaProdutos);
    }
}