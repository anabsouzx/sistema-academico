package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarTurmas;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class EditarTurma {
    @FXML
    private ComboBox<Professor> cbNomeProfessor;

    @FXML
    private ComboBox<Disciplina> cbNomeDisciplina;

    private Turma turmaSelecionada;
    private GerenciarTurmas controllerGerenciarTurmas;

    private DAOTurma daoTurma = new DAOTurma();
    private DAOProfessor daoProfessor = new DAOProfessor();
    private DAODisciplina daoDisciplina = new DAODisciplina();

    public void setTurmaParaEditar(Turma turma, GerenciarTurmas controller) {
        this.turmaSelecionada = turma;
        this.controllerGerenciarTurmas = controller;

        popularComboBoxes();

        cbNomeProfessor.setValue(turma.getProfessor());
        cbNomeDisciplina.setValue(turma.getDisciplina());
    }

    @FXML
    void salvarEdicao(ActionEvent event) {
        try {
            Professor professorSelecionado = cbNomeProfessor.getValue();
            Disciplina disciplinaSelecionada = cbNomeDisciplina.getValue();

            //Professor professor = daoProfessor.buscarPorCodigo(codProfessor);
            //Disciplina disciplina = daoDisciplina.buscarPorCodigo(codDisciplina);

            if (professorSelecionado == null || disciplinaSelecionada == null) {
                mostrarAlerta("Erro", "Professor ou Disciplina não encontrados.");
                return;
            }

            // Atualiza o objeto 'turmaParaEditar' com os novos dados
            turmaSelecionada.setProfessor(professorSelecionado);
            turmaSelecionada.setDisciplina(disciplinaSelecionada);

            if (daoTurma.atualizarTurma(turmaSelecionada)) {
                mostrarAlerta("Sucesso", "Turma atualizada com sucesso!");
                controllerGerenciarTurmas.carregarDados(); // Atualiza a tabela principal
                controllerGerenciarTurmas.limparPainelCentral(); // Fecha a tela de edição
            } else {
                mostrarAlerta("Erro", "Não foi possível encontrar a turma para atualizar.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Todos os campos devem ser preenchidos com números válidos.");
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
}
