package com.aninha.sistemaacademicojavafx.controller;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOAluno {
    private static ObservableList<Aluno> alunos = FXCollections.observableArrayList();
    private static int proximoCodigoAluno = 1;

    public DAOAluno(){

    }

    public ObservableList<Aluno> listarAlunos(){
        return alunos;
    }

    public ObservableList<Aluno> listarAlunosCb(){
        return FXCollections.observableArrayList(alunos);
    }

    public void adicionar(Aluno aluno) {
        aluno.setCodigoAluno(proximoCodigoAluno++); // Define o código e incrementa para o próximo
        alunos.add(aluno);
    }

    public void apagarTudo(){
        alunos.clear();
        proximoCodigoAluno = 1;
    }
}
