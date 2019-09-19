package com.nekosighed.miaosha.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    /**
     * 避免工具类被创建
     */
    Md5Utils() {
    }

    /**
     * 将字符串进行md5加密
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String encodeByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
        return base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
    }
}
