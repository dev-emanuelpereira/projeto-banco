

import application.representation.BancoController;
import application.services.ContaService;
import application.services.SistemaBancoService;
import config.ManipuladorString;
import model.entities.Conta;
import model.entities.SistemaBanco;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        List<Conta> contas = new ArrayList<Conta>();
        Conta conta = new Conta();
        ManipuladorString manipuladorString = new ManipuladorString();
        SistemaBanco sistemaBanco = new SistemaBanco(conta, contas);
        ContaService contaService = new ContaService(conta, manipuladorString, sistemaBanco);
        SistemaBancoService sistemaBancoService = new SistemaBancoService(contaService, manipuladorString);
        BancoController bancoController = new BancoController(sistemaBancoService, manipuladorString);
        
        bancoController.tela_inicial();
    }
}
