package com.aninha.sistemaacademicojavafx.modelo;

import java.util.Date;

public class Aluno extends Pessoa {
    private int codigoAluno;

    public Aluno(String nome, Date dataDeNascimento, String cpf, String telefone) {
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
