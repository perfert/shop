/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.mas.core.domain.entity.Entity;

/**
 * 会员
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class Member extends Entity {
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "U_MEMBER";
	public static final String DEFAULT_CODE = "86";
          
	public static final String JSON_KEY_NICK ="nickname";
    public static final String JSON_KEY_HXID ="hxid";
    public static final String JSON_KEY_FXID ="fxid";
    
    public static final String JSON_KEY_ID ="id";
    public static final String JSON_KEY_ACCOUNT ="account";
    public static final String JSON_KEY_CODE = "code";
    public static final String JSON_KEY_OXID ="oxid";
    public static final String JSON_KEY_SPLIT = "split";
    public static final String JSON_KEY_USERNAME = "username";
    
    public static final String JSON_KEY_SEX ="sex";
    public static final String JSON_KEY_AVATAR ="portraitUri";
    public static final String JSON_KEY_ALIAS ="alias";
    public static final String JSON_KEY_CITY ="city";
    public static final String JSON_KEY_PROVINCE ="province";
    public static final String JSON_KEY_TEL ="tel";
    public static final String JSON_KEY_SIGN ="sign";
    public static final String JSON_KEY_PASSWORD = "password";
    
    public static final String AVATAR_Path = "upload/avatar/";
    public static final String CODE_ACCOUNT_SPLIT = "_";
    
	private Object parentId;
	private Object secondParentId;
	private Object thirdParentId;
	// 账户
	private String code;
	private String account;
	//用户名,code_account
	private String username;
	private String password;
	private String wxid;
	private String mobile;

	private String payPassword;
	private Integer lft;
	private Integer rgt;
	// 账户信息
	private String realName;
	private String nickName;
	private String sex;
	private Date birthday;
	private String sign;
	private String avatar;
	private String qrcode;
	private String province;
	private String city;
	
    private String token;
    private Date tokenDate;
	
    /** 密码Key */
    private String safeKey;
    /** 支付密码Key */
    private String paySafeKey;
    /** 连续登录失败次数 */
    private Integer payFailureCount;
    /** 锁定日期 */
    private Date payLockDate;
    
	//view,相对于好友来说的别名
	private String alias;
	private BigDecimal cash;
	private BigDecimal freeze;
	
	public Object getParentId() {
		return parentId;
	}

	public void setParentId(Object parentId) {
		this.parentId = parentId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}
	
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(Date tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLft() {
		return lft;
	}

	public void setLft(Integer lft) {
		this.lft = lft;
	}

	public Integer getRgt() {
		return rgt;
	}

	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getSecondParentId() {
		return secondParentId;
	}

	public void setSecondParentId(Object secondParentId) {
		this.secondParentId = secondParentId;
	}

    public Object getThirdParentId() {
        return thirdParentId;
    }

    public void setThirdParentId(Object thirdParentId) {
        this.thirdParentId = thirdParentId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSafeKey() {
        return safeKey;
    }

    public void setSafeKey(String safeKey) {
        this.safeKey = safeKey;
    }

    public String getPaySafeKey() {
        return paySafeKey;
    }

    public void setPaySafeKey(String paySafeKey) {
        this.paySafeKey = paySafeKey;
    }

    public Integer getPayFailureCount() {
        return payFailureCount;
    }

    public void setPayFailureCount(Integer payFailureCount) {
        this.payFailureCount = payFailureCount;
    }

    public Date getPayLockDate() {
        return payLockDate;
    }

    public void setPayLockDate(Date payLockDate) {
        this.payLockDate = payLockDate;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    
    
}