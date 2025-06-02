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
        carregarTela("gerencia/gerenciar-alunos.fxml");
    }

    @FXML
    void gerenciarCursos(ActionEvent event) throws IOException {
        carregarTela("gerencia/gerenciar-cursos.fxml");
    }

    @FXML
    void gerenciarDisciplinas(ActionEvent event) throws IOException {
        carregarTela("gerencia/gerenciar-disciplinas.fxml");
    }

    @FXML
    void gerenciarMatriculas(ActionEvent event) throws IOException {
        carregarTela("gerencia/gerenciar-matriculas.fxml");
    }

    @FXML
    void gerenciarProfs(ActionEvent event) throws IOException {
        carregarTela("gerencia/gerenciar-professores.fxml");
    }

    @FXML
    void gerenciarTurmas(ActionEvent event) throws IOException {
        carregarTela("gerencia/gerenciar-turmas.fxml");
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent conteudoDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(conteudoDaTela);
    }

}
