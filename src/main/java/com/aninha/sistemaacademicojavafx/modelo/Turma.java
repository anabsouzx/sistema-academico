package com.aninha.sistemaacademicojavafx.modelo;

import java.util.ArrayList;


public class Turma {
    private int codigoTurma;
    private Professor professor;
    private Disciplina disciplina;
    private ArrayList<Aluno> listaAlunos; // Lista tipada com objetos do tipo Aluno

    public Turma(int codigoTurma, Disciplina disciplina, Professor professor, ArrayList<Aluno> listaAlunos) {
        this.codigoTurma = codigoTurma;
        this.disciplina = disciplina;
        this.professor = professor;
        this.listaAlunos = listaAlunos;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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
