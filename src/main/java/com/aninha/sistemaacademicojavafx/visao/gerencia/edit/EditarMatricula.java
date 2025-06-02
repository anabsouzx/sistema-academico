package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarMatriculas;
import javafx.fxml.FXML;
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

    public void setMatriculaParaEditar(Matricula matricula, GerenciarMatriculas controllerPai) {
        this.matricula = matricula;
        this.controllerPai = controllerPai;

        campoNumero.setText(String.valueOf(matricula.getNumMatricula()));
        campoCodAluno.setText(String.valueOf(matricula.getCodAluno()));
        campoCodDisciplina.setText(String.valueOf(matricula.getCodDisciplina()));
        campoSemestre.setText(String.valueOf(matricula.getSemestre()));
        campoAno.setText(String.valueOf(matricula.getAno()));
    }

    // Aqui você pode adicionar métodos para salvar alterações, cancelar, etc.
}
