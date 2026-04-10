package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import javafx.scene.control.Alert;

public class ProdutoModel {
    private int id;
    private String nome;
    private String codBarras;
    private String descricao;
    private String categoria;
    private double preco;
    private int quantidade;

    // CONSTRUTOR
    public ProdutoModel(int id, String nome, String codBarras, String descricao, 
                        String categoria, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.codBarras = codBarras;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // GETTERS (corrigidos para padrão JavaBeans)
    public int getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getCodBarras() { return this.codBarras; }
    public String getDescricao() { return this.descricao; }
    public String getCategoria() { return this.categoria; }
    public double getPreco() { return this.preco; }
    public int getQuantidade() { return this.quantidade; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCodBarras(String codBarras) { this.codBarras = codBarras; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    // SALVAR
    public void salvar() {
        try (Connection conn = conexao.getConnection();
             PreparedStatement insert = conn.prepareStatement(
                 "INSERT INTO produto (nome, codBarras, descricao, categoria, preco, quantidade) VALUES (?,?,?,?,?,?)");
             PreparedStatement update = conn.prepareStatement(
                 "UPDATE produto SET nome=?, codBarras=?, descricao=?, categoria=?, preco=?, quantidade=? WHERE id=?")) {

            if (this.id > 0) {
                update.setString(1, this.nome);
                update.setString(2, this.codBarras);
                update.setString(3, this.descricao);
                update.setString(4, this.categoria);
                update.setDouble(5, this.preco);
                update.setInt(6, this.quantidade);
                update.setInt(7, this.id);
                update.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
                mensagem.setContentText("Produto Alterado!");
                mensagem.showAndWait();
            } else {
                insert.setString(1, this.nome);
                insert.setString(2, this.codBarras);
                insert.setString(3, this.descricao);
                insert.setString(4, this.categoria);
                insert.setDouble(5, this.preco);
                insert.setInt(6, this.quantidade);
                insert.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto Cadastrado!");
                mensagem.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BUSCAR
    public void buscar(String valor) {
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
                this.codBarras = resultado.getString("codBarras");
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
    public void excluir() {
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta = conn.prepareStatement("DELETE FROM produto WHERE id=?")) {

            if (this.id > 0) {
                consulta.setInt(1, this.id);
                consulta.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto Excluído!");
                mensagem.showAndWait();
            } else {
                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Produto Não Localizado!");
                mensagem.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LISTAR
    public List<ProdutoModel> listarProdutos(String valor) {
        List<ProdutoModel> produtos = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta = conn.prepareStatement("SELECT * FROM produto");
             PreparedStatement consultaWhere = conn.prepareStatement(
                 "SELECT * FROM produto WHERE nome LIKE ? OR descricao LIKE ? OR categoria LIKE ?")) {

            ResultSet resultado;
            if (valor == null) {
                resultado = consulta.executeQuery();
            } else {
                consultaWhere.setString(1, "%" + valor + "%");
                consultaWhere.setString(2, "%" + valor + "%");
                consultaWhere.setString(3, "%" + valor + "%");
                resultado = consultaWhere.executeQuery();
            }

            while (resultado.next()) {
                ProdutoModel p = new ProdutoModel(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("codBarras"),
                    resultado.getString("descricao"),
                    resultado.getString("categoria"),
                    resultado.getDouble("preco"),
                    resultado.getInt("quantidade")
                );
                produtos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produtos;
    }

    // PROCESSAR ESTOQUE
    public void processaEstoque(String operacao) {
        if (this.id > 0) {
            String sql = "UPDATE produto SET quantidade = quantidade + ? WHERE id=?";
            if (operacao.equals("Saida")) {
                sql = "UPDATE produto SET quantidade = quantidade - ? WHERE id=?";
            }

            try (Connection conn = conexao.getConnection();
                 PreparedStatement consulta = conn.prepareStatement(sql)) {
                consulta.setInt(1, this.quantidade);
                consulta.setInt(2, this.id);
                consulta.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
