package com.github.rodolfoba.criptologia.k128.algoritmo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.rodolfoba.criptologia.k128.algoritmo.SubChaveKR5;

public class SubChaveKR5Test {

    @Test
    public void deveSerValido() {
        byte b0 = 0b00110001;
        byte b1 = 0b01010101;
        byte b2 = 0b01111011;
        byte b3 = (byte) 0b11111111;
        
        byte[] entrada = new byte[16];
        byte[] x = new byte[] { 0x10, 0x10, 0x10, b0 };
        byte[] y = new byte[] { 0x10, 0x10, 0x10, b1 };
        byte[] z = new byte[] { 0x10, 0x10, 0x10, b2 };
        byte[] w = new byte[] { 0x10, 0x10, 0x10, b3 };
        
        System.arraycopy(x, 0, entrada, 0, x.length);
        System.arraycopy(y, 0, entrada, 4, y.length);
        System.arraycopy(z, 0, entrada, 8, z.length);
        System.arraycopy(w, 0, entrada, 12, w.length);
        
        SubChaveKR5 kr5 = new SubChaveKR5(entrada);
        assertEquals(b0 & 0b00011111, kr5.getValores()[0]);
        assertEquals(b1 & 0b00011111, kr5.getValores()[1]);
        assertEquals(b2 & 0b00011111, kr5.getValores()[2]);
        assertEquals(b3 & 0b00011111, kr5.getValores()[3]);
    }

}
