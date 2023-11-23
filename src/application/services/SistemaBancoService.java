package application.services;

import java.time.LocalDate;
import java.util.List;

import config.Formatador;
import config.ManipuladorString;
import model.entities.Banco;
import model.entities.Conta;
import model.entities.SistemaBanco;
import model.enums.ContaAtiva;

public class SistemaBancoService {

    private ContaService contaService;
    private ManipuladorString manipuladorString;
    private Formatador formatador;
    private SistemaBanco sistemaBanco;

    public SistemaBancoService() {
    }

    public SistemaBancoService(ContaService contaService, ManipuladorString manipuladorString, Formatador formatador,
            SistemaBanco sistemaBanco) {
        this.contaService = contaService;
        this.manipuladorString = manipuladorString;
        this.formatador = formatador;
        this.sistemaBanco = sistemaBanco;
    }

    public Conta calcular_regras_tela_inicial(int opcao_numero) {
        switch (opcao_numero) {
            case 1:
                contaService.criarConta();
                break;
            case 2:
                Conta conta = contaService.entrarConta();
                return conta;
            case 3:
                exibirTodasContas();
                break;
            case 4:
                Banco.banco_fechado();
                break;
        }
        return null;
    }

    public void calcular_regras_entrar_conta(int opcao_numero, Conta conta) {
        switch (opcao_numero) {
            case 1:
                contaService.sacar(conta);
                break;
            case 2:
                contaService.depositar(conta);
                break;
            case 3:
                contaService.fazerTransferencia(conta);
                break;
            case 4:
                contaService.exibirInformações(conta, conta.getCpf(), conta.getData_nascimento());
                break;
            case 5:
                contaService.sairConta();
                break;
            case 6:
                contaService.fecharConta(conta, conta.getCpf(), conta.getSenha());
                break;
        }
    }

    private void exibirTodasContas() {
        try {
            List<Conta> contas = sistemaBanco.getContas();
            if (contas.size() == 0) {
                System.out.println("\nMENSAGEM: Nenhuma conta foi cadastrada no sistema.");
                Thread.sleep(3000);
                return;
            }
            String formato_tabela = "| %-11s | %-14s | %-29s | %-7s | %-11s | %-18s |";

            linhas(6);
            System.out.println(String.format(formato_tabela, "Conta Ativa", "Tipo de Conta", "Titular", "N Conta",
                    "CPF", "Data de Nascimento"));
            linhas(6);

            for (Conta conta : contas) {
                System.out.println(String.format(formato_tabela, conta.getContaAtiva(), conta.getTipoConta(),
                        conta.getTitular_conta(), conta.getNumero_conta(), conta.getCpf(),
                        Formatador.formatar_data(conta.getData_nascimento())));
            }

            linhas(6);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void linhas(int numColunas) {
        for (int i = 0; i < numColunas; i++) {
            System.out.print("+-----------------");
        }
        System.out.println("+");
    }
}
