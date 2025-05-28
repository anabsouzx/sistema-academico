package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Curso;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOCurso {

    Connection conexao;

    public DAOCurso(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirCurso(Curso c) {
        String sql = "INSERT INTO Curso(codigoCurso, nomeCurso, duracao) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, c.getCodigoCurso());
            ps.setString(2, c.getNomeCurso());
            ps.setInt(3, c.getDuracao());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Curso> listarCursos() {
        ObservableList<Curso> cursos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Curso";

        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int codigoCurso = rs.getInt("codigoCurso");
                String nomeCurso = rs.getString("nomeCurso");
                int duracao = rs.getInt("duracao");

                Curso c = new Curso(codigoCurso, nomeCurso, duracao);
                cursos.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    public void apagarTudo() {
        String sql = "DELETE FROM Curso";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
