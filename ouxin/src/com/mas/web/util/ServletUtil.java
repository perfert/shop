package com.mas.web.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mas.common.file.util.PathUtil;

/**
 * Servlet 工具.
 * 
 * @version 1.0
 * 
 * @author MAS
 */
public final class ServletUtil 
{
	/** 365天 总秒数 */
	public static final long ONE_YEAR_SECONDS = 31536000L;
	
	public static final String HEADER_ENCODING = "encoding";
	public static final String HEADER_NOCACHE = "no-cache";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String HEADER_GZIP = "gzip";
	public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	public static final boolean DEFAULT_NOCACHE = true;
	public static final String TYPE_TEXT = "text/plain";
	public static final String TYPE_JSON = "application/json";
	public static final String TYPE_XML = "text/xml";
	public static final String TYPE_HTML = "text/html";
	public static final String TYPE_JS = "text/javascript";
	
	public static final String H_EXPIRES = "Expires";
	public static final String H_CACHE_CONTROL = "Cache-Control";
	public static final String H_LAST_MODIFIED = "Last-Modified";
	public static final String H_ETAG = "ETag";
	public static final String H_IF_MODIFIED_SINCE = "If-Modified-Since";
	public static final String H_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String H_CONTENT_ENCODING= "Content-Encoding";
	public static final String H_VARY = "Vary";
	public static final String H_CONTENT_DISPOSITION = "Content-Disposition";
	
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds)
	{
		response.setDateHeader(H_EXPIRES, System.currentTimeMillis() + expiresSeconds * 1000L);
		response.setHeader(H_CACHE_CONTROL, "max-age=" + expiresSeconds);
	}
	
	public static void setNoCacheHeader(HttpServletResponse response)
	{
		response.setDateHeader(H_EXPIRES, 5767277549583335424L);
		response.setHeader(H_CACHE_CONTROL, HEADER_NOCACHE);
	}
	
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate)
	{
		response.setDateHeader(H_LAST_MODIFIED, lastModifiedDate);
	}
	
	public static void setEtag(HttpServletResponse response, String etag)
	{
		response.setHeader(H_ETAG, etag);
	}
	
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified)
	{
		long ifModifiedSince = request.getDateHeader(H_IF_MODIFIED_SINCE);
	    if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L)) {
	      response.setStatus(304);
	      return false;
	    }
	    return true;
	}
	
	public static boolean checkAccetptGzip(HttpServletRequest request)
	{
		String acceptEncoding = request.getHeader(H_ACCEPT_ENCODING);
	    return (StringUtils.containsIgnoreCase(acceptEncoding, HEADER_GZIP));
	}
	
	public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException
	{
		response.setHeader(H_CONTENT_ENCODING, HEADER_GZIP);
	    response.setHeader(H_VARY, HEADER_ACCEPT_ENCODING);
	    return new GZIPOutputStream(response.getOutputStream());
	}
	
	public static void setDownloadableHeader(HttpServletResponse response, String fileName)
	{
		response.setHeader(H_CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
	}
	
	public static HttpServletRequest request()
	{
		return
				( ( ServletRequestAttributes ) RequestContextHolder.getRequestAttributes() ).getRequest();
	}
	
	/**
	 * ServletContext RealPath.
	 * 
	 * @return String
	 */
	public static String realPath()
	{
		String result = PathUtil.getTomcatWebRootPath();
		return result;
	}
	
	 /**
	 * 获取ip地址.
	 * 
	 * @param req HttpServletRequest.
	 * 
	 * @return IP.
	 */
	public static String getIP(HttpServletRequest req) 
	{
        String ip = req.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        	ip = req.getHeader("Proxy-Client-IP");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        	ip = req.getHeader("WL-Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        	ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        	ip = req.getHeader("HTTP_CLIENT_IP");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        	ip = req.getRemoteAddr();  
        return ip;  
	}
	
	private ServletUtil() {}
}