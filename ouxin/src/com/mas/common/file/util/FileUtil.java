/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mas.common.exception.RuntimeEx;
import com.mas.common.file.FileException;
import com.mas.common.util.DateTimeUtil;
import com.mas.common.verify.VerifyUtil;

/**
 * File auxiliary utility. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class FileUtil {
    
    // 缓存文件头信息-文件头信息  
    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();  
    static {  
        // images  
        mFileTypes.put("FFD8FFE0", "jpg");  
        mFileTypes.put("89504E47", "png");  
        mFileTypes.put("47494638", "gif");  
        mFileTypes.put("49492A00", "tif");  
        mFileTypes.put("424D", "bmp");  
        //  
        mFileTypes.put("2321414D", "amr"); 
        mFileTypes.put("00000018", "mp4"); 
        mFileTypes.put("41433130", "dwg"); // CAD  
        mFileTypes.put("38425053", "psd");  
        mFileTypes.put("7B5C727466", "rtf"); // 日记本  
        mFileTypes.put("3C3F786D6C", "xml");  
        mFileTypes.put("68746D6C3E", "html");  
        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件  
        mFileTypes.put("D0CF11E0", "doc");  
        mFileTypes.put("D0CF11E0", "xls");//excel2003版本文件  
        mFileTypes.put("5374616E64617264204A", "mdb");  
        mFileTypes.put("252150532D41646F6265", "ps");  
        mFileTypes.put("255044462D312E", "pdf");  
        mFileTypes.put("504B0304", "docx");  
        mFileTypes.put("504B0304", "xlsx");//excel2007以上版本文件  
        mFileTypes.put("52617221", "rar");  
        mFileTypes.put("57415645", "wav");  
        mFileTypes.put("41564920", "avi");  
        mFileTypes.put("2E524D46", "rm");  
        mFileTypes.put("000001BA", "mpg");  
        mFileTypes.put("000001B3", "mpg");  
        mFileTypes.put("6D6F6F76", "mov");  
        mFileTypes.put("3026B2758E66CF11", "asf");  
        mFileTypes.put("4D546864", "mid");  
        mFileTypes.put("1F8B08", "gz");  
    }  
    
    /** 
     * 方法描述：根据文件路径获取文件头信息 
     * @param filePath 文件路径 
     * @return 文件头信息 
     */  
    public static String getFileType(String filePath) {  
        return mFileTypes.get(getFileHeader(filePath));  
    }  
    
    public static String getFileType(InputStream ip) {  
        return mFileTypes.get(getFileHeader(ip));  
    } 
    
   
    public static String getFileHeader(InputStream ip){
        String value = null;  
        try {  
            byte[] b = new byte[4];  
            /* 
             * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length 
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len) 
             * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。 
             */  
            ip.read(b, 0, b.length);  
            value = bytesToHexString(b);  
        } catch (Exception e) {  
        } finally {  
            if (null != ip) {  
                try {  
                    ip.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        return value; 
    }
  
    public static void main(String[] args) {  
        final String fileType = getFileType("E:/web/apache-tomcat-7.0.73/webapps/chat/upload/record/image/4bc9532c-99a3-4abd-9d89-a8651f795b80.jpg");
        System.out.println(fileType);
    }
    
    /** 
     * 方法描述：根据文件路径获取文件头信息 
     * @param filePath 文件路径 
     * @return 文件头信息 
     */  
    public static String getFileHeader(String filePath) {  
        FileInputStream is = null;  
        String value = null;  
        try {  
            is = new FileInputStream(filePath);  
            byte[] b = new byte[4];  
            /* 
             * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length 
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len) 
             * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。 
             */  
            is.read(b, 0, b.length);  
            value = bytesToHexString(b);  
        } catch (Exception e) {  
        } finally {  
            if (null != is) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        return value;  
    }  
  
    /** 
     * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示 
     * @param src 要读取文件头信息的文件的byte数组 
     * @return   文件头信息 
     */  
    private static String bytesToHexString(byte[] src) {  
        StringBuilder builder = new StringBuilder();  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        String hv;  
        for (int i = 0; i < src.length; i++) {  
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写  
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();  
            if (hv.length() < 2) {  
                builder.append(0);  
            }  
            builder.append(hv);  
        }  
        return builder.toString();  
    }  
    
    /**
     * 返回 File.</br>
     * 自动创建文件, 如果不存在.
     * 
     * @param path 
     * 
     * @return File.
     */
    public static File mkdir(String url)
    {
        File file = new File(url);
        if ( ! file.exists() ) file.mkdirs();
        return file;
    }
    
    
	/**
	 * The byte array is written to the destination file. </br>
	 * 
	 * @param in Byte array.
	 * @param dest Destination file.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static void copy(byte[] in, File dest) throws FileException
	{
		copy(in, dest, StreamUtil.BUFFER_SIZE);
	}
	
	/**
	 * The byte array is written to the destination file.
	 * The default buffer value to the 4096B.
	 * 
	 * @param in Byte array.
	 * @param dest Destination file.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static void copy(byte[] in, File dest, int bufferSize) throws FileException
	{
		if ( null != in && 0 < in.length ) 
		{
			OutputStream fileStream = null;
			ByteArrayInputStream inStream = null;
			OutputStream outStream = null;
			try {
				deleteFileIfExist(dest);				
				StreamUtil.copy(
						inStream = new ByteArrayInputStream(in)
						, outStream= new BufferedOutputStream(fileStream = new FileOutputStream(dest))
						, bufferSize
						);
			} catch (FileNotFoundException ex) {
				throw new FileException(FileMessage.msgByFileNotFound(dest), ex);
			} catch (FileException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new FileException(ex);
			} finally {
				closingStream(fileStream);
				closingStream(inStream, outStream);
			}
		}
	}
	
	/**
	 * Copy the source file to the destination file.</br>
	 * The default buffer value to the 4096B.
	 * 
	 * @param src Source file.
	 * @param dest Destination file.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static void copy(File src, File dest) throws FileException
	{
		copy(src, dest, StreamUtil.BUFFER_SIZE);
	}
	
	/**
	 * Copy the source file to the destination file.
	 * 
	 * @param src Source file.
	 * @param dest Destination file.
	 * @param bufferSize Each write buffer value.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static void copy(File src, File dest, int bufferSize) throws FileException
	{
		InputStream fileIn = null;
		InputStream in = null;
		OutputStream fileOut = null;
		OutputStream out = null;
		try {
			deleteFileIfExist(dest);			
			StreamUtil.copy(
					in = new BufferedInputStream(fileIn = new FileInputStream(src))
					, out = new BufferedOutputStream(fileOut = new FileOutputStream(dest))
					, bufferSize
					);
		} catch (FileNotFoundException ex) {
			throw new FileException(FileMessage.msgByFileNotFound(src), ex);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			closingStream(fileIn, fileOut);
			closingStream(in, out);
		}
	}
	
	/**
	 * Copy the source file to the destination file.
	 * 
	 * @param is InputStream.
	 * @param dest Destination file.
	 * @param bufferSize Each write buffer value.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static void copy(InputStream is, File dest, int bufferSize) throws FileException
	{
		InputStream in = null;
		OutputStream fileOut = null;
		OutputStream out = null;
		try {
			deleteFileIfExist(dest);			
			StreamUtil.copy(
					in = new BufferedInputStream(is)
					, out = new BufferedOutputStream(fileOut = new FileOutputStream(dest))
					, bufferSize
					);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			closingStream(is, fileOut);
			closingStream(in, out);
		}
	}
		
	/**
	 * Close the input/output stream, if not empty.
	 * 
	 * @param in Input stream.
	 * @param out Output stream.
	 */
	public static void closingStream(InputStream in, OutputStream out)
	{
		closingStream(in);
		closingStream(out);
	}
	
	/**
	 * Close the input stream, if not empty.
	 * 
	 * @param ins Input stream array.
	 */
	public static void closingStream(InputStream... ins)
	{
		if ( VerifyUtil.isNotEmpty(ins) ) {
			for ( InputStream in : ins ) {
				if ( VerifyUtil.isNotNull(in) ) {
					try {
						in.close();
					} catch (IOException ex) {
						log.error(FileMessage.msgByIOException(ex));
						continue ;
					}
				}
			}
		}
	}
	
	/**
	 * Close the output stream, if not empty.
	 * 
	 * @param ins Output stream array.
	 */
	public static void closingStream(OutputStream... outs)
	{
		if ( VerifyUtil.isNotEmpty(outs) ) {
			for ( OutputStream out : outs ) {
				if ( VerifyUtil.isNotNull(out) ) {
					try {
						out.close();
					} catch (IOException ex) {
						log.error(FileMessage.msgByIOException(ex));
						continue ;
					}
				}
			}
		}
	}
	
	/**
	 * @return YYYYMMDDHHMMSSS 格式文件名.
	 */
	public static String fileNameByDate()
	{
		return
				DateTimeUtil.format(DateTimeUtil.YYYYMMDDHHMMSSS);
	}
	
	/**
	 * According to file URI, delete files, if the file exists.
	 * 
	 * @param uris File URI array.
	 */
	public static void remove(String... uris)
	{
		if ( VerifyUtil.isNotEmpty(uris) ) {
			for ( String uri : uris ) {
				if ( VerifyUtil.isNotBlank(uri) ) {
					deleteFileIfExist(new File(getUrlByUri(uri)));
				}
			}
		}
	}
	
	/**
	 * According to file URI, delete files, if the file exists.
	 * 
	 * @param uris File URI array.
	 */
	public static void remove(List<String> uris)
	{
		if( VerifyUtil.isNotEmpty( uris ) )
		{
			remove( uris.toArray( new String[]{} ) );
		}
	}
	
	/**
	 * According to file URI, get the file URL.
	 * 
	 * @param uri File URI.
	 * 
	 * @return file URL.
	 */
	public static String getUrlByUri(String uri)
	{
		assertUrlNotBlank(uri);
		
		return PathUtil.getTomcatWebRootPath() + uri;
	}
	
	/**
	 * To get the folder URL or UR (with "/" or "\" at the end).
	 * 
	 * @param url URL or URI.
	 * 
	 * @return URL or URI.
	 */
	public static String getFolderUrl(String url)
	{
		assertUrlNotBlank(url);
		
		if ( 0 > url.lastIndexOf(".") ) {
			return url;
		}
		
		int slashIdx = url.lastIndexOf("/");
		int backSlashIdx = url.lastIndexOf(File.separator);
		if ( 0 > slashIdx && 0 > backSlashIdx ) {
			return "/";
		}
		
		int maxIdx = Math.max(slashIdx, backSlashIdx);		
		return 
				url.lastIndexOf(".") == maxIdx + 1 ? url.substring(0, maxIdx) + "/"
						: url.substring(0, maxIdx + 1);
	}
	
	/**
	 * Remove the leading "/" or "\", if the beginning of the present.
	 * 
	 * @param uri File URL or URI.
	 * 
	 * @return {@link String}.
	 */
	public static String removeIfStartWithSlash(String uri)
	{
		assertUrlNotBlank(uri);
		
		if ( uri.startsWith("/") || uri.startsWith(File.separator) ) {
			return removeIfStartWithSlash(uri.substring(1));
		}
		return uri;
	}
	
	/**
	 * According to the URL or URI, get the file extension, if it exists.
	 * 
	 * @param url File URL or URI.
	 * 
	 * @return file extension or null.
	 */
	public static String getSuffix(String url)
	{
		assertUrlNotBlank(url);
		
		return 
				0 < url.indexOf(".") ? url.substring(url.lastIndexOf(".") + 1, url.length())
						: null;
	}
	
	/**
	 * Construction of the File object. </br>
	 * Create all folder by url, if not present.
	 * 
	 * @param url File URL.
	 * 
	 * @return {@link File}.
	 */
	public static File mkdirs(String url)
	{
		assertUrlNotBlank(url);
		
		File file = new File(url);	
		File mkdirFolders = null;
		
		int lastPointLen = -1;
		if ( 0 <= url.indexOf(".") && url.length() != ( lastPointLen = url.lastIndexOf(".") ) + 1 && ( ( url.contains( "/" ) && lastPointLen > url.lastIndexOf( "/" ) ) || ( url.contains( "\\" ) && lastPointLen > url.lastIndexOf( "\\" ) ) ) ) {
			mkdirFolders = file.getParentFile();
		} else {
			mkdirFolders = file;
		}
		
		if ( ! mkdirFolders.exists() ) {
			mkdirFolders.mkdirs();
		}				
		return file;
	}
	
	private static void deleteFileIfExist(File file)
	{
		if ( file.exists() ) {
			file.delete();
		}
	}
	
	private static void assertUrlNotBlank(String url)
	{
		if ( VerifyUtil.isBlank(url) ) {
			throw new RuntimeEx(FileMessage.msgByNoUrl());
		}
	}
	
	private FileUtil() { super(); }
	private static final Logger log = LogManager.getLogger(FileUtil.class);
}