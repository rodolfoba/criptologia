package com.github.rodolfoba.criptologia.k128.algoritmo;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ChavePrincipalKTest {

    @Test
    public void deveSerIgual() {
        Senha senha8 = new Senha("ABCD1234");
        Senha senha16 = new Senha("ABCD1234ABCD1234");
        assertArrayEquals(new ChavePrincipalK(senha8).getValor(), new ChavePrincipalK(senha16).getValor());
    }
}
