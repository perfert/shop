/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture;

import java.awt.Color;

import com.mas.common.exception.RuntimeEx;
import com.mas.common.file.util.FileMessage;
import com.mas.common.file.util.FileUtil;
import com.mas.common.verify.VerifyUtil;

/**
 * Picture auxiliary utility. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class PictureUtil 
{
	private static final String[] PICTURE_SUFFIX = new String[] { "jpg", "jpeg", "gif", "png", "bmp" };
	
	/**
	 * Whether or not the file is a picture format.
	 * 
	 * @param url URL or URI.
	 * 
	 * @return true or false.
	 */
	public static boolean isPicture(String url)
	{
		if ( VerifyUtil.isNotBlank(url) ) {			
			String suffix = FileUtil.getSuffix(url);
			if ( VerifyUtil.isNotBlank(suffix) ) {
				for ( String str : pictureSuffix() ) {
					if ( str.equalsIgnoreCase(suffix) ) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Hexadecimal color string turn RGB.
	 * 
	 * @param color Hexadecimal color string.
	 * 
	 * @return {@link Color}.
	 */
	public static Color parseToColor(String color)
	{
		if ( VerifyUtil.isBlank(color) ) {
			throw new RuntimeEx(FileMessage.msgByNoHexColor());
		}
		if ( 6 != color.length() ) {
			throw new RuntimeEx(FileMessage.msgByErrorFormatHexColor(color));
		}

		return new Color(Integer.parseInt(color, 16));
	}
	
	/**
	 * @return Picture default suffix.
	 */
	public static String[] pictureSuffix()
	{
		return PICTURE_SUFFIX;
	}
	
	private PictureUtil() { super(); }
}