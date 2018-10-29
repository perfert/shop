/**    
 * 文件名：WealthService.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.wallet;

import io.rong.messages.CmdMsgMessage;
import io.rong.service.MessageService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.util.DateTimeUtil;
import com.mas.common.util.DesUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.security.util.PasswordUtil;
import com.mas.security.util.verify.ApiUtil;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.MemberTicket;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.domain.entity.rp.RedPacketBack;
import com.mas.user.domain.entity.vo.RpPayVo;
import com.mas.user.domain.entity.vo.QrCodeVo;
import com.mas.user.domain.entity.vo.WalletPayVo;
import com.mas.user.domain.entity.wallet.Ofc;
import com.mas.user.domain.entity.wallet.Transfer;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.domain.entity.wallet.Withdraw;
import com.mas.user.domain.entity.wallet.WithdrawBack;
import com.mas.user.repository.dao.redpacket.RedPacketBackDao;
import com.mas.user.repository.dao.wallet.OfcDao;
import com.mas.user.repository.dao.wallet.SnDao;
import com.mas.user.repository.dao.wallet.TransferDao;
import com.mas.user.repository.dao.wallet.WealthDao;
import com.mas.user.repository.dao.wallet.WithdrawBackDao;
import com.mas.user.repository.query.wallet.OfcQueryDao;
import com.mas.user.repository.query.wallet.WealthQueryDao;
import com.mas.user.service.MemberService;
import com.mas.user.service.MemberTicketService;
import com.mas.user.service.image.ImageService;
import com.mas.user.service.redpacket.RedPacketService;
import com.mas.web.job.OrderJob;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.util.ServletUtil;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class WealthService extends BaseServiceImpl<Wealth> {

    /**
     * 获取鸥信红包支付信息
     * @param mid
     * @param string
     * @return
     */
    public WalletPayVo getPayInfo(String mid, String string) {
        if(StringUtils.isEmpty(mid))
            return null;
        WalletPayVo vo = new WalletPayVo();
        Member member = memberService.get(mid);
        if(StringUtils.isNotEmpty(member.getPayPassword()) && !member.getPayPassword().equals("0"))
            vo.setIsHasPwd(1);
        else
            vo.setIsHasPwd(0);
        List<WealthType> list = wealthTypeService.getAll();
        for(WealthType wealthType : list){
            wealthType.setBalance(getIfNotExistCreate(mid, wealthType.getId().toString(), false).getCash());
        }
        vo.setTypeList(list);
        return vo;
    }
    
    /**
     * 获取会员财富
     * @param memberId  会员ID.
     * @param wealthTypeId 财富类型
     * @param containPayPwd 是否包含会员支付密码
     * @return
     */
    public Wealth getIfNotExistCreate(String memberId, String wealthTypeId, boolean containPayPwd) {
        Wealth wealth = queryDao.getByMemberId(memberId,wealthTypeId,containPayPwd);
        String address = wealth.getAddress();
        if(StringUtils.isEmpty(address)){
            setAddress(wealth,wealthTypeId, memberId);
        }
        return wealth;
    }
    
    /**
     * 后台直充
     * @param id
     * @param cash
     */
    public void recharge(String adminId,String memberId,String wealthTypeId, BigDecimal cash) {
        Wealth wealth = getIfNotExistCreate(memberId,wealthTypeId,false);
        BigDecimal nowcash = wealth.getCash().add(cash);
        BigDecimal oldCash = wealth.getCash();
        
        boolean success = dao.updateCash(wealth.getId(),cash);
        int status = WealthRecord.Status.RECHARGE_SUCCESS.value();//充值成功
        Object recordId = recordAdmin(wealth.getId(),memberId,WealthRecord.Type.INCOME.value(),wealthTypeId,status,nowcash,oldCash,new BigDecimal(0),cash,
          Wealth.RP_PALTFORM,null,adminId,snDao.generate());
        if(success && null != recordId){
        }else
            throw new ServiceException("充值失败!");
    }
    
    /**
     * 充值
     * @param out_trade_no 订单号
     * @param address      地址
     * @param fromAddress  来自地址
     * @param payType      支付类型
     * @param total_fee    金额
     */
    public void rechargeOfc(String out_trade_no, String address,String fromAddress, String payType,String total_fee) {
        if(StringUtils.isEmpty(out_trade_no) ||StringUtils.isEmpty(out_trade_no) || StringUtils.isEmpty(total_fee))
            throw new ServiceException("LACK_PARAMS");
        Member member = getMemberByAddress(address,true);
        if(null == member)
            throw new ServiceException("USER_NOT_EXIST");
        //订单是否存在
        if(ofcQueryDao.exist(out_trade_no))
            throw new ServiceException("OUT_TRADE_NO_USED");
        Wealth wealth = getByAddress(address);
        if(null == wealth)
            throw new ServiceException("WEALTH_NOT_EXIST");
        
        if(!wealthTypeService.isEnable(wealth.getWealthType().toString()))
            throw new ServiceException("wealth.type.stop");
        BigDecimal cash = new BigDecimal(total_fee);
        Ofc ofc = new Ofc();
        ofc.setSn(out_trade_no);
        ofc.setAddress(address);
        ofc.setFromAddress(fromAddress);
        ofc.setPayType(payType);
        ofc.setTotalFee(cash);
        ofc.setHasPay(1);
        ofc.setWealthTypeId(wealth.getWealthType());
        ofc.setMemberId(member.getId());
        ofc.setState(1);
        Object ofcId = ofcDao.persistence(ofc);
        
        BigDecimal nowcash = wealth.getCash().add(cash);
        BigDecimal oldCash = wealth.getCash();
        boolean success = dao.updateCash(wealth.getId(),cash);
        int status = WealthRecord.Status.RECHARGE_SUCCESS.value();//充值成功
        Object recordId = recordOfc(wealth.getId(),member.getId().toString(),WealthRecord.Type.INCOME.value(),null,status,nowcash,oldCash,new BigDecimal(0),cash,
          Wealth.WALLET_OFC,null,ofcId,snDao.generate());
        if(success && null != recordId){
            //付款人,数量,时间
            try {
                JSONObject json = new JSONObject();
                WealthType wealthType = wealthTypeService.get(wealth.getWealthType());
                json.put("sendName", wealthType.getName());
                json.put("cash", total_fee);
                json.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,new Date()));
                messageService.publishCmdMsgMessageFriend("SYSTEM", "1", CmdMsgMessage.CMD_MESSAGE_WALLET_TYPE,json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else
            throw new ServiceException("RECHARGE_FAILED");
    }
    
    
    /**
     * 红包退款
     * @param rpId
     * @param memberId
     * @param backCash
     */
    public void rpBack(String rpId, String memberId,String payTypeId, BigDecimal backCash) {
        RedPacketBack back = new RedPacketBack();
        back.setState(1);
        back.setStatus(WealthRecord.Status.BACK_MONEY.value());
        back.setCash(backCash);
        back.setSn(snDao.generate());
        back.setRpId(rpId);
        back.setMemberId(memberId);
        Object rpBackId = backDao.persistence(back);
       
        Wealth wealth = getIfNotExistCreate(memberId,payTypeId,false);
        BigDecimal nowcash = wealth.getCash().add(backCash);
        BigDecimal oldCash = wealth.getCash();
        
        boolean wealthUpdate = dao.updateCash(wealth.getId(),backCash);
        boolean rpUpdate = redPacketService.updateBack(rpId);
        int status = WealthRecord.Status.BACK_MONEY.value();//退款成功
        
        Object recordId = recordRp(wealth.getId(),memberId,WealthRecord.Type.INCOME.value(),null,status,nowcash,oldCash,new BigDecimal(0),backCash,
                Wealth.RP_RETURN,null,rpId,null,rpBackId,back.getSn());
        if(wealthUpdate && rpUpdate && null != recordId){
        }else
            throw new ServiceException("退款失败!");
    }
    
    /**
     * 支付红包
     * @param mid 会员ID
     * @param wealthTypeId 支付类型
     * @param rpId 红包ID
     * @param payPassword
     */
    public RpPayVo payRp(String mid, String wealthTypeId,String rpId, String payPassword) {
        RpPayVo vo = new RpPayVo();
        Member member = memberService.get(mid);
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
                    memberService.updatePayFailure(mid,count,null);
                }
            }
            
            String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
            if (!PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) {
                memberService.updatePayFailure(mid,count + 1,new Date());
                vo.setCode(201);
                return vo;
            }
        }
        RedPacket rp = redPacketService.get(rpId);
        if(null == rp)
            throw new ServiceException("rp.error.noexist");
        if(rp.getHasPay() == 1)
            throw new ServiceException("rp.error.pay");
        Wealth wealth = getIfNotExistCreate(mid,wealthTypeId,false);
        if(rp.getCash().compareTo( new BigDecimal(0)) < 0)
            throw new ServiceException("wallet.money.lower");
        if(wealth.getCash().compareTo(rp.getCash()) < 0)
            throw new ServiceException("rp.error.money.enough");
        //扣钱
        BigDecimal operateCash = new BigDecimal("-" + rp.getCash().toString());
        BigDecimal nowcash = wealth.getCash().subtract(rp.getCash());
        BigDecimal oldCash = wealth.getCash();
        
        Date payDate = new Date();
        boolean flag1 = dao.updateCash(wealth.getId(),operateCash);
        boolean flag2 = redPacketService.updatePaySuccess(rpId,wealthTypeId,payDate);
        if(flag1 && flag2){
            int status = WealthRecord.Status.PAY_SUCCESS.value();//支付成功 
            Object recordId = recordRp(wealth.getId(),mid,WealthRecord.Type.EXPENSE.value(),wealthTypeId,status,nowcash,oldCash,rp.getCash(),new BigDecimal(0),
                Wealth.RP_SEND,null,rpId,null,null,rp.getSn());
            if(null != recordId){
                //内存保存红包
                RedPacket rpCopy = new RedPacket();
                rpCopy.setId(rp.getId());
                rpCopy.setCreateDate(payDate);
                OrderJob.increase(rpCopy);
                
                vo.setCode(200);
                vo.setWealthRecordId(recordId);
                return vo;
            }else{
                throw new ServiceException("rp.error.pay.failure");
            }
        }else{
            throw new ServiceException("rp.error.pay.failure");
        }
    }
    
    /**
     * 抢红包收入
     * @param openId 开红包会员ID
     * @param rpId   红包ID
     * @param rpItemId 红包子项ID
     * @param sn       编号
     * @param receiveCash 红包收入
     */
    public void openRp(String openId, String rpId, String rpItemId, String sn,BigDecimal receiveCash) {
        Member member = memberService.get(openId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        RedPacket redPacket = redPacketService.get(rpId);
        if(null == redPacket)
            throw new ServiceException("rp.error.noexist");
        Wealth wealth = getIfNotExistCreate(openId,redPacket.getPayType().toString(),false);
        BigDecimal nowcash = wealth.getCash().add(receiveCash);
        BigDecimal oldCash = wealth.getCash();
        
        boolean success = dao.updateCash(wealth.getId(),receiveCash);
        int status = WealthRecord.Status.SAVE_MONEY.value();//已存入零钱
        Object recordId = recordRp(wealth.getId(),openId,WealthRecord.Type.INCOME.value(),null,status,nowcash,oldCash,new BigDecimal(0),receiveCash,
          Wealth.RP_RECEIVE,null,rpId,rpItemId,null,sn);
        if(!success || null == recordId)
            throw new ServiceException("rp.error.receive");
    }
    
    /**
     * 获取接收二维码图
     * @param memberId 会员ID
     * @param wealthTypeId 财富类型
     * @return
     */
    public QrCodeVo getReceiveQrCode(String memberId,String wealthTypeId) {
        Member member = memberService.get(memberId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(!wealthTypeService.isEnable(wealthTypeId))
            throw new ServiceException("wealth.type.stop");
        Wealth wealth = getIfNotExistCreate(memberId,wealthTypeId,false);
        MemberTicket tick = ticketService.queryByWealthId(wealth.getId(),MemberTicket.Type.RECEIVE.value());
        
        String codePath = null;
        StringBuilder sb = new StringBuilder(JsonCtr.HOST + "download/ouxinchat"); 
        sb.append("?otype=" + MemberTicket.Type.RECEIVE.value());
        sb.append("&mid=" + member.getId());
        sb.append("&address="+ wealth.getAddress());
        String content = sb.toString();
        if(null == tick || !new File( ServletUtil.realPath() + tick.getImage() ).exists()){
            codePath = imageService.uploadReceiveQrCode(memberId,content,member.getAvatar());
            if(null == tick){
                tick = new MemberTicket();
                tick.setState(1);
                tick.setMemberId(memberId);
                tick.setWealthId(wealth.getId());
            }
            tick.setImage(codePath);
            tick.setContent(content);
            tick.setOtype(MemberTicket.Type.RECEIVE.value());
            ticketService.persistence(tick);
        }else
            codePath = tick.getImage();
        QrCodeVo vo = new QrCodeVo();
        vo.setQrcode(codePath);
        vo.setRealAddress(wealth.getAddress());
        return vo;
    }
   
    /**
     * 获取接收二维码图
     * @param memberId 会员ID
     * @param wealthTypeId 财富类型
     * @param num 数量
     * @return
     */
    public MemberTicket getReceiveQrCode(String memberId,String wealthTypeId, String num) {
        Member member = memberService.get(memberId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(!wealthTypeService.isEnable(wealthTypeId))
            throw new ServiceException("wealth.type.stop");
        if(StringUtils.isNotEmpty(num)){
            Double realNum = Double.parseDouble(num);
            if(realNum < 0)
                throw new ServiceException("wallet.error.qrcode.num");
        }else
            throw new ServiceException("wallet.error.qrcode.num");
            
        Wealth wealth = getIfNotExistCreate(memberId,wealthTypeId,false);
        
        StringBuilder sb = new StringBuilder(JsonCtr.HOST + "download/ouxinchat"); 
        sb.append("?otype=" + MemberTicket.Type.RECEIVE_NUM.value());
        sb.append("&mid=" + member.getId());
        sb.append("&address="+ wealth.getAddress());
        sb.append("&num=" + num);
        String content = sb.toString();
        
        String codePath = imageService.uploadReceiveQrCodeByNum(content,member.getAvatar());
        MemberTicket tick = new MemberTicket();
        tick.setState(1);
        tick.setMemberId(memberId);
        tick.setWealthId(wealth.getId());
        tick.setImage(codePath);
        tick.setContent(content);
        tick.setOtype(MemberTicket.Type.RECEIVE_NUM.value());
        Object result = ticketService.persistence(tick);
        if(null != result){
            tick.setId(result);
            return tick;
        }
        return null;
    }
    
    /**
     * 获取用户信息(只有昵称和头像数据)
     * @param address
     * @param containMemberId 是包含会员ID
     * @return
     */
    public Member getMemberByAddress(String address,boolean containMemberId) {
        if(StringUtils.isEmpty(address))
            return null;
        return queryDao.getMemberByAddress(address,containMemberId);
    }
    
    
    /**
     * 获取用户财富信息
     * @param address
     * @param containMemberId 是包含会员ID
     * @return
     */
    public Wealth getByAddress(String address) {
        if(StringUtils.isEmpty(address))
            return null;
        return queryDao.getByAddress(address);
    }
   
    
    /**
    * 转账
     * @param mid 用户ID
     * @param address 转账地址
     * @param payPassword 支付密码
     * @param num 数量
     * @param remark 留言
     * @return
     */
    public RpPayVo paySendMoney(String mid, String wealthTypeId,String address, String payPassword, String num, String remark) {
        RpPayVo vo = new RpPayVo();
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Wealth wealth = getIfNotExistCreate(mid,wealthTypeId,false);
        Member receive = getMemberByAddress(address,true);
        if(null == receive)
            throw new ServiceException("wallet.error.send.noexist");
        Wealth receiveWealth = getIfNotExistCreate(receive.getId().toString(),wealthTypeId,false);
        
        String pwd = member.getPayPassword();
        if(StringUtils.isNotEmpty(pwd) && !pwd.equals("0")){
            Integer count = member.getPayFailureCount() != null ? member.getPayFailureCount() : 0;
            if(count >= 3){
                Date lockDate = member.getPayLockDate();
                if(DateTimeUtil.addMinutes(lockDate, 60).after(new Date())){
                    throw new ServiceException("wallet.error.paypwd.lock");
                }else{
                    count = 0;
                    memberService.updatePayFailure(mid,count,null);
                }
            }
            String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
            if (!PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) {
                memberService.updatePayFailure(mid,count + 1,new Date());
                vo.setCode(201);
                return vo;
            }
        }
        
        BigDecimal payCash = new BigDecimal(num);
        if(payCash.compareTo( new BigDecimal(0)) < 0)
            throw new ServiceException("wallet.money.lower");
        if(wealth.getCash().compareTo(payCash) < 0)
            throw new ServiceException("rp.error.money.enough");
        Transfer transfer = new Transfer();
        transfer.setSn(snDao.generate());
        transfer.setCash(payCash);
        transfer.setHasPay(1);
        transfer.setReceiveType(0);
        transfer.setPayType(wealthTypeId);
        transfer.setMemberId(member.getId());
        transfer.setReceiveId(receive.getId());
        transfer.setState(1);
        Object tranferId = transferDao.persistence(transfer);
        //转账
        BigDecimal operateCash = new BigDecimal("-" + payCash.toString());
        BigDecimal nowcash = wealth.getCash().subtract(payCash);
        BigDecimal oldcash = wealth.getCash();
        boolean flag1 = dao.updateCash(wealth.getId(),operateCash);

        BigDecimal receiveNowcash = receiveWealth.getCash().add(payCash);
        BigDecimal receiveOldcash = receiveWealth.getCash();
        boolean flag2 = dao.updateCash(receiveWealth.getId(),payCash);
        if(flag1 && flag2){
            int status = WealthRecord.Status.TRANSFER_SUCCESS.value();//已转账
            Object recordId = recordTransfer(wealth.getId(),member.getId(),WealthRecord.Type.EXPENSE.value(),wealthTypeId,status,nowcash,oldcash,payCash,new BigDecimal(0),
                    Wealth.WALLET_SEND,null,tranferId,transfer.getSn());
            
            int statusReceive = WealthRecord.Status.RECEIVE_SUCCESS.value();//已收钱
            Object recordReceiveId = recordTransfer(receiveWealth.getId(),receive.getId(),WealthRecord.Type.INCOME.value(),null,statusReceive,receiveNowcash,receiveOldcash,new BigDecimal(0),payCash,
                    Wealth.WALLET_RECEIVE,null,tranferId,snDao.generate());
            if(null != recordId && null != recordReceiveId){
                vo.setCode(200);
                vo.setWealthRecordId(recordId);
                vo.setReceiveWealthRecordId(recordReceiveId);
                return vo;
            }else
                throw new ServiceException("wallet.error.send.failure");
        }else{
            throw new ServiceException("wallet.error.send.failure");
        }
    }
    
    /**
     * 提币
      * @param mid 用户ID
      * @param address 转账地址
      * @param payPassword 支付密码
      * @param num 数量
      * @param remark 留言
      * @return
      */
     public RpPayVo withdraw(String mid, String wealthTypeId,String address, String payPassword, String num) {
         if(StringUtils.isEmpty(mid) || StringUtils.isEmpty(mid) || StringUtils.isEmpty(num))
             throw new ServiceException("parameter.error");
         
         RpPayVo vo = new RpPayVo();
         Member member = memberService.get(mid);
         if(null == member)
             throw new ServiceException("user.error.noexist");
         Wealth wealth = getIfNotExistCreate(mid,wealthTypeId,false);
         
         String pwd = member.getPayPassword();
         if(StringUtils.isNotEmpty(pwd) && !pwd.equals("0")){
             Integer count = member.getPayFailureCount() != null ? member.getPayFailureCount() : 0;
             if(count >= 3){
                 Date lockDate = member.getPayLockDate();
                 if(DateTimeUtil.addMinutes(lockDate, 60).after(new Date())){
                     throw new ServiceException("wallet.error.paypwd.lock");
                 }else{
                     count = 0;
                     memberService.updatePayFailure(mid,count,null);
                 }
             }
             String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
             if (!PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) {
                 memberService.updatePayFailure(mid,count + 1,new Date());
                 vo.setCode(201);
                 return vo;
             }
         }
         
         BigDecimal payCash = new BigDecimal(num);
         if(payCash.compareTo( new BigDecimal(0)) < 0)
             throw new ServiceException("wallet.money.lower");
         if(wealth.getCash().compareTo(payCash) < 0)
             throw new ServiceException("rp.error.money.enough");
         Withdraw withDraw = new Withdraw();
         withDraw.setAddress(address);
         withDraw.setNum(payCash);
         withDraw.setStatus(Withdraw.Status.WITHDRAW_IN_HAND.value());//处理中
         withDraw.setState(1);
         withDraw.setSn(snDao.generate());
         withDraw.setWealthTypeId(wealthTypeId);
         withDraw.setMemberId(mid);
         Object wdId = wthdrawService.persistence(withDraw);
         
         //扣款
         BigDecimal operateCash = new BigDecimal("-" + payCash.toString());
         BigDecimal nowcash = wealth.getCash().subtract(payCash);
         BigDecimal oldcash = wealth.getCash();
         boolean flag1 = dao.updateCash(wealth.getId(),operateCash);
         if(flag1){
             int status = WealthRecord.Status.WITHDRAW_IN_HAND.value();//提币申请
             Object recordId = recordWithdraw(wealth.getId(),member.getId(),WealthRecord.Type.EXPENSE.value(),wealthTypeId,status,nowcash,oldcash,payCash,new BigDecimal(0),
                     Wealth.WALLET_WITHDRAW,null,wdId,null,withDraw.getSn());
             if(null != recordId){
                 vo.setCode(200);
                 vo.setWealthRecordId(recordId);
                 return vo;
             }else
                 throw new ServiceException("wallet.withdraw.error");
         }else{
             throw new ServiceException("wallet.withdraw.error");
         }
     }
     
     /**
      * 提币成功
      * @param id
      */
     public void handleSuccess(String id) {
         wthdrawService.updateStatus(Withdraw.Status.WITHDRAW_SUCCESS.value(), id);
         int status = WealthRecord.Status.WITHDRAW_SUCCESS.value();//提币申请
         boolean success = recordService.updateStatusByWithdrawId(status,id,new Date());
         System.out.println( success);
         if(!success)
             throw new ServiceException("wallet.withdraw.error");
     }

     public void handleFailed(String id) {
         Withdraw withdraw = wthdrawService.get(id);
         if(null == withdraw)
             throw new ServiceException("wallet.withdraw.error");
         if(withdraw.getStatus() != Withdraw.Status.WITHDRAW_IN_HAND.value())
             throw new ServiceException("wallet.withdraw.error");
         //提币失败
         boolean wdStatus = wthdrawService.updateStatus(Withdraw.Status.WITHDRAW__FAIL.value(), id);
         //提币记录失败
         int status = WealthRecord.Status.WITHDRAW__FAIL.value();
         boolean recordStatus = recordService.updateStatusByWithdrawId(status,id,new Date());
         
         WithdrawBack back = new WithdrawBack();
         back.setState(1);
         back.setStatus(WealthRecord.Status.BACK_MONEY.value());
         back.setCash(withdraw.getNum());
         back.setSn(snDao.generate());
         back.setWdId(id);
         back.setCash(withdraw.getNum());
         back.setMemberId(withdraw.getMemberId());
         Object rpBackId = withdrawBackDao.persistence(back);
         
         Wealth wealth = getIfNotExistCreate(withdraw.getMemberId().toString(),withdraw.getWealthTypeId().toString(),false);
         BigDecimal nowcash = wealth.getCash().add(withdraw.getNum());
         BigDecimal oldCash = wealth.getCash();
         //退款
         boolean wealthUpdate = dao.updateCash(wealth.getId(),withdraw.getNum());
         if(wdStatus && recordStatus && wealthUpdate){
             int rStatus = WealthRecord.Status.WITHDRAW__BACK.value();//退款成功
             Object recordId = recordWithdraw(wealth.getId(),withdraw.getMemberId(),WealthRecord.Type.INCOME.value(),withdraw.getWealthTypeId().toString(),rStatus,nowcash,oldCash,new BigDecimal(0),withdraw.getNum(),
                     Wealth.WALLET_WITHDRAW,null,id,rpBackId,back.getSn());
             if(null == recordId)
                 throw new ServiceException("wallet.withdraw.error");
         }else{
             throw new ServiceException("wallet.withdraw.error");
         }
     }
     
     /**
      * 订单支付
      * @param thirdOrderId
      * @param id
      * @param payCash
      * @return
      */
     public boolean payOrder(String thirdOrderId, Object wealthId, BigDecimal payCash) {
         BigDecimal operateCash = new BigDecimal("-" + payCash.toString());
         return dao.updateCash(wealthId, operateCash);
     }
     
     /**
      * 押金支付
      * @param mortagageId
      * @param id
      * @param payCash
      * @return
      */
     public boolean mortagagePay(Object mortagageId, Object wealthId, BigDecimal payCash) {
         BigDecimal operateCash = new BigDecimal("-" + payCash.toString());
         return dao.updateCash(wealthId, operateCash);
     }
     
     /**
      * 押金退款
      * @param mortagageId
      * @param wealthId
      * @param payCash
      * @return
      */
     public boolean mortagageBack(Object mortagageId, Object wealthId, BigDecimal payCash) {
         return dao.updateCash(wealthId, payCash);
     }
     
     /**
      * 订单确认
      * @param thirdOrderId
      * @param wealthId
      * @param payCash
      * @return
      */
     public boolean confirmOrder(String thirdOrderId, String wealthId, BigDecimal payCash) {
         return dao.updateCash(wealthId, payCash);
     }
     
     /**
      * 订单退款
      * @param thirdOrderId
      * @param id
      * @param payCash
      * @return
      */
     public boolean backOrder(String thirdOrderId, String wealthId, BigDecimal payCash) {
         return dao.updateCash(wealthId, payCash);
     }
     
     /**
      * 第三方付款
      * @param wealthId 钱ID
      * @param memberId 会员ID
      * @param type     收入类型
      * @param payType  支付方式
      * @param status   操作状态
      * @param nowCash  当前值
      * @param oldCash  以前值
      * @param expense  支出
      * @param income   收入
      * @param remark   标题
      * @param tips     备注
      * @param guaranteeId
      * @param sn
      * @return
      */
     public Object recordPayThirdOrder(Object wealthId, Object memberId, int type, String payType, int status,
             BigDecimal nowCash, BigDecimal oldCash, BigDecimal expense, BigDecimal income, String remark,
             String tips, Object guaranteeId, String sn) {
         WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                 6,payType,null,null,null,null,null,null,null,null,guaranteeId,null,wealthId,memberId);
         return recordService.persistence(record);
     }
     
    /**
     * 获取地址
     * @param wealth
     * @param memberId
     */
    private void setAddress(Wealth wealth,String wealthTypeId,String memberId){
        JSONObject jsonObject = new JSONObject();
        String token = "fe206d0cb198c3c270d4f0468c60162d";
        jsonObject.put("mid",memberId);
        jsonObject.put("mch_id","open.ouxichat.com");
        jsonObject.put("wealth_id",wealth.getId());
        ApiUtil.encode(token, jsonObject);
        
        WealthType wealthType = wealthTypeService.get(wealthTypeId);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(wealthType.getAddressUrl());
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        // 解决中文乱码问题
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response)throws ClientProtocolException, IOException {//
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        
        try {
            String responseBody = httpclient.execute(httpPost, responseHandler);
            JSONObject jObject = JSONObject.parseObject(responseBody);
            if(jObject.getString("return_code").equals("SUCCESS")){
                String address = jObject.getJSONObject("data").getString("address");
                if(StringUtils.isNotEmpty(address)){
                    wealth.setAddress(address);
                    dao.persistence(wealth);
                    return;
                }
            }
            throw new ServiceException("wallet.error.address");
        } catch (Exception e) {
            System.out.println("==加载地址失败=======" + e.getMessage());
            throw new ServiceException("wallet.error.address");
        } 
    }
    
    /**
     * 红包记录
     * @param wealthId 钱ID
     * @param memberId 会员ID
     * @param type     收入类型
     * @param payType  支付方式
     * @param status   操作状态
     * @param nowCash  当前值
     * @param oldCash  以前值
     * @param expense  支出
     * @param income   收入
     * @param remark   标题
     * @param tips     备注
     * @param rpId     红包ID
     * @param rpItemId 领取红包ID
     * @param returnId 退款ID
     * @param sn     订单号
     * @return
     */
    private Object recordRp(Object wealthId,Object memberId,int type,Object wealthTypeId,int status,BigDecimal nowCash,BigDecimal oldCash,BigDecimal expense,BigDecimal income,
            String remark,String tips,Object rpId,Object rpItemId,Object returnId,String sn){
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                0,wealthTypeId,rpId,rpItemId,returnId,null,null,null,null,null,null,null,wealthId,memberId);
        return recordService.persistence(record);
    }
    
    /**
     * 平台直充
     * @param wealthId 钱ID
     * @param memberId 会员ID
     * @param type     收入类型
     * @param payType  支付方式
     * @param status   操作状态
     * @param nowCash  当前值
     * @param oldCash  以前值
     * @param expense  支出
     * @param income   收入
     * @param remark   标题
     * @param tips     备注
     * @param adminId
     * @param sn
     * @return
     */
    private Object recordAdmin(Object wealthId,Object memberId,int type,Object payType,int status,BigDecimal nowCash,BigDecimal oldCash,BigDecimal expense,BigDecimal income,
            String remark,String tips,Object adminId,String sn){
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                1,null,null,null,null,adminId,null,null,null,null,null,null,wealthId,memberId);
        return recordService.persistence(record);
    }
    
    /**
     * 二维码收付款
     * @param wealthId 钱ID
     * @param memberId 会员ID
     * @param type     收入类型
     * @param payType  支付方式
     * @param status   操作状态
     * @param nowCash  当前值
     * @param oldCash  以前值
     * @param expense  支出
     * @param income   收入
     * @param remark   标题
     * @param tips     备注
     * @param transferId
     * @param sn
     * @return
     */
    private Object recordTransfer(Object wealthId,Object memberId,int type,Object payType,int status,BigDecimal nowCash,BigDecimal oldCash,BigDecimal expense,BigDecimal income,
            String remark,String tips,Object transferId,String sn){
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                2,null,null,null,null,null,null,transferId,null,null,null,null,wealthId,memberId);
        return recordService.persistence(record);
    }
    
    /**
     * OFC直充
     * @param wealthId 钱ID
     * @param memberId 会员ID
     * @param type     收入类型
     * @param payType  支付方式
     * @param status   操作状态
     * @param nowCash  当前值
     * @param oldCash  以前值
     * @param expense  支出
     * @param income   收入
     * @param remark   标题
     * @param tips     备注
     * @param ofcId
     * @param sn
     * @return
     */
    private Object recordOfc(Object wealthId,Object memberId,int type,Integer payType,int status,BigDecimal nowCash,BigDecimal oldCash,BigDecimal expense,BigDecimal income,
            String remark,String tips,Object ofcId,String sn){
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                3,null,null,null,null,null,ofcId,null,null,null,null,null,wealthId,memberId);
        return recordService.persistence(record);
    }
    
    /**
     * 提币
     * @param wealthId 钱ID
     * 
     * 
     * @param memberId 会员ID
     * @param type     收入类型
     * @param payType  支付方式
     * @param status   操作状态
     * @param nowCash  当前值
     * @param oldCash  以前值
     * @param expense  支出
     * @param income   收入
     * @param remark   标题
     * @param tips     备注
     * @param wdId
     * @param returnId
     * @param sn
     * @return
     */
    private Object recordWithdraw(Object wealthId,Object memberId,int type,String payType,int status,BigDecimal nowCash,BigDecimal oldCash,BigDecimal expense,BigDecimal income,
            String remark,String tips,Object wdId,Object returnId,String sn){
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                4,null,null,null,null,null,null,null,wdId,returnId,null,null,wealthId,memberId);
        return recordService.persistence(record);
    }
    
    @Override
    protected CrudDao<Wealth> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Wealth> queryDao() {
        return queryDao;
    }
   
    @Autowired private WealthDao dao;
    @Autowired private WealthQueryDao queryDao;
    
    @Autowired private TransferDao transferDao;
    @Autowired private WithdrawService wthdrawService;
    @Autowired private WithdrawBackDao withdrawBackDao;
    @Autowired private OfcDao ofcDao;
    @Autowired private OfcQueryDao ofcQueryDao;
    @Autowired private MemberService memberService;
    @Autowired private SnDao snDao;
    @Autowired private RedPacketBackDao backDao;
    @Autowired private RedPacketService redPacketService;
    @Autowired private MemberTicketService ticketService;
    @Autowired private ImageService imageService;
    @Autowired private WealthRecordService recordService;
    @Autowired private MessageService messageService;
    @Autowired private WealthTypeService wealthTypeService;
    
    public Object recordBackOrder(Object wealthId, Object memberId, int type, Object payType, int status,
            BigDecimal nowCash, BigDecimal oldCash, BigDecimal expense, BigDecimal income,
            String remark, String tips, Object guaranteeId, String sn) {
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                WealthRecord.OperateType.ORDER.value(),payType,null,null,null,null,null,null,null,null,guaranteeId,null,wealthId,memberId);
        return recordService.persistence(record);
    }

    public Object recordConfirmOrder(Object wealthId, Object memberId, int type, Object payType, int status,
            BigDecimal nowCash, BigDecimal oldCash, BigDecimal expense, BigDecimal income,
            String remark, String tips, Object guaranteeId, String sn) {
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                WealthRecord.OperateType.ORDER.value(),payType,null,null,null,null,null,null,null,null,guaranteeId,null,wealthId,memberId);
        return recordService.persistence(record);
    }

    public Object recordMortagageBack(Object wealthId, Object memberId, int type, String payType, int status,
            BigDecimal nowCash, BigDecimal oldCash, BigDecimal expense, BigDecimal income, String remark,
            String tips, Object mortagageId, String sn) {
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                WealthRecord.OperateType.MORTAGAGE.value(),payType,null,null,null,null,null,null,null,null,null,mortagageId,wealthId,memberId);
        return recordService.persistence(record);
    }
    
    public Object recordMortagagePay(Object wealthId, Object memberId, int type, String payType, int status,
            BigDecimal nowCash, BigDecimal oldCash, BigDecimal expense, BigDecimal income, String remark,
            String tips, Object mortagageId, String sn) {
        WealthRecord record = new WealthRecord(nowCash,oldCash,expense,income,status,remark,tips,sn,type,
                WealthRecord.OperateType.MORTAGAGE.value(),payType,null,null,null,null,null,null,null,null,null,mortagageId,wealthId,memberId);
        return recordService.persistence(record);
    }
}
