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

    Scanner input = new Scanner(System.in);

    public ContaService() {
        
    }
    public ContaService(Conta conta, ManipuladorString manipuladorString, SistemaBanco sistemaBanco) {
        this.conta = conta;
        this.manipuladorString = manipuladorString;
        this.sistemaBanco = sistemaBanco;
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

        sistemaBanco.getContas().add(conta);
    }

    public void entrarConta() {
        manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA ENTRAR NA SUA CONTA\n");
        System.out.println("CPF: ");
        Integer cpf = input.nextInt();

        System.out.println("ANO DE NASCIMENTO");
        Integer ano_nascimento = input.nextInt();

        List<Conta> contas = new ArrayList<>();
        for (Conta conta : contas) {
            if (conta.getCpf() == cpf || conta.getAno_de_nascimento() == ano_nascimento){
                System.out.println("CONTA VALIDA");
            } else {
                System.out.println("CONTA NAO ENCONTRADA NO SISTEMA");
            }
        }
    }

    public Double depositar (Double valor_depositar) {
        conta.setSaldo(+valor_depositar);
        return conta.getSaldo(); 
    }
}
