package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarDisciplinas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditarDisciplina {

    @FXML
    private TextField txtNomeDisciplina;

    @FXML
    private TextField txtCargaHoraria;

    @FXML
    private TextField txtCodCurso;

    private Disciplina disciplinaParaEditar;
    private GerenciarDisciplinas controllerGerenciador;
    private DAODisciplina daoDisciplina;
    private DAOCurso daoCurso;

    public EditarDisciplina() {
        this.daoDisciplina = new DAODisciplina();
        this.daoCurso = new DAOCurso();
    }

    public void setDisciplinaParaEditar(Disciplina disciplina, GerenciarDisciplinas controllerGerenciador) {
        this.disciplinaParaEditar = disciplina;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos();
    }

    private void popularCampos() {
        if (disciplinaParaEditar != null) {
            txtNomeDisciplina.setText(disciplinaParaEditar.getNomeDisciplina());
            txtCargaHoraria.setText(String.valueOf(disciplinaParaEditar.getCargaHoraria()));
            if (disciplinaParaEditar.getCurso() != null) {
                txtCodCurso.setText(String.valueOf(disciplinaParaEditar.getCurso().getCodigoCurso()));
            } else {
                txtCodCurso.setText("");
            }
        }
    }

    @FXML
    void atualizaDisciplinaAction(ActionEvent event) {
        String nome = txtNomeDisciplina.getText();
        String cargaStr = txtCargaHoraria.getText();
        String codCursoStr = txtCodCurso.getText();

        if (nome.isEmpty() || cargaStr.isEmpty() || codCursoStr.isEmpty()) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(cargaStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Carga horária deve ser um número inteiro.", Alert.AlertType.ERROR);
            txtCargaHoraria.requestFocus();
            return;
        }

        int codigoCurso;
        try {
            codigoCurso = Integer.parseInt(codCursoStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Código do curso deve ser um número inteiro.", Alert.AlertType.ERROR);
            txtCodCurso.requestFocus();
            return;
        }

        Curso curso = daoCurso.buscarPorCodigo(codigoCurso);
        if (curso == null) {
            mostrarAlerta("Curso não encontrado", "Nenhum curso foi encontrado com o código informado.", Alert.AlertType.ERROR);
            txtCodCurso.requestFocus();
            return;
        }

        disciplinaParaEditar.setNomeDisciplina(nome);
        disciplinaParaEditar.setCargaHoraria(cargaHoraria);
        disciplinaParaEditar.setCurso(curso);

        if (daoDisciplina.atualizarDisciplina(disciplinaParaEditar)) {
            mostrarAlerta("Sucesso", "Disciplina atualizada com sucesso!", Alert.AlertType.INFORMATION);
            if (controllerGerenciador != null) {
                controllerGerenciador.carregarDados();
            }
            fecharTelaDeEdicao();
        } else {
            mostrarAlerta("Erro", "Falha ao atualizar a disciplina.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao();
    }

    private void fecharTelaDeEdicao() {
        if (controllerGerenciador != null) {
            controllerGerenciador.limparPainelCentral();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
}
