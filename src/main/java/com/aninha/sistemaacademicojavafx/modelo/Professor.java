package com.aninha.sistemaacademicojavafx.modelo;

import java.util.Date;

public class Professor extends Pessoa{
    private int codigoProfessor;

    public Professor(String nome, Date dataDeNascimento, String cpf, String telefone) {
        super(nome, dataDeNascimento, cpf, telefone);
    }

    public int getCodigoProfessor() {
        return codigoProfessor;
    }

    public void setCodigoProfessor(int codigoProfessor) {
        this.codigoProfessor = codigoProfessor;
    }

    @Override
    public String info() {
        return "";
    }


}
