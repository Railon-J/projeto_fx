package application.model;
import java.sql.Connection;
import application.conexao;
import application.conexao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProdutoModel {
    
private String nome;
private String descricao;
private String categoria;
private double preco;
private int quantidade;
private int id;

public ProdutoModel(int id,String nome, String descricao, String categoria, double preco, int quantidade) {
    this.nome=nome;
    this.categoria=categoria;
    this.descricao=descricao;
    this.preco=preco;
    this.quantidade=quantidade;
    this.id=id;
}

//GETTERS
public String getNome() {return this.nome;}
public String getdescricao() {return this.descricao;}
public String getcategoria() {return this.categoria;}
public double getpreco() {return this.preco;}
public int getquantidade() {return this.quantidade;}
public int getid() {return this.id;}

//SETTERS
public void setNome(String nome) {this.nome=nome;}
public void setdescricao(String descricao) {this.descricao=descricao;}
public void setcategoria(String categoria) {this.categoria=categoria;}
public void setpreco(double preco) {this.preco=preco;}
public void setquantidade(int quantidade) {this.quantidade=quantidade;}
public void setid(int id) {this.id=id;}

public void Salvar () {
    try (Connection conn = conexao.getConnection();
        PreparedStatement consulta = conn.prepareStatement("insert into produto"+"(nome,descricao,categoria,preco,quantidade)"+" values (?,?,?,?,?)");){
        consulta.setString(1,this.nome);
        consulta.setString(2,this.descricao);
        consulta.setString(3,this.categoria);
        consulta.setDouble(4,this.preco);
        consulta.setInt(5,this.quantidade);
        consulta.executeUpdate();
        
        //CRIA MENSAGEM
        Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
        mensagem.setContentText("Produto Cadastrado!");
        mensagem.showAndWait();
        
    }catch (Exception e) { e.printStackTrace();}    
    }

public void Buscar(String Valor) {
    try (Connection conn = conexao.getConnection();
            PreparedStatement consulta = conn.prepareStatement("select * from produto where descricao like ? or categoria like ? or nome like ?");){
        consulta.setString(1,"%"+Valor+"%");
        consulta.setString(2,"%"+Valor+"%");
        consulta.setString(3,"%"+Valor+"%");
        //GUARDA O RESULTADO EM UMA VARIAVEL DO TIPO RESULTSET (TIPO DE DADO SQL)
        ResultSet resultado = consulta.executeQuery();
        //VERIFICA SE RETORNOU DADOS NA CONSULTA
        if(resultado.next()) {
            this.nome=resultado.getString("Nome");
            this.descricao=resultado.getString("Descricao");
            this.categoria=resultado.getString("categoria");
            this.quantidade=resultado.getInt("quantidade");
            this.preco=resultado.getDouble("preco");
            
        }else {
            //PRODUTO NÃO ENCONTRADO
            Alert mensagem = new Alert(Alert.AlertType.ERROR);
            mensagem.setContentText("Produto não encontrado!");
            mensagem.showAndWait();
        }
}catch(Exception e) { e.printStackTrace();}

}
}