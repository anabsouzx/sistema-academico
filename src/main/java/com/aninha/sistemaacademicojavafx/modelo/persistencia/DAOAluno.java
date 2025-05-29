package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class DAOAluno {

    Connection conexao;

    public DAOAluno(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirAluno(Aluno a){
        String sql = "insert into aluno(codigoaluno, nomealuno, datanasc, cpf, telefone) values (?,?,?,?,?)";
        PreparedStatement ps;

        try {
            ps = this.conexao.prepareStatement(sql);
            ps.setInt(1,a.getCodigoAluno());
            ps.setString(2,a.getNomeAluno());
            ps.setDate(3,a.getDataNasc());
            ps.setString(4,a.getCpf());
            ps.setString(5,a.getTelefone());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ObservableList<Aluno> listarAlunos(){
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        String sql = "select * from aluno";

        try {
            Statement declaracao = conexao.createStatement();
            ResultSet resposta = declaracao.executeQuery(sql);

            while(resposta.next()){

                int codigoAluno = resposta.getInt("codigoaluno");
                String nomeAluno = resposta.getString("nomealuno");
                Date dataNasc = resposta.getDate("datanasc");
                String cpf = resposta.getString("cpf");
                String telefone = resposta.getString("telefone");

                Aluno a = new Aluno(codigoAluno, nomeAluno, dataNasc, cpf, telefone);
                alunos.add(a);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return alunos;
    }

    public ObservableList<Aluno> listarTodosAlunosParaComboBox() {
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        String sql = "SELECT CodigoAluno, NomeAluno, DataNasc, CPF, Telefone FROM Aluno ORDER BY NomeAluno";
        try (Statement declaracao = conexao.createStatement();
             ResultSet resposta = declaracao.executeQuery(sql)) {

            while (resposta.next()) {
                int codigo = resposta.getInt("CodigoAluno");
                String nome = resposta.getString("NomeAluno");
                Date dataNasc = resposta.getDate("DataNasc");
                String cpf = resposta.getString("CPF");
                String telefone = resposta.getString("Telefone");
                alunos.add(new Aluno(codigo, nome, dataNasc, cpf, telefone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção de forma adequada
        }
        return alunos;
    }

    public boolean atualizarAluno(Aluno a) {
        String sql = "UPDATE aluno SET nomealuno = ?, datanasc = ?, cpf = ?, telefone = ? WHERE codigoaluno = ?";
        PreparedStatement ps = null;
        try {
            ps = this.conexao.prepareStatement(sql);
            ps.setString(1, a.getNomeAluno());
            ps.setDate(2, a.getDataNasc());
            ps.setString(3, a.getCpf());
            ps.setString(4, a.getTelefone());
            ps.setInt(5, a.getCodigoAluno());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean excluirAluno(int codigoAluno) {
        String sql = "DELETE FROM aluno WHERE codigoaluno = ?";
        PreparedStatement ps = null;
        try {
            ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, codigoAluno);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) { // erro caso esteja associado a uma chave estrangeira
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setHeaderText("Não foi possível excluir o aluno.");
            alert.setContentText("Verifique se o aluno possui registros associados (ex: matrículas) ou tente novamente.");
            alert.showAndWait();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void apagarTudo(){ // precisa de um botao
        String sql = "delete from aluno";

        PreparedStatement ps;

        try {
            ps = this.conexao.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
