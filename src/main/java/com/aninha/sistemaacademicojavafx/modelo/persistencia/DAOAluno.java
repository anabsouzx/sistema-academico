package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        String sql = "SELECT CodigoAluno, NomeAluno, DataNasc, CPF, Telefone FROM Aluno ORDER BY NomeAluno"; // Ordenar por nome ajuda na seleção
        try (Statement declaracao = conexao.createStatement();
             ResultSet resposta = declaracao.executeQuery(sql)) {

            while (resposta.next()) {
                int codigo = resposta.getInt("CodigoAluno");
                String nome = resposta.getString("NomeAluno");
                Date dataNasc = resposta.getDate("DataNasc");
                String cpf = resposta.getString("CPF");
                String telefone = resposta.getString("Telefone");
                alunos.add(new Aluno(codigo, nome, dataNasc, cpf, telefone)); // Supondo que você tem um construtor completo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção de forma adequada
        }
        return alunos;
    }

    public void apagarTudo(){
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
