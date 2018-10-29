/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.dto;

import com.mas.user.domain.entity.Member;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class MemberCtrDto extends BaseCtrDto<Member>
{
	private Member parent;
	private String password;
	private String oldPwd;
	private String smsCaptha;
	private String captcha;

	public Member getParent()
	{
		return parent;
	}

	public void setParent( Member parent )
	{
		this.parent = parent;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getOldPwd()
	{
		return oldPwd;
	}

	public void setOldPwd( String oldPwd )
	{
		this.oldPwd = oldPwd;
	}
	
	public String getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha( String captcha )
	{
		this.captcha = captcha;
	}

	public String getSmsCaptha()
	{
		return smsCaptha;
	}

	public void setSmsCaptha( String smsCaptha )
	{
		this.smsCaptha = smsCaptha;
	}
}