/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.rp;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.orm.util.Page;
import com.mas.common.util.DateTimeUtil;
import com.mas.core.service.ServiceException;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.domain.entity.rp.RedPacketConfig;
import com.mas.user.domain.entity.rp.RedPacketItem;
import com.mas.user.domain.entity.vo.CreateRedPacketVo;
import com.mas.user.domain.entity.vo.RpInfoModel;
import com.mas.user.domain.entity.vo.RpItemModel;
import com.mas.user.domain.entity.vo.StatisReceiveVo;
import com.mas.user.domain.entity.vo.StatisSendVo;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.service.redpacket.RedPacketConfigService;
import com.mas.user.service.redpacket.RedPacketItemService;
import com.mas.user.service.redpacket.RedPacketService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.rp.RedpacketDto;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * 红包Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(RedpacketCtr.URI_PREFIX)
public class RedpacketCtr extends JsonCtr<RedpacketDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/rp";
    private static final Logger log = LogManager.getLogger( RedpacketCtr.class );
    
    @Autowired private RedPacketConfigService configService;
    @Autowired private RedPacketService packetService;
    @Autowired private RedPacketItemService itemService;
    
    /**
     * 发出红统计　　　　
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "count/send", method = RequestMethod.POST )
    public void totalSend(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto) {
                JSONObject result = new JSONObject();
                Page page = new Page(dto.getPagenum(),DEFAULT_SIZE);
                packetService.queryPage(dto.getMid(),page,dto.getWealthTypeId());
                if(dto.getPagenum() == 1){
                    StatisSendVo vo =  packetService.statisticsSend(dto.getMid(),dto.getWealthTypeId());
                    result.put("maxPage", page.getTotalPageNo());
                    result.put("sendMoney", null != vo.getSendMoney() ? vo.getSendMoney() : 0 );
                    result.put("sendCount", null != vo.getSendCount() ? vo.getSendCount() : 0 );
                    result.put("nickName", vo.getNickName());
                    result.put("avatar", HOST + vo.getAvatar());
                }
                
                @SuppressWarnings("unchecked")
                List<RedPacket> itemList = (List<RedPacket>) page.getResult();
                if(null != itemList && itemList.size() > 0){
                    JSONArray itemListArray = new JSONArray();
                    if(null !=  itemList && itemList.size() > 0){
                        for (int i = 0; i < itemList.size(); i++) {
                            RedPacket redPacket = itemList.get(i);
                            JSONObject item = new JSONObject();
                            item.put("num", redPacket.getNum());
                            item.put("receiveNum", redPacket.getReceiveNum());
                            item.put("cash", redPacket.getCash());
                            item.put("type", redPacket.getType());
                            item.put("payTime", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,redPacket.getCreateDate()));
                            //有效
                            item.put("isEffect", DateTimeUtil.addHours(redPacket.getCreateDate(), 24).before(new Date()) ? 0 : 1 );
                            itemListArray.add(i, item);
                        }
                        result.put("sendHistoryList", itemListArray);
                    }
                }
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 收到红包统计
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "count/receive", method = RequestMethod.POST )
    public void totalReceive(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto) {
                JSONObject result = new JSONObject();
                Page page = new Page(dto.getPagenum(),DEFAULT_SIZE);
                packetService.queryPage(dto.getMid(),page,dto.getWealthTypeId());
                if(dto.getPagenum() == 1){
                    StatisReceiveVo vo =  itemService.statisticsReceive(dto.getMid(),dto.getWealthTypeId());
                    result.put("maxPage", page.getTotalPageNo());
                    result.put("receiveLuckCount", null != vo.getReceiveLuckCount() ? vo.getReceiveLuckCount() : 0);
                    result.put("receiveCount", null != vo.getReceiveCount() ? vo.getReceiveCount() : 0);
                    result.put("receiveMoney", null != vo.getReceiveMoney() ? vo.getReceiveMoney() : "0");
                    result.put("nickName", vo.getNickName());
                    //没有LOGO
                    result.put("avatar", HOST + vo.getAvatar());
                }
                
                itemService.queryPageByMemberId(dto.getMid(),page,dto.getWealthTypeId());
                @SuppressWarnings("unchecked")
                List<RedPacketItem> itemList = (List<RedPacketItem>) page.getResult();
                if(null != itemList && itemList.size() > 0){
                    JSONArray itemListArray = new JSONArray();
                    if(null !=  itemList && itemList.size() > 0){
                        for (int i = 0; i < itemList.size(); i++) {
                            RedPacketItem redPacketItem = itemList.get(i);
                            JSONObject item = new JSONObject();
                            item.put("userId", redPacketItem.getMemberId());
                            item.put("rpId", redPacketItem.getRpId());
                            item.put("nickname", redPacketItem.getNickName());
                            item.put("cash", redPacketItem.getCash());
                            item.put("type", redPacketItem.getType());
                            item.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,redPacketItem.getCreateDate()));
                            item.put("isLuck", redPacketItem.getIsLuck());
                            itemListArray.add(i, item);
                        }
                        result.put("receiveHistoryList", itemListArray);
                    }
                }
                
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    
    
    /**
     * 发单个红包
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "send/single", method = RequestMethod.POST )
    public void single(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto) {
                CreateRedPacketVo vo = packetService.createSingle(dto.getMid(),dto.getReceiveId(),dto.getMoney(),dto.getSummary(),dto.getEnvelopeName());
                if(null != vo && StringUtils.isNotEmpty(vo.getRpId()) ){
                    JSONObject result = new JSONObject();
                    result.put("rpId",vo.getRpId());     
                    result.put("isHasPwd", vo.getIsHasPwd());
                    List<WealthType> list = vo.getTypeList();
                    JSONArray typeJsonArray = new JSONArray();
                    if( null != list && list.size() > 0 ){
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject bean = new JSONObject(); 
                            WealthType type = list.get(i);
                            bean.put("id", type.getId());
                            bean.put("name", type.getName());
                            bean.put("domainUrl", type.getDomainUrl());
                            bean.put("isDefault", type.getIsDefault() ? "1" : "0");
                            bean.put("balance", type.getBalance());
                            typeJsonArray.add(i, bean);
                        }
                    } 
                    result.put("wealthTypeList", typeJsonArray);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "rp.error.create",dto.getLang());
                }
            }
        });
    }
    
    /**
     * 发多个红包
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "send/group", method = RequestMethod.POST )
    public void group(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto) {
                CreateRedPacketVo vo = packetService.createGroup(dto.getMid(),dto.getGroupId(),dto.getRedPacketType(),dto.getNumber(),
                        dto.getMoney(),dto.getSummary(),dto.getEnvelopeName());
                if(null != vo && StringUtils.isNotEmpty(vo.getRpId()) ){
                    JSONObject result = new JSONObject();
                    result.put("rpId",vo.getRpId());     
                    result.put("isHasPwd", vo.getIsHasPwd());
                    
                    List<WealthType> list = vo.getTypeList();
                    JSONArray typeJsonArray = new JSONArray();
                    if( null != list && list.size() > 0 ){
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject bean = new JSONObject(); 
                            WealthType type = list.get(i);
                            bean.put("id", type.getId());
                            bean.put("name", type.getName());
                            bean.put("domainUrl", type.getDomainUrl());
                            bean.put("isDefault", type.getIsDefault() ? "1" : "0");
                            bean.put("balance", type.getBalance());
                            typeJsonArray.add(i, bean);
                        }
                    } 
                    result.put("wealthTypeList", typeJsonArray);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "rp.error.create",dto.getLang());
                }
            }
        });
    }
    
    /**
     * 红包配置
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "config", method = RequestMethod.POST )
    public void config(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto)  {
                RedPacketConfig config = configService.getDefault();
                if(null != config){
                    JSONObject result = new JSONObject();
                    Locale local = getLocal(dto.getLang());
                    String summary = SpringUtils.getApplicationContext().getMessage("rp.tips",null, local);
                    String envelopeName = SpringUtils.getApplicationContext().getMessage(config.getName(),null, local);
                    result.put("summary",summary);
                    result.put("envelopeName",envelopeName);
                    result.put("maxCount",100);
                    result.put("max",config.getMax());
                    result.put("min",config.getMin());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "rp.error.config",dto.getLang());
                }
            }
        });
    }
  
    /**
     * 获取单个红包
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "info/single", method = RequestMethod.POST )
    public void info(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto)  {
                RedPacket packet = packetService.getRedPacketAndUserInfo(dto.getRpId());
                if(null != packet){
                    JSONObject result = new JSONObject();
                    int receType = packet.getReceiveType();
                    /**
                     *  根据传过来的信息来确定红包
                     *  0:未开
                     *  1:本人发的红包
                     *  2:失效(超过24小时)
                     *  3:已领完
                     *  4:接收人已领取
                     */
                    int status = -1;
                    if(DateTimeUtil.addHours(packet.getCreateDate(), 24).before(new Date())){
                        status = 2;
                        if(itemService.isExist(dto.getRpId(),dto.getMid()))
                            status = 4;
                    }else{
                        String sendId = packet.getMemberId().toString();
                        if(receType == RedPacket.ReceiveType.SINGLE.value()){
                            if(dto.getMid().toString().equals(sendId)){
                                status = 1;
                            }else{
                                if(packet.getReceiveNum() == 0){
                                    if(!dto.getMid().equals(packet.getReceiveId().toString()))
                                        throw new ServiceException("error.rp.open.auth");
                                    status = 0;
                                }else{
                                    status = 4;
                                }
                            }
                        }else
                            throw new ServiceException("error.rp.open.interface");
                    }
                    result.put("status", status);
                    result.put("type", packet.getType());
                    result.put("isSelf", 0);//是否多个红包
                    result.put("hasLeft", packet.getReceiveNum() != packet.getNum() ? 1 : 0);//是否有留下,领取完则0
                    result.put("totalMoney", packet.getCash());
                    result.put("recTotalMoney", packet.getReceiveCash());
                    result.put("total", packet.getNum());
                    result.put("receTotal", packet.getReceiveNum());
                    result.put("content", packet.getRemark());
                    result.put("username", packet.getNickName());
                    result.put("avatar",HOST +  packet.getAvatar());
                    
                    List<RedPacketItem> itemList = itemService.getList(dto.getRpId());
                    JSONArray itemListArray = new JSONArray();
                    for (int i = 0; i < itemList.size(); i++) {
                        RedPacketItem redPacketItem = itemList.get(i);
                        if(dto.getMid().equals(redPacketItem.getMemberId())){
                            //本人领取金额
                            result.put("grabMoney", redPacketItem.getCash());
                        }
                        JSONObject item = new JSONObject();
                        item.put("userId", redPacketItem.getMemberId());
                        item.put("rpId", redPacketItem.getRpId());
                        item.put("nickname", redPacketItem.getNickName());
                        item.put("cash", redPacketItem.getCash());
                        item.put("type", redPacketItem.getType());
                        item.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,redPacketItem.getCreateDate()));
                        item.put("isLuck", redPacketItem.getIsLuck());
                        itemListArray.add(i, item);
                        result.put("receiveHistory", itemListArray);
                    }
                    
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "rp.error.packet",dto.getLang());
                }
            }
        });
    }
    
    /**
     * 获取多个红包
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "info/group", method = RequestMethod.POST )
    public void infoGroup(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto) {
                RedPacket packet = packetService.getRedPacketAndUserInfo(dto.getRpId());
                if(null != packet){
                    JSONObject result = new JSONObject();
                    int receType = packet.getReceiveType();
                    int status = -1;
                    /**
                     *  根据传过来的信息来确定红包
                     *  0:未开
                     *  1:本人发的红包
                     *  2:失效(超过24小时)
                     *  3:已领完
                     *  4:接收人已领取
                     */
                    if(DateTimeUtil.addHours(packet.getCreateDate(), 24).before(new Date())){
                        status = 2;
                        if(itemService.isExist(dto.getRpId(),dto.getMid()))
                            status = 4;
                    }else{
                        if(receType == RedPacket.ReceiveType.MANY.value()){
                            //是否领取完
                            if(packet.getReceiveNum() >= packet.getNum()){
                                status = 3;
                                if(itemService.isExist(dto.getRpId(),dto.getMid()))
                                    status = 4;
                            }else{
                                if(itemService.isExist(dto.getRpId(),dto.getMid())){
                                    status = 4;
                                }else{
                                    status = 0;
                                }
                            }
                        }else
                            throw new ServiceException("error.rp.open.interface");
                    }
                    result.put("status", status);
                    result.put("type", packet.getType());
                    result.put("isSelf", 1);//是否多个红包
                    result.put("hasLeft", packet.getReceiveNum() != packet.getNum() ? 1 : 0);//是否有留下,领取完则0
                    result.put("totalMoney", packet.getCash());
                    result.put("recTotalMoney", packet.getReceiveCash());
                    result.put("total", packet.getNum());
                    result.put("receTotal", packet.getReceiveNum());
                    result.put("content", packet.getRemark());
                    result.put("username", packet.getNickName());
                    result.put("avatar", HOST + packet.getAvatar());
                    
                    Page page = new Page(1,DEFAULT_SIZE);
                    itemService.queryPage(dto.getRpId(),page);
                    @SuppressWarnings("unchecked")
                    List<RedPacketItem> itemList = (List<RedPacketItem>) page.getResult();
                    JSONArray itemListArray = new JSONArray();
                    if(null !=  itemList && itemList.size() > 0){
                        for (int i = 0; i < itemList.size(); i++) {
                            RedPacketItem redPacketItem = itemList.get(i);
                            if(dto.getMid().equals(redPacketItem.getMemberId().toString())){
                                //本人领取金额
                                result.put("grabMoney", redPacketItem.getCash());
                            }
                            JSONObject item = new JSONObject();
                            item.put("userId", redPacketItem.getMemberId());
                            item.put("rpId", redPacketItem.getRpId());
                            item.put("nickname", redPacketItem.getNickName());
                            item.put("cash", redPacketItem.getCash());
                            item.put("type", redPacketItem.getType());
                            item.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,redPacketItem.getCreateDate()));
                            item.put("isLuck", redPacketItem.getIsLuck());
                            itemListArray.add(i, item);
                        }
                        result.put("receiveHistory", itemListArray);
                    }
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "rp.error.packet",dto.getLang());
                }
            }
        });
    }
    
    /**
     * 获取红包领取列表
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "info/detail", method = RequestMethod.POST )
    public void infoList(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto){
                //nextNum
                JSONObject result = new JSONObject();
                Page page = new Page(dto.getPagenum(),DEFAULT_SIZE);
                itemService.queryPage(dto.getRpId(),page);
                
                RedPacket packet = packetService.getRedPacketAndUserInfo(dto.getRpId());
                //result.put("status", status);
                result.put("type", packet.getType());
                if(packet.getReceiveType() != 0)
                    result.put("isSelf", 1);//是否多个红包
                else
                    result.put("isSelf", 0);//是否多个红包
                result.put("hasLeft", packet.getReceiveNum() != packet.getNum() ? 1 : 0);//是否有留下,领取完则0
                result.put("totalMoney", packet.getCash());
                result.put("recTotalMoney", packet.getReceiveCash());
                result.put("total", packet.getNum());
                result.put("receTotal", packet.getReceiveNum());
                result.put("content", packet.getRemark());
                result.put("username", packet.getNickName());
                result.put("avatar", HOST + packet.getAvatar());
                
                @SuppressWarnings("unchecked")
                List<RedPacketItem> itemList = (List<RedPacketItem>) page.getResult();
                if(null != itemList && itemList.size() > 0){
                    JSONArray itemListArray = new JSONArray();
                    if(null !=  itemList && itemList.size() > 0){
                        for (int i = 0; i < itemList.size(); i++) {
                            RedPacketItem redPacketItem = itemList.get(i);
                            JSONObject item = new JSONObject();
                            item.put("userId", redPacketItem.getMemberId());
                            item.put("rpId", redPacketItem.getRpId());
                            item.put("nickname", redPacketItem.getNickName());
                            item.put("cash", redPacketItem.getCash());
                            item.put("type", redPacketItem.getType());
                            item.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,redPacketItem.getCreateDate()));
                            item.put("isLuck", redPacketItem.getIsLuck());
                            itemListArray.add(i, item);
                        }
                        result.put("receiveHistory", itemListArray);
                    }
                }
                if(page.hasNextPageNo())
                    result.put("nextNum", page.getNextPageNo());
                else
                    result.put("nextNum", -1);
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 开红包
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "open/single", method = RequestMethod.POST )
    public void openSingle(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto) {
                if(StringUtils.isNotEmpty(dto.getRpId())){
                    RpInfoModel info = packetService.openSingleRedPacket(dto.getRpId(),dto.getMid());
                    if(null != info){
                        JSONObject result = new JSONObject();
                        result.put("status", info.getStatus());
                        result.put("type", info.getType());
                        result.put("isSelf", info.getIsSelf());
                        result.put("hasLeft", info.getHasLeft());
                        result.put("totalMoney", info.getTotalMoney());
                        result.put("recTotalMoney", info.getRecTotalMoney());
                        result.put("total", info.getTotal());
                        result.put("receTotal", info.getReceTotal());
                        result.put("content", info.getContent());
                        result.put("username", info.getUsername());
                        result.put("avatar", HOST + info.getAvatar());
                      
                        if(null != info.getItemList() && info.getItemList().size() > 0){
                            JSONArray itemList = new JSONArray();
                            for (int i = 0; i < info.getItemList().size(); i++) {
                                RpItemModel model = info.getItemList().get(i);
                                JSONObject item = new JSONObject();
                                item.put("userId", model.getUserId());
                                item.put("rpId", model.getRpId());
                                item.put("nickname", model.getNickname());
                                item.put("cash", model.getCash());
                                item.put("type", model.getType());
                                item.put("time", model.getTime());
                                item.put("isLuck", model.getIsLuck());
                                itemList.add(i, item);
                            }
                            result.put("receiveHistory", itemList);
                        }
                        jsonObject.put( KEY_RESULT, result );
                        success(jsonObject,null,dto.getLang());
                    }
                }
            }
        });
    }
    
    /**
     * 开红包
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "open/group", method = RequestMethod.POST )
    public void openGroup(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,RedpacketDto.class,log,new DoYourBuniness<RedpacketDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,RedpacketDto dto)  {
                if(StringUtils.isNotEmpty(dto.getRpId())){
                    RpInfoModel info = packetService.openGroupRedPacket(dto.getRpId(),dto.getMid());
                    if(null != info){
                        JSONObject result = new JSONObject();
                        result.put("status", info.getStatus());
                        result.put("type", info.getType());
                        result.put("isSelf", info.getIsSelf());
                        result.put("hasLeft", info.getHasLeft());
                        result.put("totalMoney", info.getTotalMoney());
                        result.put("recTotalMoney", info.getRecTotalMoney());
                        result.put("total", info.getTotal());
                        result.put("receTotal", info.getReceTotal());
                        result.put("content", info.getContent());
                        result.put("username", info.getUsername());
                        result.put("avatar", HOST + info.getAvatar());
                        
                        Page page = new Page(1,DEFAULT_SIZE);
                        itemService.queryPage(dto.getRpId(),page);
                        @SuppressWarnings("unchecked")
                        List<RedPacketItem> itemList = (List<RedPacketItem>) page.getResult();
                        JSONArray itemListArray = new JSONArray();
                        for (int i = 0; i < itemList.size(); i++) {
                            RedPacketItem redPacketItem = itemList.get(i);
                            JSONObject item = new JSONObject();
                            item.put("userId", redPacketItem.getMemberId());
                            item.put("rpId", redPacketItem.getRpId());
                            item.put("nickname", redPacketItem.getNickName());
                            item.put("cash", redPacketItem.getCash());
                            item.put("type", redPacketItem.getType());
                            item.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,redPacketItem.getCreateDate()));
                            item.put("isLuck", redPacketItem.getIsLuck());
                            itemListArray.add(i, item);
                            result.put("receiveHistory", itemListArray);
                        }
                        jsonObject.put( KEY_RESULT, result );
                        success(jsonObject,null,dto.getLang());
                    }
                }
            }
        });
    }
}