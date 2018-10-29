/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.dto.wallet;


/**
 * 充值结果。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class RechargeApiResult extends RechargeApiNotifyResult
{
	private String mch_id;					// 商户ID
	
	private String code;						// 会员账号
	private String account;					// 会员密码
	private String out_trade_no;			// 订单编号（唯一）
	private String fee_type;					// 充值类型
	private String total_fee;					// 充值金额
	private String nonce_str;				// 随机字串
	private String api_type;					// aip 类型
	
	private String recharge_no;			// 订单ID。
	
	private String attach;						// 原样带回
	private String result_code;				// 状态 SUCCESS / FAILED
	
	public RechargeApiResult( String return_code, String return_msg )
	{
		super( return_code, return_msg );
	}

	public String getMch_id()
	{
		return mch_id;
	}

	public void setMch_id( String mch_id )
	{
		this.mch_id = mch_id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode( String code )
	{
		this.code = code;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount( String account )
	{
		this.account = account;
	}

	public String getOut_trade_no()
	{
		return out_trade_no;
	}

	public void setOut_trade_no( String out_trade_no )
	{
		this.out_trade_no = out_trade_no;
	}

	public String getFee_type()
	{
		return fee_type;
	}

	public void setFee_type( String fee_type )
	{
		this.fee_type = fee_type;
	}

	public String getTotal_fee()
	{
		return total_fee;
	}

	public void setTotal_fee( String total_fee )
	{
		this.total_fee = total_fee;
	}

	public String getNonce_str()
	{
		return nonce_str;
	}

	public void setNonce_str( String nonce_str )
	{
		this.nonce_str = nonce_str;
	}

	public String getApi_type()
	{
		return api_type;
	}

	public void setApi_type( String api_type )
	{
		this.api_type = api_type;
	}

	public String getAttach()
	{
		return attach;
	}

	public void setAttach( String attach )
	{
		this.attach = attach;
	}

	public String getResult_code()
	{
		return result_code;
	}

	public void setResult_code( String result_code )
	{
		this.result_code = result_code;
	}

	public String getRecharge_no()
	{
		return recharge_no;
	}

	public void setRecharge_no( String recharge_no )
	{
		this.recharge_no = recharge_no;
	}
}
