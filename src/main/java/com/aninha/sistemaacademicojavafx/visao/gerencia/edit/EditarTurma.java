package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarTurmas;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class EditarTurma {
    @FXML
    private ComboBox<Professor> cbNomeProfessor;

    @FXML
    private ComboBox<Disciplina> cbNomeDisciplina;

    @FXML
    private ListView<Aluno> listViewAlunos;


    private Turma turmaSelecionada;
    private GerenciarTurmas controllerGerenciarTurmas;

    private DAOTurma daoTurma = new DAOTurma();
    private DAOProfessor daoProfessor = new DAOProfessor();
    private DAODisciplina daoDisciplina = new DAODisciplina();
    private DAOAluno daoAluno = new DAOAluno();
    public void setTurmaParaEditar(Turma turma, GerenciarTurmas controller) {
        this.turmaSelecionada = turma;
        this.controllerGerenciarTurmas = controller;

        popularComboBoxes();

        cbNomeProfessor.setValue(turma.getProfessor());
        cbNomeDisciplina.setValue(turma.getDisciplina());
        popularListViewAlunos(); // já popula os alunos
        selecionarAlunosDaTurma(); // seleciona os da turma

    }

    @FXML
    void salvarEdicao(ActionEvent event) {
        try {
            Professor professorSelecionado = cbNomeProfessor.getValue();
            Disciplina disciplinaSelecionada = cbNomeDisciplina.getValue();
            ObservableList<Aluno> alunosSelecionados = listViewAlunos.getSelectionModel().getSelectedItems();

            if (professorSelecionado == null || disciplinaSelecionada == null) {
                mostrarAlerta("Erro", "Professor ou Disciplina não encontrados.");
                return;
            }

            if (alunosSelecionados == null || alunosSelecionados.isEmpty()) {
                mostrarAlerta("Erro", "Selecione pelo menos um aluno para a turma.");
                return;
            }

            // Atualiza os dados da turma
            turmaSelecionada.setProfessor(professorSelecionado);
            turmaSelecionada.setDisciplina(disciplinaSelecionada);
            turmaSelecionada.setListaAlunos(new ArrayList<>(alunosSelecionados));

            if (daoTurma.atualizarTurma(turmaSelecionada)) {
                mostrarAlerta("Sucesso", "Turma atualizada com sucesso!");
                controllerGerenciarTurmas.carregarDados(); // Atualiza a tabela principal
                controllerGerenciarTurmas.limparPainelCentral(); // Fecha a tela de edição
            } else {
                mostrarAlerta("Erro", "Não foi possível encontrar a turma para atualizar.");
            }

        } catch (Exception e) {
            mostrarAlerta("Erro", "Ocorreu um erro ao tentar atualizar a turma: " + e.getMessage());
        }
    }


    @FXML
    void cancelarEdicao(ActionEvent event) {
        controllerGerenciarTurmas.limparPainelCentral();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void popularComboBoxes() {
        // Popula ComboBox de Professores
        ObservableList<Professor> professores = daoProfessor.listarProfessoresComboBox();
        cbNomeProfessor.setItems(professores);
        cbNomeProfessor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Professor professor) {
                return professor == null ? "" : professor.getNome();
            }
            @Override
            public Professor fromString(String string) { return null; }
        });

        // Popula ComboBox de Disciplinas
        ObservableList<Disciplina> disciplinas = daoDisciplina.listarDisciplinasComboBox();
        cbNomeDisciplina.setItems(disciplinas);
        cbNomeDisciplina.setConverter(new StringConverter<>() {
            @Override
            public String toString(Disciplina disciplina) {
                return disciplina == null ? "" : disciplina.getNomeDisciplina();
            }
            @Override
            public Disciplina fromString(String string) { return null; }
        });
    }
    private void popularListViewAlunos() {
        ObservableList<Aluno> alunos = daoAluno.listarTodosAlunosParaComboBox(); //

        listViewAlunos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println("Populando alunos..."); // debug

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
    private void selecionarAlunosDaTurma() {
        for (Aluno aluno : turmaSelecionada.getListaAlunos()) {
            listViewAlunos.getSelectionModel().select(aluno);
        }
    }


}
