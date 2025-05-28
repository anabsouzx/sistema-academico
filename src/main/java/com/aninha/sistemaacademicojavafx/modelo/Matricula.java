package com.aninha.sistemaacademicojavafx.modelo;

public class Matricula {
    private int numMatricula;
    private int codAluno;
    private int codDisciplina;
    private int semestre;
    private int ano;
    // private Aluno aluno;

    public Matricula(int numMatricula,int codAluno, int codDisciplina, int semestre, int ano) {
        this.numMatricula = numMatricula;
        this.codAluno = codAluno;
        this.codDisciplina = codDisciplina;
        this.semestre = semestre;
        this.ano = ano;
    }

    public int getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
