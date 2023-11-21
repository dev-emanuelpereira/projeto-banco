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

    public void alinhar_texto_dois_direito (String texto_esquerda, String texto_direita) {
        if (texto_direita.length() >= largura_console) {
            System.out.println(texto_esquerda + texto_direita);
        }

        int espacos_brancos = (largura_console - texto_direita.length()) - texto_esquerda.length();
        String texto_alinhado = texto_esquerda + " ".repeat(espacos_brancos) + texto_direita;
        System.out.print(texto_alinhado + "\n");

    }


}
