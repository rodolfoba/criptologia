package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.util.Arrays;

class SubChaveKR5 {

    private final byte[] valores = new byte[4];
    
    SubChaveKR5(byte[] entrada) {
        byte[] x, y, z, w;
        x = Arrays.copyOfRange(entrada, 0, 4);
        y = Arrays.copyOfRange(entrada, 4, 8);
        z = Arrays.copyOfRange(entrada, 8, 12);
        w = Arrays.copyOfRange(entrada, 12, 16);
        
        // big-endian
        valores[0] = (byte) (x[x.length - 1] & 0b00011111);
        valores[1] = (byte) (y[y.length - 1] & 0b00011111);
        valores[2] = (byte) (z[z.length - 1] & 0b00011111);
        valores[3] = (byte) (w[w.length - 1] & 0b00011111);
    }
    
    byte[] getValores() {
        return valores;
    }
    
}
