package com.aninha.sistemaacademicojavafx.modelo;

import java.sql.Date;

public class Aluno {
    private int codigoAluno;
    private String nomeAluno;
    private Date dataNasc;
    private String cpf;
    private String telefone;

    public Aluno(){

    }

    public Aluno(int codigoAluno, String nomeAluno, Date dataNasc, String cpf, String telefone) {
        this.codigoAluno = codigoAluno;
        this.nomeAluno = nomeAluno;
        this.dataNasc = dataNasc;
        this.cpf = cpf;
        this.telefone = telefone;
    }
    public Aluno(String nomeAluno, Date dataNasc, String cpf, String telefone) {
        this.nomeAluno = nomeAluno;
        this.dataNasc = dataNasc;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
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
}
