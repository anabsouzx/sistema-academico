// Declara o pacote onde está a classe (organiza o código em pastas/lógicas)
package com.aninha.sistemaacademicojavafx.modelo;

// Importa a classe LocalDate para representar datas (como data de nascimento)
import java.time.LocalDate;

// A classe Aluno representa um aluno e herda atributos e comportamentos da classe Pessoa
public class Aluno extends Pessoa {

    // Atributo exclusivo da classe Aluno: um código identificador do aluno
    private int codigoAluno;

    // Construtor da classe Aluno que recebe os dados de uma pessoa
    public Aluno(String nome, LocalDate dataDeNascimento, String cpf, String telefone) {
        // Chama o construtor da superclasse (Pessoa), passando os mesmos dados
        super(nome, dataDeNascimento, cpf, telefone);
    }

    // Método para obter o código do aluno (getter)
    public int getCodigoAluno() {
        return codigoAluno;
    }

    // Método para definir o código do aluno (setter)
    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    // Sobrescreve o método 'info()' herdado da classe Pessoa
    // Esse método retorna uma string com o nome do aluno e seu código
    @Override
    public String info() {
        return getNome() + " (ID: " + getCodigoAluno() + ")";
    }
}

