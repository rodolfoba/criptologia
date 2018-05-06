package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.math.BigInteger;
import java.util.Arrays;

class SubChaveKM32 {

    private final int[] valores = new int[4];
    
    SubChaveKM32(byte[] entrada) {
        byte[] x, y, z, w;
        x = Arrays.copyOfRange(entrada, 0, 4);
        y = Arrays.copyOfRange(entrada, 4, 8);
        z = Arrays.copyOfRange(entrada, 8, 12);
        w = Arrays.copyOfRange(entrada, 12, 16);
        
        valores[0] = new BigInteger(1, w).intValue();
        valores[1] = new BigInteger(1, z).intValue();
        valores[2] = new BigInteger(1, y).intValue();
        valores[3] = new BigInteger(1, x).intValue();
    }
    
    int[] getValores() {
        return valores;
    }
    
}
