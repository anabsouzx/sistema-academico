package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOMatricula;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarMatriculas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditarMatricula {

    @FXML
    private TextField campoNumero;

    @FXML
    private TextField campoCodAluno;

    @FXML
    private TextField campoCodDisciplina;

    @FXML
    private TextField campoSemestre;

    @FXML
    private TextField campoAno;

    private Matricula matricula;
    private GerenciarMatriculas controllerPai;

    private final DAOAluno daoAluno = new DAOAluno();
    private final DAODisciplina daoDisciplina = new DAODisciplina();
    private final DAOMatricula daoMatricula = new DAOMatricula();

    public void setMatriculaParaEditar(Matricula matricula, GerenciarMatriculas controllerPai) {
        this.matricula = matricula;
        this.controllerPai = controllerPai;

        campoNumero.setText(String.valueOf(matricula.getNumMatricula()));
        campoCodAluno.setText(String.valueOf(matricula.getAluno().getCodigoAluno()));
        campoCodDisciplina.setText(String.valueOf(matricula.getDisciplina().getCodigoDisciplina()));
        campoSemestre.setText(String.valueOf(matricula.getSemestre()));
        campoAno.setText(String.valueOf(matricula.getAno()));
    }

    @FXML
    void salvarAlteracoesAction(ActionEvent event) {
        String codAlunoStr = campoCodAluno.getText();
        String codDisciplinaStr = campoCodDisciplina.getText();
        String semestre = campoSemestre.getText();
        String anoStr = campoAno.getText();

        if (codAlunoStr.isEmpty() || codDisciplinaStr.isEmpty() || semestre.isEmpty() || anoStr.isEmpty()) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        int codAluno, codDisciplina, ano;
        try {
            codAluno = Integer.parseInt(codAlunoStr);
            codDisciplina = Integer.parseInt(codDisciplinaStr);
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Código e ano devem ser números inteiros.", Alert.AlertType.ERROR);
            return;
        }

        Aluno aluno = daoAluno.buscarPorCodigo(codAluno);
        Disciplina disciplina = daoDisciplina.buscarPorCodigo(codDisciplina);

        if (aluno == null || disciplina == null) {
            mostrarAlerta("Erro", "Aluno ou Disciplina não encontrados.", Alert.AlertType.ERROR);
            return;
        }

        // Atualiza a matrícula
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setSemestre(Integer.parseInt(campoSemestre.getText()));
        matricula.setAno(ano);

        // Não há necessidade de atualizar em DAO, pois lista é a mesma referência
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
