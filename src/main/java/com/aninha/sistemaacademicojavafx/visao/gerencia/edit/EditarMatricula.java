package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.controller.DAOMatricula;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarMatriculas;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditarMatricula {
    @FXML
    private TextField campoCodAluno;

    @FXML
    private TextField campoCodCurso;

    @FXML
    private TextField campoSemestre;

    @FXML
    private TextField campoAno;

    @FXML
    private ComboBox<Aluno> comboAluno;

    @FXML
    private ComboBox<Curso> comboCurso;

    private Matricula matricula;
    private GerenciarMatriculas controllerPai;

    private final DAOAluno daoAluno = new DAOAluno();
    private final DAOCurso daoCurso = new DAOCurso(); // Usando Curso
    private final DAOMatricula daoMatricula = new DAOMatricula();

    public void setMatriculaParaEditar(Matricula matricula, GerenciarMatriculas controllerPai) {
        this.matricula = matricula;
        this.controllerPai = controllerPai;

        campoSemestre.setText(String.valueOf(matricula.getSemestre()));
        campoAno.setText(String.valueOf(matricula.getAno()));

        comboAluno.setItems(FXCollections.observableArrayList(daoAluno.listarAlunos()));
        comboCurso.setItems(FXCollections.observableArrayList(daoCurso.listarCursos()));

        comboAluno.setValue(matricula.getAluno());
        comboCurso.setValue(matricula.getCurso());
    }

    @FXML
    void salvarAlteracoesAction(ActionEvent event) {
        String semestreStr = campoSemestre.getText();
        String anoStr = campoAno.getText();

        Aluno aluno = comboAluno.getValue();
        Curso curso = comboCurso.getValue();

        if (aluno == null || curso == null || semestreStr.isEmpty() || anoStr.isEmpty()) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos e selecione Aluno e Curso.", Alert.AlertType.WARNING);
            return;
        }

        int semestre, ano;
        try {
            semestre = Integer.parseInt(semestreStr);
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Semestre e ano devem ser números inteiros.", Alert.AlertType.ERROR);
            return;
        }

        // Atualiza a matrícula
        matricula.setAluno(aluno);
        matricula.setCurso(curso);
        matricula.setSemestre(semestre);
        matricula.setAno(ano);

        mostrarAlerta("Sucesso", "Matrícula atualizada com sucesso!", Alert.AlertType.INFORMATION);
        controllerPai.carregarDados();
        fecharTela();
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTela();
    }

    private void fecharTela() {
        if (controllerPai != null) {
            controllerPai.limparPainelCentral();
        }
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(msg);
        alert.showAndWait();
    }
}
