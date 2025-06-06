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
    private BorderPane painelPrincipal; // Painel principal onde as telas serão carregadas dinamicamente

    @FXML
    void abrirCreditos(ActionEvent event) throws IOException {
        // Carrega a tela de créditos dentro do painel principal
        carregarTela("creditos.fxml");
    }

    @FXML
    void fecharApp(ActionEvent event) {
        // Fecha o aplicativo
        System.exit(0);
    }

    @FXML
    void gerenciarAlunos(ActionEvent event) throws IOException {
        // Carrega a tela de gerenciamento de alunos
        carregarTela("gerencia/gerenciar-alunos.fxml");
    }

    @FXML
    void gerenciarCursos(ActionEvent event) throws IOException {
        // Carrega a tela de gerenciamento de cursos
        carregarTela("gerencia/gerenciar-cursos.fxml");
    }

    @FXML
    void gerenciarDisciplinas(ActionEvent event) throws IOException {
        // Carrega a tela de gerenciamento de disciplinas
        carregarTela("gerencia/gerenciar-disciplinas.fxml");
    }

    @FXML
    void gerenciarMatriculas(ActionEvent event) throws IOException {
        // Carrega a tela de gerenciamento de matrículas
        carregarTela("gerencia/gerenciar-matriculas.fxml");
    }

    @FXML
    void gerenciarProfs(ActionEvent event) throws IOException {
        // Carrega a tela de gerenciamento de professores
        carregarTela("gerencia/gerenciar-professores.fxml");
    }

    @FXML
    void gerenciarTurmas(ActionEvent event) throws IOException {
        // Carrega a tela de gerenciamento de turmas
        carregarTela("gerencia/gerenciar-turmas.fxml");
    }

    // Método genérico que carrega uma tela FXML no painel central
    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile)); // Carrega o arquivo FXML informado
        Parent conteudoDaTela = fxmlLoader.load(); // Carrega os elementos da tela
        painelPrincipal.setCenter(conteudoDaTela); // Exibe a tela no centro do BorderPane
    }
}
