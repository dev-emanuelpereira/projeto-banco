package application.services;

import java.util.Scanner;

import config.ManipuladorString;
import model.entities.Conta;
import model.enums.ContaAtiva;

public class ContaService {

    private Conta conta;
    private ManipuladorString manipuladorString = new ManipuladorString();

    Scanner input = new Scanner(System.in);

    public ContaService() {
        
    }
    public ContaService(Conta conta) {
        this.conta = conta;
    }

    public void criarConta () {
        manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA CRIAR SUA CONTA");
        Conta conta = new Conta();

        System.out.println("NOME: ");
        String nome = input.nextLine();
        conta.setTitular_conta(nome);

        System.out.println("CPF: ");
        Integer cpf = input.nextInt();
        conta.setCpf(cpf);

        System.out.println("ANO DE NASCIMENTO: ");
        Integer ano_de_nascimento = input.nextInt();
        conta.setAno_de_nascimento(ano_de_nascimento);


        conta.setContaAtiva(ContaAtiva.SIM);
        
        
    }
    public Double depositar (Double valor_depositar) {
        conta.setSaldo(+valor_depositar);
        return conta.getSaldo(); 
    }
}
