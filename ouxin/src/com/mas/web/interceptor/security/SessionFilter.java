/**    
 * 文件名：SessionFilter.java    
 *    
 * 版本信息：    
 * 日期：2017-11-17    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
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

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletInputStream;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletRequestWrapper;  

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;  
  
/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-17  
 */
/** 
 * 过滤器把请求流保存起来 
 */  
public class SessionFilter implements Filter{  
    
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        // 防止流读取一次后就没有了, 所以需要将流继续写出去  
        if(isMultipartContent(httpServletRequest)){
            chain.doFilter(httpServletRequest, response); 
        }else{
            if(((HttpServletRequest) request).getRequestURI().contains("/upload/msg/")){
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                out.write("Permission denied!");
                out.flush();
                out.close();
            }else{
                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);  
                chain.doFilter(requestWrapper, response); 
            }
        }
    }  
  
    @Override  
    public void destroy() {  
          
    }  
    
    private final boolean isMultipartContent(HttpServletRequest request) {
        return FileUploadBase.isMultipartContent(new ServletRequestContext(request));
    }
  
    /** 
     * 保存流 
     *  
     * @author lyj 2015年12月16日 
     */  
    public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {  
        private Logger log = Logger.getLogger(this.getClass());  
  
        private final byte[] body;  
  
        public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {  
            super(request);  
            String sessionStream = getBodyString(request);  
            body = sessionStream.getBytes(Charset.forName("UTF-8"));  
            log.debug("保存的流" + sessionStream);  
        }  
  
        /** 
         * 获取请求Body 
         * 
         * @param request 
         * @return 
         */  
        public String getBodyString(final ServletRequest request) {  
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
        public InputStream cloneInputStream(ServletInputStream inputStream) {  
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
        @Override  
        public BufferedReader getReader() throws IOException {  
            return new BufferedReader(new InputStreamReader(getInputStream()));  
        }  
  
        @Override  
        public ServletInputStream getInputStream() throws IOException {  
  
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);  
  
            return new ServletInputStream() {  
  
                @Override  
                public int read() throws IOException {  
                    return bais.read();  
                }  

            };  
        }  
    }  
}  