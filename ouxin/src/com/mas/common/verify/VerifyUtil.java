/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.verify;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Verifer auxiliary utility.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class VerifyUtil
{
	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";// "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername( String username )
	{
		return Pattern.matches( REGEX_USERNAME, username );
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword( String password )
	{
		return Pattern.matches( REGEX_PASSWORD, password );
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile( String mobile )
	{
		return Pattern.matches( REGEX_MOBILE, mobile );
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail( String email )
	{
		return Pattern.matches( REGEX_EMAIL, email );
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese( String chinese )
	{
		return Pattern.matches( REGEX_CHINESE, chinese );
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard( String idCard )
	{
		return Pattern.matches( REGEX_ID_CARD, idCard );
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl( String url )
	{
		return Pattern.matches( REGEX_URL, url );
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr( String ipAddr )
	{
		return Pattern.matches( REGEX_IP_ADDR, ipAddr );
	}

	/**
	 * Whether or not the argument is true.
	 * 
	 * @return true or false.
	 */
	public static boolean isTrue( Boolean argument )
	{
		return null != argument && argument;
	}

	/**
	 * Whether or not the argument is false.
	 * 
	 * @return true or false.
	 */
	public static boolean isFalse( Boolean argument )
	{
		return null != argument && !argument;
	}

	/**
	 * Whether or not the object is null.
	 * 
	 * @return true or false.
	 */
	public static boolean isNull( Object argument )
	{
		return null == argument;
	}

	/**
	 * Whether or not the object is not null.
	 * 
	 * @return true or false.
	 */
	public static boolean isNotNull( Object argument )
	{
		return !isNull( argument );
	}

	/**
	 * Whether or not the string is blank or null.
	 * 
	 * @return true or false.
	 */
	public static boolean isBlank( String argument )
	{
		return StringUtils.isBlank( argument );
	}

	/**
	 * Whether or not the string is blank or null.
	 * 
	 * @return true or false.
	 */
	public static boolean isBlank( Object argument )
	{
		return null == argument || StringUtils.isBlank( argument.toString() );
	}

	/**
	 * Whether or not the string is not blank and not null.
	 * 
	 * @return true or false.
	 */
	public static boolean isNotBlank( String argument )
	{
		return !isBlank( argument );
	}

	/**
	 * Whether or not the string is not blank and not null.
	 * 
	 * @return true or false.
	 */
	public static boolean isNotBlank( Object argument )
	{
		return !isBlank( argument );
	}

	/**
	 * Whether or not the array is null or empty.
	 * 
	 * @return true or false.
	 */
	public static <L> boolean isEmpty( L[] arguments )
	{
		return null == arguments || 0 >= arguments.length;
	}

	/**
	 * Whether or not the collection is null or empty.
	 * 
	 * @return true or false.
	 */
	public static <L> boolean isEmpty( Collection<L> arguments )
	{
		return null == arguments || arguments.isEmpty();
	}

	/**
	 * Whether or not the map is null or empty.
	 * 
	 * @return true or false.
	 */
	public static <K, V> boolean isEmpty( Map<K, V> arguments )
	{
		return null == arguments || arguments.isEmpty();
	}

	/**
	 * Whether or not the array is not null and not empty.
	 * 
	 * @return true or false.
	 */
	public static <L> boolean isNotEmpty( L[] arguments )
	{
		return !isEmpty( arguments );
	}

	/**
	 * Whether or not the collection is not null and not empty.
	 * 
	 * @return true or false.
	 */
	public static <L> boolean isNotEmpty( Collection<L> arguments )
	{
		return !isEmpty( arguments );
	}

	/**
	 * Whether or not the map is not null and not empty.
	 * 
	 * @return true or false.
	 */
	public static <K, V> boolean isNotEmpty( Map<K, V> arguments )
	{
		return !isEmpty( arguments );
	}

	private VerifyUtil()
	{
		super();
	}
}