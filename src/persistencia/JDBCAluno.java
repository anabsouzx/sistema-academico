package persistencia;

import modelo.Aluno;

import java.sql.*;
import java.util.ArrayList;

public class JDBCAluno {

    Connection conexao;

    public JDBCAluno(Connection conexao) {
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

    public ArrayList<Aluno> listarAlunos(){
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
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
