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

    // metodo p atualizar turma ao editar
    public boolean atualizarTurma(Turma turmaAtualizada) {
        if (turmaAtualizada == null) {
            return false;
        }
        for (int i = 0; i < turmas.size(); i++) {
            Turma turmaExistente = turmas.get(i);
            // Compara pelo código da turma, que é o identificador único
            if (turmaExistente.getCodigoTurma() == turmaAtualizada.getCodigoTurma()) {
                // Substitui o objeto antigo pelo novo na lista
                turmas.set(i, turmaAtualizada);
                return true; // Sucesso na atualização
            }
        }
        return false; // Turma não encontrada
    }
}
