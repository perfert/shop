/**    
 * 文件名：SmsConfigService.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.verify.VerifyUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.domain.entity.SmsConfig;
import com.mas.system.repository.dao.SmsConfigDao;
import com.mas.system.repository.query.SmsConfigQueryDao;
import com.mas.web.util.sms.CheckSumBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SmsConfigService extends BaseServiceImpl<SmsConfig>
{
    @Autowired
    private SmsCodeService smsCodeService;
    
    
    public SmsConfig getDefault()
    {
        return queryDao.getDefault();
    }
    
    /**
     * 校验验证码。
     * 
     * @param ituTCode 国际冠码
     * @param mobile 手机号
     * @param code 验证码
     * 
     * @return true or false
     */
    public boolean checkCode( int type, String ituTCode, String mobile, String code ){
        return smsCodeService.checkCode(type,ituTCode,mobile,code);
    }

    /**
     * 发送验证码
     * @param type 验证码类型
     * @param ituTCode
     * @param mobile
     * @return
     */
    public boolean sendCode(int type,String ituTCode, String mobile){
        String result = null;
        if (!"86".equals(ituTCode)) {
            String code = createRandomVcode();//getRandNum(6);
            if( !sendInternationSms( ituTCode, mobile, code ) ){
                throw new ServiceException("code.error.send");
            }
        }else{
            result = sendCode(ituTCode,mobile);
        }
        SmsCode code = new SmsCode();
        code.setType(type);
        code.setCode(result);
        code.setMobileCode(ituTCode);
        code.setMobile(mobile);
        code.setState(1);
        smsCodeService.persistence(code);
        return true;
    }
    
   
    /**
     * 发送国验证码
     * @param mobileCode 国家码
     * @param mobile     手机
     * @param code       验证码
     * @return
     * @throws IOException
     */
    protected  boolean sendInternationSms(String mobileCode, String mobile, String code ) {
        try {
            //发送内容
            String content = "[ouxin] Your verification code is " + code + ", please complete within 10 minutes, do not divulge to others."; 
           
            // 创建StringBuffer对象用来操作字符串
            StringBuffer sb = new StringBuffer( "http://m.5c.com.cn/api/send/?" );   // http://m.5c.com.cn/api/send/?
            
            // APIKEY
            sb.append("?apikey=" + "163f7022ed9ebbd35bcaaff429f909a0" );    // 163f7022ed9ebbd35bcaaff429f909a0

            //用户名
            sb.append("&username=" + "baifen" );       // baifen
            
            // 向StringBuffer追加密码
            sb.append("&password_md5=" + "1656f571a4b98eee72319948bae64146" );        // 1656f571a4b98eee72319948bae64146
            
            // 向StringBuffer追加手机号码
            sb.append("&mobile=" + mobileCode + mobile);

            // 向StringBuffer追加消息内容转URL标准码
            sb.append("&content="+ URLEncoder.encode(content,"GBK"));

            // 创建url对象
            URL url = new URL(sb.toString());

            // 打开url连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置url请求方式 ‘get’ 或者 ‘post’
            connection.setRequestMethod("POST");

            // 发送
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            // 返回发送结果
            String inputline = in.readLine();
            boolean result = inputline.startsWith( "success" );
            if( ! result ) log.error( "国际SMS发送失败：" + inputline );
            return result;
        } catch (Exception e) {
            log.error( "国际SMS发送失败：" + e.getMessage() );
            return false;
        }
       
    }
    
    /**
     * 发送验证码
     * 
     * @param ituTCode 国际冠码
     * @param mobile 手机号
     * 
     * @return 验证码
     */
    private String sendCode( String ituTCode, String mobile ){
        try {
            SmsConfig config = getDefault();
            String curTime = String.valueOf((new Date().getTime() / 1000L));
            String checkSum = CheckSumBuilder.getCheckSum(config.getPassword(), config.getNonceString(), curTime);

            //创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
            //HttpClient  
            CloseableHttpClient httpclient = httpClientBuilder.build();  
            HttpPost post = new HttpPost(config.getSmsApi());
            // 设置请求的header
            post.addHeader("AppKey", config.getAccount());
            post.addHeader("Nonce", config.getNonceString());
            post.addHeader("CurTime", curTime);
            post.addHeader("CheckSum", checkSum);
            post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 设置请求参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("templateid", config.getSmsModuleCode()));
            nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
            nameValuePairs.add(new BasicNameValuePair("codeLen", "6"));
            JSONObject jsonObject = null;

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            // 执行请求
            HttpResponse response = httpclient.execute(post);
            String responseEntity = EntityUtils.toString(response.getEntity(), "utf-8");
            // 判断是否发送成功，发送成功返回true
            jsonObject = JSON.parseObject(responseEntity);
            String code = jsonObject.getString("code");
            if ("200".equals(code)) {
                return jsonObject.getString("obj");
            } else {
                log.error("SMS execute send [ " + ituTCode + "+" + mobile + " ] failed：" + jsonObject.toJSONString());
            }
        } catch (Exception ex) {
            log.error("SMS execute send [ " + ituTCode + "+" + mobile + " ] exception：" + ex.getMessage());
        }
        return null;
    }
    
    
    
    /**
     * 发送信息
     * 
     * @param ituTCode 国际冠码
     * @param mobile 手机号
     * @param message 信息
     * @param arguments 信息参数
     * 
     * @return true or false
     */
    public boolean sendMessage( String ituTCode, String mobile, String message, Object... arguments ){
        if( StringUtils.isBlank( ituTCode )|| StringUtils.isBlank( mobile )|| ( "86".equals( ituTCode ) && ! VerifyUtil.isMobile( mobile ) ) ) {
            log.error( "sms send message error：itutCode or mobile is error." );
            return false;
        }
        try{
            
            if( ! "86".equals( ituTCode ) ){
                log.error( "sms send message failed：Unsupported country." );
                return false;
            }
            
            SmsConfig config = getDefault();
            String curTime = String.valueOf( ( new Date().getTime() / 1000L ) );
            String checkSum = CheckSumBuilder.getCheckSum( config.getPassword(), config.getNonceString(), curTime );
            
            //创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
            //HttpClient  
            CloseableHttpClient httpclient = httpClientBuilder.build();  
            HttpPost post = new HttpPost( config.getMessageApi() );
            // 设置请求的header
            post.addHeader( "AppKey", config.getAccount() );
            post.addHeader( "Nonce", config.getNonceString() );
            post.addHeader( "CurTime", curTime );
            post.addHeader( "CheckSum", checkSum );
            post.addHeader( "Content-Type", "application/x-www-form-urlencoded;charset=utf-8" );
            // 设置请求参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add( new BasicNameValuePair( "templateid", config.getMessageModuleCode() ) );
            nameValuePairs.add( new BasicNameValuePair( "mobiles", "['" + mobile + "']" ) );
            nameValuePairs.add( new BasicNameValuePair( "params", "['" + MessageFormat.format( message, arguments ) + "']" ) );
            post.setEntity( new UrlEncodedFormEntity( nameValuePairs, "utf-8" ) );
            // 执行请求
            HttpResponse response = httpclient.execute( post );
            String responseEntity = EntityUtils.toString( response.getEntity(), "utf-8" );
            // 判断是否发送成功，发送成功返回true
            JSONObject json = JSON.parseObject( responseEntity );
            String code = json.getString( "code" );
            if( code.equals( "200" ) )
            {
                return true;
            }
            else
            {
                log.error( "SMS message send [ " + ituTCode + "+" + mobile + " ] failed：" + json );
            }
        }
        catch( Exception ex )
        {
            log.error( "SMS message send [ " + ituTCode + "+" + mobile + " ] exception：" + ex.getMessage() );
        }
        return false;
    }
    
    /**
     * 随机生成6位随机验证码
      * 方法说明
      * @Discription:扩展说明
      * @return
      * @return String
      * @Author: feizi
      * @Date: 2015年4月17日 下午7:19:02
      * @ModifyUser：feizi
      * @ModifyDate: 2015年4月17日 下午7:19:02
     */
    public String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
    
    @Override
    protected QueryDao<SmsConfig> queryDao()
    {
        return queryDao;
    }
    
    @Override
    protected CrudDao<SmsConfig> crudDao()
    {
        return dao;
    }

    private static final Logger log = LogManager.getLogger();
    @Autowired private SmsConfigDao dao;
    @Autowired private SmsConfigQueryDao queryDao;
}
