package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DAODisciplina {

    // Lista de disciplinas em memória
    private static ObservableList<Disciplina> disciplinas = FXCollections.observableArrayList();
    private static int proximoCodigoDisciplina = 1;

    public DAODisciplina() {
        // Construtor padrão
    }

    //Lista todas as disciplinas cadastradas.
    public ObservableList<Disciplina> listarDisciplinas() {
        return disciplinas;
    }


    // Lista de disciplinas formatada para ComboBox.
    public ObservableList<Disciplina> listarDisciplinasComboBox() {
        return FXCollections.observableArrayList(disciplinas.sorted((a, b) -> a.getNomeDisciplina().compareToIgnoreCase(b.getNomeDisciplina())));
    }

    //Adiciona uma nova disciplina à lista, com código gerado automaticamente.
    public void adicionar(Disciplina d) {
        d.setCodigoDisciplina(proximoCodigoDisciplina++);
        disciplinas.add(d);
    }


    // Remove todas as disciplinas e reinicia o contador de código.
    public void apagarTudo() {
        disciplinas.clear();
        proximoCodigoDisciplina = 1;
    }
}
