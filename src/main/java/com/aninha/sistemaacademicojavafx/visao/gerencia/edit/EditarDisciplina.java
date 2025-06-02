package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
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

    // Método chamado pela tela de gerenciamento ao abrir a edição
    public void setDisciplinaParaEditar(Disciplina disciplina, GerenciarDisciplinas controllerGerenciador) {
        this.disciplinaParaEditar = disciplina;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos();
    }

    // Preenche os campos com os dados atuais da disciplina
    private void popularCampos() {
        if (disciplinaParaEditar != null) {
            txtNomeDisciplina.setText(disciplinaParaEditar.getNomeDisciplina());
            txtCargaHoraria.setText(String.valueOf(disciplinaParaEditar.getCargaHoraria()));
            txtCodCurso.setText(String.valueOf(disciplinaParaEditar.getCodCurso()));
        }
    }

    // Ação do botão "Atualizar"
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
        int codCurso;

        try {
            cargaHoraria = Integer.parseInt(cargaStr);
            codCurso = Integer.parseInt(codCursoStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Carga horária e código do curso devem ser números inteiros.", Alert.AlertType.ERROR);
            return;
        }

        // Atualiza os dados na instância
        disciplinaParaEditar.setNomeDisciplina(nome);
        disciplinaParaEditar.setCargaHoraria(cargaHoraria);
        disciplinaParaEditar.setCodCurso(codCurso);

        // Atualiza no DAO
        DAODisciplina dao = new DAODisciplina();
        if (dao.atualizarDisciplina(disciplinaParaEditar)) {
            mostrarAlerta("Sucesso", "Disciplina atualizada com sucesso!", Alert.AlertType.INFORMATION);
            controllerGerenciador.carregarDados();
            fecharTelaDeEdicao();
        } else {
            mostrarAlerta("Erro", "Falha ao atualizar a disciplina.", Alert.AlertType.ERROR);
        }
    }

    // Ação do botão "Cancelar"
    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao();
    }

    // Fecha a tela limpando o painel central
    private void fecharTelaDeEdicao() {
        if (controllerGerenciador != null) {
            controllerGerenciador.limparPainelCentral();
        }
    }

    // Método utilitário para exibir alertas
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
}
