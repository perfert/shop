/**    
 * 文件名：MemberWealth.java    
 *    
 * 版本信息：    
 * 日期：2017-12-19    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.wallet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.core.service.ServiceException;
import com.mas.security.util.PasswordUtil;
import com.mas.security.util.verify.ApiUtil;
import com.mas.user.domain.entity.MemberTicket;
import com.mas.user.domain.entity.vo.RpPayVo;
import com.mas.user.domain.entity.vo.QrCodeVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WealthTypeService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.wallet.RechargeApiNotifyResult;
import com.mas.web.member.controller.dto.wallet.RechargeApiResult;
import com.mas.web.member.controller.dto.wallet.RechargeCtrDto;
import com.mas.web.member.controller.dto.wallet.WalletDto;
import com.mas.web.springmvc.util.SpringUtils;
import com.mas.web.util.JsonUtil;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-19  
 */
@Controller
@RequestMapping(MemberWealthCtr.URI_PREFIX)
public class MemberWealthCtr extends JsonCtr<WalletDto> {

    public static final String URI_PREFIX = WalletCtr.URI_PREFIX + "/wealth";

    private static final Logger log = LogManager.getLogger(MemberWealthCtr.class);

    @Autowired
    private WealthService wealthService;
    @Autowired
    private WealthTypeService wealthTypeService;

