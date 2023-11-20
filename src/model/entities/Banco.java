package model.entities;

import java.util.Scanner;

public abstract class Banco {

    public abstract void banco_aberto ();

    public static void banco_fechado () {
        System.out.println("Obrigado por utilizar o nosso Banco!");
        new Scanner(System.in).close();
        System.exit(0);
    };


}
