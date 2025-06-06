package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarDisciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GerenciarDisciplinas implements Initializable {

    @FXML
    private TableColumn<Disciplina, Integer> colunaCargaH;

    @FXML
    private TableColumn<Disciplina, String> colunaCodigoC;

    @FXML
    private TableColumn<Disciplina, Integer> colunaCodigoD;

    @FXML
    private TableColumn<Disciplina, String> colunaNomeD;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableView<Disciplina> tableDisciplinas;

    @FXML
    private ComboBox<Curso> comboCursos;

    private final DAODisciplina daoDisciplina = new DAODisciplina();
    private final DAOTurma daoTurma = new DAOTurma();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoD.setCellValueFactory(new PropertyValueFactory<>("codigoDisciplina"));
        colunaNomeD.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
        colunaCargaH.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

        colunaCodigoC.setCellValueFactory(cellDataFeatures -> {
            Disciplina disciplina = cellDataFeatures.getValue();
            Curso curso = disciplina.getCurso();
            if (curso != null) {
                return new SimpleStringProperty(curso.getNomeCurso() + " (ID: " + curso.getCodigoCurso() + ")");
            }
            return new SimpleStringProperty("Curso não especificado");
        });

        carregarDados();
    }

    @FXML
    void inserirDisciplina(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-disciplina.fxml");
    }

    @FXML
    void editarDisciplina(ActionEvent event) {
        Disciplina selecionada = tableDisciplinas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para editar.", Alert.AlertType.WARNING);
            return;
        }
        abrirTelaEditarDisciplina(selecionada);
    }

    @FXML
    void excluirDisciplina(ActionEvent event) {
        Disciplina selecionada = tableDisciplinas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para excluir.", Alert.AlertType.WARNING);
            return;
        }

        boolean emUso = false;
        for (Turma turma : daoTurma.listarTurmas()) {
            if (turma.getDisciplina() != null &&
                    turma.getDisciplina().getCodigoDisciplina() == selecionada.getCodigoDisciplina()) {
                emUso = true;
                break;
            }
        }

        if (emUso) {
            mostrarAlerta("Exclusão não permitida", "Esta disciplina não pode ser excluída, pois está associada a uma turma.", Alert.AlertType.ERROR);
            return;
        }

        daoDisciplina.excluirDisciplina(selecionada);
        carregarDados();
    }

    public void carregarDados() {
        ObservableList<Disciplina> lista = daoDisciplina.listarDisciplinas();
        tableDisciplinas.setItems(lista);
        tableDisciplinas.refresh();
    }

    public void limparPainelCentral() {
        painelPrincipal.setCenter(null);
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent conteudoDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(conteudoDaTela);
    }

    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }

    public void abrirTelaEditarDisciplina(Disciplina disciplinaSelecionada) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aninha/sistemaacademicojavafx/visao/gerencia/edit/editar-disciplina.fxml"));
            Parent root = loader.load();

            EditarDisciplina editarDisciplinaController = loader.getController();
            editarDisciplinaController.setDisciplinaParaEditar(disciplinaSelecionada, this);

            exibirNoPainelCentral(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exibirNoPainelCentral(Parent tela) {
        painelPrincipal.setCenter(tela);
    }
}
