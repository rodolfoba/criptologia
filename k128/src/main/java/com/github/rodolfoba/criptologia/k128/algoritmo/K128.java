package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.math.BigInteger;
import java.util.Arrays;

import com.github.rodolfoba.criptologia.k128.util.ByteUtil;

public class K128 {

    private static final byte[] CONSTANTE_XOR_K0 = new byte[] {
            (byte) 0x5A, (byte) 0x82, (byte) 0x79, (byte) 0x99, 
            (byte) 0x87, (byte) 0x4A, (byte) 0xA6, (byte) 0x7D, 
            (byte) 0x65, (byte) 0x7B, (byte) 0x7C, (byte) 0x8E, 
            (byte) 0xBD, (byte) 0x07, (byte) 0x02, (byte) 0x42
    };
    
    private static final BigInteger BINT_CONSTANTE_XOR_K0 = new BigInteger(1, CONSTANTE_XOR_K0);
    private static final byte ITERACOES_N = 12;
    private static final byte[] CBC_PAD;
    
    static {
        CBC_PAD = new byte[16];
        for (int i = 0; i < 16; i++) {
            CBC_PAD[i] = (byte) 0xFF;
        }
    }
    
    
    private final ChavePrincipalK chaveK;
    private final int numeroDeIteracoes;
    private byte[][] chavesIntermediariasK;
    
    private K128(ChavePrincipalK chaveK, byte numeroDeIteracoes) {
        this.chaveK = chaveK;
        this.numeroDeIteracoes = numeroDeIteracoes;
        
        chavesIntermediariasK = new byte[numeroDeIteracoes][];
    }
    
    private byte[] criptografar(byte[] x) {
        BigInteger bintChaveK = new BigInteger(1, chaveK.getValor());
        BigInteger xor = bintChaveK.xor(BINT_CONSTANTE_XOR_K0);
        byte[] xorBytes = ByteUtil.fixedSizeByteArray(xor, (CONSTANTE_XOR_K0.length));
        
//        System.out.println("ChaveK=" + bintChaveK.toString(16));
//        System.out.println("ConstanteXORK0=" + BINT_CONSTANTE_XOR_K0.toString(16));
//        System.out.println("K (XOR) Const=" + xor.toString(16));
        
        for (byte i = 0; i < numeroDeIteracoes; i++) {
            if (0 == i) {
                chavesIntermediariasK[i] = GeradorChaveIntermediariaK.gerar(xorBytes, i);
            } else {
                chavesIntermediariasK[i] = GeradorChaveIntermediariaK.gerar(chavesIntermediariasK[i - 1], i);
            }
//            System.out.println("ChaveK" + i + "=" + ByteUtil.parseHexString(chavesIntermediariasK[i]));
            
            
            SubChaveKR5 kr5 = new SubChaveKR5(chavesIntermediariasK[i]);
            SubChaveKM32 km32 = new SubChaveKM32(chavesIntermediariasK[i]);
            x = umaIteracaoCriptografia(x, kr5, km32);
//            System.out.println("X" + i + "=" + ByteUtil.parseHexString(x));
        }
        
        return x;
    }
    
    private byte[] decriptografar(byte[] y) {
        BigInteger bintChaveK = new BigInteger(1, chaveK.getValor());
        BigInteger xor = bintChaveK.xor(BINT_CONSTANTE_XOR_K0);
        byte[] xorBytes = ByteUtil.fixedSizeByteArray(xor, (CONSTANTE_XOR_K0.length));
        
//        System.out.println("ChaveK=" + bintChaveK.toString(16));
//        System.out.println("ConstanteXORK0=" + BINT_CONSTANTE_XOR_K0.toString(16));
//        System.out.println("K (XOR) Const=" + xor.toString(16));
        
        for (byte i = 0; i < numeroDeIteracoes; i++) {
            if (0 == i) {
                chavesIntermediariasK[i] = GeradorChaveIntermediariaK.gerar(xorBytes, i);
            } else {
                chavesIntermediariasK[i] = GeradorChaveIntermediariaK.gerar(chavesIntermediariasK[i - 1], i);
            }
//            System.out.println("ChaveK" + i + "=" + ByteUtil.parseHexString(chavesIntermediariasK[i]));
        }
        
        for (byte i = (byte) (numeroDeIteracoes - 1); i >= 0; i--) {
            SubChaveKR5 kr5 = new SubChaveKR5(chavesIntermediariasK[i]);
            SubChaveKM32 km32 = new SubChaveKM32(chavesIntermediariasK[i]);
            y = umaIteracaoDecriptografia(y, kr5, km32);
//            System.out.println("Y" + i + "=" + ByteUtil.parseHexString(y));
        }
        
        return y;
    }
    
    
    private byte[] umaIteracaoCriptografia(byte[] x, SubChaveKR5 kr5, SubChaveKM32 km32) {
        byte[] a, b, c, d;
        a = Arrays.copyOfRange(x, 0, 4);
        b = Arrays.copyOfRange(x, 4, 8);
        c = Arrays.copyOfRange(x, 8, 12);
        d = Arrays.copyOfRange(x, 12, 16);
        
        c = ByteUtil.fixedSizeXOR(c, FuncaoF.f2(d, kr5.getValores()[0], km32.getValores()[0]), 4);
        b = ByteUtil.fixedSizeXOR(b, FuncaoF.f1(c, kr5.getValores()[1], km32.getValores()[1]), 4);
        a = ByteUtil.fixedSizeXOR(a, FuncaoF.f3(b, kr5.getValores()[2], km32.getValores()[2]), 4);
        d = ByteUtil.fixedSizeXOR(d, FuncaoF.f2(a, kr5.getValores()[3], km32.getValores()[3]), 4);
        
        return ByteUtil.concat(c, b, a, d);
    }
    
