package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class DAOCurso {

    Connection conexao;

    public DAOCurso(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirCurso(Curso c) {
        String sql = "insert into Curso(codigoCurso, nomeCurso, duracao) values( ?, ?, ?)";
        PreparedStatement ps;

        try {
            ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, c.getCodigoCurso());
            ps.setString(2, c.getNomeCurso());
            ps.setInt(3, c.getDuracao());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Curso> listarCursos() {
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        String sql = "select * from Curso";

        try {
            Statement declaração = conexao.createStatement();
            ResultSet resposta = declaração.executeQuery(sql);

            while (resposta.next()) {
                int codigoCurso = resposta.getInt("codigoCurso");
                String nomeCurso = resposta.getString("nomeCurso");
                int duracao = resposta.getInt("duracao");

                Curso c = new Curso(codigoCurso, nomeCurso, duracao);
                cursos.add(c);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }


        return cursos;
    }

    public void apagarTudo() {
        String sql = "delete from Curso";

        PreparedStatement ps;
        try {
            ps = this.conexao.prepareStatement(sql);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ObservableList<Curso> listarCursosComboBox() {
        ObservableList<Curso> cursos = FXCollections.observableArrayList();
        String sql = "SELECT codigoCurso, nomeCurso, duracao FROM Curso ORDER BY nomeCurso"; // Ordenar por nome ajuda na seleção
        try (Statement declaracao = conexao.createStatement();
             ResultSet resposta = declaracao.executeQuery(sql)) {

            while (resposta.next()) {
                int codigo = resposta.getInt("codigoCurso");
                String nome = resposta.getString("nomeCurso");
                int duracao = resposta.getInt("duracao");
                cursos.add(new Curso(codigo, nome, duracao)); // Supondo que você tem um construtor completo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção de forma adequada
        }
        return cursos;
    }
}