    /**
     * 获取财富类型列表
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "type", method = RequestMethod.POST )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,WalletDto.class,log,new DoYourBuniness<WalletDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto){
                List<WealthType> list = wealthTypeService.getAll();
                JSONArray result = new JSONArray();
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject bean = new JSONObject(); 
                        WealthType type = list.get(i);
                        bean.put("id", type.getId());
                        bean.put("name", type.getName());
                        bean.put("domainUrl", type.getDomainUrl());
                        bean.put("isDefault", type.getIsDefault() ? "1" : "0");
                        result.add(i, bean);
                    }
                } 
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 地址对应用户信息
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "address", method = RequestMethod.POST)
    public void address(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                Wealth wealth = wealthService.getByAddress(dto.getAddress());
                if (null != wealth) {
                    JSONObject result = new JSONObject();
                    result.put("portraitUri", HOST + wealth.getAvatar());
                    result.put("nickname", wealth.getNickName());
                    result.put("balance", wealth.getCash());
                    result.put("wealthTypeId", wealth.getWealthType());
                    result.put("wealthTypeName", wealth.getWealthTypeName());
                    if (StringUtils.isNotEmpty(wealth.getPayPassword()) && !wealth.getPayPassword().equals("0"))
                        result.put("isHasPwd", 1);
                    else 
                        result.put("isHasPwd", 0);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject, null, dto.getLang());
                } else {
                    throw new ServiceException("user.error.noexist");
                }
            }
        });
    }

    /**
     * 接收二维码
     * 
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "receive", method = RequestMethod.POST)
    public void receive(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                QrCodeVo vo = wealthService.getReceiveQrCode(dto.getMid(),dto.getWealthTypeId());
                JSONObject result = new JSONObject();
                if (null != vo) {
                    result.put("qrcode", HOST + vo.getQrcode());
                    result.put("realAddress",vo.getRealAddress());
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject, null, dto.getLang());
                } else {
                    error(jsonObject, "waller.error.qrcode.receive", dto.getLang());
                }
            }
        });
    }

    /**
     * 接收二维码带数量
     * 
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "receive/num", method = RequestMethod.POST)
    public void receive_num(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto) {
                MemberTicket ticket = wealthService.getReceiveQrCode(dto.getMid(),dto.getWealthTypeId(), dto.getNum());
                JSONObject result = new JSONObject();
                if (null != ticket) {
                    result.put("id", ticket.getId());
                    result.put("qrcode", HOST + ticket.getImage());
                    result.put("num", dto.getNum());
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject, null, dto.getLang());
                } else {
                    error(jsonObject, "wallet.error.qrcode.receive", dto.getLang());
                }
            }
        });
    }

    /**
     * 二维码转账
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public void send(@RequestBody
    String body, HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response, body, WalletDto.class, log, new DoYourBuniness<WalletDto>() {

            @Override
            public void handleBusiness(JSONObject jsonObject, WalletDto dto){
                JSONObject result = new JSONObject();
                RpPayVo vo = wealthService.paySendMoney(dto.getMid(), dto.getWealthTypeId(),dto.getAddress(),dto.getPayPassword(),dto.getNum(), null);
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

    /**
     * 第三方充值。
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    public void recharge(HttpServletRequest request, HttpServletResponse response) {
        String receive = "";
        log.info("=================recharge======================");
        System.out.println("==================recharge============================");
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            receive = new String(outSteam.toByteArray(), "utf-8");
        } catch (IOException ex) {
            log.info("=================recharge======================" + ex.getMessage());
            JsonUtil.renderJson( response, new RechargeApiNotifyResult( "DATA_ERROR", errorCode.get( "DATA_ERROR" ) ) );
            return ;
        }
        
        RechargeCtrDto dto = null;
        Map<String, Object> map = null;
        Object orderId = null;
        try {
            dto = com.alibaba.fastjson.JSONObject.parseObject(receive, RechargeCtrDto.class);
            map = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(dto), HashMap.class);

            if (null == dto ||  map.isEmpty()) {
                JsonUtil.renderJson( response, new RechargeApiNotifyResult( "XML_FORMAT_ERROR", errorCode.get( "XML_FORMAT_ERROR" ) ) );
                return;
            }
            if (StringUtils.isBlank(dto.getMch_id()))
                throw new ServiceException("LACK_PARAMS");
            if (!mch_id.equals(dto.getMch_id()))
                throw new ServiceException("MCHID_NOT_EXIST");
            if (StringUtils.isBlank(dto.getAddress()))
                throw new ServiceException("ADDRESS_ERROR");
            if (StringUtils.isBlank(dto.getOut_trade_no()))
                throw new ServiceException("LACK_PARAMS");
            if (StringUtils.isBlank(dto.getFee_type()))
                throw new ServiceException("LACK_PARAMS");
            if (!recharge_type.equals(dto.getFee_type()))
                throw new ServiceException("PARAMS_FAILED");
            if (StringUtils.isBlank(dto.getTotal_fee()))
                throw new ServiceException("LACK_PARAMS");
            BigDecimal totalFee = new BigDecimal(dto.getTotal_fee(), MathContext.DECIMAL32);
            if (0D >= totalFee.doubleValue())
                throw new ServiceException("PARAMS_FAILED");
            if (StringUtils.isBlank(dto.getNonce_str()))
                throw new ServiceException("LACK_PARAMS");
            if (StringUtils.isBlank(dto.getCreate_ip()))
                throw new ServiceException("LACK_PARAMS");
            if (StringUtils.isBlank(dto.getApi_type()))
                throw new ServiceException("LACK_PARAMS");
            if (!api_type.equals(dto.getApi_type()))
                throw new ServiceException("PARAMS_FAILED");
            // 验证
            if (!check(map))
                throw new ServiceException("SIGNERROR");

            /*
             * 进行充值Service操作
             */
            // orderId = service 操作的订单ID
            wealthService.rechargeOfc(dto.getOut_trade_no(), dto.getAddress(), dto.getFromAddress(), dto.getFee_type(),dto.getTotal_fee());
            // 返回成功
            JsonUtil.renderJson( response, new RechargeApiNotifyResult( "SUCCESS", errorCode.get( "SUCCESS" ) ) );
        } catch (Exception ex) {
            log.info("=================recharge======================" + ex.getMessage());
            
            String msg = errorCode.get( "XML_FORMAT_ERROR" );
            if(ex instanceof ServiceException){
                String result = errorCode.get(ex.getMessage());
                if(StringUtils.isNotEmpty(result))
                    msg = result;
            }
            JsonUtil.renderJson( response, new RechargeApiNotifyResult( "XML_FORMAT_ERROR", msg ) );
            return;
        }

        // 通知，当存在notify_url时。
        if (StringUtils.isNotBlank(dto.getNotify_url())) {
            try {
                notify(orderId, dto);
            } catch (Exception ex) {
                JsonUtil.renderJson( response, new RechargeApiNotifyResult( "NOTIFY_ERROR", errorCode.get( "NOTIFY_ERROR" ) ) );
            }
        }
    }

    private static final String mch_id = "rc.ouxinchat.com";

    private static final String mch_key = "cc5fdb4a3e947d586da859df3a58012b";

    private static final String recharge_type = "1";

    private static final String api_type = "web";

    private static final Map<String, String> errorCode = new HashMap<String, String>();

    static {
        errorCode.put("SYSTEMERROR", "SYSTEMERROR"); // 系统错误或超时，请重新调用。
        errorCode.put("OUT_TRADE_NO_USED", "OUT_TRADE_NO_USED"); // 重复订单号。
        errorCode.put("MCHID_NOT_EXIST", "MCHID_NOT_EXIST"); // MCH_ID 错误。
        errorCode.put("LACK_PARAMS", "LACK_PARAMS"); // 缺少参数。
        errorCode.put("PARAMS_FAILED", "PARAMS_FAILED"); // 参数错误。
        errorCode.put("SIGNERROR", "SIGNERROR"); // 签名错误。
        errorCode.put("XML_FORMAT_ERROR", "XML_FORMAT_ERROR"); // XML 格式错误。
        errorCode.put("NOT_UTF8", "NOT_UTF8"); // 编码格式错误。
        errorCode.put("CASH_ERROR", "CASH_ERROR"); // 金额错误。
        errorCode.put("DATA_ERROR", "DATA_ERROR"); // 数据错误。
        errorCode.put("SURPLUS_PARAMS", "SURPLUS_PARAMS"); // 冗长参数。
        errorCode.put("USER_NOT_EXIST", "USER_NOT_EXIST"); // 会员不存在。
        errorCode.put("RECHARGE_FAILED", "RECHARGE_FAILED"); // 充值失败。
        errorCode.put("NOTIFY_ERROR", "NOTIFY_ERROR"); // 回调错误
        errorCode.put("ADDRESS_ERROR", "ADDRESS_ERROR"); // 地址错误
        errorCode.put("SUCCESS", "SUCCESS");
    }

    // 校验数据。
    private boolean check(Map<String, Object> map) {
        // 校验数据。
        List<String> list = new ArrayList<String>(map.keySet());
        // 按照参数名ASCII字典序排序
        Collections.sort(list);
        String check = "";
        String sign = "";
        for (String key : list) {
            if (key.equals("sign"))
                sign = map.get(key).toString();
            if (!"key".equals(key) && !"sign".equals(key)) {
                if (StringUtils.isNotBlank(check))
                    check += "&";
                check += key + "=" + map.get(key);
            }
        }

        try {
            return sign.equals(PasswordUtil.encryptByMd5(check + "&key=" + mch_key).toUpperCase());
        } catch (Exception ex) {
            return false;
        }
    }

    @SuppressWarnings( "unchecked" )
    private void notify( final Object orderId, final RechargeCtrDto dto ) throws Exception
    {
        RechargeApiResult result = new RechargeApiResult( "0000", "SUCCESS" );
        result.setResult_code( "SUCCESS" );
        result.setMch_id( dto.getMch_id() );
        result.setCode( dto.getCode() );
        result.setAccount( dto.getAccount() );
        result.setOut_trade_no( dto.getOut_trade_no() );
        result.setFee_type( dto.getFee_type() );
        result.setTotal_fee( dto.getTotal_fee() );
        result.setNonce_str( ApiUtil.generateNoncestr(32) );
        result.setApi_type( dto.getApi_type() );
        result.setAttach( dto.getAttach() );
        result.setRecharge_no( orderId.toString() );
        
        Map<String, Object> map = com.alibaba.fastjson.JSONObject.parseObject( com.alibaba.fastjson.JSONObject.toJSONString( result ), HashMap.class );
        
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
        check += "&key=" + mch_key;
        
        String sign = PasswordUtil.encryptByMd5( check ).toUpperCase();
        map.put( "sign", sign );
        
        final String xml = getSignRequestXml( map );
        
        if( StringUtils.isNotBlank( dto.getNotify_url() ) )
        {
            new Thread( new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        int i = 0;
                        do {
                            CloseableHttpClient httpclient = HttpClients.createDefault();
                            HttpPost httpPost = new HttpPost("http://localhost:8080/waitsrv/GenXmlServlet");
                            httpPost.addHeader("Content-Type", "text/xml");
                            StringEntity stringEntity = new StringEntity(xml, "UTF-8");
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
                            String responseBody = httpclient.execute(httpPost, responseHandler);
                            //System.out.println("notify....receive....: " + receive);
                            if( StringUtils.isBlank( responseBody ) || ! "SUCCESS".equals( responseBody ) )
                            {
                                //log.error( "to notify return: " + receive );
                                if( 0 == i )
                                {
                                    Thread.sleep( 5 * 1000 );       // 5 秒发送
                                } else if( 1 == i )
                                {
                                    Thread.sleep( 60 * 1000 );      // 1分钟发送
                                } else if( 2 == i )
                                {
                                    Thread.sleep( 5 * 60 * 1000 );      // 5分钟发送
                                } else if( 3 == i )
                                {
                                    Thread.sleep( 30 * 60 * 1000 );     // 30分钟发送
                                } else if( 4 == i )
                                {
                                    Thread.sleep( 1 * 60 * 60 * 1000 );     // 60分钟发送
                                }                           
                            } else {
                                i = 100;
                                // Service 操作，通知成功 rechargeOrderDoService.notifySuccess( orderId );
                            }
                            i ++;
                        } while( i < 3 );
                    }catch(Exception ex ){
                        log.error( "notify error +++++++++++++++++++++++++++++++: " + ex.getMessage() );
                    }
                }
            }
            ).start();
        }
    }
    
    /**
     * @Description：将请求参数转换为xml格式的string
     * @param parameters
     *            请求参数
     * @return
     */
    private static String getSignRequestXml(Map<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set<?> es = parameters.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "key".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
