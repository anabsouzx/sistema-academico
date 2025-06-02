package com.aninha.sistemaacademicojavafx.modelo;

import java.time.LocalDate;

public class Aluno extends Pessoa {
    private int codigoAluno;

    public Aluno(String nome, LocalDate dataDeNascimento, String cpf, String telefone) {
        super(nome, dataDeNascimento, cpf, telefone);
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    @Override
    public String info() {
        return getNome()+" (ID: "+getCodigoAluno()+")";
    }
}
