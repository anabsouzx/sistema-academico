package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarAlunos; // Para poder chamar um método de recarregar
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane; // Para fechar a tela de edição

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditarAluno {
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtNomeA;
    @FXML
    private TextField txtTel;

    private Aluno alunoParaEditar;
    private GerenciarAlunos controllerGerenciador; // Para chamar o refresh da tabela principal

    public void setAlunoParaEditar(Aluno aluno, GerenciarAlunos controllerGerenciador) {
        this.alunoParaEditar = aluno;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos();
    }

    private void popularCampos() {
        /*if (alunoParaEditar != null) {
            txtNomeA.setText(alunoParaEditar.getNomeAluno());
            // formatar a data para exibição dd/MM/yyyy
            if (alunoParaEditar.getDataNasc() != null) {
                SimpleDateFormat sdfExibicao = new SimpleDateFormat("dd/MM/yyyy");
                txtData.setText(sdfExibicao.format(alunoParaEditar.getDataNasc()));
            }
            txtCpf.setText(alunoParaEditar.getCpf());
            txtTel.setText(alunoParaEditar.getTelefone());
        }*/
    }

    @FXML
    void atualizaAlunoAction(ActionEvent event) {
        String nome = txtNomeA.getText();
        String dataStr = txtData.getText();
        String cpf = txtCpf.getText();
        String tel = txtTel.getText();

        if (nome.isEmpty() || dataStr.isEmpty() || cpf.isEmpty() || tel.isEmpty()) {
            mostrarAlerta("Conteúdo vazio", "Todos os campos precisam ser preenchidos", Alert.AlertType.WARNING);
            return;
        }

        java.sql.Date dataSql = null;
        SimpleDateFormat sdfParse = new SimpleDateFormat("dd/MM/yyyy");
        sdfParse.setLenient(false);
        try {
            java.util.Date utilDate = sdfParse.parse(dataStr);
            dataSql = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            mostrarAlerta("Data Incorreta", "Formato correto: dd/MM/yyyy", Alert.AlertType.ERROR);
            txtData.requestFocus();
            return;
        }

        //alunoParaEditar.setNomeAluno(nome);
        //alunoParaEditar.setDataNasc(dataSql);
        //alunoParaEditar.setCpf(cpf);
        //alunoParaEditar.setTelefone(tel);

        /*Conexao conexao = null;
        try {
            conexao = new Conexao();
            DAOAluno daoAluno = new DAOAluno(conexao.getConexao());
            if (daoAluno.atualizarAluno(alunoParaEditar)) {
                mostrarAlerta("Sucesso", "Aluno atualizado com sucesso!", Alert.AlertType.INFORMATION);
                if (controllerGerenciador != null) {
                    controllerGerenciador.carregarDados();
                }
                fecharTelaDeEdicao();
            } else {
                mostrarAlerta("Erro", "Não foi possível atualizar o aluno.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro Crítico", "Ocorreu um erro inesperado.", Alert.AlertType.ERROR);
        } finally {
            if (conexao != null) {
                conexao.fecharConexao();
            }
        }*/
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao();
    }

    private void fecharTelaDeEdicao() {
        // Para fechar a tela, assumindo que ela está no centro de um BorderPane no GerenciarAlunos
        // Precisamos de uma referência ao painel principal de GerenciarAlunos,
        // ou uma maneira mais robusta de gerenciar as telas.
        // Uma forma simples é o controllerGerenciador limpar o centro do seu painel.
        if (controllerGerenciador != null) {
            controllerGerenciador.limparPainelCentral(); // Método a ser criado
        }
    }


    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}