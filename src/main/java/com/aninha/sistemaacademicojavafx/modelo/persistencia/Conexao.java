package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection conexao;

    public Connection abrirConexao(){
        String url="jdbc:mysql://localhost:3306/sistemaacademico?useTimezone=true&serverTimezone=UTC";
        String user="root";
        String pass="";

        try {
            conexao = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conexao;
    }

    public void fecharConexao(){
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public Conexao() {
        abrirConexao();
    }

    

}
