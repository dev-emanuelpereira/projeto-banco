package application.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import config.Formatador;
import config.ManipuladorString;
import model.entities.Conta;
import model.entities.SistemaBanco;
import model.enums.ContaAtiva;

public class ContaService {

    private Conta conta;
    private Formatador formatador;
    private SistemaBanco sistemaBanco;
    private ManipuladorString manipuladorString;

    public ContaService() {

    }

    public ContaService(Conta conta, ManipuladorString manipuladorString, SistemaBanco sistemaBanco,
            Formatador formatador) {
        this.conta = conta;
        this.manipuladorString = manipuladorString;
        this.sistemaBanco = sistemaBanco;
        this.formatador = formatador;
    }

    public void criarConta() {
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

            boolean maior_de_idade = false;
            while (!maior_de_idade) {
                int dataAtual = LocalDateTime.now().getYear();

                System.out.print("ANO DE NASCIMENTO: ");
                int ano_de_nascimento = input.nextInt();
                conta.setAno_de_nascimento(ano_de_nascimento);

                int idade = dataAtual - ano_de_nascimento;
                if (idade > 18) {
                    maior_de_idade = true;
                } else {
                    System.out.println("ERRO: Você não pode abrir uma conta, pois não é maior de idade");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            boolean tipoContaConfere = false;
            while (!tipoContaConfere) {
                System.out.print("TIPO DE CONTA (CORRENTE OU POUPANCA): ");
                String tipoConta = input.next();
                tipoConta = tipoConta.toUpperCase().trim();
                if (tipoConta.equals("CORRENTE")) {
                    conta.setTipoConta(tipoConta);
                    tipoContaConfere = true;
                } else if (tipoConta.equals("POUPANCA")) {
                    conta.setTipoConta(tipoConta);
                    tipoContaConfere = true;
                } else {
                    System.out.println("\nERRO: Tipo de conta inserido inválido.\n");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            boolean senhasConferem = false;
            while (!senhasConferem) {
                System.out.print("\nDIGITE UMA SENHA: ");
                String senha1 = input.next();

                System.out.print("DIGITE A SENHA NOVAMENTE: ");
                String senha2 = input.next();

                if (senha1.equals(senha2)) {
                    conta.setSenha(senha1);
                    senhasConferem = true;
                    continue;
                } else {
                    System.out.println("\nERRO: As senhas não conferem.\n");
                    try {
                        Thread.sleep(2500);
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

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (conta.getTipoConta().equals("POUPANCA")) {
                conta.setSaldo(150.00);
            } else {
                conta.setSaldo(50.00);
            }

            System.out.println("");
            manipuladorString.centralizar_texto("CONTA CADASTRADA COM SUCESSO!");
            System.out.println(String.format("MENSAGEM: Você acaba de ganhar R$ %.2f por ter criado uma nova conta!",
                    conta.getSaldo()));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        System.out.print("SENHA: ");
        String senha = input.next();

        List<Conta> contas = sistemaBanco.getContas();
        if (contas.size() == 0) {
            System.out.println("ERRO: Nenhuma conta foi cadastrada ainda.");
        } else {
            boolean contaEncontrada = false;
            for (Conta conta : contas) {
                if (conta.getCpf().equals(cpf) && conta.getSenha().equals(senha)) {
                    contaEncontrada = true;
                    Conta contaEntrar = conta;

                    try {
                        System.out.print("\nEntrando na conta");
                        Thread.sleep(1000);
                        System.out.print(".");
                        Thread.sleep(1000);
                        System.out.print(".");
                        Thread.sleep(1000);
                        System.out.print(".");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    public void sacar(Conta conta) {
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

    public void depositar(Conta conta) {
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

    public void exibirInformações(Conta conta, Long cpf) {
        String cpf_formatado = formatador.formatar_cpf(cpf);
        manipuladorString.centralizar_texto("Informações sobre sua conta");
        System.out.print(String.format("""
                --------------------------------------------------------------------------------
                    Numero da Conta: %s
                    Titular da Conta: %s
                    Data de Nascimento: %d
                    CPF: %s
                    Tipo da Conta: %s
                    Conta Ativa:""", conta.getNumero_conta(), conta.getTitular_conta(), conta.getAno_de_nascimento(),
                conta.getCpf(), conta.getTipoConta()));
        System.out.print(" " + conta.getContaAtiva());
        System.out.println("\n--------------------------------------------------------------------------------\n");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String gerarNumeroContaUnico() {
        Random random = new Random();
        int numeroAleatorio = 100000 + random.nextInt(900000);

        return String.valueOf(numeroAleatorio);
    }

    public void fazerTransferencia(Conta conta) {
        Scanner input = new Scanner(System.in);
        Conta contaDeposito = new Conta();

        System.out.print("INSIRA O CPF DA CONTA QUE DESEJA TRANSFERIR: ");
        Long cpf = input.nextLong();

        List<Conta> contas = sistemaBanco.getContas();
        boolean contaEncontrada = false;

        for (Conta contaTransferencia : contas) {
            if (contaTransferencia.getCpf().equals(cpf)) {
                contaEncontrada = true;
                contaDeposito = contaTransferencia;

                System.out.println("QUANTO DESEJA TRANSFERIR: ");
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
                    contaDeposito.setSaldo(contaDeposito.getSaldo() + saldo);

                    manipuladorString.centralizar_texto("Transferência realizado com sucesso!");
                    System.out.println(String.format("""

                            Saldo atual: %.2f
                            """, conta.getSaldo()));
                }
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
    }

    public void fecharConta(Conta conta, Long cpf, String senha) {
        Scanner input = new Scanner(System.in);

        System.out.println("Deseja mesmo fechar a conta?");
        String opcao = input.nextLine();
        opcao.toUpperCase();

        if (opcao.equals("SIM")) {
            boolean contaEncontrada = false;
            List<Conta> contas = sistemaBanco.getContas();
            for (Conta contaFechar : contas) {
                if (contaFechar.getCpf().equals(cpf) && conta.getSenha().equals(senha)) {
                    contaEncontrada = true;
                    if (conta.getSaldo() > 0) {
                        System.out.println("ERRO: Você não pode fechar sua conta com um saldo na conta.");
                    } else {
                        conta.setContaAtiva(ContaAtiva.NAO);
                        System.out.println("Conta cancelada com sucesso.");
                    }
                    
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

        } else if (opcao.equals("NAO")) {
            System.out.println("");
        } else {
            System.out.println("OPS. Opção inválida.");
        }
    }
}
