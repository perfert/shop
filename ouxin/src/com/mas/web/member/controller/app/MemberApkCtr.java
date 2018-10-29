package com.mas.web.member.controller.app;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.service.app.ApkService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.BaseDto;

/**
 * 版本Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberApkCtr.URI_PREFIX)
public class MemberApkCtr extends JsonCtr<BaseDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/version";
    @Autowired private ApkService apkService;
    
    private static final Logger log = LogManager.getLogger( MemberApkCtr.class );
    
    @RequestMapping( value = "init", method = RequestMethod.POST )
    public void init(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        try {
            Apk apk = apkService.getTheLatestApk();
            if(null != apk){
                jsonObject.put("filePath", HOST + apk.getFilePath());
                jsonObject.put( "statusCode", 200 );
            }else{
                jsonObject.put( "statusCode", 0 );
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject);
        }
        writeJson(jsonObject, response);
    }
    
    @RequestMapping( value = "latest", method = RequestMethod.POST )
    public void latest(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,BaseDto.class,log,new DoYourBuniness<BaseDto>() {
            
            @Override
            public void handleBusiness(com.alibaba.fastjson.JSONObject jsonObject, BaseDto dto) throws JSONException {
                Apk apk = apkService.getTheLatestApk();
                if(null != apk){
                    JSONObject data = new JSONObject();
                    data.put("versionName",apk.getVersionName());
                    data.put("version",apk.getVersion());
                    //带语言说明
                    String detail = apk.getDetail();
                    if(StringUtils.isNotEmpty(detail) )
                        data.put("detail",apk.getDetail());
                    else
                        data.put("detail","");
                    data.put("filePath",HOST + apk.getFilePath());
                    jsonObject.put( KEY_RESULT, data );
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
    
    
    
    
}