package com.mas.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.mas.core.service.ServiceException;
import com.mas.security.util.verify.ApiUtil;

/**
 * JSON 工具.
 * 
 * @version 1.0
 * 
 * @author MAS
 */
public final class JsonUtil 
{
	private static final ObjectMapper mapper = new ObjectMapper().configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	
	public static final String KEY_STATUS = "status";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_URL = "url";
	
	public static void main(String[] args) {
	    JSONObject jsonObject = new JSONObject();
        String token = "fe206d0cb198c3c270d4f0468c60162d";
        jsonObject.put("mid",1);
        jsonObject.put("mch_id","open.ouxichat.com");
        jsonObject.put("wealth_id",15563);//15563 15580
        ApiUtil.encode(token, jsonObject);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.olefincash.com/api/ouchat/address/genarate");
        //ofc http://www.olefincoin.com/api  occ http://www.olefincash.com
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        // 解决中文乱码问题
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response)throws ClientProtocolException, IOException {//
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        
        try {
            String responseBody = httpclient.execute(httpPost, responseHandler);
            JSONObject jObject = JSONObject.parseObject(responseBody);
            if(jObject.getString("return_code").equals("SUCCESS")){
                String address = jObject.getJSONObject("data").getString("address");
                System.out.println("==加载地址=======" + address);
            }
            throw new ServiceException("wallet.error.address");
        } catch (Exception e) {
            System.out.println("==加载地址失败=======" + e.getMessage());
            throw new ServiceException("wallet.error.address");
        } 
    }
	
	
	/**
	 * rend 数据.到 response.
	 * 
	 * @param response HttpServletResponse.
	 * @param contentType 数据.类型.
	 * @param content	数据..
	 * @param cache 是否开启缓存.
	 */
	public static void render(HttpServletResponse response, String contentType, String content, boolean cache) 
	{
		initResponse(response, contentType, cache);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * "text/plain" 类型.<br>
	 * 不开启缓存.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 */
	public static void renderText(HttpServletResponse response, String content) 
	{
		renderText(response, content, false);
	}
	
	/**
	 * "text/plain" 类型.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据..
	 * @param cache 是否开启缓存.
	 */
	public static void renderText(HttpServletResponse response, String content, boolean cache) 
	{
		render(response, ServletUtil.TYPE_TEXT, content, cache);
	}
	
	/**
	 * "text/html" 类型.<br>
	 * 不开启缓存.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 */
	public static void renderHtml(HttpServletResponse response, String content) 
	{
		renderHtml(response, content, false);
	}
	
	/**
	 * "text/html" 类型.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 * @param cache 是否开启缓存.
	 */
	public static void renderHtml(HttpServletResponse response, String content, boolean cache) 
	{
		render(response, ServletUtil.TYPE_HTML, content, cache);
	}
	
	/**
	 * "text/xml" 类型.<br>
	 * 不开启缓存.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 */
	public static void renderXml(HttpServletResponse response, String content) 
	{
		renderXml(response, content, false);
	}
	
	/**
	 * "text/xml" 类型.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 * @param cache 是否开启缓存.
	 */
	public static void renderXml(HttpServletResponse response, String content, boolean cache) 
	{
		render(response, ServletUtil.TYPE_XML, content, cache);
	}
	
	/**
	 * "text/javascript" 类型.<br>
	 * 不开启缓存.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 */
	public static void renderJs(HttpServletResponse response, String content) 
	{
		renderJs(response, content, false);
	}
	
	/**
	 * "text/javascript" 类型.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 * @param cache 是否开启缓存.
	 */
	public static void renderJs(HttpServletResponse response, String content, boolean cache) 
	{
		render(response, ServletUtil.TYPE_JS, content, cache);
	}
	
	/**
	 * "application/json" 类型.<br>
	 * 不开启缓存.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 */
	public static void renderJson(HttpServletResponse response, String content)
	{
		renderJson(response, content, false);
	}
	
	/**
	 * "application/json" 类型.
	 * 
	 * @param response HttpServletResponse.
	 * @param content 数据.
	 * @param cache 是否开启缓存.
	 */
	public static void renderJson(HttpServletResponse response, String content, boolean cache)
	{
		// IE10 不支付 TYPE_JSON 格式, 这里换成 TYPE_HTML 格式.
		render(response, ServletUtil.TYPE_HTML, content, cache);
	}
	
	/**
	 * "application/json" 类型.<br>
	 * 不开启缓存.
	 * 
	 * @param response HttpServletResponse.
	 * @param data 数据.
	 */
	public static void renderJson(HttpServletResponse response, Object data)
	{
		renderJson(response, data, false);
	}
	
	/**
	 * "application/json" 类型.
	 * 
	 * @param response HttpServletResponse.
	 * @param data 数据.
	 * @param cache 是否开启缓存.
	 */
	public static void renderJson(HttpServletResponse response, Object data, boolean cache) 
	{
		initResponse(response, ServletUtil.TYPE_HTML, cache);
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
		
	/**
	 * 初始化 response.
	 * 
	 * @param response original HttpServletResponse.
	 * @param contentType 内容类型.
	 * @param cache 是否开启缓存.
	 * 
	 * @return HttpServletResponse
	 */
	private static HttpServletResponse initResponse(HttpServletResponse response, String contentType, boolean cache)
	{
	    response.setContentType(contentType + ";charset=" + ServletUtil.DEFAULT_ENCODING);
	    if ( ! cache ) {
	    	ServletUtil.setNoCacheHeader(response);
	    }
	    return response;
	}
	
	private JsonUtil() {}
	
	
	
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param typeReference 对象类型
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.notNull(json);
		Assert.notNull(typeReference);
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转换为JSON字符串
	 * @param object 对象
	 */
	public static String toJson(Object object) {
		Assert.notNull(object);
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param javaType 对象类型
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.notNull(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param valueType 对象类型
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.notNull(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}