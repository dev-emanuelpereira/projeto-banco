package application.services;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
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
    public static LocalDate data_atual = LocalDate.now();

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
            String nome = input.nextLine().trim().toUpperCase();
            conta.setTitular_conta(nome);

            System.out.print("CPF (SOMENTE NUMEROS): ");
            Long cpf = input.nextLong();
            cpf = cadastrar_cpf(input, cpf);
            conta.setCpf(cpf);

            System.out.print("DIA DE NASCIMENTO: ");
            int dia_de_nascimento = input.nextInt();

            System.out.print("MÊS DE NASCIMENTO: ");
            int mes_de_nascimento = input.nextInt();

            System.out.print("ANO DE NASCIMENTO: ");
            int ano_de_nascimento = input.nextInt();

            LocalDate data_nascimento = LocalDate.of(ano_de_nascimento, mes_de_nascimento, dia_de_nascimento);
            int idade = calcularIdade(data_atual, data_nascimento);
            if (idade < 18) {
                System.out.println("\nERRO: Você não pode abrir uma conta, pois não é maior de idade");
                Thread.sleep(2000);
                return;
            }
            conta.setData_nascimento(data_nascimento);

            conferir_tipo_conta(conta, input);

            boolean senhasConferem = false;
            while (!senhasConferem) {
                System.out.println("\nDIGITE UMA SENHA: ");
                String senha1 = input.next().trim();

                System.out.println("\nDIGITE A SENHA NOVAMENTE: ");
                String senha2 = input.next().trim();

                if (senha1.equals(senha2)) {
                    conta.setSenha(senha1);
                    senhasConferem = true;
                    continue;
                } else {
                    System.out.println("\nERRO: As senhas não conferem.\n");
                    Thread.sleep(2500);

                }
            }

            List<Conta> contas = sistemaBanco.getContas();
            for (Conta contaDuplicada : contas) {
                if (contaDuplicada.getCpf().equals(cpf)) {
                    System.out.println(String.format("\nERRO: Conta bancária já cadastrada com o CPF %s.",
                            formatador.formatar_cpf(cpf)));
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
            Thread.sleep(2500);

            System.out.println("");
            manipuladorString.centralizar_texto("CONTA CADASTRADA COM SUCESSO!");

            exibirInformações(conta, cpf, data_nascimento);
            Thread.sleep(3000);

            System.out.println(String.format("MENSAGEM: Você acaba de ganhar R$ %.2f por ter criado uma nova conta!",
                    conta.getSaldo()));

            Thread.sleep(3000);
            sistemaBanco.getContas().add(conta);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DateTimeException e) {
            System.out.println("\nOPS. Data Invalida");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return;
        }

    }

    private Long cadastrar_cpf(Scanner input, Long cpf) {
        String cpf_string = String.valueOf(cpf).trim();
        while (cpf_string.length() != 11) {
            System.out.println("\nERRO: CPF Inválido!\n");
            System.out.print("CPF: ");
            cpf = input.nextLong();
            cpf_string = String.valueOf(cpf).trim();

        }
        return cpf;

    }

    private void conferir_tipo_conta(Conta conta, Scanner input) {
        boolean tipoContaConfere = false;
        try {
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
                    Thread.sleep(2000);
                }
            }
            if (conta.getTipoConta().equals("POUPANCA")) {
                conta.setSaldo(150.00);
            } else {
                conta.setSaldo(50.00);
            }
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }

    public Conta entrarConta() {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("INSIRA AS INFORMAÇÕES PARA ENTRAR NA SUA CONTA\n");

        Long cpf = 0L;
        String numero_conta = "";
        boolean credenciais_confere = false;
        try {
            while (!credenciais_confere) {
                System.out.print("(SOMENTE NUMEROS) CPF ou Numero da Conta: ");
                String entrar = input.nextLine().trim();

                if (entrar.length() == 11) {
                    cpf = Long.parseLong(entrar);
                    credenciais_confere = true;
                } else if (entrar.length() == 6) {
                    numero_conta = entrar;
                    credenciais_confere = true;
                } else {
                    System.out.println("\nERRO: Dados incorretos.");

                    Thread.sleep(3000);
                    return null;
                }
                System.out.print("SENHA: ");
                String senha = input.nextLine();

                List<Conta> contas = sistemaBanco.getContas();
                if (contas.size() == 0) {
                    System.out.println("ERRO: Nenhuma conta foi cadastrada ainda.");
                } else {
                    boolean contaEncontrada = false;
                    for (Conta conta : contas) {
                        if (conta.getContaAtiva().equals(ContaAtiva.NAO)) {
                            System.out
                                    .println("\nMENSAGEM: Você não pode entrar na sua conta pois ela foi desativada.");
                            Thread.sleep(3000);
                            return null;
                        } else if (conta.getCpf().equals(cpf) && conta.getSenha().equals(senha)) {
                            contaEncontrada = true;
                            Conta contaEntrar = conta;

                            System.out.print("\nEntrando na conta");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);

                            return contaEntrar;

                        }
                        if (conta.getNumero_conta().equals(numero_conta) && conta.getSenha().equals(senha)) {
                            contaEncontrada = true;
                            Conta contaEntrar = conta;

                            System.out.print("\nEntrando na conta");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);

                            return contaEntrar;

                        }
                    }
                    if (!contaEncontrada) {
                        System.out.println(
                                "\nERRO: Não foi possível entrar na conta, verifique os dados inseridos e tente novamente.");
                        Thread.sleep(2000);
                        return null;
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sacar(Conta conta) {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("Qual valor deseja sacar?\n");

        System.out.println(String.format("Saldo Atual: R$ %.2f", conta.getSaldo()));
        System.out.print("Valor para sacar: R$ ");
        Double valor = input.nextDouble();

        Double saldoAtual = conta.getSaldo();
        try {
            if (saldoAtual < valor) {
                System.out.println(String.format("""

                        ERRO: Saldo insuficiente para sacar.
                        Saldo atual: %.2f""", saldoAtual));

                Thread.sleep(2500);

            } else {
                conta.setSaldo(saldoAtual - valor);
                System.out.println("");
                manipuladorString.centralizar_texto("Saque realizado com sucesso!");
                System.out.println(String.format("""
                        Saldo atual: R$ %.2f
                        """, conta.getSaldo()));

                Thread.sleep(3000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depositar(Conta conta) {
        Scanner input = new Scanner(System.in);
        manipuladorString.centralizar_texto("Qual valor desejar depositar?");
        System.out.print("Valor para depositar: R$ ");
        Double valor = input.nextDouble();
        Double saldoAtual = conta.getSaldo();
        conta.setSaldo(saldoAtual + valor);

        manipuladorString.centralizar_texto("Depósito realizado com sucesso!");
        System.out.println(String.format("""

                Saldo anterior: R$ %.2f
                Saldo atual: R$ %.2f

                """, saldoAtual, conta.getSaldo()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void exibirInformações(Conta conta, Long cpf, LocalDate data_nascimento) {

        String cpf_formatado = formatador.formatar_cpf(cpf);
        System.out.print(String.format("""
                --------------------------------------------------------------------------------
                    Numero da Conta: %s
                    Titular da Conta: %s
                    Data de Nascimento: %s
                    CPF: %s
                    Tipo da Conta: %s
                    Conta Ativa:""", conta.getNumero_conta(), conta.getTitular_conta(),
                formatador.formatar_data(data_nascimento),
                cpf_formatado, conta.getTipoConta()));
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
        try {
            Scanner input = new Scanner(System.in);
            Conta contaDeposito = new Conta();

            manipuladorString.centralizar_texto("TRANSFERENCIA BANCÁRIA");
            System.out.print("\nCPF da conta que deseja transferir: ");
            Long cpf = input.nextLong();

            if (cpf.equals(conta.getCpf())) {
                System.out.println("\nOPS! Você não pode fazer uma transferência pra si próprio.");
                Thread.sleep(3000);
                return;
            }

            List<Conta> contas = sistemaBanco.getContas();
            boolean contaEncontrada = false;

            for (Conta contaTransferencia : contas) {
                if (contaTransferencia.getCpf().equals(cpf)) {
                    contaEncontrada = true;
                    contaDeposito = contaTransferencia;

                    System.out.print("Valor a ser transferido: R$ ");
                    Double saldo = input.nextDouble();
                    if (conta.getSaldo() < saldo) {
                        System.out.println("\nERRO: Saldo insuficiente para realizar esta transferência");
                        Thread.sleep(3000);

                    } else {
                        Thread.sleep(2000);
                        conta.setSaldo(conta.getSaldo() - saldo);
                        contaDeposito.setSaldo(contaDeposito.getSaldo() + saldo);

                        System.out.println("");
                        manipuladorString.centralizar_texto("Transferência realizado com sucesso!");
                        System.out.println(String.format("""
                                Transferência realizada para: %s
                                CPF: %s
                                Valor transferido: R$ %.2f

                                Seu saldo atual: R$ %.2f

                                """, contaDeposito.getTitular_conta(), formatador.formatar_cpf(contaDeposito.getCpf()),
                                saldo, conta.getSaldo()));
                        Thread.sleep(5000);
                    }
                    continue;
                }

            }
        
        if (!contaEncontrada) {
            System.out.println("\nERRO: a conta referenciada não existe.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fecharConta(Conta conta, Long cpf, String senha) {
        try {
            Scanner input = new Scanner(System.in);

            System.out.println("\nDeseja mesmo fechar a conta?");
            String opcao = input.nextLine().toUpperCase();

            if (opcao.equals("SIM")) {
                boolean contaEncontrada = false;
                List<Conta> contas = sistemaBanco.getContas();
                for (Conta contaFechar : contas) {
                    if (contaFechar.getCpf().equals(cpf) && conta.getSenha().equals(senha)) {
                        contaEncontrada = true;
                        if (conta.getSaldo() > 0) {
                            System.out.println("\nERRO: Você não pode fechar sua conta com um saldo na conta.");
                            Thread.sleep(3000);
                        } else {
                            conta.setContaAtiva(ContaAtiva.NAO);
                            System.out
                                    .println("\nConta cancelada com sucesso! Você não poderá mais entrar nesta conta.");
                            Thread.sleep(3000);
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
                return;
            } else {
                System.out.println("OPS. Opção inválida.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int calcularIdade(LocalDate data_atual, LocalDate data_nascimento) {

        Period periodo = Period.between(data_nascimento, data_atual);

        int idade = periodo.getYears();

        return idade;
    }

    public void sairConta() {
        try {
            System.out.print("\nSaindo da conta");
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
    }
}
