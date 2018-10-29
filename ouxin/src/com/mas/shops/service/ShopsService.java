/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.mas.common.orm.util.Page;
import com.mas.common.util.DateTimeUtil;
import com.mas.common.util.DesUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.security.util.PasswordUtil;
import com.mas.shops.domain.entity.Guarantee;
import com.mas.shops.domain.entity.Mortagage;
import com.mas.shops.domain.entity.Notify;
import com.mas.shops.domain.entity.Shops;
import com.mas.shops.domain.entity.ShopsAttention;
import com.mas.shops.domain.vo.MortagagePayVo;
import com.mas.shops.domain.vo.ThirdPayResultVo;
import com.mas.shops.domain.vo.ThirdPayVo;
import com.mas.shops.domain.vo.WealthTypeVo;
import com.mas.shops.repository.dao.ShopsDao;
import com.mas.shops.repository.query.ShopsQueryDao;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.dao.wallet.SnDao;
import com.mas.user.service.MemberService;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WealthTypeService;


/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ShopsService  extends BaseServiceImpl<Shops>{

    @Override
    protected CrudDao<Shops> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Shops> queryDao() {
        return queryDao;
    }
    
    /**
     * 创建第三方店铺(未处理退押金,又重新建店)
     * @param mid　会员id
     * @param shopsId 第三方店名id
     * @param title 标题
     * @param legalPerson 法人
     * @param cardNo 身份证
     * @param mobile 联系电话
     * @param address 地址
     * @param link 网址
     * @return
     */
    public Object createThirdShops(String mid, String shopsId, String title, String legalPerson, String cardNo,String mobile, String address, String link,String logo,String describe,String companyName) {
        if(StringUtils.isEmpty(mid) || StringUtils.isEmpty(shopsId) || StringUtils.isEmpty(title) || StringUtils.isEmpty(link))
            throw new ServiceException("参数错误");
        if(queryDao.isExist(mid))
            throw new ServiceException("已存在店铺,店名为:" +title );
        Shops shops = new Shops();
        shops.setTitle(title);
        shops.setLegalPerson(legalPerson);
        shops.setCardNo(cardNo);
        shops.setMobile(mobile);
        shops.setAddress(address);
        shops.setLink(link);
        shops.setLogo(logo);
        shops.setDetail(describe);
        shops.setName(companyName);
        shops.setMemberId(mid);
        shops.setThirdShopsId(shopsId);
        shops.setStoreStatus(1);
        shops.setAuthStatus(1);
        shops.setState(1);
        return dao.persistence(shops);
    }
    
    /**
     * 第三方扣押金
     * @param memberId
     * @param wealthTypeId
     * @param cash
     * @return
     */
    public MortagagePayVo mortagagePay(String memberId,String payPassword,String wealthTypeId,String cash) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(wealthTypeId) || StringUtils.isEmpty(cash))
            throw new ServiceException("parameter.error");
        else{
            MortagagePayVo vo = new MortagagePayVo();
            Member member = memberService.get(memberId);
            if(null == member)
                throw new ServiceException("user.error.noexist");
            String pwd = member.getPayPassword();
            if(StringUtils.isNotEmpty(pwd) && !pwd.equals("0")){
                Integer count = member.getPayFailureCount() != null ? member.getPayFailureCount() : 0;
                if(count >= 3){
                    Date lockDate = member.getPayLockDate();
                    if(DateTimeUtil.addMinutes(lockDate, 60).after(new Date())){
                        throw new ServiceException("wallet.error.paypwd.lock");
                    }else{
                        count = 0;
                        memberService.updatePayFailure(memberId,count,null);
                    }
                }
                String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
                if (!PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) {
                    memberService.updatePayFailure(memberId,count + 1,new Date());
                    vo.setCode(201);
                    return vo;
                }
            }
            //验证是否已支付
            Wealth wealth = wealthService.getIfNotExistCreate(memberId,wealthTypeId,false);
            BigDecimal payCash = new BigDecimal(cash);
            //扣钱
            if(payCash.compareTo( new BigDecimal(0)) < 0)
                throw new ServiceException("wallet.money.lower");
            if(wealth.getCash().compareTo(payCash) < 0)
                throw new ServiceException("rp.error.money.enough");
            //生成保证金(要不要编号)
            Mortagage bean = new Mortagage();
            bean.setMemberId(memberId);
            bean.setWealthTypeId(wealthTypeId);
            bean.setWealthId(wealth.getId());
            bean.setCash(payCash);
            bean.setStatus(Mortagage.Status.CREATE.value());
            Object mortagageId = mortagageService.persistence(bean);
            //扣款
            BigDecimal nowcash = wealth.getCash().subtract(payCash);
            BigDecimal oldcash = wealth.getCash();
            boolean flag1 = wealthService.mortagagePay(mortagageId,wealth.getId(),payCash);
            if(flag1){
                int status = WealthRecord.Status.MORTAGAGE_CREATE.value();//已转账
                Object recordId = wealthService.recordMortagagePay(wealth.getId(),memberId,WealthRecord.Type.EXPENSE.value(),wealthTypeId,
                        status,nowcash,oldcash,payCash,new BigDecimal(0), Wealth.MORTAGAGE_CREATE,null,mortagageId,snDao.generate());
                Object notifyId = createMortagageNotify(memberId, Notify.Type.DEPOSIT.getType());
                if(null != recordId && null != notifyId){
                    vo.setCode(200);
                    vo.setRecordId(recordId);
                    vo.setMortagageId(mortagageId);
                    returnBackService.notifyMortagage(notifyId,memberId,Notify.Type.DEPOSIT.getType());
                    return vo;
                }else
                    throw new ServiceException("wallet.error.send.failure");
            }else{
                throw new ServiceException("wallet.error.send.failure");
            }
        }
    }

    /**
     * 退还押金
     * 
     * @param mid
     * @return
     */
    public MortagagePayVo mortagageBack(String memberId) {
        if(StringUtils.isEmpty(memberId))
            throw new ServiceException("parameter.error");
        Mortagage bean = mortagageService.getNoBackMortagage(memberId);
        if(null == bean)
            throw new ServiceException("parameter.error");
        //验证是否已支付
        Wealth wealth = wealthService.getIfNotExistCreate(memberId,bean.getWealthTypeId().toString(),false);
        //扣款
        BigDecimal nowcash = wealth.getCash().add(bean.getCash());
        BigDecimal oldcash = wealth.getCash();
        //更改状态
        boolean update = mortagageService.updateStatus(bean.getId(),Mortagage.Status.RETURN.value());
        boolean flag1 = wealthService.mortagageBack(bean.getId(),wealth.getId(),bean.getCash());
        if(flag1 && update){
            MortagagePayVo vo = new MortagagePayVo();
            int statusReceive = WealthRecord.Status.Mortagage__RETURN.value();
            Object recordId = wealthService.recordMortagageBack(wealth.getId(),wealth.getMemberId(),WealthRecord.Type.INCOME.value(),null,statusReceive,
                    nowcash,oldcash,new BigDecimal(0),bean.getCash(),Wealth.MORTAGAGE_BACK,null,bean.getId(),snDao.generate());
            Object notifyId = createMortagageNotify(memberId, Notify.Type.DEPOSIT_REFUND.getType());
            if(null != recordId && null != notifyId){
                vo.setCode(200);
                vo.setRecordId(recordId);
                vo.setMortagageId(bean.getId());
                returnBackService.notifyMortagage(notifyId,memberId,Notify.Type.DEPOSIT_REFUND.getType());
                return vo;
            }else
                throw new ServiceException("wallet.error.send.failure");
        }else{
            throw new ServiceException("wallet.error.send.failure");
        }
    }
    
    /**
     * 获取店铺信息包含证件信息
     * @param mid 会员id
     * @return
     */
    public Shops getByMemberId(String mid,boolean containCertificate) {
        if(StringUtils.isNotEmpty(mid))
            return queryDao.getByMemberId(mid,containCertificate);
        else
            return null;
    }

    /**
     * 审核店铺
     * @param id 店铺id
     * @param verify 审核状态(0:不通过,1:通过)
     * @param reason
     */
    public void verify(Object id, Integer verify, String reason) {
        if(null== id || null==verify || verify < 1 || verify>2)
            throw new ServiceException("参数错误");
        dao.updateAuthStatus(id,verify,reason);
    }
   
    /**
     * 获取第三方支付信息
     * @param mid
     * @param rateList
     * @return
     */
    public ThirdPayVo getPayInfo(String mid,List<WealthTypeVo> rateList) {
        if(StringUtils.isEmpty(mid) ||  null == rateList ||  rateList.size() < 0)
            throw new ServiceException("parameter.error");
        ThirdPayVo vo = new ThirdPayVo();
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(member.getPayPassword()) && !member.getPayPassword().equals("0"))
            vo.setIsHasPwd(1);
        else
            vo.setIsHasPwd(0);
        List<WealthType> typeList = new ArrayList<WealthType>();
        for (int i = 0; i < rateList.size(); i++) {
            WealthTypeVo wealthTypeVo = rateList.get(i);
            WealthType type = wealthTypeService.getWealth(mid,wealthTypeVo.getId());
            if(null == type || null == wealthTypeVo.getValue() || wealthTypeVo.getValue().compareTo(new BigDecimal(0)) < 0)
                throw new ServiceException("parameter.error");
            type.setCost(wealthTypeVo.getValue());
            typeList.add(type);
        }
        vo.setTypeList(typeList);
        return vo;
    }
    
    /**
     * 创建订单
     * @param memberId 支付会员id
     * @param wealthTypeId 支付类型id
     * @param payPassword 支付密码
     * @param thirdOrderId 第三方订单id
     * @param shopsId 店铺id
     * @param cash 总额
     * @param name 商品名称
     * @param sn 商品编号
     * @return
     */
    public ThirdPayResultVo createOrder(String memberId,String wealthTypeId,String payPassword, String thirdOrderId,String shopsId, String cash,String name,String sn) {  
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(wealthTypeId) || StringUtils.isEmpty(cash)|| StringUtils.isEmpty(name))
            throw new ServiceException("parameter.error");
        if(queryDao.checkOrder(memberId,shopsId))
            throw new ServiceException("third.buy.error");
        ThirdPayResultVo vo = new ThirdPayResultVo();
        Member member = memberService.get(memberId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        String pwd = member.getPayPassword();
        if(StringUtils.isNotEmpty(pwd) && !pwd.equals("0")){
            Integer count = member.getPayFailureCount() != null ? member.getPayFailureCount() : 0;
            if(count >= 3){
                Date lockDate = member.getPayLockDate();
                if(DateTimeUtil.addMinutes(lockDate, 60).after(new Date())){
                    throw new ServiceException("wallet.error.paypwd.lock");
                }else{
                    count = 0;
                    memberService.updatePayFailure(memberId,count,null);
                }
            }
            
            String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
            if (!PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) {
                memberService.updatePayFailure(memberId,count + 1,new Date());
                vo.setCode(201);
                return vo;
            }
        }
        //验证是否已支付
        Wealth wealth = wealthService.getIfNotExistCreate(memberId,wealthTypeId,false);
        BigDecimal payCash = new BigDecimal(cash);
        //扣钱
        if(payCash.compareTo( new BigDecimal(0)) < 0)
            throw new ServiceException("wallet.money.lower");
        if(wealth.getCash().compareTo(payCash) < 0)
            throw new ServiceException("rp.error.money.enough");
        //生成保证金
        Guarantee bean = new Guarantee();
        bean.setMemberId(memberId);
        bean.setWealthTypeId(wealthTypeId);
        bean.setWealthId(wealth.getId());
        bean.setShopsId(shopsId);
        bean.setOrderId(thirdOrderId);
        bean.setCash(payCash);
        bean.setName(name);
        bean.setSn(sn);
        bean.setOrderState(Guarantee.OrderState.CREATE.value());
        bean.setState(1);
        Object guaranteeId = guaranteeService.persistence(bean);
        //扣款
        BigDecimal nowcash = wealth.getCash().subtract(payCash);
        BigDecimal oldcash = wealth.getCash();
        boolean flag1 = wealthService.payOrder(thirdOrderId,wealth.getId(),payCash);
        if(flag1){
            int status = WealthRecord.Status.TRANSFER_SUCCESS.value();//已转账
            Object recordId = wealthService.recordPayThirdOrder(wealth.getId(),member.getId(),WealthRecord.Type.EXPENSE.value(),wealthTypeId,
                    status,nowcash,oldcash,payCash,new BigDecimal(0),name,null,guaranteeId,sn);
            Object notifyId = createNotify(thirdOrderId,Notify.Type.ORDER_PAY.getType());
            if(null != recordId && null != notifyId){
                vo.setCode(200);
                vo.setRecordId(recordId);
                vo.setGuaranteeId(guaranteeId);
                returnBackService.notifyCreateOrder(notifyId,thirdOrderId,Notify.Type.ORDER_PAY.getType());
                return vo;
            }else
                throw new ServiceException("wallet.error.send.failure");
        }else{
            throw new ServiceException("wallet.error.send.failure");
        }
    }
    
    /**
     * 确认订单
     * @param thirdOrderId
     * @return
     */
    public Object confirmOrder(String thirdOrderId) {
        if(StringUtils.isEmpty(thirdOrderId) )
            throw new ServiceException("parameter.error");
        Guarantee bean = guaranteeService.getByOrderId(thirdOrderId);
        Shops shops = get(bean.getShopsId());
        //转给店铺
        Wealth receiveWealth = wealthService.getIfNotExistCreate(shops.getMemberId().toString(),bean.getWealthTypeId().toString(),false);
        BigDecimal receiveNowcash = receiveWealth.getCash().add(bean.getCash());
        BigDecimal receiveOldcash = receiveWealth.getCash();
        //更改状态
        boolean update = guaranteeService.updateStatus(bean.getId(),Guarantee.OrderState.CONFIRM.value());
        boolean flag2 = wealthService.confirmOrder(bean.getOrderId().toString(),receiveWealth.getId().toString(),bean.getCash());
        Object notifyId = createNotify(thirdOrderId,Notify.Type.TRADE_SUCCESS.getType());
        if(flag2 && update && null != notifyId){
            int statusReceive = WealthRecord.Status.ORDER__CONFIRM.value();
            Object recordId = wealthService.recordConfirmOrder(receiveWealth.getId(),receiveWealth.getMemberId(),WealthRecord.Type.INCOME.value(),bean.getWealthTypeId(),statusReceive,receiveNowcash,
                    receiveOldcash,new BigDecimal(0),bean.getCash(),Wealth.ORDER_CONFIRM,null,bean.getId(),snDao.generate());
            returnBackService.notifyCreateOrder(notifyId,thirdOrderId,Notify.Type.TRADE_SUCCESS.getType());
            return recordId;
        }else{
            throw new ServiceException("确认失败");
        }
    }
    
    /**
     * 订单退款
     * @param thirdOrderId
     * @return
     */
    public Object backOrder(String thirdOrderId) {
        if(StringUtils.isEmpty(thirdOrderId) )
            throw new ServiceException("parameter.error");
        Guarantee bean = guaranteeService.getByOrderId(thirdOrderId);
        //转给购买用户
        Wealth receiveWealth = wealthService.getIfNotExistCreate(bean.getMemberId().toString(),bean.getWealthTypeId().toString(),false);
        BigDecimal receiveNowcash = receiveWealth.getCash().add(bean.getCash());
        BigDecimal receiveOldcash = receiveWealth.getCash();
        //更改状态
        boolean update = guaranteeService.updateStatus(bean.getId(),Guarantee.OrderState.RETURN.value());
        boolean flag2 = wealthService.backOrder(bean.getOrderId().toString(),receiveWealth.getId().toString(),bean.getCash());
        Object notifyId = createNotify(thirdOrderId,Notify.Type.ORDER_REFUND.getType());
        if(flag2 && update){
            int statusReceive = WealthRecord.Status.ORDER__RETURN.value();
            Object recordId = wealthService.recordBackOrder(receiveWealth.getId(),receiveWealth.getMemberId(),WealthRecord.Type.INCOME.value(),bean.getWealthTypeId(),statusReceive,
                    receiveNowcash,receiveOldcash,new BigDecimal(0),bean.getCash(),Wealth.ORDER_RETURN,null,bean.getId(),snDao.generate());
            returnBackService.notifyCreateOrder(notifyId,thirdOrderId,Notify.Type.ORDER_REFUND.getType());
            return recordId;
        }else{
            throw new ServiceException("确认失败");
        }
    }
    
    /**
     * 查询店铺
     * @param query 查询的key
     * @return
     */
    public void queryPage(Page page, String query) {
        if(StringUtils.isEmpty(query) )
            throw new ServiceException("parameter.error");
        queryDao.queryPage(page,query);
    }
    
    /**
     * 查询关注的店
     * @param mid
     * @return
     */
    public List<ShopsAttention> queryAttention(String mid) {
        if(StringUtils.isEmpty(mid) )
            throw new ServiceException("parameter.error");
        return shopsAttentionService.queryAttention(mid);
    }
    
    
    /**
     *  获取店铺信息
     * @param memberId
     * @param shopsId
     * @return
     */
    public Shops get(String memberId, String shopsId) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(shopsId) )
            throw new ServiceException("parameter.error");
        if(shopsAttentionService.exist(memberId,shopsId))
            return queryDao.get(memberId, shopsId);
        else{
            Shops shops = dao.get(shopsId);
            shops.setAttention(0);
            return shops;
        }
    }
    
    /**
     * 修改关注
     * @param memberId 会员ID
     * @param shopsId 店铺ID
     * @param attention
     * @return
     */
    public boolean setAttention(String memberId,String shopsId,boolean attention) {
        if(attention){
            if(!shopsAttentionService.exist(memberId,shopsId)){
                Shops shops = dao.get(shopsId);
                if(null == shops)
                    throw new ServiceException("parameter.error");
                Object id = shopsAttentionService.save(memberId,shopsId,dao.get(shopsId).getTitle(),shops.getLogo(),shops.getDetail(),shops.getLink(),ShopsAttention.LOOK);
                return id != null ? true : false;
            }else{
                return shopsAttentionService.updateAttention(memberId,shopsId,ShopsAttention.LOOK);
            }
        }else{
            return shopsAttentionService.updateAttention(memberId,shopsId,ShopsAttention.NO_LOOK);
        }
    }
    
    private Object createNotify(String thirdOrderId,int type){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", thirdOrderId);
        jsonObject.put("code", "200");
        jsonObject.put("type", type+"");
        return notifyService.insert(thirdOrderId,type,Notify.Status.CREATE.getStatus(),jsonObject.toJSONString());
    }
   
    private Object createMortagageNotify(String memberId,int type){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", memberId);
        jsonObject.put("code", "200");
        jsonObject.put("type", type+"");
        return notifyService.insert(memberId,type,Notify.Status.CREATE.getStatus(),jsonObject.toJSONString());
    }
    
    @Autowired
    private ShopsDao dao;
    @Autowired
    private ShopsQueryDao queryDao;
    @Autowired
    private GuaranteeService guaranteeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WealthService wealthService;
    @Autowired
    private WealthTypeService wealthTypeService;
    @Autowired
    private MortagageService mortagageService;
    @Autowired
    private ShopsAttentionService shopsAttentionService;
    
    @Autowired private SnDao snDao;
    @Autowired private NotifyService notifyService;
    @Autowired private ReturnBackService returnBackService;
    


    
    
    
}