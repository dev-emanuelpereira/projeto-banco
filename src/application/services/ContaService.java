package application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import config.ManipuladorString;
import model.entities.Conta;
import model.entities.SistemaBanco;
import model.enums.ContaAtiva;

public class ContaService {

    private Conta conta;
    private SistemaBanco sistemaBanco;
    private ManipuladorString manipuladorString;



    public ContaService() {
        
    }
    public ContaService(Conta conta, ManipuladorString manipuladorString, SistemaBanco sistemaBanco) {
        this.conta = conta;
        this.manipuladorString = manipuladorString;
        this.sistemaBanco = sistemaBanco;
    }

    public void criarConta () {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA CRIAR SUA CONTA");
        Conta conta = new Conta();

        System.out.println("NOME: ");
        String nome = input.nextLine();
        conta.setTitular_conta(nome);


        System.out.println("CPF: ");
        Long cpf = input.nextLong();
        conta.setCpf(cpf);
        

        System.out.println("ANO DE NASCIMENTO: ");
        Integer ano_de_nascimento = input.nextInt();
        conta.setAno_de_nascimento(ano_de_nascimento);

        List<Conta> contas = sistemaBanco.getContas();
        for (Conta contaDuplicada : contas) {
            if (contaDuplicada.getCpf().equals(cpf)) {
                System.out.println(String.format("ERRO: Conta bancária já cadastrada com o CPF %d.", cpf));
                return;
            }
        }

        conta.setContaAtiva(ContaAtiva.SIM);

        System.out.println("Por ter criado uma conta nova, você acaba de ganhar R$ 50,00!");

        conta.setSaldo(50.00);
        sistemaBanco.getContas().add(conta);
        
        
    }

    public Conta entrarConta() {
        Scanner input = new Scanner(System.in);

        manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA ENTRAR NA SUA CONTA\n");
        System.out.println("CPF: ");
        Long cpf = input.nextLong();

        System.out.println("ANO DE NASCIMENTO: ");
        Integer ano_nascimento = input.nextInt();

        List<Conta> contas = sistemaBanco.getContas();
        
        for (Conta conta : contas) {
            boolean contaEncontrada = false;
            if (conta.getCpf().equals(cpf) && conta.getAno_de_nascimento().equals(ano_nascimento)) {
                contaEncontrada = true;
                Conta contaEntrar = conta;
                return contaEntrar;

            }
        
            if (!contaEncontrada) {
                System.out.println("Erro: a conta referenciada não existe.");   
            }
        }
        return null;
    }

    public void sacar () {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("Qual valor deseja sacar?");
        System.out.println("Valor: R$ ");
        Double valor = input.nextDouble();

         
        
    }
}
