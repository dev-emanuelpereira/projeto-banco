package config;

public class ManipuladorString {

    public final int largura_console = 80;

    public void centralizar_texto (String texto) {
        
        if (texto.length() >= largura_console) {
            System.out.println(texto);
        }

        int espacos_brancos = (largura_console - texto.length()) / 2;
        String texto_centralizado = " ".repeat(espacos_brancos) + texto + " ".repeat(espacos_brancos);
        System.out.println(texto_centralizado + "\n");
    }
}
