package com.aninha.sistemaacademicojavafx.modelo;

import java.util.ArrayList;


public class Turma {
    private int codigoTurma;
    private int codigoProfessor;
    private int codDisciplina;
    private ArrayList<Aluno> listaAlunos; // Lista tipada com objetos do tipo Aluno

    public Turma(int codigoTurma, int codDisciplina, int codigoProfessor, ArrayList<Aluno> listaAlunos) {
        this.codigoTurma = codigoTurma;
        this.codDisciplina = codDisciplina;
        this.codigoProfessor = codigoProfessor;
        this.listaAlunos = listaAlunos;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
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

    public ArrayList<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(ArrayList<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public void adicionarAluno(Aluno aluno) {
        this.listaAlunos.add(aluno);
    }


    public boolean removerAluno(Aluno aluno) {
        return this.listaAlunos.remove(aluno);
    }
}
