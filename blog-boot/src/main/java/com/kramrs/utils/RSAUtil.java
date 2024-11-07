package com.kramrs.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kramrs
 * @Description: RSA加密
 */
@Slf4j
public class RSAUtil {

    //公钥，可以写前端
    public static String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQIrDkz7TpAolPqCmTIGnxgAdBIEXnmpbBA8JlvimpgXHf1AoJzICVvidx9HGTOJAdYFxF4Ip0JA+9vjflS/Lknk7+lxjdawIjIlrvSO13f3PC78OBP58zWuNhvI+zjtLUY0yXAySU2QZfRtuD4imF4l+oiOQwNFmN7/AUdKtF1wIDAQAB";

    //私钥，只能放后端
    public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJAisOTPtOkCiU+oKZMgafGAB0EgReealsEDwmW+KamBcd/UCgnMgJW+J3H0cZM4kB1gXEXginQkD72+N+VL8uSeTv6XGN1rAiMiWu9I7Xd/c8Lvw4E/nzNa42G8j7OO0tRjTJcDJJTZBl9G24PiKYXiX6iI5DA0WY3v8BR0q0XXAgMBAAECgYACBorH3GdlcuStGubiq2zxuOPhNWvmElUWB1B/D4oUWL5Z7+foNViFQjPZtR63e+8Ciq0hSkOH2E3kU70DWurh6DSMm0o3R+DAUowdyzB1mx3b0Pjeujd8UYZl3kpquILQi4uXjDPuH1NyJDx5ejhBCwBKdA8gVRwYMKbXi3aeMQJBAMdIIRn2wLKZxX/Bf4Css1BE9ofHOaSogpCeOM/MeAdc4tIsC+NbGU9PabM9RydVjIjUXK2yvpbYUn5k0p98RSkCQQC5KIzs7AeRwzPbvKSCaj021iX0uENpbYIXEMtW3RRbZPj4xINl+lAa7d17itwVy4/utxZkENBVZRM1xiE6CZL/AkEAkv1tDQ2ozRQHTViYh3zbNpXnkfctWXrB85IBLaUP0757v7Klgimr3Ygh2weT+Dtt0W9yfNyfH/ynvHr5sJGlIQJAJkbHu0Kf3fYohkhdCcjEls12Uzy0UtschYAnSPGy/6HEBkk5kJ9KaAa8PonaLXdDbenTayQ+rSiT7A3lWIYaKQJAI0/gGYe2vfZmI20aW1PWiwqfRlaUWKyapslXgE2cUGtIHS5ueKx3uOgvHxZJfwblxNXv0ttqY55pV74IUHzdZw==";


    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = decryptBASE64(publicKey);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return encryptBASE64(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = decryptBASE64(str);
        //base64编码的私钥
        byte[] decoded = decryptBASE64(privateKey);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    /**
     * 用于封装随机产生的公钥与私钥
     */
    public static final Map<Integer, String> keyMap = new HashMap<>();

    /**
     * 随机生成密钥对
     */
    public static void genKeyPair() throws Exception {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = encryptBASE64(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = encryptBASE64(privateKey.getEncoded());
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put(0, publicKeyString);
        //1表示私钥
        keyMap.put(1, privateKeyString);
    }
}
