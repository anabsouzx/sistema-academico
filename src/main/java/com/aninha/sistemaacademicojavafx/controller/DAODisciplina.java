package com.aninha.sistemaacademicojavafx.controller;

import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAODisciplina {

    // Lista de disciplinas em memória (simulando banco de dados)
    private static ObservableList<Disciplina> disciplinas = FXCollections.observableArrayList();
    private static int proximoCodigoDisciplina = 1; // Controle automático de código

    public DAODisciplina() {
        // Construtor padrão
    }

    // Retorna todas as disciplinas
    public ObservableList<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    // Retorna uma cópia da lista ordenada por nome (usada em ComboBox, por exemplo)
    public ObservableList<Disciplina> listarDisciplinasComboBox() {
        return FXCollections.observableArrayList(
                disciplinas.sorted((a, b) -> a.getNomeDisciplina().compareToIgnoreCase(b.getNomeDisciplina()))
        );
    }

    // Adiciona uma nova disciplina com código automático
    public void adicionar(Disciplina d) {
        d.setCodigoDisciplina(proximoCodigoDisciplina++);
        disciplinas.add(d);
    }

    // Atualiza uma disciplina existente com base no código
    public boolean atualizarDisciplina(Disciplina disciplinaAtualizada) {
        for (int i = 0; i < disciplinas.size(); i++) {
            Disciplina atual = disciplinas.get(i);
            if (atual.getCodigoDisciplina() == disciplinaAtualizada.getCodigoDisciplina()) {
                disciplinas.set(i, disciplinaAtualizada); // substitui o objeto
                return true;
            }
        }
        return false; // não encontrou a disciplina
    }

    // Exclui uma disciplina da lista
    public boolean excluirDisciplina(Disciplina disciplina) {
        return disciplinas.remove(disciplina);
    }

    // Limpa a lista e reinicia o contador
    public void apagarTudo() {
        disciplinas.clear();
        proximoCodigoDisciplina = 1;
    }
}
