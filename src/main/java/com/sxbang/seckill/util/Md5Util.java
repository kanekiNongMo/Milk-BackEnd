package com.sxbang.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author kaneki
 * @date 2019/6/22 15:01
 */
public class Md5Util {

    public static String salt = "kaneki";

    public static String md5(String str){
       return DigestUtils.md5Hex(str);
    }

    public static String inputToBack(String str){
       return md5(str + salt);
    }

    public static String backToDb(String str, String dbSalt){
        return md5(str + dbSalt);
    }

    public static String inputToDb(String str, String dbSalt){
        return backToDb(inputToBack(str), dbSalt);
    }
}
