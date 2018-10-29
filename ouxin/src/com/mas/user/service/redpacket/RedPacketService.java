/**    
 * 文件名：RedPacketService.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.redpacket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.orm.util.Page;
import com.mas.common.util.DateTimeUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.domain.entity.rp.RedPacketConfig;
import com.mas.user.domain.entity.rp.RedPacketItem;
import com.mas.user.domain.entity.rp.RedPacketNum;
import com.mas.user.domain.entity.vo.CreateRedPacketVo;
import com.mas.user.domain.entity.vo.RpInfoModel;
import com.mas.user.domain.entity.vo.RpItemModel;
import com.mas.user.domain.entity.vo.StatisSendVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.dao.redpacket.RedPacketDao;
import com.mas.user.repository.dao.redpacket.RedPacketNumDao;
import com.mas.user.repository.dao.wallet.SnDao;
import com.mas.user.repository.query.redpacket.RedPacketNumQueryDao;
import com.mas.user.repository.query.redpacket.RedPacketQueryDao;
import com.mas.user.service.MemberService;
import com.mas.user.service.chat.GroupMemberService;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WealthTypeService;
import com.mas.web.util.SpiltRedPacketUtil;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class RedPacketService extends BaseServiceImpl<RedPacket> {
    
    private Map<String, Long> weak = new WeakHashMap<String, Long>();
    //private Map<String, ConcurrentLinkedQueue<BigDecimal>> cache = new WeakHashMap<String, ConcurrentLinkedQueue<BigDecimal>>();
    
    /**
     * 统计发红包
     * @param memberId
     * @return
     */
    public StatisSendVo statisticsSend(String memberId,String wealthTypeId) {
        if(!StringUtils.isNotEmpty(memberId))
            throw new ServiceException("para error");
        return queryDao.statisticsSend(memberId,wealthTypeId);
    }
    
    /**
     * @param mid
     * @return
     */
    public void queryPage(String memberId, Page page,String wealthTypeId) {
        if(!StringUtils.isNotEmpty(memberId))
            throw new ServiceException("para error");
        queryDao.queryPage(memberId,page,wealthTypeId);
    }
    
    
    public RedPacket getRedPacketAndUserInfo(Object id){
        return queryDao.getRedPacketAndUserInfo(id);
    }
    
    /**
     * 开单个红包
     * @param rpId
     */
    public RpInfoModel openSingleRedPacket(String rpId,String openId) {
        RedPacket packet = queryDao.getRedPacketAndUserInfo(rpId);
        if(null == packet)
            throw new ServiceException("rp.error.noexist");
        if(0 == packet.getHasPay())
            throw new ServiceException("rp.error.pay.no");
        Member openMember = memberService.get(openId);
        if(null == openMember)
            throw new ServiceException("user.error.noexist");
        if(packet.getReceiveType() != RedPacket.ReceiveType.SINGLE.value())
            throw new ServiceException("error.rp.open.interface");
        if(!openId.equals(packet.getReceiveId().toString()))
            throw new ServiceException("error.rp.open.auth");
        
        RpInfoModel mode = new RpInfoModel();
        int status = -1;
        if(DateTimeUtil.addHours(packet.getCreateDate(), 24).before(new Date()) || (null != packet.getHasBack() && packet.getHasBack() ==1)){
            status = 2;
            if(itemService.isExist(rpId,openId))
                status = 4;
        }else{
            //到这一步都表示已领取
            status = 4;
            RedPacketItem item = null;
            if(packet.getReceiveNum() == 0){
                BigDecimal receiveCash = packet.getCash();
                packet.setReceiveNum(1);
                packet.setReceiveCash(receiveCash);
                dao.persistence(packet);
                
                item = new RedPacketItem();
                item.setType(packet.getType());
                item.setIsLuck(0);//单人红包
                item.setCash(receiveCash);
                item.setMemberId(openId);
                item.setRpId(rpId);
                item.setWealthTypeId(packet.getPayType());
                item.setState(1);
                item.setSn(snDao.generate());
                Object rpItemId = itemService.persistence(item);
                //记录
                wealthService.openRp(openId,rpId,rpItemId.toString(),item.getSn(),receiveCash);
            }else{
                //已领取
                item = itemService.get(rpId,openId);
            }
            RpItemModel itemModel = new RpItemModel();
            itemModel.setUserId(item.getMemberId().toString());
            itemModel.setNickname(openMember.getNickName());
            itemModel.setRpId(item.getRpId().toString());
            itemModel.setCash(item.getCash().toString());
            itemModel.setType(item.getType());
            itemModel.setTime(DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,item.getCreateDate()));
            itemModel.setIsLuck(0); //手气最佳
            mode.getItemList().add(itemModel);
        }
        mode.setStatus(status);
        mode.setType(packet.getType());
        mode.setIsSelf(0);
        mode.setHasLeft(0);
        //因为单人红包
        mode.setTotalMoney(packet.getCash().toString());
        mode.setRecTotalMoney(packet.getCash().toString()); 
        mode.setTotal(packet.getNum());
        mode.setReceTotal(packet.getReceiveNum());
        mode.setUsername(packet.getNickName());
        mode.setAvatar(packet.getAvatar());
        mode.setContent(packet.getRemark());
        return mode;
    }
    
    /**
     * 开多人红包[只负责开红包,不负责展示信息]
     * @param rpId
     */
    public RpInfoModel openGroupRedPacket(String rpId,String openId) {
        Long value = weak.get(rpId);
        if(null == value){
            value = Long.parseLong(rpId);
            weak.put(rpId, value);
        }
        synchronized(value){
            RedPacket packet = queryDao.getRedPacketAndUserInfo(rpId);
            if(null == packet)
                throw new ServiceException("rp.error.noexist");
            if(0 == packet.getHasPay())
                throw new ServiceException("rp.error.pay.no");
            Member openMember = memberService.get(openId);
            if(null == openMember)
                throw new ServiceException("user.error.noexist");
            if(packet.getReceiveType() != RedPacket.ReceiveType.MANY.value())
                throw new ServiceException("error.rp.open.interface");
            if(!groupMemberService.exist(openId,packet.getGroupId().toString()))
                throw new ServiceException("error.rp.open.auth");
            
            RpInfoModel mode = new RpInfoModel();
            int status = -1;
            if(DateTimeUtil.addHours(packet.getCreateDate(), 24).before(new Date()) || (null != packet.getHasBack() && packet.getHasBack() ==1)){
                status = 2;
                if(itemService.isExist(rpId,openId))
                    status = 4;
            }else{
                //是否领取完
                if(packet.getReceiveNum() >= packet.getNum()){
                    status = 3;
                    if(itemService.isExist(rpId,openId))
                        status = 4;
                }else{
                    status = 4;
                    if(!itemService.isExist(rpId,openId)){
                        if(packet.getType() == RedPacket.Type.LUCK.value()){
                            RedPacketNum redPacketNum = redPacketNumQueryDao.getNotValidAndAsc(rpId);
                            redPacketNum.setValid(1);//失效
                            Object redPacketNumId = redPacketNumDao.persistence(redPacketNum);
                            BigDecimal receiveCash = redPacketNum.getCash().add(packet.getReceiveCash());
                            Integer receiveNum = packet.getReceiveNum() + 1;
                            packet.setReceiveNum(receiveNum);
                            packet.setReceiveCash(receiveCash);
                            BigDecimal max = packet.getCash();
                            //因为锁了红包ID,可更新
                            dao.updateRePacket(rpId,receiveNum,receiveCash,max);
                            int luck = 0;
                            //最后一个红包
                            if(packet.getNum() == packet.getReceiveNum()){
                                if(redPacketNum.getIsLuck() == 1){
                                    luck = 1;
                                }
                            }
                            RedPacketItem item = new RedPacketItem();
                            item.setType(packet.getType());
                            item.setIsLuck(luck);
                            item.setCash(redPacketNum.getCash());
                            item.setMemberId(openId);
                            item.setRpId(rpId);
                            item.setWealthTypeId(packet.getPayType());
                            item.setRpNumId(redPacketNumId);
                            item.setSn(snDao.generate());
                            item.setState(1);
                            Object rpItemId = itemService.persistence(item);
                            //记录
                            wealthService.openRp(openId,rpId,rpItemId.toString(),item.getSn(),redPacketNum.getCash());
                            if(packet.getNum() == packet.getReceiveNum()){
                                itemService.updateLuck(rpId);
                            }
                        }else{
                            BigDecimal single = packet.getCash().divide(new BigDecimal(packet.getNum()));
                            BigDecimal receiveCash = single.add(packet.getReceiveCash());
                            Integer receiveNum = packet.getReceiveNum() + 1;
                            //因为锁了红包ID,可更新
                            packet.setReceiveNum(receiveNum);
                            packet.setReceiveCash(receiveCash);
                            dao.updateRePacket(rpId,receiveNum,receiveCash,packet.getCash());
                            
                            RedPacketItem item = new RedPacketItem();
                            item.setType(packet.getType());
                            //普通红包
                            item.setIsLuck(0);
                            item.setCash(single);
                            item.setMemberId(openId);
                            item.setRpId(rpId);
                            item.setSn(snDao.generate());
                            item.setState(1);
                            Object rpItemId = itemService.persistence(item);
                            wealthService.openRp(openId,rpId,rpItemId.toString(),item.getSn(),single);
                        }
                    }
                }
            }
            mode.setStatus(status);
            mode.setType(packet.getType());
            mode.setIsSelf(1);
            mode.setHasLeft(packet.getNum() != packet.getReceiveNum() ? 1 : 0);//是否有剩下
            mode.setTotalMoney(packet.getCash().toString());
            mode.setRecTotalMoney(packet.getReceiveCash().toString()); 
            mode.setTotal(packet.getNum());
            mode.setReceTotal(packet.getReceiveNum());
            mode.setUsername(packet.getNickName());
            mode.setAvatar(packet.getAvatar());
            mode.setContent(packet.getRemark());
            return mode;
        }
    }
    
    public static void main(String[] args) {
        /*Long value = 1L;
        Long value2 = 1L;
        System.out.println(value.equals(value2));
        String value3 = new String("1asd");
        String value4= new String("1asd");
        System.out.println(value3.equals(value4));*/
        
        List<BigDecimal> list = SpiltRedPacketUtil.mathList(new BigDecimal(0.1), 10);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
    
    /**
     * 创建单聊红包
     * @param mid 发送者ID
     * @param receiveId 接收者ID
     * @param money 数量
     * @param summary 祝福语
     * @param envelopeName 红包标签名
     * @return
     */
    public CreateRedPacketVo createSingle(String mid, String receiveId, BigDecimal money, String summary, String envelopeName) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Member receive = memberService.get(receiveId);
        if(null == receive)
            throw new ServiceException("user.error.noexist");
        RedPacketConfig config = configService.getDefault();
        if(null == config)
            throw new ServiceException("rp.error.config");
        if(money.compareTo(config.getMax()) > 0){
            throw new ServiceException("超过最大红包金额" + config.getMax() +  "!");
        }
        if(money.compareTo(config.getMin()) < 0){
            throw new ServiceException("低于最低红包金额" + config.getMin() + "!");
        }
        
        RedPacket rp = new RedPacket();
        rp.setType(RedPacket.Type.SIMPLE.value());
        rp.setReceiveType(RedPacket.ReceiveType.SINGLE.value());
        //是否支付
        rp.setHasPay(0);
        rp.setHasBack(0);
        rp.setCash(money);
        rp.setReceiveCash(new BigDecimal(0));
        rp.setNum(1);
        rp.setReceiveNum(0);
        rp.setRemark(summary);
        rp.setMemberId(mid);
        rp.setReceiveId(receiveId);
        rp.setState(1);
        rp.setSn(snDao.generate());
        CreateRedPacketVo vo = new CreateRedPacketVo();
        vo.setRpId(dao.persistence(rp).toString());
        if(StringUtils.isNotEmpty(member.getPayPassword()) && !member.getPayPassword().equals("0"))
            vo.setIsHasPwd(1);
        else
            vo.setIsHasPwd(0);
        
        List<WealthType> list = wealthTypeService.getAll();
        for(WealthType wealthType : list){
            wealthType.setBalance(wealthService.getIfNotExistCreate(mid, wealthType.getId().toString(), false).getCash());
        }
        vo.setTypeList(list);
        return vo;
    }
    
    /**
     * 创建多人红包
     * @param mid 发送者ID
     * @param groupId 群ID
     * @param redPacketType 红包类型
     * @param number 分为几个红包
     * @param money 总数量
     * @param summary 祝福语
     * @param envelopeName 红包标签名
     * @return
     */
    public CreateRedPacketVo createGroup(String mid, String groupId, int redPacketType, int number, BigDecimal money,String summary, String envelopeName) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(!groupMemberService.exist(mid,groupId))
            throw new ServiceException("group.error.noexist");
        RedPacketConfig config = configService.getDefault();
        if(null == config)
            throw new ServiceException("rp.error.config");
        
        BigDecimal total = money;
        if(redPacketType == RedPacket.Type.SIMPLE.value()){
            //最大
            BigDecimal max = money.multiply(new BigDecimal(number));
            if(max.compareTo(config.getMax()) > 0)
                throw new ServiceException("超过最大红包金额" + config.getMax() +  "!");
            //最小
            BigDecimal min = money;
            if(min.compareTo(config.getMin()) < 0)
                throw new ServiceException("低于最低红包金额" + config.getMin() + "!");
            total = max;
        }else if(redPacketType == RedPacket.Type.LUCK.value()){
            //最大
            if(money.compareTo(config.getMax()) > 0)
                throw new ServiceException("超过最大红包金额" + config.getMax() +  "!");
            //最小
            BigDecimal min = config.getMin().multiply(new BigDecimal(number));
            if(money.compareTo(min) < 0)
                throw new ServiceException("单个红包不可低于" + config.getMin() + "!");
        }else
            throw new ServiceException("rp.error.type");
        
        RedPacket rp = new RedPacket();
        if(redPacketType != RedPacket.Type.SIMPLE.value())
            rp.setType(RedPacket.Type.LUCK.value());
        else
            rp.setType(RedPacket.Type.SIMPLE.value());
        rp.setReceiveType(RedPacket.ReceiveType.MANY.value());
        rp.setCash(total);//总金额
        rp.setReceiveCash(new BigDecimal(0));
        rp.setNum(number);
        rp.setReceiveNum(0);
        rp.setRemark(summary);
        rp.setMemberId(mid);
        rp.setGroupId(groupId);
        rp.setHasPay(0);
        rp.setHasBack(0);
        rp.setState(1);
        rp.setSn(snDao.generate());
        Object result = dao.persistence(rp);
        
        String rpId = result.toString();
        if(redPacketType == RedPacket.Type.LUCK.value()){
            //ConcurrentLinkedQueue<BigDecimal> list = SpiltRedPacketUtil.math(money, number);
            //cache.put(rpId,list);
            List<BigDecimal> list = com.mas.web.util.RedPacketUtils.getAllHotPacket(money.doubleValue(), number, 3d, 1d);
            //List<BigDecimal> list = SpiltRedPacketUtil.mathList(money, number);
            if(null == list || list.size() < 0)
                throw new ServiceException("rp.create.error");
            List<RedPacketNum> numList = new ArrayList<RedPacketNum>();
            int max = 0;
            BigDecimal big = null;
            for (int i = 0; i < list.size(); i++) {
                RedPacketNum redPacketNum = new RedPacketNum();
                redPacketNum.setCash(list.get(i));
                redPacketNum.setPriority(i + 1);
                redPacketNum.setValid(0);
                redPacketNum.setIsLuck(0);
                redPacketNum.setState(1);
                redPacketNum.setRpId(rpId);
                numList.add(redPacketNum);
                if(null == big)
                    big = redPacketNum.getCash();
                if(big.compareTo(redPacketNum.getCash()) < 0){
                    big = redPacketNum.getCash();
                    max = i;
                }
            }
            numList.get(max).setIsLuck(1);
            redPacketNumDao.insertList(numList);
        }
        Long value = Long.parseLong(rpId);
        weak.put(rpId, value);
        
        CreateRedPacketVo vo = new CreateRedPacketVo();
        vo.setRpId(rpId);
        if(StringUtils.isNotEmpty(member.getPayPassword()) && !member.getPayPassword().equals("0"))
            vo.setIsHasPwd(1);
        else
            vo.setIsHasPwd(0);
        List<WealthType> list = wealthTypeService.getAll();
        for(WealthType wealthType : list){
            wealthType.setBalance(wealthService.getIfNotExistCreate(mid, wealthType.getId().toString(), false).getCash());
        }
        vo.setTypeList(list);
        return vo;
    }
    
    /**
     * 更新支付成功
     * @param rpId       
     * @param payType 支付类型
     * @param payDate 支付日期
     * @return
     */
    public boolean updatePaySuccess(String rpId, String payTypeId,Date payDate) {
        if(StringUtils.isEmpty(rpId))
            return false;
        return dao.updatePaySuccess(rpId,payTypeId,payDate);
    }
    
    /**
     * 更新退款状态
     * @param rpId
     * @return
     */
    public boolean updateBack(String rpId) {
        if(StringUtils.isEmpty(rpId))
            return false;
        return dao.updateBack(rpId);
    }

    
   /**
    * 获取红包领取过期列
    * @param date(几天前)
    * @param pageNo
    * @param pageSize
    * @return
    */
    public List<RedPacket> getExpire(Date date, int pageNo, int pageSize){
        return queryDao.getExpire(date,pageNo,pageSize);
    }
    
    public void queryPage(Date date, Page page) {
        queryDao.getExpire(date,page);
    }
    
    @Override
    protected CrudDao<RedPacket> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<RedPacket> queryDao() {
        return queryDao;
    }
    
    @Autowired private RedPacketDao dao;
    @Autowired private RedPacketQueryDao queryDao;
    @Autowired private MemberService memberService;
    @Autowired private GroupMemberService groupMemberService;
    @Autowired private RedPacketConfigService configService;
    @Autowired private RedPacketItemService itemService;
    @Autowired private WealthTypeService wealthTypeService;
    @Autowired private RedPacketNumDao redPacketNumDao;
    @Autowired private RedPacketNumQueryDao redPacketNumQueryDao;
    @Autowired private WealthService wealthService;
    @Autowired private SnDao snDao;

    

   
}
    /*if(packet.getType() == RedPacket.Type.LUCK.value()){
    ConcurrentLinkedQueue<BigDecimal> queue = cache.get(rpId);
    if(null != queue){
        if (!queue.isEmpty()) {
            BigDecimal cash = queue.poll();
            packet.setReceiveNum(packet.getReceiveNum() + 1);
            packet.setReceiveCash(packet.getCash().add(cash));
            dao.persistence(packet);
            
            RedPacketItem item = new RedPacketItem();
            item.setType(packet.getType());
            item.setIsLuck(0);//单人红包
            item.setCash(packet.getCash());
            item.setMemberId(openId);
            item.setRpId(rpId);
            item.setState(1);
            itemService.persistence(item);
        }else{
            throw new ServiceException("缓存红包出错");
        }
    }else{
        //服务器关闭
        BigDecimal total = packet.getCash();
        BigDecimal receive = packet.getReceiveCash();
        int totalNum = packet.getNum();
        int receNum = packet.getReceiveNum();
        if(total.compareTo(receive) > 0){
            ConcurrentLinkedQueue<BigDecimal> list = SpiltRedPacketUtil.math(total.subtract(receive), totalNum - receNum);
            cache.put(rpId,list);
            
            
        }else{
            throw new ServiceException("缓存红包失效");
        }
    }*/
