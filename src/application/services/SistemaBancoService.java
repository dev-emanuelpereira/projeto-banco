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

    public Conta calcular_regras_tela_inicial (int opcao_numero) {
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

    public void calcular_regras_entrar_conta (int opcao_numero, Conta conta) {
        switch (opcao_numero) {
            case 1:
                contaService.sacar(conta);
                break;
            case 2:
                contaService.depositar(conta);
                break;
            case 3:
                contaService.fazerTransferencia(conta);
            case 4:
                contaService.exibirInformações(conta);
                break;
        }
    }
}
