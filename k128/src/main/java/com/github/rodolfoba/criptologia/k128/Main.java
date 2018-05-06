package com.github.rodolfoba.criptologia.k128;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import com.github.rodolfoba.criptologia.k128.algoritmo.K128;

public class Main {

    public static void main(String[] args) throws Exception {
        String senha = "ABCD1234";
        
//        byte[] x = new byte[] { 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11 };
//        byte[] y = K128.criptografar(x, senha);
//        byte[] z = K128.decriptografar(y, senha);
//        System.out.println("X=" + ByteUtil.parseHexString(x));
//        System.out.println("Y=" + ByteUtil.parseHexString(y));
//        System.out.println("Z=" + ByteUtil.parseHexString(z));

        String pathArquivo = "arquivos";
        String pathSaida = "saida";
        
//        String nomeEntrada = "entrada64";
//        String nomeEntrada = "entrada128";
//        String nomeEntrada = "entrada129";
//        String nomeEntrada = "entrada256";
        String nomeEntrada = "imagem.png";

        if (!Files.exists(Paths.get(pathArquivo, pathSaida))) {
            Files.createDirectory(Paths.get(pathArquivo, pathSaida));
        }
        
        byte[] entrada = Files.readAllBytes(Paths.get(pathArquivo, nomeEntrada));
        
        Instant inicio = Instant.now();
        byte[] y = K128.criptografar(entrada, senha);
        byte[] z = K128.decriptografar(y, senha);
        Instant fim = Instant.now();
        System.out.println("Tempo (ms): " + Duration.between(inicio, fim).toMillis());
        
        Files.write(Paths.get(pathArquivo, pathSaida, nomeEntrada + ".enc"), y);
        Files.write(Paths.get(pathArquivo, pathSaida, nomeEntrada + ".dec"), z);
        
        
        // Extraindo os 8 bits de um byte
//        byte b = (byte) 0b10101001;
//        byte b1 = (byte) (b >> 4 & 0b00001111); //160
//        byte b2 = (byte) (b & 0x0F); //9
//        
//        System.out.println(b1 & 0xFF);
//        System.out.printf("%02x\n", b1);
//        String strbin = String.format("%8s", Integer.toBinaryString(b1 & 0xFF));
//        System.out.println(strbin.replace(' ', '0'));
//        
//        System.out.println();
//        System.out.println(b2 & 0xFF);
//        System.out.printf("%02x\n", b2);
//        strbin = String.format("%8s", Integer.toBinaryString(b2 & 0xFF));
//        System.out.println(strbin.replace(' ', '0'));
        
        
        
        // Bitwise operators
//        byte b = (byte) 0b11110000; // 240
//        System.out.println(b & 0xFF);
//        System.out.printf("%02x\n", b);
//        String strbin = String.format("%8s", Integer.toBinaryString(b & 0xFF));
//        System.out.println(strbin.replace(' ', '0'));
        
        

//      int i = 0xFFFFFFFF;
//      long l = 0xFFFFFFFFl;
//      System.out.println(i);
//      System.out.println(l);
        
//        byte CONST_R = 0b10011;
//        int rounds = (((int) Math.pow((0 + 2), 2)) % 3);
//        byte b = ByteUtil.leftRotateByte(CONST_R, rounds, 5);
//        System.out.println(CONST_R);
//        System.out.println(b);
//        System.out.println(rounds);
        
//        long i1 = 0b1111111111111111111111111111111 + 1l;
//        System.out.println(i1);
//        System.out.println();
//        System.out.println("MAX=" + Integer.MAX_VALUE);
        
//        byte[] bytes = new byte[] {
//                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//        };
//        
//        BigInteger constanteK0 = new BigInteger("5A827999874AA67D657B7C8EBD070242", 16);
//        
//        BigInteger bint = new BigInteger(1, bytes);
//        System.out.println(bint.toString(16));
//        System.out.println(bint.toByteArray().length);
//        
//        
//        byte[] novo = ByteUtil.fixedSizeByteArray(bint, 16);
//        System.out.println(new BigInteger(1, novo).toString(16));
//        System.out.println(novo.length);
//        
//        byte[] bufferbytes = ByteBuffer.wrap(novo).array();
//        System.out.println(ByteUtil.parseHexString(bufferbytes));
//        
//        BigInteger xor = bint.xor(constanteK0);
//        System.out.println(xor.toString(16));
//        
//        byte[] xorBytes = ByteUtil.fixedSizeByteArray(xor, 16);
//        byte[] a1 = Arrays.copyOfRange(xorBytes, 0, 4);
//        byte[] a2 = Arrays.copyOfRange(xorBytes, 4, 8);
//        byte[] a3 = Arrays.copyOfRange(xorBytes, 8, 12);
//        byte[] a4 = Arrays.copyOfRange(xorBytes, 12, 16);
//
//        System.out.println(new BigInteger(1, a1).toString(16));
//        System.out.println(new BigInteger(1, a2).toString(16));
//        System.out.println(new BigInteger(1, a3).toString(16));
//        System.out.println(new BigInteger(1, a4).toString(16));
        
        
//        long l1 = 0xFFFFFFFFl;
//        long l2 = 0x10000000l;
//        long l3 = 0x10000000l;
//        long l4 = 0x10000000l;
//        
//        long[] l = new long[] { l1, l2, l3, l4 };
//        
//        String hex1 = "11111111111111111111111111111111";
//        String hex2 = "11111111111111111111111111111111";
//        hex1 = "11111111111111111111111111111111";
//        hex2 = "10000000100000001000000010000000";
//        BigInteger bint1 = new BigInteger(hex1, 16);
//        BigInteger bint2 = new BigInteger(hex2, 16);
//        System.out.println(bint1.toString(16));
//        System.out.println(bint2.toString(16));
//        
//        BigInteger xor = bint1.xor(bint2);
//        System.out.println(xor.toString(16));
//        
//        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 4);
//        buffer.putInt((int) l1).putInt((int) l2).putInt((int) l3).putInt((int) l4);
//        System.out.println(new BigInteger(buffer.array()).toString(16));
//        
//        byte[] a1 = Arrays.copyOfRange(xor.toByteArray(), 0, 4);
//        byte[] a2 = Arrays.copyOfRange(xor.toByteArray(), 4, 8);
//        byte[] a3 = Arrays.copyOfRange(xor.toByteArray(), 8, 12);
//        byte[] a4 = Arrays.copyOfRange(xor.toByteArray(), 12, 16);
//
//        System.out.println(new BigInteger(a1).toString(16));
//        System.out.println(new BigInteger(a2).toString(16));
//        System.out.println(new BigInteger(a3).toString(16));
//        System.out.println(new BigInteger(a4).toString(16));
        
        
        
//        
//        for (byte b : bytes) {
//            System.out.println(new BigInteger(Byte.toString(b)).toString(16));
//        }
        
        /*
        int k32 = 0b1;
        byte[] x = new byte[] {0b0, 0b1};
        int xint = new BigInteger(x).intValue();
        int soma = k32 + xint;
        int xor = k32 ^ xint;
        System.out.println(soma);
        System.out.println(xor);
        
        // dividir k32 em 4 bytes
        byte[] k32bytes = ByteBuffer.allocate(Integer.BYTES).putInt(k32).array();
        System.out.println(k32bytes.length);
        System.out.println(ByteUtil.parseHexString(k32bytes));
        
        
//        BigInteger bint1 = new BigInteger(Integer.toString(Integer.MAX_VALUE));
//        BigInteger bint2 = new BigInteger(Integer.toString(1));
//        BigInteger bint3 = bint1.add(bint2).mod(new BigInteger(Integer.toString(Integer.MAX_VALUE)));
//        
//        System.out.println(bint3.toString());
//        
//        long int1 = Integer.MAX_VALUE;
//        long int2 = 1l;
//        long int3 = int1 + int2;
//        System.out.println(int3);

 */
    }

}
