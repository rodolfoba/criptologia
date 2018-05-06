package com.github.rodolfoba.criptologia.k128.algoritmo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.rodolfoba.criptologia.k128.algoritmo.SubChaveKM32;

public class SubChaveKM32Test {

    @Test
    public void deveSerValido() {
        byte[] entrada = new byte[16];
        byte[] x = new byte[] { 0x01, 0x02, 0x03, 0x04 }; // 16909060
        byte[] y = new byte[] { 0x05, 0x06, 0x07, 0x08 }; // 84281096
        byte[] z = new byte[] { 0x09, 0x10, 0x11, 0x12 }; // 152047890
        byte[] w = new byte[] { 0x13, 0x14, 0x15, 0x16 }; // 320083222
        
        System.arraycopy(x, 0, entrada, 0, x.length);
        System.arraycopy(y, 0, entrada, 4, y.length);
        System.arraycopy(z, 0, entrada, 8, z.length);
        System.arraycopy(w, 0, entrada, 12, w.length);
        
        SubChaveKM32 km32 = new SubChaveKM32(entrada);
        assertEquals(320083222, km32.getValores()[0]);
        assertEquals(152047890, km32.getValores()[1]);
        assertEquals(84281096, km32.getValores()[2]);
        assertEquals(16909060, km32.getValores()[3]);
    }

}
