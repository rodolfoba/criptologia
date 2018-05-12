package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Arrays;

import com.github.rodolfoba.criptologia.k128.hamming.Hamming;
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
    
    private byte[] criptografa(byte[] x) {
        BigInteger bintChaveK = new BigInteger(1, chaveK.getValor());
        BigInteger xor = bintChaveK.xor(BINT_CONSTANTE_XOR_K0);
        byte[] xorBytes = ByteUtil.fixedSizeByteArray(xor, (CONSTANTE_XOR_K0.length));
        
        for (byte i = 0; i < numeroDeIteracoes; i++) {
            if (0 == i) {
                chavesIntermediariasK[i] = ChaveIntermediariaK.gerar(xorBytes, i);
            } else {
                chavesIntermediariasK[i] = ChaveIntermediariaK.gerar(chavesIntermediariasK[i - 1], i);
            }
            System.out.println("ChaveK(" + i + ") = " + ByteUtil.parseHexString(chavesIntermediariasK[i]));
            
            
            SubChaveKR5 kr5 = new SubChaveKR5(chavesIntermediariasK[i]);
            SubChaveKM32 km32 = new SubChaveKM32(chavesIntermediariasK[i]);
            x = umaIteracaoCriptografia(x, kr5, km32);
            System.out.println("X(" + i + ") = " + ByteUtil.parseHexString(x));
        }
        
        return x;
    }
    
    private byte[] decriptografa(byte[] y) {
        BigInteger bintChaveK = new BigInteger(1, chaveK.getValor());
        BigInteger xor = bintChaveK.xor(BINT_CONSTANTE_XOR_K0);
        byte[] xorBytes = ByteUtil.fixedSizeByteArray(xor, (CONSTANTE_XOR_K0.length));
        
        for (byte i = 0; i < numeroDeIteracoes; i++) {
            if (0 == i) {
                chavesIntermediariasK[i] = ChaveIntermediariaK.gerar(xorBytes, i);
            } else {
                chavesIntermediariasK[i] = ChaveIntermediariaK.gerar(chavesIntermediariasK[i - 1], i);
            }
            
            System.out.println("ChaveK(" + i + ") = " + ByteUtil.parseHexString(chavesIntermediariasK[i]));
        }
        
        for (byte i = (byte) (numeroDeIteracoes - 1); i >= 0; i--) {
            SubChaveKR5 kr5 = new SubChaveKR5(chavesIntermediariasK[i]);
            SubChaveKM32 km32 = new SubChaveKM32(chavesIntermediariasK[i]);
            y = umaIteracaoDecriptografia(y, kr5, km32);
            System.out.println("Y(" + i + ") = " + ByteUtil.parseHexString(y));
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
        BigInteger bintChaveK = new BigInteger(1, chaveK.getValor());
        System.out.println("ChaveK = " + bintChaveK.toString(16));
        
        int quantidade = (dados.length / 16);
        int resto = dados.length % 16;
        byte[] x = dados;
        if (resto > 0) {
            quantidade++;
            x = normaliza(dados);
        }
        
        byte[] y = new byte[x.length + 16];
        byte[] xor = CBC_PAD;
        quantidade++; // byte extra
        for (int i = 0; i < quantidade; i++) {
            byte[] bloco; 
            if (i == (quantidade - 1)) {
                bloco = new byte[16];
                byte pad = 0;
                if (resto > 0) {
                    pad = (byte) (16 - resto);
                }
                bloco[bloco.length - 1] = pad;
            } else {
                bloco = Arrays.copyOfRange(x, (16 * i), (16 * i) + 16);
            }
            bloco = ByteUtil.fixedSizeXOR(bloco, xor, 16);

            byte[] cripto = k128.criptografa(bloco);
            xor = cripto;
            System.arraycopy(cripto, 0, y, 16 * i, cripto.length);
        }
        
        return y;
    }
    
    public static byte[] decriptografar(byte[] y, String senhaTexto) {
        Senha senha = new Senha(senhaTexto);
        ChavePrincipalK chaveK = new ChavePrincipalK(senha);
        K128 k128 = new K128(chaveK, ITERACOES_N);
        BigInteger bintChaveK = new BigInteger(1, chaveK.getValor());
        System.out.println("ChaveK = " + bintChaveK.toString(16));
        
        byte pad = 0;
        int quantidade = (y.length / 16);
        byte[] x = new byte[y.length];
        int i;
        byte[] xor = CBC_PAD;
        for (i = 0; i < quantidade; i++) {
            byte[] bloco = Arrays.copyOfRange(y, (16 * i), (16 * i) + 16);
            byte[] decriptografado = k128.decriptografa(bloco); 
            decriptografado = ByteUtil.fixedSizeXOR(decriptografado, xor, 16);
            
            xor = bloco;
            System.arraycopy(decriptografado, 0, x, 16 * i, decriptografado.length);
            
            if (i == (quantidade - 1)) {
                pad = decriptografado[decriptografado.length - 1];
            }
        }
        
        // Remove bytes de padding e bloco extra de controle;
        return Arrays.copyOfRange(x, 0, (x.length - 16) - pad);
    }
    
    public static void calcularAleatoriedadeMetodo1(byte[] bytes, String senhaTexto) {
        calcularAleatoriedade(bytes, senhaTexto, false);
    }
    
    public static void calcularAleatoriedadeMetodo2(byte[] bytes, String senhaTexto) {
        calcularAleatoriedade(bytes, senhaTexto, true);
    }
    
    private static void calcularAleatoriedade(byte[] bytes, String senhaTexto, boolean isModo2) {
        if (bytes.length < (512 / 8)) {
            throw new K128Exception("Entrada não possui 512 bits");
        }
        
        bytes = normaliza(bytes);
        int quantidadeDeBlocos512 = (bytes.length / (512 / 8));
        if (bytes.length % (512 / 8) > 0) {
            quantidadeDeBlocos512++;
        }
        
        for (int k = 1; k <= quantidadeDeBlocos512; k++) {
            byte[] vetEntra = Arrays.copyOfRange(bytes, ((k - 1) * 64), ((64 * k) <= bytes.length) ? (k * 64) : (k * 64) + (bytes.length % 64));
            byte[] vetEntraC = criptografar(vetEntra, senhaTexto);
            
            byte[][] vetsAlterC = new byte[vetEntra.length * 8][];
            
            byte[] vetAlter = Arrays.copyOfRange(vetEntra, 0, vetEntra.length);
            long[] somaH = new long[vetEntra.length / 16];
            int[] max = new int[vetEntra.length / 16];
            int[] min = new int[vetEntra.length / 16];
            int[][] media = new int[vetEntra.length / 16][vetEntra.length * 8];
            for (int j = 0; j < (vetEntra.length * 8); j++) {
                vetAlter[(j / 8)] = Hamming.alteraBit(vetAlter[(j / 8)], (j % 8) == 0 ? 1 : j % 8);
                
                if (isModo2 && ((j / 8) < (vetEntra.length - 1))) {
                    vetAlter[(j / 8) + 1] = Hamming.alteraBit(vetAlter[(j / 8) + 1], (j % 8) == 0 ? 1 : j % 8);
                }
                
                vetsAlterC[j] = criptografar(vetAlter, senhaTexto);
                
                int quantidadeDeBlocos128 = vetEntra.length / 16;
                System.out.println("Para j = " + (j + 1));

                for (int i = 0; i < quantidadeDeBlocos128; i++) {
                    byte[] blocoVetEntraC = Arrays.copyOfRange(vetEntraC, i * 16, 16 + (i * 16));
                    byte[] blocoVetAlterC = Arrays.copyOfRange(vetsAlterC[j], i * 16, 16 + (i * 16));
                    
                    int distancia = Hamming.distancia(blocoVetEntraC, blocoVetAlterC);
                    somaH[i] += distancia;
                    if (i > 0 && (i % 4 > 0)) {
                        somaH[i] += somaH[i - 1];
                    }
                    
                    System.out.println(MessageFormat.format("H({0}) = {1}", i + 1, distancia));
                    
                    // min
                    if (distancia < min[i] ) {
                        min[i] = distancia;
                    }
                    
                    // max
                    if (distancia > max[i]) {
                        max[i] = distancia;
                    }
                    
                    // media
                    media[i][j] = distancia;
                }
            }
            
            for (int c = 1; c <= min.length; c++) {
                System.out.println(MessageFormat.format("SomaH({0}) = {1}", c, somaH[c - 1]));
                System.out.println(MessageFormat.format("Min({0}) = {1}", c, min[c - 1]));
                System.out.println(MessageFormat.format("Max({0}) = {1}", c, max[c - 1]));
                
                int total = 0;
                for (int valor : media[c - 1]) {
                    total += valor;
                }
                
                System.out.println(MessageFormat.format("Media({0}) = {1}", c, (double) total / media[c - 1].length));
            }
        }
    }
    
    private static byte[] normaliza(byte[] original) {
        int quantidade = (original.length / 16);
        int resto = original.length % 16;
        if (resto > 0) {
            quantidade++;
        }
        
        byte[] normalizado = new byte[16 * quantidade];
        System.arraycopy(original, 0, normalizado, 0, original.length);
        
        // Realiza pad caso dados menor que 16 bytes
        if (original.length < 16) {
            System.arraycopy(CBC_PAD, 0, normalizado, original.length, 16 - original.length);
        }
        
        // Realiza pad caso ultimo bloco seja menor que 16 bytes
        if (resto > 0) {
            System.arraycopy(CBC_PAD, 0, normalizado, original.length, (16 - resto));
        }
        
        return normalizado;
    }
    
}
