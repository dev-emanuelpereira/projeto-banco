package model.entities;

import java.time.LocalDate;
import java.util.Random;

import application.representation.BancoController;
import model.enums.ContaAtiva;

public class Conta extends Banco {

    BancoController bancoController;

    private String numero_conta;
    private String titular_conta;
    private LocalDate data_nascimento;
    private Long cpf;
    private Double saldo = 0.0;
    private String tipoConta;
    private ContaAtiva contaAtiva;
    private String senha;


    public String getSenha() {
        return senha;
    }


    public void setSenha(String senha) {
        this.senha = senha;
    }


    public Conta() {
    }


    public Conta(String numero_conta, String titular_conta, Double saldo, ContaAtiva contaAtiva, LocalDate data_nascimento, Long cpf, String tipoConta, String senha) {
        this.numero_conta = numero_conta;
        this.titular_conta = titular_conta;
        this.saldo = saldo;
        this.contaAtiva = contaAtiva;
        this.data_nascimento = data_nascimento;
        this.cpf = cpf;
        this.tipoConta = tipoConta;
        this.senha = senha;
    }


    public String getTipoConta() {
        return tipoConta;
    }


    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }


    public String getNumero_conta() {
        return numero_conta;
    }


    public void setNumero_conta(String numero_conta) {
        this.numero_conta = numero_conta;
    }


    public String getTitular_conta() {
        return titular_conta;
    }


    public void setTitular_conta(String titular_conta) {
        this.titular_conta = titular_conta;
    }


    public Double getSaldo() {
        return saldo;
    }


    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }


    public ContaAtiva getContaAtiva() {
        return contaAtiva;
    }


    public void setContaAtiva(ContaAtiva contaAtiva) {
        this.contaAtiva = contaAtiva;
    }


    @Override
    public void banco_aberto() {
        bancoController.iniciar();
    }


    public LocalDate getData_nascimento() {
        return data_nascimento;
    }


    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }


    public Long getCpf() {
        return cpf;
    }


    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }


    @Override
    public String toString() {
        return "Conta [numero_conta=" + numero_conta + ", titular_conta=" + titular_conta + ", data_nascimento="
                + data_nascimento + ", cpf=" + cpf + ", saldo=" + saldo + ", contaAtiva=" + contaAtiva + "]";
    }
}
