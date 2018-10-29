/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.i18n;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mas.common.verify.VerifyUtil;

/**
 * Internationalization message auxiliary utility.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class I18nMessageUtil
{
	/**
	 * Internationalization message.
	 * 
	 * @param baseName Internationalization resource file name.
	 * @param resourceKey Internationalization message key.
	 * @param resourceArguments 
	 * 
	 * @return {@link String} or null.
	 */
	public static String getMessage(String baseName, String resourceKey, Object... resourceArguments)
	{
		if( VerifyUtil.isBlank( baseName ) )
		{
			log.error( UN_RESOURCE );
			return null;
		}
		if( VerifyUtil.isBlank( resourceKey ) )
		{
			log.error( "Unspecified Internationalization resource key." );
			return null;
		}
		
		String message = null;
		try
		{
			message = getBundle( baseName ).getString( resourceKey );
			return
					VerifyUtil.isEmpty( resourceArguments ) ? message
							: MessageFormat.format(message, resourceArguments);
		}
		catch( MissingResourceException ex )
		{
			log.error( "Internationalization resource files: [" + baseName + "] or key: [" + resourceKey + "] not found." );
		}
		catch( Exception ex )
		{
			log.error( ex.getMessage() );
		}
		
		return message;
	}
	
	/**
	 * Message bundle.
	 * 
	 * @param baseName Internationalization resource file name.
	 * 
	 * @return {@link ResourceBundle} or null.
	 */
	public static ResourceBundle getBundle(String baseName)
	{
		if( VerifyUtil.isBlank( baseName ) )
		{
			log.error( UN_RESOURCE );
			return null;
		}
		
		return ResourceBundle.getBundle(baseName);
	}
	
	private static final String UN_RESOURCE = "Unspecified Internationalization resource files.";
	
	private static final Logger log = LogManager.getLogger(I18nMessageUtil.class);
	
	private I18nMessageUtil() { super(); }
}