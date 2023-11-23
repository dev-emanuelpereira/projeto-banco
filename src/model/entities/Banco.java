package model.entities;

import java.util.Scanner;

public abstract class Banco {
    
    public abstract void banco_aberto ();

    public static void banco_fechado () {

        System.out.println("\nObrigado por utilizar nosso banco. Volte sempre!\n");
        new Scanner(System.in).close();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    };


}
