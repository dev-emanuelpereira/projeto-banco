package model.entities;

import java.util.List;
import java.util.ArrayList;

public class SistemaBanco {
    private Conta conta;
    private List<Conta> contas = new ArrayList<Conta>();

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

    

    


    
    
}
