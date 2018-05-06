package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.util.Arrays;

class ChavePrincipalK {

    private static final int MAX_BYTES = 16;
    
    private byte[] valor;
    
    ChavePrincipalK(Senha senha) {
        byte[] bytes = Arrays.copyOf(senha.getValor(), MAX_BYTES);
        for (int i = senha.getValor().length; i < MAX_BYTES; i++) {
            bytes[i] = senha.getValor()[i - senha.getValor().length];
        }
        
        this.valor = bytes;
    }
    
    byte[] getValor() {
        return valor;
    }
    
}
