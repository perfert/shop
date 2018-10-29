/**    
 * 文件名：MD5.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.security.util.verify;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 项目名称：ouxin 类名称： 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class MD5 {
    /**
     * 密码字段加密方法，
     * 
     * @param userpwd
     *            原始输入
     * @return 应该提交到接口的密码参数
     */
    public static String pwdEncript(String userpwd) {
        return MD5.md5("#345!2%fwej%od" + userpwd);
    }

    /**
     * 将字符串进行MD5加密，返回加密后的字符串（实际上是该字符串的报文摘要）
     * 
     * @param string
     *            utf8编码格式
     * @return
     */
    public static String md5(String string) {

        byte[] hash;

        try {

            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("Huh, MD5 should be supported?", e);

        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("Huh, UTF-8 should be supported?", e);

        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {

            if ((b & 0xFF) < 0x10)
                hex.append("0");

            hex.append(Integer.toHexString(b & 0xFF));

        }

        return hex.toString();

    }

    public static void main(String[] args) {
        System.out.println(MD5.md5("123456"));
    }
}
