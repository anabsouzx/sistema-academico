package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarProfessores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InserirProfessor {

    @FXML
    private TextField txtNomeP;

    @FXML
    private TextField txtData;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtTel;

    private GerenciarProfessores controllerPrincipal;

    public void setControllerPrincipal(GerenciarProfessores controller) {
        this.controllerPrincipal = controller;
    }

    @FXML
    void salvarAction(ActionEvent event) {
        String nome = txtNomeP.getText();
        String dataStr = txtData.getText();
        String cpf = txtCpf.getText();
        String tel = txtTel.getText();

        if (nome.isEmpty() || dataStr.isEmpty() || cpf.isEmpty() || tel.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.", Alert.AlertType.WARNING);
            return;
        }

        // Converte data de nascimento para LocalDate
        LocalDate dataNascimento;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataNascimento = LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            mostrarAlerta("Erro na data", "Formato correto: dd/MM/yyyy", Alert.AlertType.ERROR);
            return;
        }

        Professor novoProf = new Professor(nome, dataNascimento, cpf, tel);

        DAOProfessor dao = new DAOProfessor();
        dao.adicionar(novoProf);

        mostrarAlerta("Sucesso", "Professor adicionado com sucesso!", Alert.AlertType.INFORMATION);

        if (controllerPrincipal != null) {
            controllerPrincipal.carregarDados();
            controllerPrincipal.limparPainelCentral();
        }
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        if (controllerPrincipal != null) {
            controllerPrincipal.limparPainelCentral();
        }
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(msg);
        alert.showAndWait();
    }
}
