/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.springmvc.util;

import java.io.File;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.mas.common.file.FileBean;
import com.mas.common.file.FileException;
import com.mas.common.file.util.FileUtil;
import com.mas.common.file.util.PathUtil;
import com.mas.common.verify.VerifyUtil;

/**
 * 文件上传工具。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class UploadUtil
{
	/**
	 * 上传文件.
	 * 
	 * @param multipartFile Spring MultipartFile.
	 * @param savePath 文件保存路径.
	 * 
	 * @return FileBean / null.
	 * 
	 * @throws FileException 
	 */
	public static FileBean upload(MultipartFile multipartFile, String savePath) throws FileException
	{
		FileBean result = null;
		String uri = null;
		
		try
		{
		    //获取上传流，下载存放目录地址
		    //可改成异步上传    
            String saveFolder = FileUtil.getFolderUrl( savePath );
            String suffix = FileUtil.getSuffix( multipartFile.getOriginalFilename() );
            String fileName = FileUtil.fileNameByDate() + "." + suffix;
            uri = saveFolder + fileName;
            
            //String path = PathUtil.getTomcatWebRootPath() + saveFolder;         
            String path = PathUtil.getTomcatWebRootPath();  
            
			File outFile = new File(path + uri);
			if (!outFile.getParentFile().exists()) {
			        outFile.getParentFile().mkdirs();
            }
			//File outFile = new File( FileUtil.mkdirs( path ), fileName );
			FileUtil.copy(multipartFile.getBytes(), outFile);
			
			result = new FileBean(fileName, PathUtil.getTomcatWebRootPath() + uri, uri, multipartFile.getContentType(), suffix, multipartFile.getSize());
		} 
		catch (Exception ex) {
			result = null;
			//if ( VerifyUtil.isNotBlank(uri) ) FileUtil.remove(uri);
			log.error( ex.getMessage() );
			throw new FileException( "上传图片失败！" );
		}
		
		return result;
	}
	
	/**
     * 上传文件.
     * 
     * @param multipartFile Spring MultipartFile.
     * @param savePath 文件保存路径.
     * 
     * @return FileBean / null.
     * 
     * @throws FileException 
     */
    public static FileBean upload(MultipartFile multipartFile, String savePath, String fileName) throws FileException
    {
        FileBean result = null;
        String uri = null;
        
        try
        {
            //获取上传流，下载存放目录地址
            //可改成异步上传    
            String saveFolder = FileUtil.getFolderUrl( savePath );
            String suffix = FileUtil.getSuffix( multipartFile.getOriginalFilename() );
            fileName = fileName + "." + suffix;
            uri = saveFolder + fileName;
            
            //String path = PathUtil.getTomcatWebRootPath() + saveFolder;         
            String path = PathUtil.getTomcatWebRootPath();  
            
            File outFile = new File(path + uri);
            if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
            }
            //File outFile = new File( FileUtil.mkdirs( path ), fileName );
            FileUtil.copy(multipartFile.getBytes(), outFile);
            
            result = new FileBean(fileName, PathUtil.getTomcatWebRootPath() + uri, uri, multipartFile.getContentType(), suffix, multipartFile.getSize());
        } 
        catch (Exception ex) {
            result = null;
            //if ( VerifyUtil.isNotBlank(uri) ) FileUtil.remove(uri);
            log.error( ex.getMessage() );
            throw new FileException( "上传图片失败！" );
        }
        
        return result;
    }
	
	/**
	 * 上传文件.
	 * 
	 * @param multipartFile Spring MultipartFile.
	 * @param savePath 文件保存路径.
	 * 
	 * @return FileBean / null.
	 */
	/*public static FileBean upload(MultipartFile multipartFile, String savePath)
	{
		FileBean result = null;
		String uri = null;
		
		try
		{
			String saveFolder = FileUtil.folderUri(savePath);
			String suffix = suffix(multipartFile.getOriginalFilename());
			String fileName = fileNameByDate() + "." + suffix;
			uri = saveFolder + fileName;
			
			String path = PathUtil.getTomcatWebRootPath() + saveFolder;			
			
			File outFile = new File(mkdir(path), fileName);
			FileUtil.copy(multipartFile.getBytes(), outFile);
			
			result = new FileBean(fileName, PathUtil.getTomcatWebRootPath() + uri, uri, multipartFile.getContentType(), suffix, multipartFile.getSize());
		} 
		catch (Exception ex) {
			result = null;
			if ( VerifyUtil.isNotBlank(uri) ) FileUtil.remove(uri);
			log.error( ex.getMessage() );
		}
		
		return result;
	}*/
	
	/**
	 * 根据URI, 获取文件.
	 * 
	 * @param uri 文件URI.
	 * 
	 * @return File
	 */
	/*public static File getFile(String uri)
	{
		return new File(PathUtil.getTomcatWebRootPath() + fileUri(uri));
	}*/
	
	/**
	 * @return YYYYMMDDHHMMSSS 格式文件名.
	 */
	/*public static String fileNameByDate()
	{
		return
				DateTimeUtil.format(DateTimeUtil.YYYYMMDDHHMMSSS);
	}*/
	
	/**
	 * 获取文件后缀.
	 * 
	 * @param fileName 文件 URL.
	 * 
	 * @return 文件后缀.
	 */
	/*public static String suffix(String fileUrl)
	{
		return
				fileUrl.substring(fileUrl.lastIndexOf(".") + 1, fileUrl.length());
	}*/
	
	/**
	 * 文件 URI.
	 * 
	 * @return 去除 "/" 或 "\" 开头, 如果存在.
	 */
	/*public static String fileUri(String uri)
	{
		if ( uri.startsWith("/") || uri.startsWith(File.separator) ) return fileUri(uri.substring(1));
		return 
				uri.contains(File.separator) ? uri.replace(File.separator, "/")
						: uri;
	}*/
	
	/**
	 * 文件夹 URI.
	 * 
	 * @return 去除 "/" 或 "\" 开头, 如果存在.</br>
	 * 				添加 "/" 或 "\" 到未尾, 如果不存在.
	 */
	/*public static String folderUri(String folderUri)
	{
		folderUri = fileUri(folderUri);
		return
				! folderUri.endsWith("/") ? folderUri + "/"
						: folderUri;
	}*/
	
	/**
	 * 根据文件URI, 获取文件所在文件夹URI.
	 * 
	 * @param fileUri 文件URI.
	 * 
	 * @return String / ""
	 */
	/*public static String getFolderByFileUri(String fileUri)
	{
		if ( fileUri.contains(File.separator) ) fileUri = fileUri.replace(File.separator, "/");
		if ( 0 <= fileUri.indexOf("/") ) 
		{
			return fileUri(fileUri.substring(0, fileUri.lastIndexOf("/") + 1));
		} else return "";
	}*/
	
	/**
	 * 返回 File.</br>
	 * 自动创建文件, 如果不存在.
	 * 
	 * @param path 
	 * 
	 * @return File.
	 */
	/*public static File mkdir(String url)
	{
		File file = new File(url);
		if ( ! file.exists() ) file.mkdirs();
		return file;
	}*/
	
	private UploadUtil() { }
	
	private static final Logger log = LogManager.getLogger(UploadUtil.class);
}