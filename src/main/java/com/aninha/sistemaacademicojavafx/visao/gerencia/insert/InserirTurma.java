package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InserirTurma implements Initializable {
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnInserirTurma;

    @FXML
    private ComboBox<Disciplina> comboBoxDisciplina;

    @FXML
    private ComboBox<Professor> comboBoxProfessor;

    @FXML
    private ListView<Aluno> listViewAlunos;

    @FXML
    private BorderPane painelPrincipalInserirTurma;

    @FXML
    void cancelarAction(ActionEvent event) {

    }

    @FXML
    void insereTurmaAction(ActionEvent event) {
        Professor professorSelecionado = comboBoxProfessor.getSelectionModel().getSelectedItem();
        Disciplina disciplinaSelecionada = comboBoxDisciplina.getSelectionModel().getSelectedItem();
        ObservableList<Aluno> alunosSelecionadosObs = listViewAlunos.getSelectionModel().getSelectedItems();

        if (professorSelecionado == null) {
            mostrarAlerta("Validação", "Selecione um professor.", Alert.AlertType.WARNING);
            return;
        }
        if (disciplinaSelecionada == null) {
            mostrarAlerta("Validação", "Selecione uma disciplina.", Alert.AlertType.WARNING);
            return;
        }
        if (alunosSelecionadosObs == null || alunosSelecionadosObs.isEmpty()) {
            mostrarAlerta("Validação", "Selecione pelo menos um aluno para a turma.", Alert.AlertType.WARNING);
            return;
        }

        ArrayList<Aluno> alunosSelecionadosList = new ArrayList<>(alunosSelecionadosObs);

        // O construtor de Turma espera Professor, Disciplina, ArrayList<Aluno>
        Turma novaTurma = new Turma(disciplinaSelecionada, professorSelecionado, alunosSelecionadosList);

        try {
            daoTurma.adicionar(novaTurma); // DAOTurma define o codigoTurma
            mostrarAlerta("Sucesso", "Nova turma inserida com sucesso! Código: " + novaTurma.getCodigoTurma(), Alert.AlertType.INFORMATION);

            // Limpar campos após inserção bem-sucedida
            comboBoxProfessor.getSelectionModel().clearSelection();
            comboBoxDisciplina.getSelectionModel().clearSelection();
            listViewAlunos.getSelectionModel().clearSelection();

            // Opcional: Fechar a tela ou redirecionar
            // Se esta tela foi carregada no painel central de GerenciarTurmas:
            // if (controllerGerenciador != null) {
            //     controllerGerenciador.limparPainelCentralEAtualizar(); // Método a ser criado em GerenciarTurmas
            // }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Ocorreu um erro ao inserir a turma: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private DAOProfessor daoProfessor;
    private DAODisciplina daoDisciplina;
    private DAOAluno daoAluno;
    private DAOTurma daoTurma;

    public InserirTurma() {
        this.daoProfessor = new DAOProfessor();
        this.daoDisciplina = new DAODisciplina();
        this.daoAluno = new DAOAluno();
        this.daoTurma = new DAOTurma();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        popularComboBoxProfessor();
        popularComboBoxDisciplina();
        popularListViewAlunos();

        // Configurar ListView para seleção múltipla
        listViewAlunos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void popularComboBoxProfessor() {
        ObservableList<Professor> professores = daoProfessor.listarProfessoresComboBox(); //
        comboBoxProfessor.setItems(professores);
        comboBoxProfessor.setConverter(new StringConverter<Professor>() {
            @Override
            public String toString(Professor professor) {
                return (professor == null) ? "" : professor.getNome() + " (ID: " + professor.getCodigoProfessor() + ")";
            }

            @Override
            public Professor fromString(String string) {
                return comboBoxProfessor.getItems().stream()
                        .filter(p -> (p.getNome() + " (ID: " + p.getCodigoProfessor() + ")").equals(string))
                        .findFirst().orElse(null);
            }
        });
    }

    private void popularComboBoxDisciplina() {
        ObservableList<Disciplina> disciplinas = daoDisciplina.listarDisciplinasComboBox(); //
        comboBoxDisciplina.setItems(disciplinas);
        comboBoxDisciplina.setConverter(new StringConverter<Disciplina>() {
            @Override
            public String toString(Disciplina disciplina) {
                return (disciplina == null) ? "" : disciplina.getNomeDisciplina() + " (ID: " + disciplina.getCodigoDisciplina() + ")";
            }

            @Override
            public Disciplina fromString(String string) {
                return comboBoxDisciplina.getItems().stream()
                        .filter(d -> (d.getNomeDisciplina() + " (ID: " + d.getCodigoDisciplina() + ")").equals(string))
                        .findFirst().orElse(null);
            }
        });
    }

    private void popularListViewAlunos() {
        ObservableList<Aluno> alunos = daoAluno.listarTodosAlunosParaComboBox(); //
        listViewAlunos.setItems(alunos);
        // Melhorar a exibição dos alunos no ListView
        listViewAlunos.setCellFactory(param -> new ListCell<Aluno>() {
            @Override
            protected void updateItem(Aluno aluno, boolean empty) {
                super.updateItem(aluno, empty);
                if (empty || aluno == null || aluno.getNome() == null) {
                    setText(null);
                } else {
                    setText(aluno.getNome() + " (ID: " + aluno.getCodigoAluno() + ")");
                }
            }
        });
    }

    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(null);
        alert.showAndWait();
    }
}
