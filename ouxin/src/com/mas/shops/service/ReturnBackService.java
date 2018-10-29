/**    
 * 文件名：ImageService.java    
 *    
 * 版本信息：    
 * 日期：2017-11-20    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.shops.service;

import java.io.IOException;

import javax.annotation.Resource;

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
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.security.util.verify.ApiUtil;
import com.mas.shops.domain.entity.Notify;
import com.mas.web.member.controller.JsonCtr;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-20  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ReturnBackService {
    
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;
    @Autowired
    private NotifyService notifyService;

    /**
     * 回调支付成功
     * @param notifyId 提示id
     * @param thirdOrderId 订单id
     * @param type 提示类型
     */
    public void notifyCreateOrder(final Object notifyId,final String thirdOrderId,final int type) {
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
                    jsonObject.put("orderId",thirdOrderId);
                    jsonObject.put("type",type);
                    jsonObject.put("code",200);
                    ApiUtil.encode(JsonCtr.THIRD_KEY, jsonObject);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost("https://mall.ouxinchat.com/mall-service/m/api/orderPayment/orderPaymentNotice");
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
                                notifyService.updateStatus(notifyId,Notify.Status.FAIL.getStatus());
                                throw new ClientProtocolException("Unexpected response status: " + status);
                            }
                        }
                    };
                    try {
                        String responseBody = httpclient.execute(httpPost, responseHandler);
                        com.alibaba.fastjson.JSONObject jObject = com.alibaba.fastjson.JSONObject.parseObject(responseBody);
                        if(jObject.getJSONObject("data").getString("code").equals("200")){
                            notifyService.updateStatus(notifyId,Notify.Status.SUCCESS.getStatus());
                        }else{
                            notifyService.updateStatus(notifyId,Notify.Status.FAIL.getStatus());
                        }
                    } catch (Exception e) {
                        notifyService.updateStatus(notifyId,Notify.Status.FAIL.getStatus());
                        System.out.println("===支付回调失败=====" + e.getMessage());
                    } 
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 押金回调
     * @param notifyId
     * @param memberId
     * @param type
     */
    public void notifyMortagage(final Object notifyId, final String memberId, final int type) {
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
                    jsonObject.put("id", memberId);
                    jsonObject.put("code", "200");
                    jsonObject.put("type", type+"");
                    ApiUtil.encode(JsonCtr.THIRD_KEY, jsonObject);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost("https://mall.ouxinchat.com/mall-service/m/api/store/storePaymentNotice");//mall.ouxinchat.com
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
                                notifyService.updateStatus(notifyId,Notify.Status.FAIL.getStatus());
                                throw new ClientProtocolException("Unexpected response status: " + status);
                            }
                        }
                    };
                    try {
                        String responseBody = httpclient.execute(httpPost, responseHandler);
                        com.alibaba.fastjson.JSONObject jObject = com.alibaba.fastjson.JSONObject.parseObject(responseBody);
                        if(jObject.getJSONObject("data").getString("code").equals("200")){
                            notifyService.updateStatus(notifyId,Notify.Status.SUCCESS.getStatus());
                        }else{
                            notifyService.updateStatus(notifyId,Notify.Status.FAIL.getStatus());
                        }
                    } catch (Exception e) {
                        notifyService.updateStatus(notifyId,Notify.Status.FAIL.getStatus());
                        System.out.println("===押金回调失败=====" + e.getMessage());
                    } 
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
