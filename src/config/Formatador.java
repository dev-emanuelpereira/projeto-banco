package config;

import java.time.LocalDate;

public class Formatador {
    public static String formatar_data(LocalDate data_nascimento) {
        
        String data_formatada = String.format("%d/%d/%d", data_nascimento.getDayOfMonth(), data_nascimento.getMonthValue(), data_nascimento.getYear());

        return data_formatada;
    }

    public String formatar_cpf (Long cpfLong) {
        
        String cpf_formatado = String.valueOf(cpfLong);

        while (cpf_formatado.length() < 11) {
            cpf_formatado = "0" + cpf_formatado;
        }

        return cpf_formatado.substring(0, 3) + "." + cpf_formatado.substring(3, 6) + "." + cpf_formatado.substring(6, 9) + "-" + cpf_formatado.substring(9);
    }

}

