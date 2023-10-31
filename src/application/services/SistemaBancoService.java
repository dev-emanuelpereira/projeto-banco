package application.services;

import config.ManipuladorString;
import model.entities.Banco;
import model.entities.Conta;

public class SistemaBancoService {
    
    private ContaService contaService = new ContaService();
    ManipuladorString manipuladorString = new ManipuladorString();

    public void calcular_regras (int opcao_numero) {
        switch (opcao_numero){
            case 1:
                contaService.criarConta();
            case 2:
                //contaService.entrarConta();
            case 3:
                Banco.banco_fechado();
                

        }
    }
}
