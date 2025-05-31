package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOMatricula;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GerenciarMatriculas implements Initializable {
    @FXML
    private TableColumn<Matricula, Integer> colunaAno;

    @FXML
    private TableColumn<Matricula, Integer> colunaCodA;

    @FXML
    private TableColumn<Matricula, Integer> colunaCodDisc;

    @FXML
    private TableColumn<Matricula, Integer> colunaNumero;

    @FXML
    private TableColumn<Matricula, Integer> colunaSemestre;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableView<Matricula> tableMatriculas;

    @FXML
    void inserirMatricula(ActionEvent event) throws IOException{
        carregarTela("insert/inserir-matricula.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaNumero.setCellValueFactory(new PropertyValueFactory<>("numMatricula"));
        colunaCodA.setCellValueFactory(new PropertyValueFactory<>("codAluno"));
        colunaCodDisc.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
        colunaSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));

        carregarDados();
    }

    private void carregarDados() {

    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }
}
