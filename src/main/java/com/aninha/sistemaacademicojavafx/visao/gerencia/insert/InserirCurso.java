package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOCurso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class InserirCurso {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TextField txtDuracao;

    @FXML
    private TextField txtNomeC;

    @FXML
    void insereCurso(ActionEvent event) {
        String nome = txtNomeC.getText();
        String duracaoStr = txtDuracao.getText();
        int codigo = 0;

        if(nome.isEmpty() || duracaoStr.isEmpty()){
            System.out.println("ta vazio mancho");
            return;
        }

        // converter para int
        int duracao;

        try {
            duracao = Integer.parseInt(duracaoStr);
        } catch (NumberFormatException e) {
            System.out.println("ei man isso né um numero não");
            return;
        }

        // adiçao na tabela mysql

    }
}
