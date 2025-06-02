package com.aninha.sistemaacademicojavafx.modelo; // Pacote onde a classe está localizada

// Classe que representa uma matrícula de um aluno em uma disciplina
public class Matricula {

    // Número único da matrícula
    private int numMatricula;

    // Aluno que está sendo matriculado
    private Aluno aluno;

    // Disciplina em que o aluno está sendo matriculado
    private Disciplina disciplina;

    // Semestre em que a matrícula está sendo feita (1 ou 2, por exemplo)
    private int semestre;

    // Ano da matrícula
    private int ano;

    // Construtor - usado para criar um novo objeto Matricula com os dados fornecidos
    public Matricula(int numMatricula, Aluno aluno, Disciplina disciplina, int semestre, int ano) {
        this.numMatricula = numMatricula;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.semestre = semestre;
        this.ano = ano;
    }

    // Retorna o número da matrícula
    public int getNumMatricula() {
        return numMatricula;
    }

    // Define (altera) o número da matrícula
    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    // Retorna o aluno da matrícula
    public Aluno getAluno() {
        return aluno;
    }

    // Define (altera) o aluno da matrícula
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    // Retorna a disciplina da matrícula
    public Disciplina getCodDisciplina() {
        return disciplina;
    }

    // Define (altera) a disciplina da matrícula
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    // Retorna o semestre da matrícula
    public int getSemestre() {
        return semestre;
    }

    // Define (altera) o semestre da matrícula
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    // Retorna o ano da matrícula
    public int getAno() {
        return ano;
    }

    // Define (altera) o ano da matrícula
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
