package application.services;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
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
        try {
            Scanner input = new Scanner(System.in);
            manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA CRIAR SUA CONTA");
            Conta conta = new Conta();

            System.out.print("NOME: ");
            String nome = input.nextLine();
            conta.setTitular_conta(nome);


            System.out.print("CPF: ");
            Long cpf = input.nextLong();
            conta.setCpf(cpf);
            

            System.out.print("ANO DE NASCIMENTO: ");
            Integer ano_de_nascimento = input.nextInt();
            conta.setAno_de_nascimento(ano_de_nascimento);

            boolean tipoContaConfere = false;
            while (!tipoContaConfere) {
                System.out.print("TIPO DE CONTA (CORRENTE OU POUPANÇA): ");
                String tipoConta = input.next();
                tipoConta = tipoConta.toUpperCase().trim();
                if (tipoConta.equals("CORRENTE")) {
                    conta.setTipoConta(tipoConta);
                    tipoContaConfere = true;
                } else if (tipoConta.equals("POUPANÇA")) {
                    conta.setTipoConta(tipoConta);
                    tipoContaConfere = true;
                } else {
                    System.out.println("ERRO: Tipo de conta inserido inválido.");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            List<Conta> contas = sistemaBanco.getContas();
            for (Conta contaDuplicada : contas) {
                if (contaDuplicada.getCpf().equals(cpf)) {
                    System.out.println(String.format("ERRO: Conta bancária já cadastrada com o CPF %d.", cpf));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }

            conta.setContaAtiva(ContaAtiva.SIM);
            conta.setNumero_conta(gerarNumeroContaUnico());
            
            System.out.println("\nMENSAGEM: Você acaba de ganhar R$ 50,00 por ter criado uma nova conta!");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            conta.setSaldo(+50.00);
            sistemaBanco.getContas().add(conta);
        } catch (InputMismatchException e) {
            System.out.println("OPS. Digite corretamente.");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        

        
        
        
    }

    public Conta entrarConta() {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA ENTRAR NA SUA CONTA\n");
        System.out.print("CPF: ");
        Long cpf = input.nextLong();

        System.out.print("ANO DE NASCIMENTO: ");
        Integer ano_nascimento = input.nextInt();

        List<Conta> contas = sistemaBanco.getContas();
        if (contas.size() == 0) {
            System.out.println("ERRO: Nenhuma conta foi cadastrada ainda.");
        } else {
            boolean contaEncontrada = false;
            for (Conta conta : contas) {
                if (conta.getCpf().equals(cpf) && conta.getAno_de_nascimento().equals(ano_nascimento)) {
                    contaEncontrada = true;
                    Conta contaEntrar = conta;
                    return contaEntrar;

                }
            }
            if (!contaEncontrada) { 
                    System.out.println("ERRO: a conta referenciada não existe.");   
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        }
        return null;
    }

    public void sacar (Conta conta) {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("Qual valor deseja sacar?");
        System.out.println("Valor: R$ ");
        Double valor = input.nextDouble();

        Double saldoAtual = conta.getSaldo();
        if (saldoAtual < valor) {
            System.out.println(String.format("""

                ERRO: Saldo insuficiente para sacar.
                Saldo atual: %.2f""", saldoAtual));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        } else {
            conta.setSaldo(saldoAtual - valor);
            System.out.println(String.format("""

                Saque realizado com sucesso.
                Saldo atual: R$ %.2f
                """, conta.getSaldo()));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public void depositar (Conta conta) {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("Qual valor desejar depositar?");
        System.out.println("Valor: R$ ");
        Double valor = input.nextDouble();
        Double saldoAtual = conta.getSaldo();
        conta.setSaldo(saldoAtual + valor);

        System.out.println(String.format("""

                Depósito realizado com sucesso.

                Saldo anterior: R$ %.2f 
                Saldo atual: R$ %.2f

                """, saldoAtual, conta.getSaldo()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    
    }
    public void exibirInformações(Conta conta) {
        manipuladorString.centralizar_texto("Informações sobre sua conta");
        System.out.print(String.format("""
            --------------------------------------------------------------------------------
                Numero da Conta: %s
                Titular da Conta: %s
                Data de Nascimento: %d
                CPF: %s
                Tipo da Conta: %s
                Conta Ativa:""", conta.getNumero_conta(), conta.getTitular_conta(), conta.getAno_de_nascimento(), conta.getCpf(), conta.getTipoConta()));
        System.out.print(" " + conta.getContaAtiva());
        System.out.println("\n--------------------------------------------------------------------------------\n");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        

    }

    private String gerarNumeroContaUnico () {
        Random random = new Random();
        int numeroAleatorio = 100000 + random.nextInt(900000);

        return String.valueOf(numeroAleatorio);
    }

    public void fazerTransferencia(Conta conta) {
        Scanner input = new Scanner(System.in);
        System.out.print("INSIRA O CPF DA CONTA QUE DESEJA TRANSFERIR: ");
        Long cpf = input.nextLong();

        List<Conta> contas = sistemaBanco.getContas();
        boolean contaEncontrada = false;
        Conta contaTransferir = new Conta();
        for (Conta contaTransferencia : contas) {
                if (contaTransferencia.getCpf().equals(cpf)) {
                    contaEncontrada = true;
                    contaTransferir = contaTransferencia;
                    continue;

                }
            }
            if (!contaEncontrada) { 
                    System.out.println("ERRO: a conta referenciada não existe.");   
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        
        System.out.print("QUANTO DESEJA TRANSFERIR: ");
        Double saldo = input.nextDouble();
        
        if (conta.getSaldo() < saldo) {
            System.out.println("ERRO: Saldo insuficiente para realizar esta transferência");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            conta.setSaldo(conta.getSaldo() - saldo);
            contaTransferir.setSaldo(conta.getSaldo() + saldo);

            manipuladorString.centralizar_texto("Transferência realizado com sucesso!");
            System.out.println(String.format("""
                    
                    Saldo atual: %.2f
                    """, conta.getSaldo()));
        }


    }    
       
}
