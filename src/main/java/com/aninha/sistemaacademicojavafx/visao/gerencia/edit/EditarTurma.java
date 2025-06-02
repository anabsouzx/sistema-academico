package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

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
        txtCodigoProfessor.setText(String.valueOf(turma.getCodigoProfessor()));
        txtCodDisciplina.setText(String.valueOf(turma.getCodDisciplina()));
        txtAno.setText(String.valueOf(turma.getAno()));
        txtSemestre.setText(String.valueOf(turma.getSemestre()));
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
            turmaSelecionada.setCodigoProfessor(codProfessor);
            turmaSelecionada.setCodDisciplina(codDisciplina);
            turmaSelecionada.setAno(ano);
            turmaSelecionada.setSemestre(semestre);

            // Aqui você pode adicionar código para atualizar no banco de dados, se necessário

            controllerGerenciarTurmas.carregarDados(); // Atualiza a tabela
            controllerGerenciarTurmas.limparPainelCentral(); // Volta para a tela principal

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
