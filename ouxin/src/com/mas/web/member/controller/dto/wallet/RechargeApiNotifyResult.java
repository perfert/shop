/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.dto.wallet;

/**
 * 充值错误通知。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class RechargeApiNotifyResult
{
	private String return_code;
	private String return_msg;
	
	public RechargeApiNotifyResult( String return_code, String return_msg )
	{
		this.return_code = return_code;
		this.return_msg = return_msg;
	}

	public String getReturn_code()
	{
		return return_code;
	}

	public void setReturn_code( String return_code )
	{
		this.return_code = return_code;
	}

	public String getReturn_msg()
	{
		return return_msg;
	}

	public void setReturn_msg( String return_msg )
	{
		this.return_msg = return_msg;
	}
}
