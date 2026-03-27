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

    public ProdutoModel(int id, String nome, String descricao, String categoria, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // GETTERS
    public int getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getdescricao() { return this.descricao; }
    public String getcategoria() { return this.categoria; }
    public double getpreco() { return this.preco; }
    public int getquantidade() { return this.quantidade; }

    // SETTERS
    public void setNome(String nome) { this.nome = nome; }
    public void setdescricao(String descricao) { this.descricao = descricao; }
    public void setcategoria(String categoria) { this.categoria = categoria; }
    public void setpreco(double preco) { this.preco = preco; }
    public void setquantidade(int quantidade) { this.quantidade = quantidade; }

    // SALVAR (INSERT ou UPDATE)
    public void Salvar() {
        try (Connection conn = conexao.getConnection()) {

            if (this.id > 0) {
                // UPDATE
                PreparedStatement consulta = conn.prepareStatement(
                    "UPDATE produto SET nome=?, descricao=?, categoria=?, preco=?, quantidade=? WHERE id=?"
                );

                consulta.setString(1, this.nome);
                consulta.setString(2, this.descricao);
                consulta.setString(3, this.categoria);
                consulta.setDouble(4, this.preco);
                consulta.setInt(5, this.quantidade);
                consulta.setInt(6, this.id);

                consulta.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto atualizado!");
                mensagem.showAndWait();

            } else {
                // INSERT
                PreparedStatement consulta = conn.prepareStatement(
                    "INSERT INTO produto (nome, descricao, categoria, preco, quantidade) VALUES (?,?,?,?,?)"
                );

                consulta.setString(1, this.nome);
                consulta.setString(2, this.descricao);
                consulta.setString(3, this.categoria);
                consulta.setDouble(4, this.preco);
                consulta.setInt(5, this.quantidade);

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
                        resultado.getInt("quantidade")
                        );
                produtos.add(p);
            }
        }catch(Exception e) {e.printStackTrace();}
        return produtos;
    }
}