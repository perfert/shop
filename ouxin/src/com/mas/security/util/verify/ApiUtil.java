/**    
 * 文件名：ApiUtil.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.security.util.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 
 * 
 * mch_key存哪
 * 在哪验证
 * 
 * 项目名称：ouxin 类名称： 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class ApiUtil {
    
    
    /**
     * 编码数据。
     * 
     * @param mch_key
     *            密钥
     * @param bean
     *            编码对象
     */
    public static <L extends ApiEntity> void encode(String mch_key, L bean) {
        Map<String, Object> map;
        encode(mch_key, map = JSONObject.parseObject(JSONObject.toJSONString(bean)));
        bean.setNonce_str(map.get("nonce_str").toString());
        bean.setSign(map.get("sign").toString());
    }
    
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        String mid = jsonObject.getString("mid");
        
        String s = ApiUtil.encode("123",jsonObject);
        System.out.println(jsonObject.toJSONString());
        System.out.println(s);
    }

    /**
     * 编码数据。
     * 
     * @param mch_key
     *            密钥
     * @param map
     *            编码对象
     */
    public static String encode(String mch_key, Map<String, Object> map) {
        map.put("nonce_str", generateNoncestr(32));
        String check = stitching(map);
        check += "&key=" + mch_key;
        try {
            map.put("sign", MD5.md5(check).toUpperCase());
            return check;
        } catch (Exception ex) {
            map.put("sign", "");
            return null;
        }
    }
    
    /**
     * 校验数据。
     * 
     * @param mch_key
     *            密钥
     * @param checkJsonString
     *            校验的字符串
     * 
     * @return true or false.
     */
    public static boolean check(String mch_key, String checkJsonString) {
        return check(mch_key, JSONObject.parseObject(checkJsonString));
    }

    /**
     * 校验数据。
     * 
     * @param mch_key
     *            密钥
     * @param checkJsonObject
     *            校验的JSON对象
     * 
     * @return true or false.
     */
    public static boolean check(String mch_key, JSONObject checkJsonObject) {
        String sign = (String) checkJsonObject.get("sign");
        if (StringUtils.isBlank(sign))
            return false;
        String check = stitching(checkJsonObject);
        check += "&key=" + mch_key;
        try {
            return sign.equals(MD5.md5(check).toUpperCase());
        } catch (Exception ex) {
            return false;
        }
    }

    @SuppressWarnings({ "unchecked" })
    private static String stitching(Map<String, Object> map) {
        // stitching 数据。
        List<String> list = new ArrayList<String>(map.keySet());
        // 按照参数名ASCII字典序排序
        Collections.sort(list);
        String check = "";
        Object val = null;
        for (String key : list) {
            if (!"key".equals(key) && !"sign".equals(key)) {
                if (StringUtils.isNotBlank(check))
                    check += "&";
                val = map.get(key);
                if (null != val) {
                    if (val instanceof Map) {
                        check += key + "={";
                        check += stitching((Map<String, Object>) val);
                        check += "}";
                    } else if (val instanceof Collection) {
                        check += key + "=[";
                        check += stitching((Collection<?>) val);
                        check += "]";
                    } else {
                        check += key + "=" + val;
                    }
                }
            }
        }
        return check;
    }

    private static String stitching(Collection<?> collection) {
        String check = "";
        if (null != collection && collection.size() > 0) {
            boolean hasVal = false;
            boolean handle = false;
            for (Object obj : collection) {
                if (null != obj) {
                    handle = false;
                    try {
                        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(obj));
                        check += stitching(json);
                        handle = true;
                    } catch (Exception ex) {
                        try {
                            JSONArray array = JSONObject.parseArray(JSONObject.toJSONString(obj));
                            check += stitching(array);
                            handle = true;
                        } catch (Exception e) {
                        }
                    }
                    if (!handle) {
                        if (hasVal)
                            check += "&";
                        check += JSONObject.toJSONString(obj);
                        hasVal = true;
                    }
                }
            }
        }
        return check;
    }

    /**
     * 生成随机字符串
     * 
     * @param length
     *            字符串长度
     * 
     * @return lenth 长度字符串
     */
    public static String generateNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

}
