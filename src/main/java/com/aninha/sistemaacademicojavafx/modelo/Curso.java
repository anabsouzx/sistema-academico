// Declara o pacote onde essa classe está organizada (estrutura do projeto)
package com.aninha.sistemaacademicojavafx.modelo;

// Declaração da classe Curso. Representa um curso dentro do sistema acadêmico.
public class Curso {

    // Atributo que armazena o código identificador do curso (ex: 101, 102)
    private int codigoCurso;

    // Atributo que armazena o nome do curso (ex: Engenharia, Medicina)
    private String nomeCurso;

    // Atributo que representa a duração do curso em anos ou semestres (depende da regra da aplicação)
    private int duracao;

    // Construtor da classe Curso, inicializa nome e duração (o código é setado separadamente)
    public Curso(String nomeCurso, int duracao) {
        this.nomeCurso = nomeCurso;
        this.duracao = duracao;
    }

    // Getter (acessador) para o código do curso
    public int getCodigoCurso() {
        return codigoCurso;
    }

    // Setter (modificador) para o código do curso
    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    // Getter para o nome do curso
    public String getNomeCurso() {
        return nomeCurso;
    }

    // Setter para o nome do curso
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    // Getter para a duração do curso
    public int getDuracao() {
        return duracao;
    }

    // Setter para a duração do curso
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return nomeCurso + " (ID: " + codigoCurso + ")";
    }

}
