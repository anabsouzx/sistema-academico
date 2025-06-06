package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarDisciplinas;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditarDisciplina {

    @FXML
    private TextField txtNomeDisciplina;

    @FXML
    private TextField txtCargaHoraria;

    @FXML
    private ComboBox<Curso> comboCursos;


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
        carregarCursos();
        popularCampos();
    }

    private void carregarCursos() {
        ObservableList<Curso> cursos = daoCurso.listarCursos();
        comboCursos.setItems(cursos);
    }

    private void popularCampos() {
        if (disciplinaParaEditar != null) {
            txtNomeDisciplina.setText(disciplinaParaEditar.getNomeDisciplina());
            txtCargaHoraria.setText(String.valueOf(disciplinaParaEditar.getCargaHoraria()));

            if (disciplinaParaEditar.getCurso() != null) {
                comboCursos.getSelectionModel().select(disciplinaParaEditar.getCurso());
            }
        }
    }

    @FXML
    void atualizaDisciplinaAction(ActionEvent event) {
        String nome = txtNomeDisciplina.getText();
        String cargaStr = txtCargaHoraria.getText();
        Curso cursoSelecionado = comboCursos.getSelectionModel().getSelectedItem();

        if (nome.isEmpty() || cargaStr.isEmpty() || cursoSelecionado == null) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos e selecione um curso.", Alert.AlertType.WARNING);
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

        disciplinaParaEditar.setNomeDisciplina(nome);
        disciplinaParaEditar.setCargaHoraria(cargaHoraria);
        disciplinaParaEditar.setCurso(cursoSelecionado);

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
