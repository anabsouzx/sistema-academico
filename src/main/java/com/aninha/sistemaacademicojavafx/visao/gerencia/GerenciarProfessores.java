package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOProfessor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class GerenciarProfessores implements Initializable {
    @FXML
    private TableColumn<Professor, Integer> colunaCodigoP;

    @FXML
    private TableColumn<Professor, String> colunaCpf;

    @FXML
    private TableColumn<Professor, Date> colunaData;

    @FXML
    private TableColumn<Professor, String> colunaNomeP;

    @FXML
    private TableColumn<Professor, String> colunaTel;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableView<Professor> tableProfessores;

    @FXML
    void editarProf(ActionEvent event) throws IOException{

    }

    @FXML
    void excluirProf(ActionEvent event) throws IOException{

    }

    @FXML
    void inserirProf(ActionEvent event) throws IOException{
        carregarTela("insert/inserir-professor.fxml");
    }

    private DAOProfessor daoProfessor;

    public GerenciarProfessores() {
        this.daoProfessor = new DAOProfessor();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigoAluno"));
        colunaNomeP.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataDeNascimento"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        carregarDados();
    }

    public void carregarDados() {
        ObservableList<Professor> listaDeProfessores = daoProfessor.listarProfessores(); // DAO agora usa lista
        tableProfessores.setItems(listaDeProfessores);
        tableProfessores.refresh();
    }

    public void limparPainelCentral() {
        painelPrincipal.setCenter(null);
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }

    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}