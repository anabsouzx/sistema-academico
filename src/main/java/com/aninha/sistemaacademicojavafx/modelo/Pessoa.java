package com.aninha.sistemaacademicojavafx.modelo;

import java.time.LocalDate;

public abstract class Pessoa {
    int codigo;
    String nome;
    LocalDate dataDeNascimento;
    String cpf;
    String telefone;

    // getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // construtor
    public Pessoa(String nome, LocalDate dataDeNascimento, String cpf, String telefone) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public abstract String info();
}
