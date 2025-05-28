package com.aninha.sistemaacademicojavafx.modelo;

public class Disciplina {
    private int codigoDisciplina;
    private String nomeDisciplina;
    private int codCurso;
    private int cargaHoraria;


    public Disciplina(int codigoDisciplina, String nomeDisciplina, int codCurso, int cargaHoraria) {
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.codCurso = codCurso;
        this.cargaHoraria = cargaHoraria;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}
