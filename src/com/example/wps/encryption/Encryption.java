/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.wps.encryption;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.Key;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Arnaud Rosette <arnaudrosette@gmail.com>
 */
public class Encryption {

    private static String algorithm = "AES";


    public static byte[] xor(byte[] array1, byte[] array2) throws Exception {
        if(array1.length != array2.length) {
            throw new Exception("Arrays not with the same length");
        }
        byte[] res = new byte[array1.length];
        for(int i=0; i<array1.length; ++i) {
            res[i] = (byte) (((int)array1[i])^((int)array2[i]));
        }
        return res;
    }
    
    public static byte[] sha1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return sha1hash;
    }

    public static byte[] encrypt(String input, String key) throws InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        SecretKeySpec skeySpec = new SecretKeySpec(sha1(key), algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] inputBytes = input.getBytes();
        return cipher.doFinal(inputBytes);
    }
    
    public static byte[] encrypt(String input, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] inputBytes = input.getBytes();
        return cipher.doFinal(inputBytes);
    }

    public static String decrypt(byte[] encryptionBytes, String key)
            throws InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        SecretKeySpec skeySpec = new SecretKeySpec(sha1(key), algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
        String recovered = new String(recoveredBytes);
        return recovered;
    }
    
    public static String decrypt(byte[] encryptionBytes, byte[] key)
            throws InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
        String recovered = new String(recoveredBytes);
        return recovered;
    }
}
