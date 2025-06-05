package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.*;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarTurma;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GerenciarTurmas implements Initializable {

    @FXML
    private BorderPane painelPrincipal;

    // Em GerenciarTurmas.java
    @FXML
    private TableView<Turma> tableTurmas;

    @FXML
    private TableColumn<Turma, Integer> colunaCodigoTurma;

    @FXML
    private TableColumn<Turma, String> colunaProfessor; // Para exibir o nome do Professor

    @FXML
    private TableColumn<Turma, String> colunaDisciplina; // Para exibir o nome da Disciplina

    @FXML
    private TableColumn<Turma, String> colunaAlunos; // Para informações da lista de Alunos

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
        Turma selecionada = tableTurmas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // Se não estiver em uso, a exclusão é realizada normalmente
        daoTurma.excluirTurma(selecionada);
        carregarDados();
    }

    @FXML
    void inserirTurma(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-turma.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoTurma.setCellValueFactory(new PropertyValueFactory<>("codigoTurma"));
        colunaDisciplina.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
        colunaProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));

        // coluna alunos
        colunaAlunos.setCellValueFactory(cellDataFeatures -> {
            Turma turma = cellDataFeatures.getValue();
            ArrayList<Aluno> listaAlunos = turma.getListaAlunos();
            if (listaAlunos == null || listaAlunos.isEmpty()) {
                return new SimpleStringProperty("Nenhum aluno");
            } else {
                String nomes = listaAlunos.stream()
                        .map(Aluno::getNome)
                        .limit(3) // Limita para não ficar muito extenso
                        .collect(Collectors.joining("\n"));
                if (listaAlunos.size() > 3) {
                    nomes += ", ... (" + listaAlunos.size() + " total)";
                } else {
                    nomes += " (" + listaAlunos.size() + " total)";
                }
                return new SimpleStringProperty(nomes);
            }
        });

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

