package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DAOCurso {

    // Lista de cursos armazenados na memória
    private static ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private static int proximoCodigoCurso = 1; // Código automático

    public DAOCurso() {
        // Construtor padrão
    }

    //Lista todos os cursos.
    public ObservableList<Curso> listarCursos() {
        return cursos;
    }


    //Lista de cursos formatada para ComboBox (cópia da lista original).
    public ObservableList<Curso> listarCursosComboBox() {
        return FXCollections.observableArrayList(cursos);
    }

    //Adiciona um novo curso e define seu código automaticamente.
    public void adicionar(Curso curso) {
        curso.setCodigoCurso(proximoCodigoCurso++);
        cursos.add(curso);
    }

    //Remove todos os cursos e reinicia o contador de códigos.
    public void apagarTudo() {
        cursos.clear();
        proximoCodigoCurso = 1;
    }
}
