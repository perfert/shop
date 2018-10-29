/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.wallet;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.mas.user.domain.entity.vo.RpPayVo;
import com.mas.user.domain.entity.wallet.Withdraw;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WithdrawService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.wallet.WthdrawDto;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * 提币Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(WithdrawCtr.URI_PREFIX)
public class WithdrawCtr extends JsonCtr<WthdrawDto> {

    public static final String URI_PREFIX = WalletCtr.URI_PREFIX  + "/withdraw";

    private static final Logger log = LogManager.getLogger(WithdrawCtr.class);

    @Autowired
    private WithdrawService wthdrawService;
    @Autowired
    private WealthService wealthService;
    
    //提币列表
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public void tradeHistory(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WthdrawDto.class, log, new DoYourBuniness<WthdrawDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WthdrawDto dto) {
                JSONObject result = new JSONObject();
                Page page = new Page(dto.getPagenum(), DEFAULT_SIZE);
                wthdrawService.queryPage(dto.getMid(),  dto.getWealthTypeId(),page);
                if (dto.getPagenum() == 1) {
                    result.put("maxPage", page.getTotalPageNo());
                }

                @SuppressWarnings("unchecked")
                List<Withdraw> itemList = (List<Withdraw>) page.getResult();
                if (null != itemList && itemList.size() > 0) {
                    JSONArray itemArray = new JSONArray();
                    if (null != itemList && itemList.size() > 0) {
                        for (int i = 0; i < itemList.size(); i++) {
                            Withdraw wthdraw = itemList.get(i);
                            JSONObject item = new JSONObject();
                            Locale local = null;
                            if(dto.getLang().equalsIgnoreCase("zh"))
                                local = Locale.CHINA;
                            else
                                local = Locale.ENGLISH;
                            String message = SpringUtils.getApplicationContext().getMessage("wthdraw.status." + wthdraw.getStatus(),null, local);
                            item.put("id", wthdraw.getId());
                            item.put("address", wthdraw.getAddress());
                            item.put("status", wthdraw.getStatus());
                            item.put("statusName", message);
                            item.put("num", wthdraw.getNum());
                            item.put("time",DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, wthdraw.getModifyDate()));
                            itemArray.add(i, item);
                        }
                    }
                    result.put("details", itemArray);
                }
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject, null, dto.getLang());
            }
        });
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void add(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WthdrawDto.class, log, new DoYourBuniness<WthdrawDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WthdrawDto dto){
                JSONObject result = new JSONObject();
                RpPayVo vo = wealthService.withdraw(dto.getMid(), dto.getWealthTypeId(), dto.getAddress(),dto.getPayPassword(),dto.getNum());
                if (null != vo) {
                    if (vo.getCode() == 200) {
                        jsonObject.put(KEY_RESULT, result);
                        success(jsonObject, null, dto.getLang());
                    } else if (vo.getCode() == 201) {
                        jsonObject.put(KEY_STATUS, vo.getCode());
                        jsonObject.put(KEY_MESSAGE,SpringUtils.getApplicationContext().getMessage("rp.paypwd.error", null, getLocal(dto.getLang())));
                    } else {
                        error(jsonObject, null, dto.getLang());
                    }
                } else {
                    error(jsonObject, null, dto.getLang());
                }
            }
        });
    }

}