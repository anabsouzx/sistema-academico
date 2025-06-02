package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InserirAluno {
    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtData;

    @FXML
    private TextField txtNomeA;

    @FXML
    private TextField txtTel;

    DAOAluno daoAluno;

    public InserirAluno(){
        this.daoAluno = new DAOAluno();
    }

    @FXML
    void insereAluno(ActionEvent event) {
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
        Date dataD = null;
        String formato = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);

        try {
            dataD = sdf.parse(data);
        } catch (ParseException e) {
            Alert dataA = new Alert(Alert.AlertType.ERROR);
            dataA.setTitle("Data Incorreta");
            dataA.setHeaderText("O campo data não foi preenchido corretamente");
            dataA.setContentText("Formato correto: dd/MM/yyyy. Ex: 25/12/2000");
            dataA.showAndWait();
            txtData.requestFocus();
            return;
        }

        // criar aluno
        Aluno aluno = new Aluno(nome, dataD, cpf, tel);

        // add na lista
        try {
            daoAluno.adicionar(aluno); // O DAO agora lida com a lista

            // Feedback para o utilizador
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("Aluno inserido");
            sucesso.setContentText("O aluno " + nome + " foi inserido com sucesso!");
            sucesso.showAndWait();

            txtNomeA.setText("");
            txtData.setText("");
            txtCpf.setText("");
            txtTel.setText("");
            txtNomeA.requestFocus(); // Foco no primeiro campo para nova inserção
        } catch (Exception e) {
            e.printStackTrace();
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Erro ao inserir aluno");
            erro.setContentText("Ocorreu um erro inesperado: " + e.getMessage());
            erro.showAndWait();
        }

        txtNomeA.clear();
        txtData.clear();
        txtCpf.clear();
        txtTel.clear();
    }
}
