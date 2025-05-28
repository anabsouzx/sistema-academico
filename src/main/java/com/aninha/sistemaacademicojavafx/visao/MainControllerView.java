package com.aninha.sistemaacademicojavafx.visao;

import com.aninha.sistemaacademicojavafx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainControllerView {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    void abrirCreditos(ActionEvent event) throws IOException {
        carregarTela("creditos.fxml");
    }

    @FXML
    void fecharApp(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void gerenciarAlunos(ActionEvent event) throws IOException {
        carregarTela("gerenciar-alunos.fxml");
    }

    @FXML
    void gerenciarCursos(ActionEvent event) {

    }

    @FXML
    void gerenciarDisciplinas(ActionEvent event) {

    }

    @FXML
    void gerenciarMatriculas(ActionEvent event) {

    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent conteudoDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(conteudoDaTela);
    }

}
