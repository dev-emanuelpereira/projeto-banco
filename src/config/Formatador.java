package config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatador {
    public String formatar_data(String data) {

        SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoComBarra = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dataNascimento = formatoOriginal.parse(data);
            String dataFormatadaComBarra = formatoComBarra.format(dataNascimento);

            return dataFormatadaComBarra;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    public String formatar_cpf (Long cpf) {
        String cpf_formatado = "" + cpf;
        cpf_formatado = cpf_formatado.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3-$4");
        return cpf_formatado;
    }

}

