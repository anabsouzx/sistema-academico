package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.controller.DAOProfessor;
import com.aninha.sistemaacademicojavafx.modelo.Professor;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarProfessores; // Para poder chamar um método de recarregar
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditarProfessor {
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtNomeA;
    @FXML
    private TextField txtTel;

    private Professor professorParaEditar;
    private GerenciarProfessores controllerGerenciador; // Para chamar o refresh da tabela principal
    private DAOProfessor daoProfessor; // interagir com dados

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EditarProfessor(){
        this.daoProfessor = new DAOProfessor();
    }

    public void setAlunoParaEditar(Professor professor, GerenciarProfessores controllerGerenciador) {
        this.professorParaEditar = professor;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos();
    }

    private void popularCampos() {
        if (professorParaEditar != null) {
            txtNomeA.setText(professorParaEditar.getNome());
            if (professorParaEditar.getDataDeNascimento() != null) {
                txtData.setText(professorParaEditar.getDataDeNascimento().format(dateFormatter));
            } else {
                txtData.setText("");
            }
            txtCpf.setText(professorParaEditar.getCpf());
            txtTel.setText(professorParaEditar.getTelefone());
        }
    }

    @FXML
    void atualizaProfessorAction(ActionEvent event) {
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
        professorParaEditar.setNome(nome);
        professorParaEditar.setDataDeNascimento(dataDeNascimento);
        professorParaEditar.setCpf(cpf);
        professorParaEditar.setTelefone(tel);

        // Tenta atualizar o aluno usando o DAO
        if (daoProfessor.atualizarProfessor(professorParaEditar)) {
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
            controllerGerenciador.limparPainelCentral(); // Metodo a ser criado
        }
    }


    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}