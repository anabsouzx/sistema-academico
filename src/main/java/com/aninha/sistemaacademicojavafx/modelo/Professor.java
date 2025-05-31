package com.aninha.sistemaacademicojavafx.modelo;

import java.util.Date;

public class Professor extends Pessoa{
    private int CodigoProfessor;

    public Professor(String nome, Date dataDeNascimento, String cpf, String telefone) {
        super(nome, dataDeNascimento, cpf, telefone);
    }

    public int getCodigoProfessor() {
        return CodigoProfessor;
    }

    public void setCodigoProfessor(int CodigoProfessor) {
        this.CodigoProfessor = CodigoProfessor;
    }

    @Override
    public String info() {
        return "Aluno: " + getNome() +
                "\nID: " + getCodigoProfessor() +
                "\nCPF: " + getCpf() +
                "\nNascimento: " + getDataDeNascimento() +
                "\nTelefone: " + getTelefone();
    }



}
