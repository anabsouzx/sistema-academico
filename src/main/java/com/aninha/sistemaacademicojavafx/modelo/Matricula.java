package com.aninha.sistemaacademicojavafx.modelo;

public class Matricula {
    private int numMatricula;
    private Aluno aluno;
    private Disciplina disciplina;
    private int semestre;
    private int ano;
    // private Aluno aluno;

    public Matricula(int numMatricula,Aluno aluno, Disciplina disciplina, int semestre, int ano) {
        this.numMatricula = numMatricula;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.semestre = semestre;
        this.ano = ano;
    }

    public int getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getCodDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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

    public String getNomeAluno() {
        return (this.aluno != null) ? this.aluno.getNome() : "N/D";
    }

    public Integer getCodigoAlunoProperty() { // Renomeei para evitar conflito se mantiver getCodAluno() int
        return (this.aluno != null) ? this.aluno.getCodigoAluno() : null;
    }
}
