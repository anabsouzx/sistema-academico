package com.aninha.sistemaacademicojavafx.modelo;

public class Disciplina {
    private int codigoDisciplina;
    private String nomeDisciplina;
    private Curso curso; // associação correta com o objeto Curso
    private int cargaHoraria;

    // Construtor completo
    public Disciplina(int codigoDisciplina, String nomeDisciplina, Curso curso, int cargaHoraria) {
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.curso = curso;
        this.cargaHoraria = cargaHoraria;
    }
    public Disciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    // Construtor usado na inserção
    public Disciplina(String nomeDisciplina, Curso curso, int cargaHoraria) {
        this.nomeDisciplina = nomeDisciplina;
        this.curso = curso;
        this.cargaHoraria = cargaHoraria;
    }

    // Getters e setters
    public int getCodigoDisciplina() {return codigoDisciplina; }

    public void setCodigoDisciplina(int codigoDisciplina) {this.codigoDisciplina = codigoDisciplina; }

    public String getNomeDisciplina() { return nomeDisciplina; }

    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }

    public Curso getCurso() { return curso; }

    public void setCurso(Curso curso) { this.curso = curso; }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}
