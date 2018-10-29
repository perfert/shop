/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.domain.entity.vo;

import java.util.List;

import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.wallet.WealthType;

/**
 * 会员 view object.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class MemberVo extends Member
{
	private static final long serialVersionUID = 1L;
	
	private Object parentAccount;
	private Object secondParentAccount;
	private Object thirdParentAccount;
	
	private Object parentLogo;
	private Object secondParentLogo;
	private Object thirdParentLogo;
	
	private Integer parentLevel;
	private Integer secondParentLevel;
	private Integer thirdParentLevel;
	
	private String parentNickName;
	private String secondNickName;
	private String thirdNickName;
	
	private List<WealthType> typeList;
	
	
	public Object getParentLogo()
	{
		return parentLogo;
	}
	public void setParentLogo( Object parentLogo )
	{
		this.parentLogo = parentLogo;
	}
	public Integer getParentLevel()
	{
		return parentLevel;
	}
	public void setParentLevel( Integer parentLevel )
	{
		this.parentLevel = parentLevel;
	}
	public Object getSecondParentLogo()
	{
		return secondParentLogo;
	}
	public void setSecondParentLogo( Object secondParentLogo )
	{
		this.secondParentLogo = secondParentLogo;
	}
	public Integer getSecondParentLevel()
	{
		return secondParentLevel;
	}
	public void setSecondParentLevel( Integer secondParentLevel )
	{
		this.secondParentLevel = secondParentLevel;
	}
	public Object getThirdParentLogo()
	{
		return thirdParentLogo;
	}
	public void setThirdParentLogo( Object thirdParentLogo )
	{
		this.thirdParentLogo = thirdParentLogo;
	}
	public Integer getThirdParentLevel()
	{
		return thirdParentLevel;
	}
	public void setThirdParentLevel( Integer thirdParentLevel )
	{
		this.thirdParentLevel = thirdParentLevel;
	}
	public String getParentNickName()
	{
		return parentNickName;
	}
	public void setParentNickName( String parentNickName )
	{
		this.parentNickName = parentNickName;
	}
	public String getSecondNickName()
	{
		return secondNickName;
	}
	public void setSecondNickName( String secondNickName )
	{
		this.secondNickName = secondNickName;
	}
	public String getThirdNickName()
	{
		return thirdNickName;
	}
	public void setThirdNickName( String thirdNickName )
	{
		this.thirdNickName = thirdNickName;
	}
	public Object getParentAccount()
	{
		return parentAccount;
	}
	public void setParentAccount( Object parentAccount )
	{
		this.parentAccount = parentAccount;
	}
	public Object getSecondParentAccount()
	{
		return secondParentAccount;
	}
	public void setSecondParentAccount( Object secondParentAccount )
	{
		this.secondParentAccount = secondParentAccount;
	}
	public Object getThirdParentAccount()
	{
		return thirdParentAccount;
	}
	public void setThirdParentAccount( Object thirdParentAccount )
	{
		this.thirdParentAccount = thirdParentAccount;
	}
    public List<WealthType> getTypeList() {
        return typeList;
    }
    public void setTypeList(List<WealthType> typeList) {
        this.typeList = typeList;
    }
	
	
}