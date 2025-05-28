package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        int codigo = 0;
        String nome = txtNomeA.getText();
        String data = txtData.getText();
        String cpf = txtCpf.getText();
        String tel = txtTel.getText();

        if(nome.isEmpty() || data.isEmpty() || cpf.isEmpty() || tel.isEmpty()){
            Alert vazio = new Alert(Alert.AlertType.WARNING);
            vazio.setTitle("Conteúdo vazio");
            vazio.setHeaderText("Todos os campos precisam ser preenchidos");
            vazio.setContentText("Certifique-se que blablabla");
            vazio.show();
            // escrever mensagem de erro bonitinho nao aguento mais
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
            Alert dataA = new Alert(Alert.AlertType.ERROR);
            dataA.setTitle("Data Incorreta");
            dataA.setHeaderText("O campo data não foi preenchido corretamente");
            dataA.setContentText("Formato correto:dd/MM/yyyy");
            dataA.show();
            txtData.requestFocus();
            return;
        }

        // inserir na tabela
        Aluno aluno = new Aluno(codigo,nome,dataSql,cpf,tel);

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
