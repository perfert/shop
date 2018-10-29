/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.wallet;

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
import com.mas.user.domain.entity.vo.WalletPayVo;
import com.mas.user.domain.entity.wallet.Transfer;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.service.wallet.TransferService;
import com.mas.user.service.wallet.WealthRecordService;
import com.mas.user.service.wallet.WealthService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.wallet.WalletDto;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * 钱包Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * @author yixuan
 */
@Controller
@RequestMapping(WalletCtr.URI_PREFIX)
public class WalletCtr extends JsonCtr<WalletDto> {

    public static final String URI_PREFIX = JsonCtr.prefix + "/wallet";

    private static final Logger log = LogManager.getLogger(WalletCtr.class);

    @Autowired
    private WealthService wealthService;

    @Autowired
    private WealthRecordService wealthRecordService;

    @Autowired
    private TransferService transferService;

    /**
     * 钱包首页
     * 
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public void index(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                Wealth wealth = wealthService.getIfNotExistCreate(dto.getMid(), dto.getWealthTypeId(), true);
                JSONObject result = new JSONObject();
                if (null != wealth) {
                    result.put("balance", wealth.getCash());
                    result.put("isHasPwd", StringUtils.isNotEmpty(wealth.getPayPassword())
                            && !wealth.getPayPassword().equals("0") ? 1 : 0);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject, null, dto.getLang());
                } else {
                    error(jsonObject, "wallet.error.noexist", dto.getLang());
                }
            }
        });
    }

    /**
     * 交易记录
     * 
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "account/trades", method = RequestMethod.POST)
    public void tradeHistory(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                JSONObject result = new JSONObject();
                Page page = new Page(dto.getPagenum(), DEFAULT_SIZE);
                wealthRecordService.queryPage(dto.getMid(), dto.getDataType(), page);
                if (dto.getPagenum() == 1) {
                    result.put("maxPage", page.getTotalPageNo());
                }

                @SuppressWarnings("unchecked")
                List<WealthRecord> itemList = (List<WealthRecord>) page.getResult();
                if (null != itemList && itemList.size() > 0) {
                    JSONArray itemListArray = new JSONArray();
                    if (null != itemList && itemList.size() > 0) {
                        for (int i = 0; i < itemList.size(); i++) {
                            WealthRecord wealthRecord = itemList.get(i);
                            JSONObject item = new JSONObject();

                            Locale local = null;
                            if (dto.getLang().equalsIgnoreCase("zh"))
                                local = Locale.CHINA;
                            else
                                local = Locale.ENGLISH;
                            String message = SpringUtils.getApplicationContext().getMessage(wealthRecord.getRemark(),
                                    null, local);
                            item.put("id", wealthRecord.getId());
                            if (null != wealthRecord.getWdId()) {
                                String status = SpringUtils.getApplicationContext().getMessage(
                                        "wallet.record." + wealthRecord.getStatus(), null, local);
                                item.put("status", status);
                            }

                            item.put("name", message);
                            if (wealthRecord.getType() != 1) {
                                item.put("opType", "in");
                                item.put("moneyYuan", wealthRecord.getIncome());
                            } else {
                                item.put("opType", "out");
                                item.put("moneyYuan", wealthRecord.getExpense());
                            }
                            item.put("opTime",
                                    DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, wealthRecord.getCreateDate()));
                            item.put("dateMonth", DateTimeUtil.format("yyyy-MM", wealthRecord.getCreateDate()));
                            itemListArray.add(i, item);
                        }
                        result.put("details", itemListArray);
                    }
                }
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject, null, dto.getLang());
            }
        });
    }

    /**
     * 交易记录
     * 
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "account/trades/detail", method = RequestMethod.POST)
    public void detail(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                WealthRecord record = wealthRecordService.get(dto.getDetailId());
                JSONObject result = new JSONObject();
                if (null != record) {
                    Locale local = getLocal(dto.getLang());
                    String message = SpringUtils.getApplicationContext().getMessage(record.getRemark(), null, local);
                    result.put("title", message);
                    if (record.getType() != 1) {
                        result.put("type", record.getType());
                        result.put("cash", record.getIncome());
                    } else {
                        result.put("type", record.getType());
                        result.put("cash", record.getExpense());
                    }
                    if (null != record.getPayType()) {
                        String payType = SpringUtils.getApplicationContext().getMessage(
                                "wallet.packet." + record.getPayType(), null, local);
                        result.put("payType", payType);
                    }

                    if (null != record.getStatus()) {
                        String status = SpringUtils.getApplicationContext().getMessage(
                                "wallet.record." + record.getStatus(), null, local);
                        result.put("status", status);
                        // 提币到账
                        if (record.getStatus() == WealthRecord.Status.WITHDRAW_SUCCESS.value())
                            result.put("finishTime",
                                    DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, record.getModifyDate()));
                    }
                    // 操作类型,0:红包,1:后台直充,2:转账,3:OFC钱包
                    if (null != record.getOperateType() && record.getOperateType() == 2 && record.getType() == 1) {
                        Transfer transfer = transferService.getByReceiveInfo(record.getTransferId());
                        if (null != transfer)
                            result.put("receiveName", transfer.getNickName());
                    }
                    if (null != record.getRpId())
                        result.put("rpId", record.getRpId());

                    result.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, record.getCreateDate()));
                    result.put("sn", record.getSn());
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject, null, dto.getLang());
                } else {
                    error(jsonObject, "wallet.error.noexist", dto.getLang());
                }
            }
        });
    }

    /**
     * 支付
     * 
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "pay", method = RequestMethod.POST)
    public void pay(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                JSONObject result = new JSONObject();
                WalletPayVo vo = wealthService.getPayInfo(dto.getMid(), "");
                if (null != vo) {
                    result.put("isHasPwd", vo.getIsHasPwd());
                    List<WealthType> list = vo.getTypeList();
                    JSONArray typeJsonArray = new JSONArray();
                    if (null != list && list.size() > 0) {
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
                    success(jsonObject, null, dto.getLang());
                } else {
                    error(jsonObject, "rp.error.create", dto.getLang());
                }
            }
        });
    }

}