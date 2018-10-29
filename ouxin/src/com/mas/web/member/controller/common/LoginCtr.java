/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.common;

import io.rong.models.TokenResult;
import io.rong.service.impl.IUserService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.file.util.FileUtil;
import com.mas.core.service.ServiceException;
import com.mas.security.util.RSAUtils;
import com.mas.system.domain.entity.Country;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.service.impl.CountryService;
import com.mas.system.service.impl.SmsConfigService;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.service.MemberService;
import com.mas.user.service.app.AdPositionService;
import com.mas.user.service.app.ApkService;
import com.mas.web.interceptor.springmvc.freemarker.RichFreeMarkerView;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.LoginDto;
import com.mas.web.member.controller.dto.chat.RegistCtrDto;
import com.mas.web.util.ServletUtil;

/**
 * 登录/注册
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(LoginCtr.URI_PREFIX)
public class LoginCtr extends JsonCtr<LoginDto>{
    
    private static final Logger log = LogManager.getLogger( LoginCtr.class );
    
    public static final String URI_PREFIX = JsonCtr.prefix;
    
    @Autowired private AdPositionService  adPositionService;
    @Autowired private MemberService service;
    @Autowired private SmsConfigService configService;
    @Autowired private ApkService apkService;
    @Autowired private IUserService rUserService;
    @Autowired private CountryService countryService;
    
    private PrivateKey privateKey = RSAUtils.keyStrToPrivate(RSAUtils.PRIVATE_KEY);  
   
    //国家列表(要重写)
    @RequestMapping( value = "countrys", method = RequestMethod.POST )
    public void countrys(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,LoginDto.class,log,new DoYourBuniness<LoginDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, LoginDto dto) {
                JSONArray result = new JSONArray();
                List<Country> list = countryService.queryAll();
                for (int i = 0; i < list.size(); i++) {
                    Country country = list.get(i);
                    JSONObject bean = new JSONObject(); 
                    bean.put("cn", country.getName());
                    bean.put("en", country.getCnName());
                    if(country.getCode().equals("86"))
                        bean.put("check", 1);
                    else
                        bean.put("check", 0);
                    bean.put("lang", country.getLang());
                    bean.put("code", country.getCode());
                    bean.put("orderNum", i+1);
                    result.add(i,bean);
                }
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    
    //获取闪屏页面广告图
    @RequestMapping( value = "init", method = RequestMethod.POST )
    public void init(HttpServletRequest request, HttpServletResponse response, RegistCtrDto dto){
        JSONObject jsonObject = new JSONObject(); 
        JSONArray array = new JSONArray();
        String lang = dto.getLang();
        try {
            List<Ad> list =  adPositionService.getAdListBySign("oxinBg");  
            Apk apk = apkService.getTheLatestApk();
            if( null != list && list.size() > 0 ){
                for (int i = 0; i < list.size(); i++) {
                    Ad ad = list.get(i);
                    JSONObject mJson = new JSONObject();
                    mJson.put("title", ad.getTitle());
                    mJson.put("type", ad.getType());
                    if(Ad.Type.TEXT.value() != ad.getType())
                        mJson.put("path", ad.getFilePath());
                    else
                        mJson.put("content", ad.getContent());
                    mJson.put("url", ad.getUrl());
                    array.add(i,mJson);
                }
                jsonObject.put("adlist", array);
            }
            if(null != apk){
                JSONObject data = new JSONObject();
                data.put("versionName",apk.getVersionName());
                data.put("version",apk.getVersion());
                String detail = apk.getDetail();
                if(StringUtils.isNotEmpty(detail) )
                    data.put("detail",apk.getDetail());
                else
                    data.put("detail","");
                data.put("filePath",apk.getFilePath());
                jsonObject.put("apk", data);
            }
            jsonObject.put( "statusCode", 200 );
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        writeJson(jsonObject, response);
    }

    //注册
    @RequestMapping( value = "register", method = RequestMethod.POST )
    public void register(HttpServletRequest request, HttpServletResponse response){//@RequestBody String body
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                String data = (String) multipart.getParameter("data");
                RegistCtrDto dto = (RegistCtrDto) parseObject(data, RegistCtrDto.class);
                lang = dto.getLang();
                boolean success = configService.checkCode(SmsCode.TYPE_REGISTER, dto.getCode(), dto.getAccount(), dto.getCaptcha());
                if(!success)
                    throw new ServiceException( "code.error" );
                
                MultipartFile multipartFile = multipart.getFile("avatar");
                String fileName = null;
                String uploadPath = Member.AVATAR_Path;
                if(null != multipartFile){
                    
                    fileName = UUID.randomUUID().toString() + ".png";
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), fileName);
                    //FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    Thumbnails.of(multipartFile.getInputStream())
                    .scale(1f).outputQuality(0.9f)
                    .outputFormat("png")
                    .toFile(outFile);
                }
                if(StringUtils.isNotEmpty(fileName))
                    dto.setHeadImage(uploadPath + fileName);
                else
                    //设置默认头像
                    dto.setHeadImage(LOGO);
                
                String privateDecryptedResult = RSAUtils.decryptedToStrByPrivate(dto.getPassword(),privateKey); 
                service.register(dto.getCode() ,dto.getAccount(), privateDecryptedResult, dto.getNickname(),dto.getHeadImage());
                success(jsonObject,"register.success",dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject, lang);
        }
        writeJson(jsonObject, response);
    }

    //用户token
    @RequestMapping( value = "token", method = RequestMethod.POST )
    public void token(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        JSONObject jsonObject = new JSONObject(); 
        LoginDto dto = null;
        try {
            dto = (LoginDto) parseObject(body, LoginDto.class);
            String macKey = service.getToken(dto.getMid(), new Date(), 7);
            if(macKey.equalsIgnoreCase(dto.getToken())){
                Member member = service.get(dto.getMid());
                if( null != member ){
                    if(StringUtils.isEmpty(member.getAvatar()))
                        member.setAvatar(LOGO);
                    TokenResult token = rUserService.getToken(member.getId().toString(), member.getUsername(),HOST + member.getAvatar());
                    JSONObject result = new JSONObject();
                    result.put("token", token.getToken());
                    result.put("id", member.getId());
                    result.put("mchKey", member.getToken());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,"login.success",dto.getLang());
                    member.setPassword( null );
                    request.getSession().setAttribute( RichFreeMarkerView.CURRENT_LOGIN_MEMBER, member );
                }
            }else {
                error(jsonObject, "login.error",dto.getLang());
            }
        } catch( Exception ex ){
           handlerException(ex, log, jsonObject,null != dto? dto.getLang() : DEFAULT_LANG);
        }
        writeJson(jsonObject, response);
    }
   
   
   //登录
	@RequestMapping( value = "login", method = RequestMethod.POST )
	public void login(final HttpServletRequest request, HttpServletResponse response,@RequestBody String body){
	    doInCallBack(response,body,LoginDto.class,log,new DoYourBuniness<LoginDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, LoginDto dto) throws Exception {
                String privateDecryptedResult = RSAUtils.decryptedToStrByPrivate(dto.getPassword(),privateKey); 
                Member member = service.login( dto.getCode(), dto.getAccount(), privateDecryptedResult );
                if( null != member ){
                    if(StringUtils.isEmpty(member.getAvatar()))
                        member.setAvatar(LOGO);
                    TokenResult token = rUserService.getToken(member.getId().toString(), member.getUsername(),HOST +  member.getAvatar());
                    JSONObject result = new JSONObject();
                    result.put("token", token.getToken());
                    result.put("id", member.getId());
                    result.put("mchKey", member.getToken());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,"login.success",dto.getLang());
                    member.setPassword( null );
                    request.getSession().setAttribute( RichFreeMarkerView.CURRENT_LOGIN_MEMBER, member );
                } else {
                    error(jsonObject, "login.error",dto.getLang());
                }
            }
        });

	}
	
	@RequestMapping( value = "login2", method = RequestMethod.POST )
    public void login2(final HttpServletRequest request, HttpServletResponse response,@RequestBody String body){
        doInCallBack(response,body,LoginDto.class,log,new DoYourBuniness<LoginDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, LoginDto dto) throws Exception {
                Member member = service.login( dto.getCode(), dto.getAccount(), dto.getPassword() );
                if( null != member ){
                    if(StringUtils.isEmpty(member.getAvatar()))
                        member.setAvatar(LOGO);
                    TokenResult token = rUserService.getToken(member.getId().toString(), member.getUsername(),HOST +  member.getAvatar());
                    JSONObject result = new JSONObject();
                    result.put("token", token.getToken());
                    result.put("id", member.getId());
                    result.put("mchKey", member.getToken());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,"login.success",dto.getLang());
                    member.setPassword( null );
                    request.getSession().setAttribute( RichFreeMarkerView.CURRENT_LOGIN_MEMBER, member );
                } else {
                    error(jsonObject, "login.error",dto.getLang());
                }
            }
        });

    }
    
	
	public static void main(String[] args) throws IOException {
	    
	    String fileName = UUID.randomUUID().toString() + ".jpg";
        File inFile = new File("E:/a.jpg");
        File outFile = new File(new File("E:/photo"),fileName);
        Thumbnails.of(inFile)
        .scale(1f).outputQuality(0.9f)
        .outputFormat("jpg")
        .toFile(outFile);
	    //Locale local = new Locale("en","US");
        /*Locale local = new Locale("zh");
	    System.out.println(local.getCountry());
	    
	    
	    String token = FileTypeUtil.getUUID() + new Random().nextInt(1000);
        System.out.println(token);
        
	    String foldername = "E:/dong";
        File folder = new File(foldername);
        if (folder == null || !folder.exists()) {  
            folder.mkdir();
        }
        String fileName="/dongge.amr";
        String path = foldername + fileName;
        File targetFile = new File(foldername + fileName);
        try{
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }
            OutputStream output = new FileOutputStream(targetFile);
            //读取大文件
            String p = "E:/web/apache-tomcat-7.0.73/webapps/chat/upload/record/audio/a714fd74-4bb4-4f2c-b57f-7b73c00e867b.amr";
            FileInputStream input = new FileInputStream(p);
            byte[] buffer=new byte[4*1024];
            while(input.read(buffer)!=-1){
                output.write(buffer);
            }
            if(null != output)
                output.close();
            if(null != input)
                input.close();
            
            DecimalFormat formater = new DecimalFormat("##0.00");
            formater.setMaximumFractionDigits(2);
            formater.setGroupingSize(0);
            formater.setRoundingMode(RoundingMode.FLOOR);
            System.out.println(formater.format(Float.parseFloat("38.9500000000")));
        } catch (Exception e) {
        }*/
    }
	
	//设置外部只有下载权限，内部有上传权限
	@RequestMapping("/download/{fileName}")
    public String download(@PathVariable String fileName, HttpServletRequest request,HttpServletResponse response) {
        if (fileName != null) {
            String realPath = request.getServletContext().getRealPath("WEB-INF/apk/");
            fileName = fileName + ".apk";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/form-data");//response.setContentType("application/force-download");// 设置强制下载不打开
                response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
                //app-debug.apk
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                                e.printStackTrace();
                } finally {
                    try {
                        if(null != bis)
                            bis.close();
                        if (null != fis) 
                            fis.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
             }    
        }
        return null;
    }
	

}