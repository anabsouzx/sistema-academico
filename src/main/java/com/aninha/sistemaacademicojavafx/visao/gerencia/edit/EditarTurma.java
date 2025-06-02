package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.modelo.Turma;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarTurmas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditarTurma {

    @FXML
    private TextField txtCodigoTurma;

    @FXML
    private TextField txtCodigoProfessor;

    @FXML
    private TextField txtCodDisciplina;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtSemestre;

    private Turma turmaSelecionada;
    private GerenciarTurmas controllerGerenciarTurmas;

    public void setTurmaParaEditar(Turma turma, GerenciarTurmas controller) {
        this.turmaSelecionada = turma;
        this.controllerGerenciarTurmas = controller;

        txtCodigoTurma.setText(String.valueOf(turma.getCodigoTurma()));
        txtCodigoProfessor.setText(String.valueOf(turma.getProfessor().getCodigoProfessor()));
        txtCodDisciplina.setText(String.valueOf(turma.getDisciplina().getCodigoDisciplina()));
        txtAno.setText(String.valueOf(turma.getAno())); // Supondo que ano foi adicionado
        txtSemestre.setText(String.valueOf(turma.getSemestre())); // idem
    }

    @FXML
    void salvarEdicao(ActionEvent event) {
        try {
            int codTurma = Integer.parseInt(txtCodigoTurma.getText());
            int codProfessor = Integer.parseInt(txtCodigoProfessor.getText());
            int codDisciplina = Integer.parseInt(txtCodDisciplina.getText());
            int ano = Integer.parseInt(txtAno.getText());
            int semestre = Integer.parseInt(txtSemestre.getText());

            turmaSelecionada.setCodigoTurma(codTurma);
            //turmaSelecionada.setProfessor(new Professor(codProfessor));
            turmaSelecionada.setDisciplina(new Disciplina(codDisciplina));
            turmaSelecionada.setAno(ano);
            turmaSelecionada.setSemestre(semestre);

            controllerGerenciarTurmas.carregarDados();
            controllerGerenciarTurmas.limparPainelCentral();

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
}
