package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DAO simulado para gerenciamento de matrículas em memória (sem uso de banco de dados).
 */
public class DAOMatricula {

    // Lista de matrículas armazenadas em memória
    private static ObservableList<Matricula> matriculas = FXCollections.observableArrayList();

    // Contador para gerar número de matrícula automaticamente
    private static int proximoNumeroMatricula = 1;

    public DAOMatricula() {
        // Construtor padrão
    }

    //Retorna a lista completa de matrículas cadastradas.
    public ObservableList<Matricula> listarMatriculas() {
        return matriculas;
    }


    //Adiciona uma nova matrícula à lista com número automático.

    public void adicionar(Matricula m) {
        m.setNumMatricula(proximoNumeroMatricula++);
        matriculas.add(m);
    }

    //Remove todas as matrículas e reinicia o contador de numMatricula.
    public void apagarTudo() {
        matriculas.clear();
        proximoNumeroMatricula = 1;
    }
}
