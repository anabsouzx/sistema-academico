package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarDisciplina;
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
import java.util.ResourceBundle;

public class GerenciarDisciplinas implements Initializable {

    @FXML
    private TableColumn<Disciplina, Integer> colunaCargaH;

    @FXML
    private TableColumn<Disciplina, Integer> colunaCodigoC;

    @FXML
    private TableColumn<Disciplina, Integer> colunaCodigoD;

    @FXML
    private TableColumn<Disciplina, String> colunaNomeD;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableView<Disciplina> tableDisciplinas;

    private DAODisciplina daoDisciplina;

    public GerenciarDisciplinas() {
        this.daoDisciplina = new DAODisciplina();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Mapeamento das colunas com os atributos da classe Disciplina
        colunaCodigoD.setCellValueFactory(new PropertyValueFactory<>("codigoDisciplina"));
        colunaNomeD.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
        colunaCargaH.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        colunaCodigoC.setCellValueFactory(new PropertyValueFactory<>("codCurso"));

        carregarDados();
    }

    @FXML
    void inserirDisciplina(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-disciplina.fxml");
    }

    @FXML
    void editarDisciplina(ActionEvent event) throws IOException {
        Disciplina selecionada = tableDisciplinas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit/editar-disciplina.fxml"));
            Parent telaEdicao = loader.load();

            EditarDisciplina controllerEdicao = loader.getController();
            controllerEdicao.setDisciplinaParaEditar(selecionada, this);

            painelPrincipal.setCenter(telaEdicao);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao carregar a tela de edição.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void excluirDisciplina(ActionEvent event) {
        Disciplina selecionada = tableDisciplinas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para excluir.", Alert.AlertType.WARNING);
            return;
        }

        daoDisciplina.excluirDisciplina(selecionada);
        carregarDados();
    }

    public void carregarDados() {
        ObservableList<Disciplina> lista = daoDisciplina.listarDisciplinas();
        tableDisciplinas.setItems(lista);
        if(lista.isEmpty()){
            Curso c = new Curso("gostrofenofe", 8);
            Disciplina d = new Disciplina("boquete parafuso",c,1);
            daoDisciplina.adicionar(d);
        }
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
}
