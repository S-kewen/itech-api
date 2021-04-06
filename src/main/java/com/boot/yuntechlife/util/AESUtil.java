package com.boot.yuntechlife.util;

public interface AESUtil {
    String encrypt(String content, String encryptKey) throws Exception;//加密

    String decrypt(String encryptStr, String decryptKey) throws Exception;//解密

    String StrToHex(String s);

    String HexToStr(String s);
}
