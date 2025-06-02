package com.aninha.sistemaacademicojavafx.controller;

import com.aninha.sistemaacademicojavafx.modelo.Turma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOTurma {

    // Lista de turmas armazenadas em memória
    private static ObservableList<Turma> turmas = FXCollections.observableArrayList();

    // Gerador de código automático para as turmas
    private static int proximoCodigoTurma = 1;

    public DAOTurma() {
        // Construtor padrão
    }

    // Retorna todas as turmas
    public ObservableList<Turma> listarTurmas() {
        return turmas;
    }

    // Retorna uma cópia da lista de turmas (útil para ComboBox, etc.)
    public ObservableList<Turma> listarTurmasComboBox() {
        return FXCollections.observableArrayList(turmas);
    }

    // Adiciona uma nova turma com código gerado automaticamente
    public void adicionar(Turma turma) {
        turma.setCodigoTurma(proximoCodigoTurma++);
        turmas.add(turma);
    }

    // Remove uma turma da lista
    public void excluirTurma(Turma turma) {
        turmas.remove(turma);
    }

    // Remove todas as turmas e reinicia o contador de código
    public void apagarTudo() {
        turmas.clear();
        proximoCodigoTurma = 1;
    }
}
