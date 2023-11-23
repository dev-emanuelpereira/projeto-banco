package application.representation;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import application.services.SistemaBancoService;
import config.ManipuladorString;
import model.entities.Conta;

public class BancoController {

    private Conta conta;
    private SistemaBancoService sistemaBancoService;
    private ManipuladorString manipuladorString;

    public BancoController(SistemaBancoService sistemaBancoService, ManipuladorString manipuladorString) {
        this.sistemaBancoService = sistemaBancoService;
        this.manipuladorString = manipuladorString;
    }

    public void iniciar() {

    }

    public void tela_inicial() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("\n");
            manipuladorString.centralizar_texto("BEM VINDO AO BANCO!");
            System.out.println(
                    """
                            O QUE DESEJA FAZER?

                            1 | Criar uma conta
                            2 | Entrar em uma conta
                            3 | Exibir contas do Banco
                            
                            4 | Sair do aplicativo
                            """);
            try {
                System.out.print("-> ");
                int opcao = input.nextInt();
                Conta conta = sistemaBancoService.calcular_regras_tela_inicial(opcao);
                if (opcao == 2) {
                    if (conta != null) {
                        tela_entrar_conta(conta);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\nOPS. Opção inválida!\n");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }

    }

    public void tela_entrar_conta(Conta conta) {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("");
            manipuladorString
                    .centralizar_texto(String.format("O que deseja fazer hoje, %s?", conta.getTitular_conta()));
            manipuladorString.alinhar_texto_dois_direito(String.format("SALDO ATUAL: R$ %.2f", conta.getSaldo()),
                    String.format("TIPO DE CONTA: %s", conta.getTipoConta()));

            System.out.println("""

                    1 | Sacar
                    2 | Depositar
                    3 | Fazer transferencia
                    4 | Exibir informações da conta

                    5 | Sair da conta
                    6 | Fechar conta
                    """);
            try {
                System.out.print("-> ");
                Integer opcao = input.nextInt();

                sistemaBancoService.calcular_regras_entrar_conta(opcao, conta);
                if (opcao == 5 || opcao == 6) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nOPS. Opção inválida!\n");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
