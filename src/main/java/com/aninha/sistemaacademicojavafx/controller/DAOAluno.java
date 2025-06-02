package com.aninha.sistemaacademicojavafx.controller;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOAluno {
    // Lista estática de alunos que simula um banco de dados na memória
    private static ObservableList<Aluno> alunos = FXCollections.observableArrayList();
    private static int proximoCodigoAluno = 1; // Gerador automático de códigos

    public DAOAluno() {} // Construtor padrão

    // Retorna todos os alunos cadastrados
    public ObservableList<Aluno> listarAlunos() {
        return alunos;
    }

    // Retorna uma cópia da lista de alunos para uso em ComboBox
    public ObservableList<Aluno> listarTodosAlunosParaComboBox() {
        return FXCollections.observableArrayList(alunos);
    }

    // Adiciona um aluno e define um código único automaticamente
    public void adicionar(Aluno aluno) {
        aluno.setCodigoAluno(proximoCodigoAluno++);
        alunos.add(aluno);
    }

    // Apaga todos os alunos e reinicia o contador de código
    public void apagarTudo() {
        alunos.clear();
        proximoCodigoAluno = 1;
    }
}
