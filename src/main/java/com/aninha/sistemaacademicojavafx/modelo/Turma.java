package com.aninha.sistemaacademicojavafx.modelo;

import java.util.ArrayList;

public class Turma {
    private int codigoTurma;
    private Professor professor;
    private Disciplina disciplina;
    private ArrayList<Aluno> listaAlunos;

    // ✅ Novos campos adicionados:
    private int ano;
    private int semestre;

    // Construtor com ano e semestre incluídos
    public Turma(int codigoTurma, Disciplina disciplina, Professor professor, ArrayList<Aluno> listaAlunos, int ano, int semestre) {
        this.codigoTurma = codigoTurma;
        this.disciplina = disciplina;
        this.professor = professor;
        this.listaAlunos = listaAlunos;
        this.ano = ano;
        this.semestre = semestre;
    }

    // Construtor alternativo, caso ainda não use ano/semestre na criação
    public Turma(int codigoTurma, Disciplina disciplina, Professor professor, ArrayList<Aluno> listaAlunos) {
        this(codigoTurma, disciplina, professor, listaAlunos, 0, 0); // ano e semestre padrão 0
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getNomeProfessor() {
        if (this.professor != null) {
            return this.professor.getNome(); // Chama o getNome() do objeto Professor
        }
        return "null";
    }

    public String getNomeDisciplina() {
        if (this.disciplina != null) {
            return this.disciplina.getNomeDisciplina(); // Chama o getNome() do objeto Professor
        }
        return "null";
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

    // ✅ Getters e Setters novos
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
}