    private byte[] umaIteracaoDecriptografia(byte[] x, SubChaveKR5 kr5, SubChaveKM32 km32) {
        byte[] a, b, c, d;
        c = Arrays.copyOfRange(x, 0, 4);
        b = Arrays.copyOfRange(x, 4, 8);
        a = Arrays.copyOfRange(x, 8, 12);
        d = Arrays.copyOfRange(x, 12, 16);
        
        d = ByteUtil.fixedSizeXOR(d, FuncaoF.f2(a, kr5.getValores()[3], km32.getValores()[3]), 4);
        a = ByteUtil.fixedSizeXOR(a, FuncaoF.f3(b, kr5.getValores()[2], km32.getValores()[2]), 4);
        b = ByteUtil.fixedSizeXOR(b, FuncaoF.f1(c, kr5.getValores()[1], km32.getValores()[1]), 4);
        c = ByteUtil.fixedSizeXOR(c, FuncaoF.f2(d, kr5.getValores()[0], km32.getValores()[0]), 4);
        
        return ByteUtil.concat(a, b, c, d);
    }
    
    public static byte[] criptografar(byte[] dados, String senhaTexto) {
        Senha senha = new Senha(senhaTexto);
        ChavePrincipalK chaveK = new ChavePrincipalK(senha);
        K128 k128 = new K128(chaveK, ITERACOES_N);
        
        int quantidade = dados.length / 16;
        int resto = dados.length % 16;
        if (resto > 0) {
            quantidade++;
        }
        
        byte[] x = new byte[(16 * quantidade)];
        System.arraycopy(dados, 0, x, 0, dados.length);
        
        // Realiza pad caso dados menor que 16 bytes
        if (dados.length < 16) {
            System.arraycopy(CBC_PAD, 0, x, dados.length, 16 - dados.length);
        }
        
        // Realiza pad caso ultimo bloco seja menor que 16 bytes
        if (resto > 0) {
            System.arraycopy(CBC_PAD, 0, x, dados.length, (16 - resto));
        }
        
        byte[] y = new byte[x.length + 16];
        byte[] xor = CBC_PAD;
        for (int i = 0; i < quantidade; i++) {
            byte[] bloco = Arrays.copyOfRange(x, (16 * i), (16 * i) + 16);
//            bloco = ByteUtil.fixedSizeXOR(bloco, xor, 16);

            byte[] cripto = k128.criptografar(bloco);
            xor = cripto;
            System.arraycopy(cripto, 0, y, 16 * i, cripto.length);
        }
        
        // adiciona bloco extra de controle
        byte[] paddings = new byte[16];
        byte pad = 0;
        if (resto > 0) {
            pad = (byte) (16 - resto);
        }
        
        paddings[paddings.length - 1] = pad;
        byte[] paddingsCripto = k128.criptografar(paddings);
        System.arraycopy(paddingsCripto, 0, y, y.length - 16, paddingsCripto.length);
        
        return y;
    }
    
    public static byte[] decriptografar(byte[] y, String senhaTexto) {
        Senha senha = new Senha(senhaTexto);
        ChavePrincipalK chaveK = new ChavePrincipalK(senha);
        K128 k128 = new K128(chaveK, ITERACOES_N);
        
        byte pad = 0;
        int quantidade = (y.length / 16) - 1;
        byte[] x = new byte[(y.length - 16)];
        int i;
        for (i = 0; i < quantidade; i++) {
            byte[] bloco = Arrays.copyOfRange(y, (16 * i), (16 * i) + 16);
            byte[] cripto = k128.decriptografar(bloco); 
            
            System.arraycopy(cripto, 0, x, 16 * i, cripto.length);
            
            if (i == (quantidade - 1)) {
                pad = cripto[cripto.length - 1];
            }
        }
        
        // Remove bytes de padding e bloco extra de controle;
        x = Arrays.copyOfRange(x, 0, (x.length - 16) - pad);
        
        return x;
    }
    
}
