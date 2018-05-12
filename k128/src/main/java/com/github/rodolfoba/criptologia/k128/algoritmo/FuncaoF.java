package com.github.rodolfoba.criptologia.k128.algoritmo;

import java.math.BigInteger;

import com.github.rodolfoba.criptologia.k128.util.ByteUtil;

class FuncaoF {

    private static final BigInteger MOD_32 = new BigInteger("FFFFFFFF", 16);
    
    static byte[] f1(byte[] x, byte k5, int k32) {
        BigInteger[] sboxes = obterSBoxes(x, k5, k32);
        BigInteger bintY = sboxes[0].add(sboxes[1]).mod(MOD_32).subtract(sboxes[2]).mod(MOD_32).xor(sboxes[3]);
        return ByteUtil.fixedSizeByteArray(bintY, Integer.BYTES);
    }
    
    static byte[] f2(byte[] x, byte k5, int k32) {
        BigInteger[] sboxes = obterSBoxes(x, k5, k32);
        BigInteger bintY = sboxes[0].subtract(sboxes[1]).mod(MOD_32).xor(sboxes[2]).add(sboxes[3]).mod(MOD_32);
        return ByteUtil.fixedSizeByteArray(bintY, Integer.BYTES);
    }
    
    static byte[] f3(byte[] x, byte k5, int k32) {
        BigInteger[] sboxes = obterSBoxes(x, k5, k32);
        BigInteger bintY = sboxes[0].xor(sboxes[1]).add(sboxes[2].mod(MOD_32).subtract(sboxes[3]).mod(MOD_32));
        return ByteUtil.fixedSizeByteArray(bintY, Integer.BYTES);
    }
    
    private static BigInteger[] obterSBoxes(byte[] x, byte k5, int k32) {
        BigInteger bintX = new BigInteger(1, x);
        BigInteger bintK32 = new BigInteger(Integer.toHexString(k32), 16);
        BigInteger bintXOR = bintK32.xor(bintX);
        
        int intXOR = bintXOR.intValue();
        int intI = ByteUtil.leftRotateInt(intXOR, k5);
        BigInteger bintI = new BigInteger(Integer.toHexString(intI), 16);
        
        byte[] bytesI = ByteUtil.fixedSizeByteArray(bintI, Integer.BYTES);
        int s1 = SBox.S1[new BigInteger(1, new byte[] {bytesI[0]}).intValueExact()];
        int s2 = SBox.S2[new BigInteger(1, new byte[] {bytesI[1]}).intValueExact()];
        int s3 = SBox.S3[new BigInteger(1, new byte[] {bytesI[2]}).intValueExact()];
        int s4 = SBox.S4[new BigInteger(1, new byte[] {bytesI[3]}).intValueExact()];
        
        BigInteger bintS1 = new BigInteger(Integer.toHexString(s1), 16);
        BigInteger bintS2 = new BigInteger(Integer.toHexString(s2), 16);
        BigInteger bintS3 = new BigInteger(Integer.toHexString(s3), 16);
        BigInteger bintS4 = new BigInteger(Integer.toHexString(s4), 16);
        return new BigInteger[] { bintS1, bintS2, bintS3, bintS4 };
    }
}
