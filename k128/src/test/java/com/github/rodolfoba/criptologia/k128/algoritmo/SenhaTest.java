package com.github.rodolfoba.criptologia.k128.algoritmo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.rodolfoba.criptologia.k128.algoritmo.Senha;
import com.github.rodolfoba.criptologia.k128.algoritmo.SenhaInvalidaException;

public class SenhaTest {

    @Test
    public void devemSerValidas() {
        String[] senhas = {
                "AB12!@#$",
                "ab12!@#$",
                "12AB!@#$",
                "12ab!@#$",
                "A1B2!@#$",
                "1A2B!@#$",
                "1A!@#$B2",
                "1A    B2",
                "0X!@#$X0",
                "ãêíóAB12",
                "AB12Ç@#$",
        };
        
        for (String senha : senhas) {
            assertNotNull(new Senha(senha));
        }
    }
    
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoNulo() {
        String valor = null;
        new Senha(valor);
    }
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoVazio() {
        String valor = "";
        new Senha(valor);
    }
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoInferiorAoTamanhoMinimo() {
        String valor = "AB12345";
        new Senha(valor);
    }
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoSuperiorAoTamanhoMaximo() {
        String valor = "12345678901234567";
        new Senha(valor);
    }
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoPossuirCaracterDeCharsetInvalido() {
        String valor = "ABCD1234ⱻ";
        new Senha(valor);
    }
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoNaoPossuirDuasLetras() {
        String valor = "1234567A";
        new Senha(valor);
    }
    
    @Test(expected = SenhaInvalidaException.class)
    public void deveFalharQuandoNaoPossuirDoisNumeros() {
        String valor = "ABCDEFG1";
        new Senha(valor);
    }
    
}
