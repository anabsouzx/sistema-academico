package com.aninha.sistemaacademicojavafx.modelo.persistencia;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOCurso {

    // Lista de cursos armazenados na memória (simulando um banco de dados)
    private static ObservableList<Curso> cursos = FXCollections.observableArrayList();

    // Controle automático de código de curso
    private static int proximoCodigoCurso = 1;

    public DAOCurso() {
        // Construtor padrão
    }

    // Retorna a lista de cursos para ser usada na tabela
    public ObservableList<Curso> listarCursos() {
        return cursos;
    }

    // Retorna uma cópia da lista (usada em ComboBox)
    public ObservableList<Curso> listarCursosComboBox() {
        return FXCollections.observableArrayList(cursos);
    }

    // Adiciona um novo curso e define um código automático
    public void adicionar(Curso curso) {
        curso.setCodigoCurso(proximoCodigoCurso++);
        cursos.add(curso);
    }

    // Atualiza um curso existente com base no código
    public boolean atualizarCurso(Curso cursoAtualizado) {
        for (int i = 0; i < cursos.size(); i++) {
            Curso existente = cursos.get(i);
            if (existente.getCodigoCurso() == cursoAtualizado.getCodigoCurso()) {
                cursos.set(i, cursoAtualizado); // substitui o objeto na mesma posição
                return true;
            }
        }
        return false; // Curso não encontrado
    }

    // Remove um curso da lista
    public boolean excluirCurso(Curso curso) {
        return cursos.remove(curso); // remove com base na igualdade de objetos
    }

    // Limpa a lista de cursos e reinicia a contagem de códigos
    public void apagarTudo() {
        cursos.clear();
        proximoCodigoCurso = 1;
    }
}
