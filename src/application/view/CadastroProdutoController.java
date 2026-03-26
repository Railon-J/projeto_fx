package application.view;

import application.model.ProdutoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CadastroProdutoController {
    
    @FXML
    private TextField txtQuantidade;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtCategoria;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtPreco;
    
    //METODO PARA SALVAR O CADASTRO DO PRODUTO
    public void Salvar() {
        
        //VERIFICA SE TEM ALGUM CAMPO VAZIO
        if(txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() ||
        txtCategoria.getText().isEmpty() || txtPreco.getText().isEmpty() ||
        txtQuantidade.getText().isEmpty()) {
        //ARMAZENA CAMPO QUE ESTA VAZIO
        String erro="";
        if(txtNome.getText().isEmpty()) {erro=erro+"\nCampo Nome em Branco!";}
        if(txtDescricao.getText().isEmpty()) {erro=erro+"\nCampo Descricao em Branco!";}
        if(txtCategoria.getText().isEmpty()) {erro=erro+"\nCampo Categoria em Branco!";}
        if(txtPreco.getText().isEmpty()) {erro=erro+"\nCampo Preco em Branco!";}
        if(txtQuantidade.getText().isEmpty()) {erro=erro+"\nCampo Quantidade em Branco!";}
        //EXIBE UMA MENSAGEM PARA PREENCHER OS CAMPOS
        Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
        mensagem.setContentText("Preenchendo campos:"+erro);
        mensagem.showAndWait();
    }else {
        //CRIANDO O OBJETO
        ProdutoModel produto = new ProdutoModel(0,
                txtNome.getText(),
                txtDescricao.getText(),
                txtCategoria.getText(),
                Double.parseDouble(txtPreco.getText()),
                Integer.parseInt(txtQuantidade.getText())
                );
        //SALVA PRODUTOS NO BANCO DE DADOS
        produto.Salvar();
        //EXIBE MENSAGEM DE CONFIRMAÇÃO
        Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
        mensagem.setContentText("Produto Cadastrado!");
        mensagem.showAndWait();
        //LIMPA OS CAMPOS
        txtNome.setText("");
        txtDescricao.setText("");
        txtCategoria.setText("");
        txtPreco.setText("");
        txtQuantidade.setText("");     
  }
 }  
    //METODO PARA BUSCAR O CADASTRO DO PRODUTO
    public void Pesquisar () {
        if(!txtBuscar.getText().isEmpty()) {
            ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0);
            //EXECUTA O METODO DE BUSCAR
            produto.Buscar(txtBuscar.getText());
            //informar os valores nos campos do formulario
            txtNome.setText(produto.getNome());//usa o get para buscar informação
            txtDescricao.setText(produto.getdescricao());
            txtCategoria.setText(produto.getcategoria());
            txtPreco.setText(String.valueOf(produto.getpreco()));
            txtQuantidade.setText(String.valueOf(produto.getquantidade()));
        }else {
            //mensagem para digitar texto no camapo buscar
   
}
}
}