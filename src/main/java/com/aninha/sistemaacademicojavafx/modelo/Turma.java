package com.aninha.sistemaacademicojavafx.modelo;

import java.util.ArrayList; // Importa a classe ArrayList para manipulação de listas

public class Turma {
    private int codigoTurma; // Identificador único da turma
    private Professor professor; // Professor responsável pela turma
    private Disciplina disciplina; // Disciplina associada à turma
    private ArrayList<Aluno> listaAlunos; // Lista de alunos matriculados na turma

    // Campos adicionais
    private int ano; // Ano letivo da turma
    private int semestre; // Semestre letivo da turma

    // Construtor com ano e semestre incluídos (comentado, pode ser usado no futuro)
    // public Turma(int codigoTurma, Disciplina disciplina, Professor professor, ArrayList<Aluno> listaAlunos, int ano, int semestre) {
    //     this.codigoTurma = codigoTurma;
    //     this.disciplina = disciplina;
    //     this.professor = professor;
    //     this.listaAlunos = listaAlunos;
    //     this.ano = ano;
    //     this.semestre = semestre;
    // }

    // Construtor alternativo, utilizado quando ano/semestre não são passados na criação da turma
    public Turma(Disciplina disciplina, Professor professor, ArrayList<Aluno> listaAlunos) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.listaAlunos = listaAlunos;
    }

    public int getCodigoTurma() {
        return codigoTurma; // Retorna o código da turma
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma; // Define o código da turma
    }

    public String getNomeProfessor() {
        // Retorna o nome do professor se ele não for nulo
        if (this.professor != null) {
            return this.professor.getNome(); // Usa o método getNome() da classe Professor
        }
        return "null"; // Caso o professor esteja nulo
    }

    public String getNomeDisciplina() {
        // Retorna o nome da disciplina se ela não for nula
        if (this.disciplina != null) {
            return this.disciplina.getNomeDisciplina(); // Usa o método getNomeDisciplina() da classe Disciplina
        }
        return "null"; // Caso a disciplina esteja nula
    }

    public Professor getProfessor() {
        return professor; // Retorna o professor da turma
    }

    public void setProfessor(Professor professor) {
        this.professor = professor; // Define o professor da turma
    }

    public Disciplina getDisciplina() {
        return disciplina; // Retorna a disciplina da turma
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina; // Define a disciplina da turma
    }

    public ArrayList<Aluno> getListaAlunos() {
        return listaAlunos; // Retorna a lista de alunos da turma
    }

    public void setListaAlunos(ArrayList<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos; // Define a lista de alunos da turma
    }

    public void adicionarAluno(Aluno aluno) {
        this.listaAlunos.add(aluno); // Adiciona um aluno à lista
    }

    public boolean removerAluno(Aluno aluno) {
        return this.listaAlunos.remove(aluno); // Remove um aluno da lista e retorna se foi bem-sucedido
    }

    // Getters e setters para os novos campos ano e semestre
    public int getAno() {
        return ano; // Retorna o ano da turma
    }

    public void setAno(int ano) {
        this.ano = ano; // Define o ano da turma
    }

    public int getSemestre() {
        return semestre; // Retorna o semestre da turma
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre; // Define o semestre da turma
    }
}
