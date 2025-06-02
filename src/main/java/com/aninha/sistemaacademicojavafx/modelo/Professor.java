package com.aninha.sistemaacademicojavafx.modelo; // Define onde essa classe está no projeto

import java.time.LocalDate; // Importa a classe para trabalhar com datas (ex: data de nascimento)

// A classe Professor herda da classe Pessoa, então aproveita os atributos e métodos dela
public class Professor extends Pessoa {

    // Código único do professor (serve como identificador)
    private int CodigoProfessor;

    // Construtor da classe, recebe dados básicos e passa para o construtor da superclasse Pessoa
    public Professor(String nome, LocalDate dataDeNascimento, String cpf, String telefone) {
        super(nome, dataDeNascimento, cpf, telefone); // Usa o construtor de Pessoa
    }

    // Getter: retorna o código do professor
    public int getCodigoProfessor() {
        return CodigoProfessor;
    }

    // Setter: define o código do professor
    public void setCodigoProfessor(int CodigoProfessor) {
        this.CodigoProfessor = CodigoProfessor;
    }

    // Método sobrescrito que retorna um resumo dos dados do professor
    @Override
    public String info() {
        return "Professor: " + getNome() +               // Mostra o nome do professor
                "\nID: " + getCodigoProfessor() +        // Mostra o código identificador
                "\nCPF: " + getCpf() +                   // Mostra o CPF
                "\nNascimento: " + getDataDeNascimento() + // Mostra a data de nascimento
                "\nTelefone: " + getTelefone();          // Mostra o telefone
    }
}
