/**    
 * 文件名：WealthRecord.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.wallet;

import java.math.BigDecimal;
import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public class WealthRecord extends Entity {
    
    private static final long serialVersionUID = -3960778674635937958L;

    public static final String TABLE_NAME = "WAL_WEALTH_RECORD";
    
    public enum Type {
        INCOME(0,""),
        EXPENSE(1,"");
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private Type(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }

    public enum Status {
        PAY_SUCCESS(0,""),//支付成功
        SAVE_MONEY(1,""), //已存入零钱
        BACK_MONEY(2,""), //退款成功
        RECHARGE_SUCCESS(3,""), //充值成功
        TRANSFER_SUCCESS(4,""),//已转账
        RECEIVE_SUCCESS(5,""),
        WITHDRAW_IN_HAND(6,""),//提币申请
        WITHDRAW_SUCCESS(7,""),//提币到账
        WITHDRAW__FAIL(8,""),  //提币失败  
        WITHDRAW__BACK(9,""),
        MORTAGAGE_CREATE(10,""),
        Mortagage__RETURN(11,""),
        ORDER__CREATE(12,""),   //创建
        ORDER__CONFIRM(13,""), //确认
        ORDER__RETURN(14,"");  //退款;//提币退款
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private Status(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }
    
    public enum OperateType {
        RP(0,"红包"),//支付成功
        ADMIN(1,"后台直充"), //已存入零钱
        TRANSFER(2,"转账"), //退款成功
        OFC(3,"OFC钱包"), //充值成功
        WITHDRAW(4,"提币"),//已转账
        MORTAGAGE(5,"押金"),
        ORDER(6,"第三方订单支付");//提币申请;  //退款;//提币退款
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private OperateType(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }

    private BigDecimal oldCash;            //原cash值
    private BigDecimal nowCash;            //现cash值
    private BigDecimal oldFreeze;
    private BigDecimal nowFreeze;   
    private BigDecimal expense;   //支出(相对于会员而言)
    private BigDecimal income;    //收入(相对于会员而言)
    
    private Integer status;              //操作状态
    private String remark;               //操作标题
    private String tips;                 //备注
    private String  sn;  
    
    private Integer type;        //收支类型,0:收入,1:支出
    private Integer operateType; //操作类型,0:红包,1:后台直充,2:转账,3:OFC钱包,4:提币,5:押金,6:第三方订单支付
    private Object payType;     // 支付方式,当type=1,才有数据
    
    private Object rpId;     //红包相关
    private Object rpItemId; //领红包相关
    private Object rpBackId;  //红包退款
    private Object adminId;   //后台直充相关,只有收入
    private Object ofcId;      //OFC充值相关,只有收入
    private Object transferId; //二维码相关
    private Object wdId;       //提币
    private Object wdBackId;   //提币退款
    
    private Object guaranteeId;
    private Object mortagageId;//押金
    
    private Object wealthId;
    private Object memberId;
    
    
    public WealthRecord(){}
    
    /**
     * @param nowCash     新财富值
     * @param oldCash     原财富值
     * @param expense     支出多少
     * @param income　　　 收入多少
     * @param status      操作状态
     * @param remark      操作标题
     * @param tips
     * @param sn          编号
     * @param type        收支类型,0:收入,1:支出
     * @param operateType 操何种数据,0:红包,1:后台直充,2:转账,3:OFC钱包,4:提币,5:押金,6:第三方订单支付
     * @param payType     支付方式
     * @param rpId        红包id
     * @param rpItemId    领红包id
     * @param rpBackId    红包退钱id
     * @param adminId　　　管理员操作id
     * @param ofcId　　　　充值ofcid
     * @param transferId  转账id
     * @param wdId　　　　 提币id
     * @param wdBackId    退币id
     * @param guaranteeId　保证金id
     * @param mortagageId　押金id
     * @param wealthId　　　财富id
     * @param memberId     会员id
     */
    public WealthRecord(BigDecimal nowCash,BigDecimal oldCash,BigDecimal expense, BigDecimal income, Integer status, String remark, String tips, String sn, Integer type,
            Integer operateType, Object payType, Object rpId, Object rpItemId, Object rpBackId, Object adminId,
            Object ofcId, Object transferId, Object wdId, Object wdBackId, Object guaranteeId, Object mortagageId,
            Object wealthId, Object memberId) {
        super();
        this.oldCash = oldCash;
        this.nowCash = nowCash;
        this.oldFreeze = new BigDecimal(0);
        this.nowFreeze = new BigDecimal(0);
        this.expense = expense;
        this.income = income;
        this.status = status;
        this.remark = remark;
        this.tips = tips;
        this.sn = sn;
        this.type = type;
        this.operateType = operateType;
        this.payType = payType;
        this.rpId = rpId;
        this.rpItemId = rpItemId;
        this.rpBackId = rpBackId;
        this.adminId = adminId;
        this.ofcId = ofcId;
        this.transferId = transferId;
        this.wdId = wdId;
        this.wdBackId = wdBackId;
        this.guaranteeId = guaranteeId;
        this.mortagageId = mortagageId;
        this.wealthId = wealthId;
        this.memberId = memberId;
        this.setState(1);
    }
    
    public BigDecimal getOldCash() {
        return oldCash;
    }

    public void setOldCash(BigDecimal oldCash) {
        this.oldCash = oldCash;
    }

    public BigDecimal getNowCash() {
        return nowCash;
    }

    public void setNowCash(BigDecimal nowCash) {
        this.nowCash = nowCash;
    }

    public BigDecimal getOldFreeze() {
        return oldFreeze;
    }

    public void setOldFreeze(BigDecimal oldFreeze) {
        this.oldFreeze = oldFreeze;
    }

    public BigDecimal getNowFreeze() {
        return nowFreeze;
    }

    public void setNowFreeze(BigDecimal nowFreeze) {
        this.nowFreeze = nowFreeze;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getWealthId() {
        return wealthId;
    }

    public void setWealthId(Object wealthId) {
        this.wealthId = wealthId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getRpId() {
        return rpId;
    }

    public void setRpId(Object rpId) {
        this.rpId = rpId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Object getAdminId() {
        return adminId;
    }

    public void setAdminId(Object adminId) {
        this.adminId = adminId;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Object getOfcId() {
        return ofcId;
    }

    public void setOfcId(Object ofcId) {
        this.ofcId = ofcId;
    }

    public Object getTransferId() {
        return transferId;
    }

    public void setTransferId(Object transferId) {
        this.transferId = transferId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getRpItemId() {
        return rpItemId;
    }

    public void setRpItemId(Object rpItemId) {
        this.rpItemId = rpItemId;
    }

    public Object getRpBackId() {
        return rpBackId;
    }

    public void setRpBackId(Object rpBackId) {
        this.rpBackId = rpBackId;
    }

    public Object getWdId() {
        return wdId;
    }

    public void setWdId(Object wdId) {
        this.wdId = wdId;
    }

    public Object getWdBackId() {
        return wdBackId;
    }

    public void setWdBackId(Object wdBackId) {
        this.wdBackId = wdBackId;
    }

    public Object getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(Object guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public Object getMortagageId() {
        return mortagageId;
    }

    public void setMortagageId(Object mortagageId) {
        this.mortagageId = mortagageId;
    }

}
