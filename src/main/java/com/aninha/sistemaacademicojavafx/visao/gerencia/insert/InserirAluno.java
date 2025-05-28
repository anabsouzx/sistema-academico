package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InserirAluno {
    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtData;

    @FXML
    private TextField txtNomeA;

    @FXML
    private TextField txtTel;

    @FXML
    void insereAluno(ActionEvent event) {
        String nome = txtNomeA.getText();
        String data = txtData.getText();
        String cpf = txtCpf.getText();
        String tel = txtTel.getText();

        if(nome.isEmpty() || data.isEmpty() || cpf.isEmpty() || tel.isEmpty()){
            System.out.println("mensagem de erro aqui");
            return;
        }

        // converter data
        java.sql.Date dataSql = null;
        String formato = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);

        try{
            java.util.Date utilDate = sdf.parse(data);
            dataSql = new java.sql.Date(utilDate.getTime());
        }catch (ParseException e){
            System.out.println("ei man");
            txtData.requestFocus();
            return;
        }

        // inserir na tabela
        Aluno aluno = new Aluno(nome,dataSql,cpf,tel);

        Conexao conexao = null;
        try{
            conexao = new Conexao();
            DAOAluno daoAluno = new DAOAluno(conexao.getConexao());

            daoAluno.inserirAluno(aluno);

            txtNomeA.setText("");
            txtData.setText("");
            txtCpf.setText("");
            txtTel.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conexao!=null){
                conexao.fecharConexao();
            }
        }
    }
}
