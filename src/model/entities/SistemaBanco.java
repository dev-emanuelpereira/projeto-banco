package model.entities;

import java.util.List;
import java.util.ArrayList;

public class SistemaBanco {
    private Conta conta;
    public List<Conta> contas;

    public SistemaBanco(Conta conta, List<Conta> contas) {
        this.conta = conta;
        this.contas = contas;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    @Override
    public String toString() {
        return "SistemaBanco [conta=" + conta + ", contas=" + contas + "]";
    }

    

    


    
    
}
