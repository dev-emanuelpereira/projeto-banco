package application.services;

import config.ManipuladorString;
import model.entities.Banco;
import model.entities.Conta;

public class SistemaBancoService {
    
    private ContaService contaService;
    private ManipuladorString manipuladorString;

    public SistemaBancoService() {
    }

    public SistemaBancoService(ContaService contaService, ManipuladorString manipuladorString) {
        this.contaService = contaService;
        this.manipuladorString = manipuladorString;
    }

    public Conta calcular_regras (int opcao_numero) {
        switch (opcao_numero){
            case 1:
                contaService.criarConta();
                break;
            case 2:
                Conta conta = contaService.entrarConta();
                return conta;
            case 3:
                Banco.banco_fechado();
                break;
                

        }
        return null;
    }
}
