package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarCursos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditarCurso {

    @FXML
    private TextField txtNomeCurso;

    @FXML
    private TextField txtDuracao;

    private Curso cursoParaEditar;
    private GerenciarCursos controllerGerenciador;
    private DAOCurso daoCurso;

    public EditarCurso() {
        this.daoCurso = new DAOCurso();
    }

    // Método chamado pelo GerenciarCursos para carregar os dados do curso selecionado
    public void setCursoParaEditar(Curso curso, GerenciarCursos controllerGerenciador) {
        this.cursoParaEditar = curso;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos();
    }

    // Preenche os campos do formulário com os dados do curso
    private void popularCampos() {
        if (cursoParaEditar != null) {
            txtNomeCurso.setText(cursoParaEditar.getNomeCurso());
            txtDuracao.setText(String.valueOf(cursoParaEditar.getDuracao()));
        }
    }

    // Ação do botão "Atualizar"
    @FXML
    void atualizaCursoAction(ActionEvent event) {
        String nome = txtNomeCurso.getText();
        String duracaoStr = txtDuracao.getText();

        if (nome.isEmpty() || duracaoStr.isEmpty()) {
            mostrarAlerta("Campos Vazios", "Todos os campos devem ser preenchidos.", Alert.AlertType.WARNING);
            return;
        }

        int duracao;
        try {
            duracao = Integer.parseInt(duracaoStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Duração inválida", "A duração deve ser um número inteiro.", Alert.AlertType.ERROR);
            txtDuracao.requestFocus();
            return;
        }

        cursoParaEditar.setNomeCurso(nome);
        cursoParaEditar.setDuracao(duracao);

        if (daoCurso.atualizarCurso(cursoParaEditar)) {
            mostrarAlerta("Sucesso", "Curso atualizado com sucesso!", Alert.AlertType.INFORMATION);
            if (controllerGerenciador != null) {
                controllerGerenciador.carregarDados(); // Atualiza a tabela
            }
            fecharTelaDeEdicao();
        } else {
            mostrarAlerta("Erro", "Não foi possível atualizar o curso. Curso não encontrado.", Alert.AlertType.ERROR);
        }
    }

    // Ação do botão "Cancelar"
    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao();
    }

    // Fecha a tela de edição limpando o painel central
    private void fecharTelaDeEdicao() {
        if (controllerGerenciador != null) {
            controllerGerenciador.limparPainelCentral(); // Deve existir no GerenciarCursos
        }
    }

    // Método utilitário para mostrar alertas
    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}
