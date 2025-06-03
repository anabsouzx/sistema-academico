package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarAlunos; // Para poder chamar um método de recarregar
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    private DAOAluno daoAluno; // interagir com dados

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EditarAluno(){
        this.daoAluno = new DAOAluno();
    }

    public void setAlunoParaEditar(Aluno aluno, GerenciarAlunos controllerGerenciador) {
        this.alunoParaEditar = aluno;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos();
    }

    private void popularCampos() {
        if (alunoParaEditar != null) {
            txtNomeA.setText(alunoParaEditar.getNome());
            if (alunoParaEditar.getDataDeNascimento() != null) {
                txtData.setText(alunoParaEditar.getDataDeNascimento().format(dateFormatter));
            } else {
                txtData.setText("");
            }
            txtCpf.setText(alunoParaEditar.getCpf());
            txtTel.setText(alunoParaEditar.getTelefone());
        }
    }

    @FXML
    void atualizaAlunoAction(ActionEvent event) {
        String nome = txtNomeA.getText();
        String dataStr = txtData.getText();
        String cpf = txtCpf.getText();
        String tel = txtTel.getText();

        if (nome.isEmpty() || dataStr.isEmpty() || cpf.isEmpty() || tel.isEmpty()) {
            mostrarAlerta("Campos Vazios", "Todos os campos precisam ser preenchidos.", Alert.AlertType.WARNING);
            return;
        }

        LocalDate dataDeNascimento = null;
        try {
            dataDeNascimento = LocalDate.parse(dataStr, dateFormatter);
        } catch (DateTimeParseException e) {
            mostrarAlerta("Data Inválida", "O formato da data deve ser dd/MM/yyyy.", Alert.AlertType.ERROR);
            txtData.requestFocus();
            return;
        }

        // Atualiza o objeto alunoParaEditar com os novos dados
        alunoParaEditar.setNome(nome);
        alunoParaEditar.setDataDeNascimento(dataDeNascimento);
        alunoParaEditar.setCpf(cpf);
        alunoParaEditar.setTelefone(tel);

        // Tenta atualizar o aluno usando o DAO
        if (daoAluno.atualizarAluno(alunoParaEditar)) {
            mostrarAlerta("Sucesso", "Aluno atualizado com sucesso!", Alert.AlertType.INFORMATION);
            if (controllerGerenciador != null) {
                controllerGerenciador.carregarDados(); // Atualiza a tabela na tela principal
            }
            fecharTelaDeEdicao(); // Fecha a tela de edição
        } else {
            mostrarAlerta("Erro", "Não foi possível atualizar o aluno. Aluno não encontrado.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao();
    }

    private void fecharTelaDeEdicao() {
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