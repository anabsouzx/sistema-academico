package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class InserirCurso {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TextField txtDuracao;

    @FXML
    private TextField txtNomeC;

    DAOCurso daoCurso;

    public InserirCurso(){
        this.daoCurso = new DAOCurso();
    }

    @FXML
    void insereCurso(ActionEvent event) {
        String nome = txtNomeC.getText();
        String duracaoStr = txtDuracao.getText();
        int codigo = 0;

        if(nome.isEmpty() || duracaoStr.isEmpty()){
            Alert vazio = new Alert(Alert.AlertType.WARNING);
            vazio.setTitle("Conteúdo vazio");
            vazio.setHeaderText("Todos os campos precisam ser preenchidos");
            vazio.setContentText("Certifique-se que blablabla");
            vazio.show();
            return;
        }

        // converter para int
        int duracao;

        try {
            duracao = Integer.parseInt(duracaoStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Número inválido");
            alert.setContentText("Certifique-se de digitar um número no campo Duração.");
            alert.show();
            txtDuracao.requestFocus();
            return;
        }

        //criar curso
        Curso curso = new Curso(nome, duracao);

        // adiçao na tabela
        try {
            daoCurso.adicionar(curso); // O DAO agora lida com a lista

            // Feedback para o utilizador
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("Curso inserido");
            sucesso.setContentText("O Curso " + nome + " foi inserido com sucesso!");
            sucesso.showAndWait();

            txtNomeC.setText("");
            txtDuracao.setText("");
            txtNomeC.requestFocus(); // Foco no primeiro campo para nova inserção
        } catch (Exception e) {
            e.printStackTrace();
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Erro ao inserir Curso");
            erro.setContentText("Ocorreu um erro inesperado: " + e.getMessage());
            erro.showAndWait();
        }
    }
}
