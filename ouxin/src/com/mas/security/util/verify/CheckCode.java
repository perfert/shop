/**    
 * 文件名：CheckCode.java    
 *    
 * 版本信息：    
 * 日期：2017-11-17    
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.verify.VerifyUtil;
import com.mas.security.util.PasswordUtil;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-11-17
 */
public final class CheckCode {
    
    public static void main(String[] args) {
        JSONObject bean = new JSONObject();
        bean.put("groupId", "33");
        
        String mid = "1";
        String mch_key = "token";
        bean.put("mid", mid);
        ApiUtil.encode(mch_key, bean);
        String value = bean.toJSONString();
        System.out.println(value);
        
        JSONObject jsonObject = JSONObject.parseObject(value);
        if(!ApiUtil.check(mch_key, jsonObject.toJSONString())){
            System.out.println("error");
        }
    }
    
    private CheckCode() {
    }
    
    public static String encode(String mch_key, Map<String, Object> map) {
        map.put("nonce_str", generateNoncestr(32));

        String check = stitching(map);
        check += "&key=" + mch_key;
        try {
            String sign = PasswordUtil.encryptByMd5(check).toUpperCase();
            map.put("sign", sign);
            return check;
        } catch (Exception ex) {
            map.put("sign", "");
            return null;
        }
    }
    
    public static <L extends ApiEntity> void encode( String mch_key, L bean )
    {
        Map<String, Object> map;
        encode( mch_key, map = JSONObject.parseObject( JSONObject.toJSONString( bean ) ) );
        bean.setNonce_str( map.get( "nonce_str" ).toString() );
        bean.setSign( map.get( "sign" ).toString() );
    }

    public static String generateNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    private static String stitching(Map<String, Object> map) {
        List<String> list = new ArrayList<String>(map.keySet());
        Collections.sort(list);
        String check = "";
        Object val = null;
        for (String key : list) {
            if (!"key".equals(key) && !"sign".equals(key)) {
                if (VerifyUtil.isNotBlank(check))
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
        if (VerifyUtil.isNotEmpty(collection)) {
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

   
}