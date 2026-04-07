package application.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML private ToggleGroup rdOperacao;
    private ObservableList<ProdutoModel> listaProdutos;
    
    ProdutoModel produto = new ProdutoModel(0, null, null, null, null, 0, 0);
    
    public void initialize() {
    	colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
    	colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	colCod.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
    	colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    	colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    	colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    	
    	ListarProdutosTab(null);
    	
    	tabProdutos.getSelectionModel().selectedItemProperty().addListener(
    			(obs,selecao,novaSelecao)->{
    				if(novaSelecao != null) {
    					produto=novaSelecao;
    					txtId.setText(String.format("%06d",produto.getID()));
    					txtProduto.setText(produto.getNome());
    					txtCod.setText(produto.getCodBarras());
    					txtQtd.setText("0");
    				}
    			});
    	btnProcessar.setOnAction(e->{
    		produto.setQuantidade(Integer.parseInt(txtQtd.getText()));
    		RadioButton operacao = (RadioButton) rdOperacao.getSelectedToggle();
    		produto.ProcessaEstoque(operacao.getText());
    		txtPesquisar.clear();
    		txtId.clear();
    		txtCod.clear();
    		txtProduto.clear();
    		txtQtd.clear();
    		ListarProdutosTab(null);
    	});
    		btnBuscar.setOnAction(e->{Pesquisar();});
    	}
    public void Pesquisar() {
    	if(!txtPesquisar.getText().isEmpty()) {
    		produto.Buscar(txtPesquisar.getText());
    		ListarProdutosTab(txtPesquisar.getText());
    		txtId.setText(String.format("%06d",produto.getID()));
    		txtProduto.setText(produto.getNome());
    		txtCod.setText(produto.getCodBarras());
    		txtQtd.setText("0");
    	} else {
    		ListarProdutosTab(null);
    	}
    }
    public List<ProdutoModel>ListarProdutos(String Valor){
    	List <ProdutoModel> produtos = new ArrayList<ProdutoModel>();
    	try(Connection conn = conexao.getConnection();
    			PreparedStatement consulta = conn.prepareStatement("select * from produto");
    			PreparedStatement consultaWhere = conn.prepareStatement("select * from produto where nome like ? or descricao like ?"+" or categoria like ?")
    			){
    		ResultSet resultado=null;
    		if(Valor == null) {
    			resultado=consulta.executeQuery();
    		} else {
    			consultaWhere.setString(1, "%"+Valor+"%");
    			consultaWhere.setString(2, "%"+Valor+"%");
    			consultaWhere.setString(3, "%"+Valor+"%");
    			resultado=consultaWhere.executeQuery();
    		}
    		while(resultado.next()) {
    			ProdutoModel p = new ProdutoModel(
    				resultado.getInt("Id"),
    				resultado.getString("produto"),
    				resultado.getString("codBarras"),
    				resultado.getString("descricao"),
    				resultado.getString("categoria"),
    				resultado.getDouble("preco"),
    				resultado.getInt("quantidade")
    					);
    			produtos.add(p);		
    		}
    	}catch(Exception e) {e.printStackTrace();}
    	return produtos;
    }
    public void ListarProdutosTab(String Valor) {
    	List <ProdutoModel> produtos = produto.ListarProdutos(Valor);
    	listaProdutos=FXCollections.observableArrayList(produtos);
    	tabProdutos.setItems(listaProdutos);
    }
}