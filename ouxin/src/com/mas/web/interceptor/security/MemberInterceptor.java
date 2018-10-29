package com.mas.web.interceptor.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.mas.security.util.verify.ApiUtil;
import com.mas.user.service.MemberService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.AuthDto;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-17  
 */
/** 
 * 过滤器把请求流保存起来 
 */  
public class MemberInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private MemberService memberService;
    
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  
            throws Exception {  
        String uri = request.getRequestURI();  
        if(uri.contains("sms") || uri.contains("pwd") || uri.contains("login") || uri.contains("token") || uri.contains("register") || uri.contains("countrys") 
                || uri.contains("version") || uri.contains("/wealth/recharge")|| uri.contains("/third")){  
            return super.preHandle(request, response, handler);  
        }else if(request instanceof MultipartHttpServletRequest) {
            String data = request.getParameter("data");
            if(!isLogin(data)){
                outPutNoLogin(response);
                return false;
            }
        }else{  
            String data = getBodyString(request);  
            if(!isLogin(data)){
                outPutNoLogin(response);
                return false;
            }
        }   
        return super.preHandle(request, response, handler);  
    }  
  
    private void outPutNoLogin(ServletResponse response){
        try {
            JSONObject jsonObject = new JSONObject(); 
            jsonObject.put(JsonCtr.KEY_STATUS, JsonCtr.LOGIN);
            jsonObject.put(JsonCtr.KEY_MESSAGE, "no auth");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            out.write(jsonObject.toString());
            out.flush();
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
    
    private boolean isLogin(String data){
        try {
            if(StringUtils.isEmpty(data))
                return false;
            AuthDto jsonObject = JSONObject.parseObject(data,AuthDto.class);
            String mid = jsonObject.getMid();
            //用户请求数据不会更新D
            long beginTimeMillis = System.currentTimeMillis();
            String mchKey = memberService.getToken(mid, new Date(), 7);
            long buildTime = System.currentTimeMillis() - beginTimeMillis;
            return ApiUtil.check(mchKey, data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** 
     * 获取请求Body 
     * 
     * @param request 
     * @return 
     */  
    public static String getBodyString(final ServletRequest request) {  
        StringBuilder sb = new StringBuilder();  
        InputStream inputStream = null;  
        BufferedReader reader = null;  
        try {  
            inputStream = cloneInputStream(request.getInputStream());  
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));  
            String line = "";  
            while ((line = reader.readLine()) != null) {  
                sb.append(line);  
            }  
        }  
        catch (IOException e) {  
            e.printStackTrace();  
        }  
        finally {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                }  
                catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (reader != null) {  
                try {  
                    reader.close();  
                }  
                catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return sb.toString();  
    }  
  
    /** 
     * Description: 复制输入流</br> 
     *  
     * @param inputStream 
     * @return</br> 
     */  
    public static InputStream cloneInputStream(ServletInputStream inputStream) {  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len;  
        try {  
            while ((len = inputStream.read(buffer)) > -1) {  
                byteArrayOutputStream.write(buffer, 0, len);  
            }  
            byteArrayOutputStream.flush();  
        }  
        catch (IOException e) {  
            e.printStackTrace();  
        }  
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());  
        return byteArrayInputStream;  
    }
    
}