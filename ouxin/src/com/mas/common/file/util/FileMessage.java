/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.util;

import java.io.File;
import java.io.IOException;

/**
 * Internationalization field message. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class FileMessage 
{
	/**
	 * @return 信息：IO异常。
	 */
	public static String msgByIOException(IOException ex)
	{
		return "io exception: " + ex.getMessage();
	}
	
	/**
	 * @return 信息：未指定的URL或URI。
	 */
	public static String msgByNoUrl()
	{
		return "Unspecified uri or url.";
	}
	
	/**
	 * @return 信息：文件未找到。
	 */
	public static String msgByFileNotFound(File file)
	{
		return "File:" + file.getName() + " not found.";
	}
	
	/**
	 * @return 信息：未指定的十六进制颜色字符串。
	 */
	public static String msgByNoHexColor()
	{
		return "Unspecified hexadecimal color string.";
	}
	
	/**
	 * @return 信息：十六进制颜色字符串格式错误。
	 */
	public static String msgByErrorFormatHexColor(String color)
	{
		return "Hexadecimal color format string:" + color + ", errors.";
	}
	
	/**
	 * @return 信息：图片缩放的宽度必须大于0。
	 */
	public static String msgByScalWidthNeedGreaterZero(int width)
	{
		return "Image scaling width:" + width + ", its value must be greater than 0.";
	}
	
	/**
	 * @return 信息：图片缩放的高度必须大于0。
	 */
	public static String msgByScalHeightNeedGreaterZero(int height)
	{
		return "Image scaling height:" + height + ", its value must be greater than 0.";
	}
	
	/**
	 * @return 信息：未指定的水印内容。
	 */
	public static String msgByNoMarkText()
	{
		return "Unspecified mark text.";
	}
	
	private FileMessage() { super(); }
}