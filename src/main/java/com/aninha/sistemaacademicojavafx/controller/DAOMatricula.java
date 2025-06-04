package com.aninha.sistemaacademicojavafx.controller;

import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DAOMatricula {

    // Lista que armazena todas as matrículas em memória
    private static ObservableList<Matricula> matriculas = FXCollections.observableArrayList();

    // Contador interno para gerar automaticamente o número da matrícula
    private static int proximoNumeroMatricula = 1;

    // Construtor padrão
    public DAOMatricula() {
        // Pode ser usado para carregar dados iniciais, se necessário
    }

    // Retorna a lista completa de matrículas cadastradas.

    public ObservableList<Matricula> listarMatriculas() {
        return matriculas;
    }

    // Adiciona uma nova matrícula à lista com número gerado automaticamente.

    public void adicionar(Matricula m) {
        m.setNumMatricula(proximoNumeroMatricula++);
        matriculas.add(m);
    }

    // Remove uma matrícula específica da lista.
    public void excluirMatricula(Matricula matricula) {
        matriculas.remove(matricula);
    }

    // Remove todas as matrículas da lista e reinicia o contador de número de matrícula.

    public void apagarTudo() {
        matriculas.clear();
        proximoNumeroMatricula = 1;
    }


    // Busca uma matrícula pelo número.

    public Matricula buscarPorNumero(int numero) {
        for (Matricula m : matriculas) {
            if (m != null && m.getNumMatricula() == numero) {
                return m;
            }
        }
        return null;
    }

}
