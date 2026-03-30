package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import javafx.scene.control.Alert;

public class ProdutoModel {
    
    private String nome;
    private String descricao;
    private String categoria;
    private double preco;
    private int quantidade;
    private int id;
    private String cod_barra;

    public ProdutoModel(int id, String nome, String descricao, String categoria, double preco, int quantidade, String cod_barra) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.cod_barra = cod_barra;
    }

 // GETTERS
    public int getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getDescricao() { return this.descricao; }
    public String getCategoria() { return this.categoria; }
    public double getPreco() { return this.preco; }
    public int getQuantidade() { return this.quantidade; }
    public String getCod() { return this.cod_barra; }
    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setCod(String cod_barra) { this.cod_barra = cod_barra; }

    // SALVAR (INSERT ou UPDATE)
    public void Salvar() {
        try (Connection conn = conexao.getConnection()) {

            if (this.id > 0) {
                // UPDATE
                PreparedStatement consulta = conn.prepareStatement(
                    "UPDATE produto SET nome=?, descricao=?, categoria=?, preco=?, quantidade=?, cod_barra=? WHERE id=?"
                );

                consulta.setString(1, this.nome);
                consulta.setString(2, this.descricao);
                consulta.setString(3, this.categoria);
                consulta.setDouble(4, this.preco);
                consulta.setInt(5, this.quantidade);
                consulta.setInt(7, this.id);
                consulta.setString(6, this.cod_barra);

                consulta.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto atualizado!");
                mensagem.showAndWait();

            } else {
                // INSERT
                PreparedStatement consulta = conn.prepareStatement(
                    "INSERT INTO produto (nome, descricao, categoria, preco, quantidade,cod_barra) VALUES (?,?,?,?,?,?)"
                );

                consulta.setString(1, this.nome);
                consulta.setString(2, this.descricao);
                consulta.setString(3, this.categoria);
                consulta.setDouble(4, this.preco);
                consulta.setInt(5, this.quantidade);
                consulta.setString(6, this.cod_barra);
                
                consulta.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto cadastrado!");
                mensagem.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BUSCAR
    public void Buscar(String valor) {
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta = conn.prepareStatement(
                 "SELECT * FROM produto WHERE descricao LIKE ? OR categoria LIKE ? OR nome LIKE ?")) {

            consulta.setString(1, "%" + valor + "%");
            consulta.setString(2, "%" + valor + "%");
            consulta.setString(3, "%" + valor + "%");

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                this.id = resultado.getInt("id");
                this.nome = resultado.getString("nome");
                this.descricao = resultado.getString("descricao");
                this.categoria = resultado.getString("categoria");
                this.quantidade = resultado.getInt("quantidade");
                this.preco = resultado.getDouble("preco");
                this.cod_barra = resultado.getString("cod_barra");
            } else {
                Alert mensagem = new Alert(Alert.AlertType.ERROR);
                mensagem.setContentText("Produto não encontrado!");
                mensagem.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EXCLUIR
    public void Excluir() {
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta =
                 conn.prepareStatement("DELETE FROM produto WHERE id=?")) {

            if (this.id > 0) {
                consulta.setInt(1, this.id);
                consulta.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto excluído!");
                mensagem.showAndWait();
            } else {
                Alert mensagem = new Alert(Alert.AlertType.ERROR);
                mensagem.setContentText("Produto não localizado!");
                mensagem.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List <ProdutoModel>ListarProdutos(String valor) {	
        List <ProdutoModel> produtos = new ArrayList<ProdutoModel>();
        try(Connection conn = conexao.getConnection();
                PreparedStatement consulta = conn.prepareStatement("Select * from produto");
                PreparedStatement consultaWhere = conn.prepareStatement("Select * from produto where nome like ?" + "or descricao like? or categoria like ?")){
                ResultSet resultado = null;
                if(valor == null) {
                    resultado=consulta.executeQuery();
                } else {
                    consultaWhere.setString(1, "%"+ valor+"%");
                    consultaWhere.setString(2,  "%"+ valor+"%");
                    consultaWhere.setString(3, "%"+ valor+"%");
                    resultado=consultaWhere.executeQuery();
                }
            while (resultado.next()) {
                ProdutoModel p = new ProdutoModel(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("descricao"),
                        resultado.getString("categoria"),
                        resultado.getDouble("preco"),
                        resultado.getInt("quantidade"),
                        resultado.getString("cod_barra")
                        );
                produtos.add(p);
            }
        }catch(Exception e) {e.printStackTrace();}
        return produtos;
    }	
}