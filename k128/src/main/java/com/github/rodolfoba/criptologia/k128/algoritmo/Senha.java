package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

class Senha {

    private static final byte TAMANHO_MIN = 8;
    private static final byte TAMANHO_MAX = 16;
    private static final Pattern PATTERN = Pattern.compile("^(?=(.*?[0-9]){2})(?=(.*?[a-zA-Z]){2}).*$");
    private static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    private final String senhaOriginal;
    private final byte[] valor;
    

    Senha(final String senha) {
        senhaOriginal = senha;
        validaNulo();
        validaTamanho();
        validaCharset();
        validaPadrao();
        valor = senhaOriginal.getBytes(CHARSET);
    }

    private void validaNulo() {
        if (null == senhaOriginal)
            throw new SenhaInvalidaException("Senha inválida. Não deve ser null");
    }
    
    private void validaTamanho() {
        if (senhaOriginal.length() < TAMANHO_MIN || senhaOriginal.length() > TAMANHO_MAX) {
            throw new SenhaInvalidaException(
                    "Senha inválida. Quantidade de caracteres inválida: " + senhaOriginal.length());
        }
    }
    
    private void validaCharset() {
        CharsetEncoder encoder = CHARSET.newEncoder();
        if (!encoder.canEncode(senhaOriginal)) {
            throw new SenhaInvalidaException("Senha não possui apenas caracteres ISO-8859-1 (Latin-1)");
        }
    }

    private void validaPadrao() {
        if (!PATTERN.matcher(senhaOriginal).matches()) {
            throw new SenhaInvalidaException("Senha deve conter pelo menos 2 letras e 2 algarismos decimais");
        }
    }

    byte[] getValor() {
        return valor;
    }

}
