package application.representation;

import java.util.Scanner;

import application.services.SistemaBancoService;
import config.ManipuladorString;
import model.entities.Conta;

public class BancoController {
    
    private Conta conta;
    SistemaBancoService sistemaBancoService = new SistemaBancoService();
    ManipuladorString manipuladorString = new ManipuladorString();
    
    public Scanner input = new Scanner(System.in);

    public void iniciar () {
        
    }

    public void tela_inicial () {
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
        sistemaBancoService.calcular_regras(opcao);
    }


    public static void depositar () {
        
        
    };

    
}
