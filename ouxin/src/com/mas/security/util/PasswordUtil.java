/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.util;

import org.apache.commons.codec.digest.DigestUtils;

import com.mas.common.exception.RuntimeEx;
import com.mas.common.verify.VerifyUtil;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class PasswordUtil
{
    
    public static String encryptByMd5(String check)
    {
        if( VerifyUtil.isBlank( check ) ) throw new RuntimeEx( "加密失败！" );
        return DigestUtils.md5Hex( check );
    }
    
	public static String encrypt(String account, String password)
	{
		if( VerifyUtil.isBlank( account ) || VerifyUtil.isBlank( password ) ) throw new RuntimeEx( "加密失败！" );
		return DigestUtils.md5Hex( password.trim() + "{" + account.trim() + "}" );
	}
	
	public static boolean isPassword(String account, String password, String encrypt)
	{
		if( VerifyUtil.isBlank( encrypt ) ) return false;
		if( VerifyUtil.isBlank( account ) || VerifyUtil.isBlank( password ) ) throw new RuntimeEx( "匹配失败！" );
		if( "0".equals( password ) ) return false;
		return encrypt.trim().equals( DigestUtils.md5Hex( password.trim() + "{" + account.trim() + "}" ) );
	}

	public static void main( String[] args )
	{
		System.out.println(encrypt( "mas", "123456" ));
	}
}
