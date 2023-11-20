package application.representation;

import java.util.Scanner;

import application.services.SistemaBancoService;
import config.ManipuladorString;
import model.entities.Conta;

public class BancoController {
    
    private Conta conta;
    private SistemaBancoService sistemaBancoService;
    private ManipuladorString manipuladorString;

    public BancoController (SistemaBancoService sistemaBancoService, ManipuladorString manipuladorString) {
        this.sistemaBancoService = sistemaBancoService;
        this.manipuladorString = manipuladorString;
    }
    public void iniciar () {
        
    }

    public void tela_inicial () {
        while (true){
            Scanner input = new Scanner(System.in);
            manipuladorString.centralizar_texto("BEM VINDO AO BANCO!");
            System.out.println(
                    """
                    O QUE DESEJA FAZER?
                    
                    1 -> Criar uma conta 
                    2 -> Entrar em uma conta
                    3 -> Sair do aplicativo
                    """
            );

            Integer opcao = input.nextInt();
            Conta conta = sistemaBancoService.calcular_regras(opcao);
            if (opcao == 2) {
                tela_entrar_conta(conta);
            }
            
            
        }

    }

    public void tela_entrar_conta (Conta conta) {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto(String.format("O que deseja fazer hoje, %s?", conta.getTitular_conta()));
        System.out.println(String.format("""
                SALDO TOTAL: %d 

                1 -> Sacar
                2 -> Depositar
                3 -> Fazer transferencia

                4 -> Sair da Conta
                """, conta.getSaldo()));
                Integer opcao = input.nextInt();

    }
}
