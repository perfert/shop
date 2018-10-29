package com.mas.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.mas.core.service.ServiceException;

/**
 * author: yixuan created on: 2017/12/8 18:21 description:
 */
public class DesUtil {
    private static Cipher cipher = null;

    private DesUtil() {
    }

    public static byte[] encrypt(byte[] var0, byte[] var1) throws Exception {
        SecureRandom var2 = new SecureRandom();
        DESKeySpec var3 = new DESKeySpec(var1);
        SecretKeyFactory var4 = SecretKeyFactory.getInstance("DES");
        SecretKey var5 = var4.generateSecret(var3);
        cipher.init(1, var5, var2);
        return cipher.doFinal(var0);
    }

    public static byte[] decrypt(byte[] var0, byte[] var1) throws Exception {
        SecureRandom var2 = new SecureRandom();
        DESKeySpec var3 = new DESKeySpec(var1);
        SecretKeyFactory var4 = SecretKeyFactory.getInstance("DES");
        SecretKey var5 = var4.generateSecret(var3);
        cipher.init(2, var5, var2);
        return cipher.doFinal(var0);
    }

    public static String decrypt(String var0) {
        try {
            return new String(decrypt(hex2byte(var0.getBytes()), "yilucaifu".getBytes()));
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * 
     * @param pwd
     * @param key
     * @return
     */
    public static String decrypt(String pwd, String key) {
        try {
            return new String(decrypt(hex2byte(pwd.getBytes("UTF-8")), key.getBytes("UTF-8")));
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String var0) {
        try {
            return byte2hex(encrypt(var0.getBytes(), "yilucaifu".getBytes()));
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String var0, String var1) {
        try {
            return byte2hex(encrypt(var0.getBytes("UTF-8"), var1.getBytes("UTF-8")));
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String byte2hex(byte[] var0) {
        String var1 = "";
        String var2 = "";

        for (int var3 = 0; var3 < var0.length; ++var3) {
            var2 = Integer.toHexString(var0[var3] & 255);
            if (var2.length() == 1) {
                var1 = var1 + "0" + var2;
            } else {
                var1 = var1 + var2;
            }
        }

        return var1.toUpperCase();
    }

    public static byte[] hex2byte(byte[] var0) {
        if (var0.length % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        } else {
            byte[] var1 = new byte[var0.length / 2];

            for (int var2 = 0; var2 < var0.length; var2 += 2) {
                String var3 = new String(var0, var2, 2);
                var1[var2 / 2] = (byte) Integer.parseInt(var3, 16);
            }

            return var1;
        }
    }

    public static void main(String[] var0) throws Exception {
        String var1 = "123qwe";
        String var2 = "thisistestkey123";
        var1 = encrypt(var1, var2);
        System.err.println(var1);
        var1 = decrypt(var1, var2);
        System.out.println(var1);

        List<String> list = new ArrayList<String>();
        String a = new String("1");
        String b = new String("2");
        String c = new String("3");
        list.add(a);
        list.add(b);
        list.add(c);

        list.remove(b);
        System.out.println(list.get(0) + " =======" + list.get(1));

        String s = "http://www.olefinassets.com/upload/address/qrcode/76151-55139916.jpg";
        s = s.substring(s.indexOf(":"));
        System.out.println(s);

        String num = "0E-8";
        double r = java.lang.Float.parseFloat(num);
        System.out.println(r + "-------");

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        System.out.println(decimalFormat.format(20.666d));

        int[] fragmentIndex = new int[] { 0, 0, 0, 0 };
        fragmentIndex[2] = 1;
        System.out.println(fragmentIndex[2] + "====");

       /* URL url = new URL("http://www.olefinassets.com/upload/address/qrcode/76151-55139916.jpg");
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置网络连接超时时间
        httpURLConnection.setConnectTimeout(3000);
        // 设置应用程序要从网络连接读取数据
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            // 从服务器返回一个输入流
            inputStream = httpURLConnection.getInputStream();
        }

        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("D:\\test1.jpg");
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        
        List<String> days = new ArrayList<String>();
        days.add("01");
        days.add("02");
        double value = 1.8;
        int i = (int) value % days.size();
        System.out.println("====days====" + i);
        
        String s2 = "1";
        //2位小数
        /*int endIndex = 2;
        if (s2.toString().contains(".")) {
            if (s2.length() - 1 - s2.toString().indexOf(".") > endIndex) {
                //小数点 + 后面几位小数
                s2 = (String) s2.toString().subSequence(0, s2.toString().indexOf(".") + 1 + endIndex);
            }
        }*/
        //首位.,自动填充0
        /*if (s2.toString().trim().substring(0).equals(".")) {
            s2 = "0" + s;
        }*/
        
      
        
        System.out.println(DesUtil.getNumFormat(1,"15.222"));
    }
    
    /**
     * 只保留小数点最后2位,不足2位补0
     * @param num
     * @return
     */
    public static String getNumFormat(int curIndex,String num){
        int index = num.indexOf(".");
        String num2 = num;
        String result = "";
        if (index > 0) {
            num2 = num2.substring(index + 1, num.length());
            if (num2.length() < curIndex) {
                while (num2.length() < curIndex) {
                    num2 = num2 + "0";
                }
            } else if (num2.length() > curIndex) {
                while (num2.length() > curIndex) {
                    num2 = num2.substring(0, num2.length() - 1);
                }
            }
            result = num.substring(0, index) + "." + num2;
        } else {
            result = num + ".00";
        }
        return result;
    }

    /**
     * 清除小数点
     * @param num
     * @return
     */
    public static String getPositiveInteger(String num){
        if(null != num && num.length() > 0){
            int index = num.indexOf(".");
            if (index > 0) {
                return num.substring(0,index);
            }
            return num;
        }else
            return num;
    }
    
    static {
        try {
            cipher = Cipher.getInstance("DES");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
