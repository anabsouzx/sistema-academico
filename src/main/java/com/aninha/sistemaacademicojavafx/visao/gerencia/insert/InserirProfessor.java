package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InserirProfessor {
    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtData;

    @FXML
    private TextField txtNomeP;

    @FXML
    private TextField txtTel;

    DAOProfessor daoProfessor;

    public InserirProfessor(){
        this.daoProfessor = new DAOProfessor();
    }

    @FXML
    void insereProf(ActionEvent event) {
        String nome = txtNomeP.getText();
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
        LocalDate dataD = null;
        String formato = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            dataD = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            Alert dataA = new Alert(Alert.AlertType.ERROR);
            dataA.setTitle("Data Incorreta");
            dataA.setHeaderText("O campo data não foi preenchido corretamente");
            dataA.setContentText("Formato correto: dd/MM/yyyy. Ex: 25/12/2000");
            dataA.showAndWait();
            txtData.requestFocus();
            return;
        }

        // criar aluno
        Professor professor = new Professor(nome, dataD, cpf, tel);

        // add na lista
        try {
            daoProfessor.adicionar(professor); // O DAO agora lida com a lista

            // Feedback para o utilizador
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("Professor inserido");
            sucesso.setContentText("O professor " + nome + " foi inserido com sucesso!");
            sucesso.showAndWait();

            txtNomeP.setText("");
            txtData.setText("");
            txtCpf.setText("");
            txtTel.setText("");
            txtNomeP.requestFocus(); // Foco no primeiro campo para nova inserção
        } catch (Exception e) {
            e.printStackTrace();
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Erro ao inserir aluno");
            erro.setContentText("Ocorreu um erro inesperado: " + e.getMessage());
            erro.showAndWait();
        }

        txtNomeP.clear();
        txtData.clear();
        txtCpf.clear();
        txtTel.clear();
    }
}
