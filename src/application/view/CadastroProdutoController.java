package application.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CadastroProdutoController {

    @FXML private TextField txtQuantidade;
    @FXML private Button btnSalvar;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtid; 

    @FXML private TableColumn<ProdutoModel, String> colCategoria;
    @FXML private TableColumn<ProdutoModel, String> colDescricao;
    @FXML private TableColumn<ProdutoModel, Integer> colId; 
    @FXML private TableColumn<ProdutoModel, String> colNome;
    @FXML private TableColumn<ProdutoModel, Double> colPreco;
    @FXML private TableColumn<ProdutoModel, Integer> colQuantidade; 

    @FXML private TableView<ProdutoModel> tabProdutos;
    private ObservableList<ProdutoModel> listaProdutos;
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0, null);

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
            try {
                produto.setNome(txtNome.getText());
                produto.setDescricao(txtDescricao.getText());
                produto.setCategoria(txtCategoria.getText());
                produto.setPreco(Double.parseDouble(txtPreco.getText()));
                produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));

                produto.Salvar();
                produto = new ProdutoModel(0, null, null, null, 0, 0, null);

                txtNome.clear();
                txtDescricao.clear();
                txtCategoria.clear();
                txtPreco.clear();
                txtQuantidade.clear();
                txtid.clear(); // apenas limpa, não usa para salvar
            } catch (NumberFormatException e) {
                Alert mensagem = new Alert(Alert.AlertType.ERROR);
                mensagem.setContentText("Erro de formato numérico! Verifique os campos Preço e Quantidade.");
                mensagem.showAndWait();
            }
        }
        ListarProdutosTab(null);
    }
    // BUSCAR
    public void Pesquisar() {
        if(!txtBuscar.getText().isEmpty()) {
            produto.Buscar(txtBuscar.getText());
            
            txtNome.setText(produto.getNome());
            txtDescricao.setText(produto.getDescricao());
            txtCategoria.setText(produto.getCategoria());
            txtPreco.setText(nf.format(produto.getPreco()));
            txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
            txtid.setText(String.format("%06d", produto.getId()));
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
            produto = new ProdutoModel(0, null, null, null, 0, 0, null);

            txtNome.clear();
            txtDescricao.clear();
            txtCategoria.clear();
            txtPreco.clear();
            txtQuantidade.clear();
            txtid.clear();
        } else {
            Alert mensagem = new Alert(Alert.AlertType.ERROR);
            mensagem.setContentText("Nenhum produto selecionado!");
            mensagem.showAndWait();
        }
        ListarProdutosTab(null);
    }

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        ListarProdutosTab(null);
    }

    public void ListarProdutosTab(String valor) {
        List<ProdutoModel> produtos = produto.ListarProdutos(valor);
        listaProdutos = FXCollections.observableArrayList(produtos);
        tabProdutos.setItems(listaProdutos);
    }
    
    @FXML
    private void PesquisarTabela(MouseEvent event) {
        ProdutoModel selecionado = tabProdutos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            produto.Buscar(selecionado.getNome());

            txtNome.setText(produto.getNome());
            txtDescricao.setText(produto.getDescricao());
            txtCategoria.setText(produto.getCategoria());
            txtPreco.setText(nf.format(produto.getPreco()));
            txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
            txtid.setText(String.format("%06d", produto.getId()));
        } else {
            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setContentText("Selecione um produto na tabela!");
            mensagem.showAndWait();
        }
    }
}
