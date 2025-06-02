package com.aninha.sistemaacademicojavafx.controller;

import com.aninha.sistemaacademicojavafx.modelo.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DAOProfessor {

    // Lista que armazena os professores em memória
    private static ObservableList<Professor> professores = FXCollections.observableArrayList();

    // Código automático para professores
    private static int proximoCodigoProfessor = 1;

    public DAOProfessor() {
        // Construtor padrão
    }


    //Lista todos os professores cadastrados.
    public ObservableList<Professor> listarProfessores() {
        return professores;
    }


     // Retorna uma cópia da lista de professores (para uso em ComboBox, por exemplo).
    public ObservableList<Professor> listarProfessoresComboBox() {
        return FXCollections.observableArrayList(professores);
    }


     // Adiciona um novo professor à lista com código automático.
    public void adicionar(Professor professor) {
        professor.setCodigoProfessor(proximoCodigoProfessor++);
        professores.add(professor);
    }


    // Remove todos os professores e reinicia o contador de código.
    public void apagarTudo() {
        professores.clear();
        proximoCodigoProfessor = 1;
    }
}
