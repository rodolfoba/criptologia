package com.github.rodolfoba.criptologia.k128.util;

import java.math.BigInteger;

public class ByteUtil {

    public static byte[] fixedSizeByteArray(BigInteger entrada, int size) {
        byte[] srcBytes = entrada.toByteArray();
        byte[] destBytes = new byte[size];
        
        int srcPos, dstPos, length;
        if (srcBytes.length > size) {
            srcPos = 1;
            dstPos = 0;
            length = size;
        } else {
            srcPos = 0;
            dstPos = size - srcBytes.length;
            length = srcBytes.length;
        }
        
        System.arraycopy(srcBytes, srcPos, destBytes, dstPos, length);
        return destBytes;
    }
    
    
    public static byte leftRotateByte(byte valor, int rotacoes, int bits) {
        return (byte) (((valor << rotacoes) | (valor >> (bits - rotacoes))) & (1 << bits) - 1);
    }
    
    public static int leftRotateInt(int valor, int rotacoes) {
        return ((valor << rotacoes) | (valor >>> (Integer.SIZE - rotacoes)));
    }
    
    public static String parseHexString(byte[] bytes) {
        StringBuilder string = new StringBuilder(bytes.length);
        for (byte b : bytes) {
            string.append(String.format("%02x", b));
        }
        return string.toString();
    }
    
    public static byte[] fixedSizeXOR(byte[] bytes1, byte[] bytes2, int size) {
        return fixedSizeByteArray(new BigInteger(1, bytes1).xor(new BigInteger(1, bytes2)), size);
    }
    
    public static byte[] xor(byte[] bytes1, byte[] bytes2) {
        return new BigInteger(1, bytes1).xor(new BigInteger(1, bytes2)).toByteArray();
    }

    public static byte[] concat(byte[]... arrays) {
        int tamanho = 0;
        for (byte[] bytes : arrays) {
            tamanho += bytes.length;
        }
        
        byte[] concat = new byte[tamanho];
        int i = 0;
        for (byte[] bytes : arrays) {
            for (byte byteInfo : bytes) {
                concat[i++] = byteInfo;
            }
        }
        
        return concat;
    }
    
}
