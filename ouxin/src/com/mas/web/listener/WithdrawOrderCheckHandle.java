/**    
 * 文件名：WithdrawOrderCheckHandle.java    
 *    
 * 版本信息：    
 * 日期：2017-12-28    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.listener;

import com.mas.user.domain.entity.vo.WithDrawVo;
import com.mas.user.service.wallet.WithdrawService;
import com.mas.web.member.controller.dto.wallet.RechargeCtrDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.orm.util.Page;
import com.mas.core.service.ServiceException;
import com.mas.security.util.PasswordUtil;
import com.mas.security.util.verify.ApiUtil;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-28  
 */
/**
 * 提币订单确认处理器。
 */
public class WithdrawOrderCheckHandle extends Thread {

    @Override
    public void run() {
        do {
            try {
                // 有多少条提币数据
                Long existCheck = service.existCheck();
                if (0L < existCheck) {
                    recharge(existCheck);
                }
            } catch (Exception ex) {
                log.error(wealthId + "-" + wealthId + " Withdraw ordercheck count exception：" + ex.getMessage());
            }
            try {
                sleep(beforeIntervalSecond);
            } catch (Exception ex) {
                log.error(wealthId + "-" + wealthId + " Withdraw ordercheck sleep [interval-" + beforeIntervalSecond
                        + "] exception：" + ex.getMessage());
            }
        } while (0 < beforeIntervalSecond);
    }
    
    public static void main(String[] args) {
        Long existCheckCount = 160L;
        long count = existCheckCount / 100;
        if (0L >= count && 0 < existCheckCount)
            count = 1;
        System.out.println(count);
    }

    private void recharge(Long existCheckCount) throws ServiceException {
        long count = existCheckCount / 100;
        if (0L >= count && 0 < existCheckCount)
            count = 1;
        Thread countThread;
        //for (long i = 0; i < count + 1; i++) {
            countThread = new Thread(new Runnable() {
                @SuppressWarnings("unchecked")
                @Override
                public void run() {
                    final Page page = new Page(1,100);
                    try {
                        service.queryPageByAddress(page);
                        List<WithDrawVo> withList =  (List<WithDrawVo>) page.getResult();
                        if (null != withList && withList.size() > 0) {
                            for (WithDrawVo item : withList) {
                                RechargeCtrDto dto = new RechargeCtrDto();
                               
                                dto.setCode(item.getCode()); // 如中国：86
                                dto.setAccount(item.getAccount()); // 帐号 13077078688 item.getAccount()
                                dto.setMch_id( "ext.ouxinchat.com" );
                                dto.setOut_trade_no( item.getId().toString() );     // 提币订单ID
                                dto.setFee_type( "1" );
                                dto.setTotal_fee( item.getNum().toString() );          // 提币数量
                                dto.setNonce_str( ApiUtil.generateNoncestr(32) );     // 随机字串
                                dto.setCreate_ip( "172.0.0.0" );
                                
                                dto.setFromAddress(item.getMyAddress()); //鸥信的地址
                                dto.setAddress(item.getAddress()); // 目标地址
                                //dto.setNotify_url( "http://localhost:8082/test/api/recharge" );
                                dto.setApi_type( "web" );
                                dto.setAttach( "" );
                                Map<String, Object> map = JSONObject.parseObject( JSONObject.toJSONString( dto ), HashMap.class );
                                // 校验数据。
                                List<String> list = new ArrayList<String>(map.keySet());
                                // 按照参数名ASCII字典序排序
                                Collections.sort(list);
                                String check = "";
                                for( String key : list )
                                {
                                    if( ! "key".equals( key ) && ! "sign".equals( key ) )
                                    {
                                        if(StringUtils.isNotBlank( check )) check +="&";
                                        check += key + "=" + map.get( key );
                                    }
                                }
                                check += "&key=ob2743124f0621cd96fe5bdeff6d59dx";
                                String sign = PasswordUtil.encryptByMd5( check ).toUpperCase();
                                map.put( "sign", sign );
                                dto.setSign( sign );

                                // String xml = PayCommonUtil.getRequestXml( map
                                // );JSONObject jsonObject = new JSONObject();
                                CloseableHttpClient httpclient = HttpClients.createDefault();
                                String ip = OFC_IP;
                                if(!item.getWealthTypeId().toString().equals(OFC_ID))
                                    ip = OCC_IP;
                                HttpPost httpPost = new HttpPost(ip);//18.144.5.233:51434
                                httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
                                // 解决中文乱码问题
                                StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(dto), "UTF-8");
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
                                    String result = httpclient.execute(httpPost, responseHandler);
                                    if (StringUtils.isNotBlank(result)) {
                                        JSONObject json = JSONObject.parseObject(result);
                                        String recode;
                                        if (StringUtils.isNotBlank(recode = json.getString("return_code"))) {
                                            if ("SUCCESS".equals(recode)) {
                                                 //充值成功，修改数据状态
                                                service.handleSuccess(item.getId().toString());
                                            } else if ("FAILED_ADDRESS".equals(recode) || "ADDRESS_ERROR".equals(recode)
                                                    || "SYSTEMERROR".equals(recode)) {
                                                 // 充值失败，修改数据状态
                                                service.handleFailed(item.getId().toString());
                                            } else{
                                                System.out.println("=====withdraw====" + recode);
                                                service.handleFailed(item.getId().toString());
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("=====withdraw====" + e.getMessage());
                                    throw new ServiceException("wallet.error.address");
                                } 
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("====withdraw=====");
                        log.error(wealthId + " withdraw check error: " + ex.getMessage());
                    }
                }
            });
            countThread.start();
            do {
                if (!countThread.isAlive())
                    break;
                try {
                    sleep(5 * 1000);
                } catch (Exception ex) {
                    log.error(wealthId + " withdraw check sleep [interval-" + beforeIntervalSecond + "]异常："
                            + ex.getMessage());
                }            } while (true);
        //}
        Long nextStaticCount = service.existCheck();
        if (0L < nextStaticCount) {
            recharge(nextStaticCount);
        }
    }

    public void stopTread() {
        beforeIntervalSecond = 0;
    }

    private long beforeIntervalSecond;

    private final String wealthId;

    private final WithdrawService service;
    
    private final String OFC_IP = "http://54.183.189.28:54802/rapi/withdraw";
    private final String OCC_IP = "http://18.144.5.233:51434:54802/rapi/withdraw";
    public static final String OFC_ID = "1";
    public static final String OCC_ID = "2";
    
    public WithdrawOrderCheckHandle(String wealthId, WithdrawService service) {
        this.wealthId = wealthId;
        this.service = service;
        this.beforeIntervalSecond = 30 * 60 * 1000;// DateUtil.dayStart( DateUtil.getDateByDay( 1 ) ).getTime() - new
          // Date().getTime() + 60 * 1000;
    }

    private static final Logger log = LogManager.getLogger();
}