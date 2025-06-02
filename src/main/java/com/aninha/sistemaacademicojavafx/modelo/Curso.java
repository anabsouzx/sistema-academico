package com.aninha.sistemaacademicojavafx.modelo;

public class Curso {
    private int codigoCurso;
    private String nomeCurso;
    private int duracao;


    public Curso(String nomeCurso, int duracao) {
        this.nomeCurso = nomeCurso;
        this.duracao = duracao;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
