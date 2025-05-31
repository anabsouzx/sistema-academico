package com.aninha.sistemaacademicojavafx.modelo;

public class Turma {
    private int codigoTurma;
    private int codigoAluno;
    private int codigoProfessor;
    private int codDisciplina;


    public Turma(int codigoTurma, int codDisciplina, int codigoProfessor, int codigoAluno){
        this.codigoTurma = codigoTurma;
        this.codDisciplina = codDisciplina;
        this.codigoProfessor = codigoProfessor;
        this.codigoAluno = codigoAluno;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public int getCodigoProfessor() {
        return codigoProfessor;
    }

    public void setCodigoProfessor(int codigoProfessor) {
        this.codigoProfessor = codigoProfessor;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }
}

