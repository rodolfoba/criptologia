package com.github.rodolfoba.criptologia.k128;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.rodolfoba.criptologia.k128.algoritmo.K128;

public class AceitacaoTest {

    String senha = "12345678ABCDEFGH";
    
    @Test
    public void test_Arquivo64Bits() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/entrada64"));
        assertEquals(8, original.length);
        
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_Arquivo128Bits() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/entrada128"));
        assertEquals(16, original.length);
        
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_Arquivo129Bits() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/entrada129"));
        assertEquals(17, original.length);
        
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_Arquivo256Bits() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/entrada256"));
        assertEquals(32, original.length);
        
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_Arquivo512Bits() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/entrada512"));
        assertEquals(64, original.length);
        
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_Arquivo640Bits() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/entrada640"));
        assertEquals(80, original.length);
        
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_ArquivoImagem() throws Exception {
        byte[] original = Files.readAllBytes(Paths.get("arquivos/imagem.png"));
        byte[] criptografado = K128.criptografar(original, senha);
        byte[] decriptografado = K128.decriptografar(criptografado, senha);
        assertArrayEquals(original, decriptografado);
    }
    
    @Test
    public void test_CalcularAleatoriedadeMetodo1() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("arquivos/entrada512"));
        K128.calcularAleatoriedadeMetodo1(bytes, senha);
        assertTrue(true);
    }
    
    @Test
    public void test_CalcularAleatoriedadeMetodo1Com640Bits() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("arquivos/entrada640"));
        K128.calcularAleatoriedadeMetodo1(bytes, senha);
        assertTrue(true);
    }
    
    @Test
    public void test_CalcularAleatoriedadeMetodo2() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("arquivos/entrada512"));
        K128.calcularAleatoriedadeMetodo2(bytes, senha);
        assertTrue(true);
    }
}
