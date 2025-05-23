package persistencia;

import modelo.Curso;
import modelo.Matricula;

import java.sql.*;
import java.util.ArrayList;

public class JDBCMatricula {

    Connection conexao;

    public JDBCMatricula(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirMatricula(Matricula m) {
        String sql = "insert into Matricula (numMatricula, codAluno, codDisciplina, semestre, ano) values( ?, ?, ?, ?, ?)";
        PreparedStatement ps;

        try {
            ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, m.getNumMatricula());
            ps.setInt(2, m.getCodAluno());
            ps.setInt(3, m.getCodDisciplina());
            ps.setInt(4, m.getSemestre());
            ps.setInt(4, m.getAno());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Matricula> listarMatriculas() {
        ArrayList<Matricula> matriculas = new ArrayList<Matricula>();
        String sql = "select * from Matricula";

        try {
            Statement declaração = conexao.createStatement();
            ResultSet resposta = declaração.executeQuery(sql);

            while (resposta.next()) {
                int numMatricula = resposta.getInt("numMatricula");
                int codAluno = resposta.getInt("codAluno");
                int codDisciplina = resposta.getInt("codDisciplina");
                int semestre = resposta.getInt("semestre");
                int ano = resposta.getInt("ano");

                Matricula m = new Matricula(numMatricula, codAluno, codDisciplina, semestre, ano);
                matriculas.add(m);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }


        return matriculas;
    }

    public void apagarTudo() {
        String sql = "delete from Matricula";

        PreparedStatement ps;
        try {
            ps = this.conexao.prepareStatement(sql);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}