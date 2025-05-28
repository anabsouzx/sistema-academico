package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Disciplina;

import java.sql.*;
import java.util.ArrayList;

public class DAODisciplina {

    Connection conexao;

    public DAODisciplina(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirDisciplina(Disciplina d) {
        String sql = "insert into Disciplina(codigoDisciplina, nomeDisciplina, codCurso, cargaHoraria) values( ?, ?, ?, ?)";
        PreparedStatement ps;

        try {
            ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, d.getCodigoDisciplina());
            ps.setString(2, d.getNomeDisciplina());
            ps.setInt(3, d.getCodCurso());
            ps.setInt(4, d.getCargaHoraria());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Disciplina> listarDisciplinas() {
        ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
        String sql = "select * from Disciplina";

        try {
            Statement declaração = conexao.createStatement();
            ResultSet resposta = declaração.executeQuery(sql);

            while (resposta.next()) {
                int codigoDisciplina = resposta.getInt("codigoDisciplina");
                String nomeDisciplina = resposta.getString("nomeDisciplina");
                int codCurso = resposta.getInt("codCurso");
                int cargaHoraria = resposta.getInt("cargaHoraria");

                Disciplina d = new Disciplina(codigoDisciplina, nomeDisciplina, codCurso, cargaHoraria);
                disciplinas.add(d);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }


        return disciplinas;
    }

    public void apagarTudo() {
        String sql = "delete from Disciplina";

        PreparedStatement ps;
        try {
            ps = this.conexao.prepareStatement(sql);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
