package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarTurmas;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class EditarTurma {

    @FXML private TextField txtCodigoTurma;
    @FXML private TextField txtAno;
    @FXML private TextField txtSemestre;
    @FXML private ComboBox<Professor> comboBoxProfessor;
    @FXML private ComboBox<Disciplina> comboBoxDisciplina;

    private Turma turmaSelecionada;
    private GerenciarTurmas controllerGerenciarTurmas;

    private final DAOProfessor daoProfessor = new DAOProfessor();
    private final DAODisciplina daoDisciplina = new DAODisciplina();

    public void setTurmaParaEditar(Turma turma, GerenciarTurmas controller) {
        this.turmaSelecionada = turma;
        this.controllerGerenciarTurmas = controller;

        txtCodigoTurma.setText(String.valueOf(turma.getCodigoTurma()));
        txtAno.setText(String.valueOf(turma.getDisciplina().getCargaHoraria())); // Exemplo de uso
        txtSemestre.setText(String.valueOf(1)); // Ajuste conforme seu modelo

        popularComboBoxes();

        comboBoxProfessor.setValue(turma.getProfessor());
        comboBoxDisciplina.setValue(turma.getDisciplina());
    }

    private void popularComboBoxes() {
        ObservableList<Professor> professores = daoProfessor.listarProfessores();
        comboBoxProfessor.setItems(professores);
        comboBoxProfessor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Professor prof) {
                return prof != null ? prof.getNome() : "";
            }

            @Override
            public Professor fromString(String string) {
                return professores.stream().filter(p -> p.getNome().equals(string)).findFirst().orElse(null);
            }
        });

        ObservableList<Disciplina> disciplinas = daoDisciplina.listarDisciplinas();
        comboBoxDisciplina.setItems(disciplinas);
        comboBoxDisciplina.setConverter(new StringConverter<>() {
            @Override
            public String toString(Disciplina disc) {
                return disc != null ? disc.getNomeDisciplina() : "";
            }

            @Override
            public Disciplina fromString(String string) {
                return disciplinas.stream().filter(d -> d.getNomeDisciplina().equals(string)).findFirst().orElse(null);
            }
        });
    }

    @FXML
    void salvarEdicao(ActionEvent event) {
        try {
            int codigoTurma = Integer.parseInt(txtCodigoTurma.getText());
            int ano = Integer.parseInt(txtAno.getText());
            int semestre = Integer.parseInt(txtSemestre.getText());
            Professor professor = comboBoxProfessor.getValue();
            Disciplina disciplina = comboBoxDisciplina.getValue();

            if (professor == null || disciplina == null) {
                mostrarAlerta("Campos obrigatórios", "Selecione um professor e uma disciplina.");
                return;
            }

            turmaSelecionada.setCodigoTurma(codigoTurma);
            turmaSelecionada.setProfessor(professor);
            turmaSelecionada.setDisciplina(disciplina);
            // Aqui você pode salvar ano/semestre se estiverem na classe Turma

            controllerGerenciarTurmas.carregarDados();
            controllerGerenciarTurmas.limparPainelCentral();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Código da turma, ano e semestre devem ser números válidos.");
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
}