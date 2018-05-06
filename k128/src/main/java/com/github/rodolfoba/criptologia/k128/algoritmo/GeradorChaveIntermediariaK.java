package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.util.Arrays;

import com.github.rodolfoba.criptologia.k128.util.ByteUtil;

class GeradorChaveIntermediariaK {

    private static final byte CONST_R = 0b10011;
    private static final int CONST_M = 0xCB3725F7;
    
    static byte[] gerar(byte[] entrada, byte iteracao) {
        byte[] x, y, z, w;
        x = Arrays.copyOfRange(entrada, 0, 4);
        y = Arrays.copyOfRange(entrada, 4, 8);
        z = Arrays.copyOfRange(entrada, 8, 12);
        w = Arrays.copyOfRange(entrada, 12, 16);
        
//        System.out.println("X=" + new BigInteger(1, x).toString(16));
//        System.out.println("Y=" + new BigInteger(1, y).toString(16));
//        System.out.println("Z=" + new BigInteger(1, z).toString(16));
//        System.out.println("W=" + new BigInteger(1, w).toString(16));
        
        byte k5; 
        int k32;
        
        k5 = ByteUtil.leftRotateByte(CONST_R, (((int) Math.pow((iteracao + 2), 2)) % 3), 5);
        k32 = ByteUtil.leftRotateInt(CONST_M, (((int) Math.pow((iteracao + 3), 2)) % 7));
        w = ByteUtil.xor(w, FuncaoF.f2(x, k5, k32));
        
        k5 = ByteUtil.leftRotateByte(CONST_R, ((iteracao + 2) % 3), 5);
        k32 = ByteUtil.leftRotateInt(CONST_M, ((iteracao + 3) % 7));
        z = ByteUtil.xor(z, FuncaoF.f1(w, k5, k32));
        
        k5 = ByteUtil.leftRotateByte(CONST_R, (((int) Math.pow((iteracao + 2), 3)) % 3), 5);
        k32 = ByteUtil.leftRotateInt(CONST_M, (((int) Math.pow((iteracao + 3), 3)) % 7));
        y = ByteUtil.xor(y, FuncaoF.f3(z, k5, k32));
        
        k5 = ByteUtil.leftRotateByte(CONST_R, (((int) Math.pow((iteracao + 2), 2)) % 3), 5);
        k32 = ByteUtil.leftRotateInt(CONST_M, (((int) Math.pow((iteracao + 3), 2)) % 7));
        x = ByteUtil.xor(x, FuncaoF.f2(y, k5, k32));
        
        return ByteUtil.concat(w, z, y, x);
    }
}
