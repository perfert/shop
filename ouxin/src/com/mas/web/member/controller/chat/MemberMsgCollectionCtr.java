/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.chat;
import java.io.File;
import java.util.Date;
import java.util.List;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.file.util.FileUtil;
import com.mas.common.util.DateTimeUtil;
import com.mas.security.util.verify.ApiUtil;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.MsgCollection;
import com.mas.user.service.chat.MsgCollectionService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.CollectionDto;
import com.mas.web.util.FileTypeUtil;
import com.mas.web.util.ServletUtil;

/**
 * MemberMsgCtr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberMsgCollectionCtr.URI_PREFIX)
public class MemberMsgCollectionCtr extends JsonCtr<CollectionDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/collection";
    
    @Autowired
    private MsgCollectionService service;
    
    @RequestMapping( value = "text" )
    public void text(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,CollectionDto.class,log,new DoYourBuniness<CollectionDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, CollectionDto dto) {
                service.text(dto.getMid(), dto.getYid(), dto.getContent());
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    @RequestMapping( value = "image", method = RequestMethod.POST )
    public void image(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                
                String data = (String) multipart.getParameter("data");
                CollectionDto dto = (CollectionDto) parseObject(data, CollectionDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                MultipartFile multipartFile = multipart.getFile("image");
                String filePath = null;
                
                if(null != multipartFile){ 
                    String suffix = FileTypeUtil.getFileType(multipartFile.getInputStream());
                    String fileName = UUID.randomUUID().toString() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT), fileName);
                    FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    
                    Thumbnails.of(multipartFile.getInputStream())
                    .scale(1f).outputQuality(0.9f)
                    .outputFormat("png")
                    .toFile(outFile);
                    
                    filePath = "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT + fileName;
                }
                service.image(dto.getMid(), dto.getYid(), filePath);
                success(jsonObject,null,dto.getLang());
            }else
                error(jsonObject, null,LANG);
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    @RequestMapping( value = "video", method = RequestMethod.POST )
    public void video(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                String data = (String) multipart.getParameter("data");
                CollectionDto dto = (CollectionDto) parseObject(data, CollectionDto.class);
                lang = dto.getLang();
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                MultipartFile multipartFile = multipart.getFile("video");
                MultipartFile thumbFile = multipart.getFile("thumb");
                String filePath = null;
                String thumbPath = null;
                
                if(null != multipartFile){ 
                    String suffix = FileTypeUtil.getFileType(multipartFile.getInputStream());
                    String fileName = UUID.randomUUID().toString() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT), fileName);
                    FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    
                    filePath = "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT + fileName;
                }
                
                if(null != thumbFile){ 
                    String suffix = FileTypeUtil.getFileType(thumbFile.getInputStream());
                    String fileName = UUID.randomUUID().toString() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT), fileName);
                    FileCopyUtils.copy(thumbFile.getBytes(),outFile);
                
                    thumbPath = "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT + fileName;
                }
                service.video(dto.getMid(), dto.getYid(), thumbPath,filePath,dto.getVideoTime());
                success(jsonObject,null,dto.getLang());
            }else
                error(jsonObject, null,LANG);
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    @RequestMapping( value = "voice" , method = RequestMethod.POST)
    public void voice(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                
                String data = (String) multipart.getParameter("data");
                CollectionDto dto = (CollectionDto) parseObject(data, CollectionDto.class);
                lang = dto.getLang();
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                MultipartFile multipartFile = multipart.getFile("voice");
                String filePath = null;
                
                if(null != multipartFile){ 
                    String suffix = "amr";
                    String fileName = UUID.randomUUID().toString() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT), fileName);
                    FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    
                    filePath = "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT + fileName;
                }
                service.voice(dto.getMid(), dto.getYid(), filePath,dto.getVoiceTime());
                success(jsonObject,null,dto.getLang());
            }else
                error(jsonObject, null,LANG);
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    @RequestMapping( value = "file", method = RequestMethod.POST )
    public void file(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                
                String data = (String) multipart.getParameter("data");
                CollectionDto dto = (CollectionDto) parseObject(data, CollectionDto.class);
                lang = dto.getLang();
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                MultipartFile multipartFile = multipart.getFile("file");
                String filePath = null;
                
                if(null != multipartFile){ 
                    String fileName = UUID.randomUUID().toString();
                    String suffix = FileTypeUtil.getFileType(multipartFile.getInputStream());
                    if(StringUtils.isNotEmpty(suffix)){
                        fileName = fileName + "." + suffix;
                    }else{
                        if(StringUtils.isNotEmpty(dto.getFileName())){
                            if(dto.getFileName().contains(".")) {
                                suffix = dto.getFileName().substring(dto.getFileName().lastIndexOf("."));
                            }else
                                suffix = "";
                        }else
                            suffix = "";
                    }
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + "file" + SPLIT), fileName);
                    FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    
                    filePath = "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + "file" + SPLIT + fileName;
                }
                service.file(dto.getMid(), dto.getYid(), filePath,dto.getFileSize());
                success(jsonObject,null,lang);
            }else
                error(jsonObject, null,lang);
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
   
    //地理位置
    @RequestMapping( value = "location", method = RequestMethod.POST )
    public void location(HttpServletRequest request, HttpServletResponse response){
        String lang = LANG;
        String key = null;
        JSONObject jsonObject = new JSONObject(); 
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                String data = (String) multipart.getParameter("data");
                CollectionDto dto = (CollectionDto) parseObject(data, CollectionDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                MultipartFile multipartFile = multipart.getFile("location");
                String filePath = null;
                if(null != multipartFile){ 
                    String suffix = "jpg";//FileTypeUtil.getFileType(multipartFile.getInputStream());
                    String fileName = UUID.randomUUID().toString() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT), fileName);
                    FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    
                    filePath = "upload/" + dto.getMid() + SPLIT +  MsgCollection.MSG_IMAGE_PATH_PREFIX + SPLIT + suffix + SPLIT + fileName;
                }
                service.location(dto.getMid(), dto.getYid(), dto.getLat(),dto.getLng(),dto.getAddress(),filePath);
                success(jsonObject,null,dto.getLang());
            }else
                error(jsonObject, null,lang);
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    public static void main(String[] args) {
        int i = 3;
        String s = "ab123cd123,f123";
        for (int j = 0; j < 3; j++) {
            s = s.replaceFirst("123", new Random().nextInt() +"");
        }
       System.out.println(s);
       
       String thumb = "thumb";
       thumb = thumb.substring(thumb.lastIndexOf("."));
       System.out.println(thumb);
    }
    
    
    @RequestMapping( value = "delete", method = RequestMethod.POST )
    public void delete(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String key = null;
        String lang = DEFAULT_LANG;
        try {
            CollectionDto dto = (CollectionDto) parseObject(body, CollectionDto.class);
            key = memberService.getToken(dto.getMid(), new Date(), 7);
            lang = dto.getLang();
            if( service.delete(dto.getId(),dto.getMid()) ){
                success(jsonObject,null,dto.getLang());
            } else {
                error(jsonObject,"delete.error",dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    @RequestMapping( value = "list", method = RequestMethod.POST )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        JSONArray array = new JSONArray();
        String key = null;
        String lang = DEFAULT_LANG;
        try {
            CollectionDto dto = (CollectionDto) parseObject(body, CollectionDto.class);
            key = dto.getMid();
            lang = dto.getLang();
            List<MsgCollection> list = service.queryByMemberUsername(dto.getMid(),dto.getPagenum(),dto.getPagesize());
            if( null != list && list.size() > 0 ){
                for (int i = 0; i < list.size(); i++) {
                    MsgCollection msgCollection = list.get(i);
                    JSONObject mJson = new JSONObject();
                    mJson.put("id", msgCollection.getId()); 
                    mJson.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,msgCollection.getCreateDate()));
                    mJson.put("type", msgCollection.getType());
                    
                    if(null != msgCollection.getCoverMemberId()){
                        JSONObject user = new JSONObject();
                        user.put(Member.JSON_KEY_ID, msgCollection.getCoverMemberId());
                        user.put(Member.JSON_KEY_NICK, msgCollection.getNick());
                        user.put(Member.JSON_KEY_AVATAR, HOST + msgCollection.getAvatar());
                        mJson.put("user", user);
                    }
                    
                    // 收藏类型,(0文本消息,1图片,2语音,3视频,4地理位置,5文件,100 自定义消息类型)
                    if(msgCollection.getType() == MsgCollection.Type.TXT.value()){
                        mJson.put("content", msgCollection.getContent());
                    }else if(msgCollection.getType() == MsgCollection.Type.IMAGE.value()) {
                        mJson.put("imageUrl", HOST + msgCollection.getImageUrl());
                    }else if(msgCollection.getType() == MsgCollection.Type.VOICE.value()) {
                        mJson.put("voiceUrl", HOST + msgCollection.getVoiceUrl());
                        mJson.put("voiceTime", msgCollection.getVoiceTime());
                    }else if(msgCollection.getType() == MsgCollection.Type.VIDEO.value()) {
                        mJson.put("videoUrl", HOST + msgCollection.getVideoUrl());
                        mJson.put("thumbUrl", HOST + msgCollection.getThumb());
                        mJson.put("videoTime", msgCollection.getVideoTime());
                    }else if(msgCollection.getType() == MsgCollection.Type.LOCATION.value()) {
                        mJson.put("lat", msgCollection.getLat());
                        mJson.put("lng", msgCollection.getLng());
                        mJson.put("address", msgCollection.getAddress());
                        mJson.put("locaitonUrl",HOST +  msgCollection.getLocationUrl());
                    }else if(msgCollection.getType() == MsgCollection.Type.FILE.value()) {
                        mJson.put("fileUrl", HOST + msgCollection.getFileUrl());
                        mJson.put("fileSize", msgCollection.getFileSize());
                    }
                    array.add(i,mJson);
                }
            } 
            jsonObject.put(KEY_RESULT, array);
            success(jsonObject,null,dto.getLang());
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
	private static final Logger log = LogManager.getLogger( MemberMsgCollectionCtr.class );
}