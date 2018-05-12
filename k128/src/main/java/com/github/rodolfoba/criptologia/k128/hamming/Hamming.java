package com.github.rodolfoba.criptologia.k128.hamming;

public class Hamming {

    private static final byte[] mask;
    
    static {
        mask = new byte[8];
        mask[0] = (byte) 0b10000000;
        mask[1] = (byte) 0b01000000;
        mask[2] = (byte) 0b00100000;
        mask[3] = (byte) 0b00010000;
        mask[4] = (byte) 0b00001000;
        mask[5] = (byte) 0b00000100;
        mask[6] = (byte) 0b00000010;
        mask[7] = (byte) 0b00000001;
    }
    
    public static byte alteraBit(byte b, int bit) {
        return (byte) (b ^ mask[bit - 1]);
    }

    public static int distancia(byte[] bloco1, byte[] bloco2) { 
        int distancia = 0;

        for (int i = 0; i < Math.min(bloco1.length, bloco2.length); i++) {
            distancia += distancia(bloco1[i], bloco2[i]);
        }
        
        return distancia;
    }
    
    public static int distancia(byte byte1, byte byte2) {
        int distancia = 0;
        byte val = (byte) (byte1 ^ byte2);

        // Conta o numero de bits setados (Knuth's algorithm)
        while (val != 0) {
            ++distancia;
            val &= val - 1;
        }

        return distancia;
    }
    
}
