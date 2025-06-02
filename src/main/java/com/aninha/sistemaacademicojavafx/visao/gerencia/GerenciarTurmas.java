package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Turma;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarTurma;
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
import java.util.ResourceBundle;

public class GerenciarTurmas implements Initializable {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableView<Turma> tableTurmas;

    @FXML
    private TableColumn<Turma, Integer> colunaCodigo;

    @FXML
    private TableColumn<Turma, Integer> colunaDisciplina;

    @FXML
    private TableColumn<Turma, Integer> colunaProfessor;

    @FXML
    private TableColumn<Turma, Integer> colunaAno;

    @FXML
    private TableColumn<Turma, Integer> colunaSemestre;

    private DAOTurma daoTurma;

    public GerenciarTurmas() {
        this.daoTurma = new DAOTurma();
    }

    @FXML
    void editarTurma(ActionEvent event) {
        Turma turmaSelecionada = tableTurmas.getSelectionModel().getSelectedItem();

        if (turmaSelecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma turma para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit/editar-turma.fxml"));
            Parent telaEdicao = loader.load();

            EditarTurma controllerEdicao = loader.getController();
            controllerEdicao.setTurmaParaEditar(turmaSelecionada, this);

            painelPrincipal.setCenter(telaEdicao);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao Carregar Tela", "Não foi possível carregar a tela de edição.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void excluirTurma(ActionEvent event) {
        Turma turmaSelecionada = tableTurmas.getSelectionModel().getSelectedItem();

        if (turmaSelecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma turma para excluir.", Alert.AlertType.WARNING);
            return;
        }

        daoTurma.excluirTurma(turmaSelecionada);
        carregarDados();
    }

    @FXML
    void inserirTurma(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-turma.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoTurma"));
        colunaDisciplina.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
        colunaProfessor.setCellValueFactory(new PropertyValueFactory<>("codProfessor"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colunaSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));

        carregarDados();
    }

    public void carregarDados() {
        ObservableList<Turma> listaTurmas = daoTurma.listarTurmas();
        tableTurmas.setItems(listaTurmas);
        tableTurmas.refresh();
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

