package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
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
        carregarTela("edit/editar-professor.fxml");
    }

    @FXML
    void excluirProf(ActionEvent event) throws IOException{
        Professor selecionada = tableProfessores.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // --- VERIFICAÇÃO ADICIONADA ---
        // Verifica se a disciplina está sendo usada em alguma matrícula
        boolean emUso = false;
        for (Turma turma : daoTurma.listarTurmas()) {
            if (turma.getProfessor() != null && turma.getProfessor().getCodigoProfessor() == selecionada.getCodigoProfessor()) {
                emUso = true;
                break; // Encontrou uma matrícula, não precisa continuar procurando
            }
        }

        // Se estiver em uso, impede a exclusão e avisa o usuário
        if (emUso) {
            mostrarAlerta("Exclusão não permitida", "Este aluno não pode ser excluído, pois existe uma matrícula em vigor.", Alert.AlertType.ERROR);
            return;
        }
        // --- FIM DA VERIFICAÇÃO ---


        // Se não estiver em uso, a exclusão é realizada normalmente
        daoProfessor.excluirProfessor(selecionada);
        carregarDados();
    }

    @FXML
    void inserirProf(ActionEvent event) throws IOException{
        carregarTela("insert/inserir-professor.fxml");
    }

    private DAOProfessor daoProfessor;
    private DAOTurma daoTurma;

    public GerenciarProfessores() {
        this.daoProfessor = new DAOProfessor();
        this.daoTurma = new DAOTurma();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigoProfessor"));
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