package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class InserirDisciplina implements Initializable {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TextField txtCargaH;

    @FXML
    private ComboBox<Curso> comboBoxCurso;

    @FXML
    private TextField txtNomeD;

    private DAODisciplina daoDisciplina;
    private DAOCurso daoCurso;

    public InserirDisciplina() {
        this.daoDisciplina = new DAODisciplina();
        this.daoCurso = new DAOCurso();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        popularComboBoxCurso();
    }

    @FXML
    void insereDisciplina(ActionEvent event) {
        String nome = txtNomeD.getText();
        String cargaHStr = txtCargaH.getText();
        Curso cursoSelecionado = comboBoxCurso.getSelectionModel().getSelectedItem();

        // Validação de campos
        if (nome.isEmpty() || cargaHStr.isEmpty() || cursoSelecionado == null) {
            mostrarAlerta("Campos obrigatórios", "Preencha todos os campos antes de salvar.", Alert.AlertType.WARNING);
            return;
        }

        int cargaH;
        try {
            cargaH = Integer.parseInt(cargaHStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato inválido", "Carga horária deve ser um número inteiro.", Alert.AlertType.ERROR);
            return;
        }

        // Criação da disciplina
        Disciplina novaDisciplina = new Disciplina(nome, cursoSelecionado, cargaH);
        daoDisciplina.adicionar(novaDisciplina);

        mostrarAlerta("Sucesso", "Disciplina cadastrada com sucesso!", Alert.AlertType.INFORMATION);

        // Limpa os campos
        txtNomeD.clear();
        txtCargaH.clear();
        comboBoxCurso.getSelectionModel().clearSelection();
        txtNomeD.requestFocus();
    }

    private void popularComboBoxCurso() {
        ObservableList<Curso> cursos = daoCurso.listarCursosComboBox();
        comboBoxCurso.setItems(cursos);

        comboBoxCurso.setConverter(new StringConverter<Curso>() {
            @Override
            public String toString(Curso curso) {
                return curso == null ? "" : curso.getNomeCurso() + " (ID: " + curso.getCodigoCurso() + ")";
            }

            @Override
            public Curso fromString(String string) {
                return comboBoxCurso.getItems().stream()
                        .filter(curso -> (curso.getNomeCurso() + " (ID: " + curso.getCodigoCurso() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
}
