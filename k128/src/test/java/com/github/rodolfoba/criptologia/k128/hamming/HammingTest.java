package com.github.rodolfoba.criptologia.k128.hamming;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HammingTest {

    @Test
    public void deveTrocarBits() {
        byte expected = (byte) 0b11111111;
        byte b = (byte) 0b11111110;
        byte alterado = Hamming.alteraBit(b, 8);
        assertEquals(expected, alterado);
        
        b = (byte) 0b11111101;
        alterado = Hamming.alteraBit(b, 7);
        assertEquals(expected, alterado);
        
        b = (byte) 0b11111011;
        alterado = Hamming.alteraBit(b, 6);
        assertEquals(expected, alterado);
        
        b = (byte) 0b11110111;
        alterado = Hamming.alteraBit(b, 5);
        assertEquals(expected, alterado);
        
        b = (byte) 0b11101111;
        alterado = Hamming.alteraBit(b, 4);
        assertEquals(expected, alterado);
        
        b = (byte) 0b11011111;
        alterado = Hamming.alteraBit(b, 3);
        assertEquals(expected, alterado);
        
        b = (byte) 0b10111111;
        alterado = Hamming.alteraBit(b, 2);
        assertEquals(expected, alterado);
        
        b = (byte) 0b01111111;
        alterado = Hamming.alteraBit(b, 1);
        assertEquals(expected, alterado);
    }
    
    @Test
    public void deveCalcularDistancia() {
        byte b1 = (byte) 0b01010101;
        byte b2 = (byte) 0b10101010;
        
        int distancia = Hamming.distancia(b1, b2);
        assertEquals(8, distancia);
        
        b1 = (byte) 0b00001111;
        b2 = (byte) 0b11111111;
        distancia = Hamming.distancia(b1, b2);
        assertEquals(4, distancia);
        
        b1 = (byte) 0b00000001;
        b2 = (byte) 0b00000000;
        distancia = Hamming.distancia(b1, b2);
        assertEquals(1, distancia);
    }
    
    
}
