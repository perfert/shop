/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.chat;
import java.io.File;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.file.util.FileUtil;
import com.mas.common.util.DateTimeUtil;
import com.mas.user.domain.entity.chat.vo.MsgType;
import com.mas.user.service.chat.MsgRecordService;
import com.mas.user.service.image.ImageService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.MsgRecordDto;
import com.mas.web.util.FileTypeUtil;
import com.mas.web.util.ServletUtil;

/**
 * 记录Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberMsgCtr.URI_PREFIX)
public class MemberMsgCtr extends JsonCtr<MsgRecordDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/msg/";
    
    @Autowired
    private MsgRecordService recordService;
    @Autowired
    private ImageService imageService;
    
    @RequestMapping( value = "send" ,method = RequestMethod.POST)
    public void send(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                MultipartFile fileFile = multipart.getFile("file");
                MultipartFile thumbFile = multipart.getFile("thumb");
                
                String data = (String) multipart.getParameter("data");
                MsgRecordDto dto = (MsgRecordDto) parseObject(data, MsgRecordDto.class);
                int msgType = dto.getMsgType();
                String sendId = dto.getSendId();
                String targetId = dto.getTargetId();
                String token = dto.getToken();
                String content = dto.getContent();
                int chatType = dto.getChatType();
                
                final StringBuffer outPath = new StringBuffer("upload/msg/record/");
                String folder = DateTimeUtil.format(DateTimeUtil.YYYYMMDD, new Date());
                outPath.append(folder);
                
                String filePath = null;
                String thumbPath = null;
                if(null != fileFile){ 
                    String suffix = null;
                    if(msgType == 4)
                        suffix = "amr";
                    else if (msgType == 5) {
                        suffix = dto.getFileType();
                    }else
                        suffix = FileTypeUtil.getFileType(fileFile.getInputStream());
                    String fileName = dto.getSendId() + FileTypeUtil.getUUID() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + outPath.toString() + SPLIT+ suffix + SPLIT), fileName);
                    FileCopyUtils.copy(fileFile.getBytes(),outFile);
                    filePath = outPath.toString() + SPLIT + suffix + SPLIT + fileName;
                }
                
                if(null != thumbFile){ 
                    String suffix = FileTypeUtil.getFileType(thumbFile.getInputStream());
                    String fileName = UUID.randomUUID().toString() + "." + suffix;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + outPath.toString() + SPLIT + suffix + SPLIT), fileName);
                    imageService.saveImage(thumbFile.getInputStream(), outFile, 1f, suffix);
                    thumbPath = outPath.toString() + SPLIT + suffix + SPLIT + fileName;
                }
                
                if(msgType == MsgType.TXT.value()){
                    recordService.persistenceTxt(sendId,targetId,token,chatType,msgType,content);
                }else if(msgType == MsgType.IMAGE.value()){
                    recordService.persistenceIMAGE(sendId,targetId,token,chatType,msgType,filePath);
                }else if(msgType == MsgType.VIDEO.value()){
                    recordService.persistenceVIDEO(sendId,targetId,token,chatType,msgType,filePath,thumbPath);
                }else if(msgType == MsgType.LOCATION.value()){
                    recordService.persistenceLOCATION(sendId,targetId,token,chatType,msgType,dto.getAddress(),dto.getLat(),dto.getLng());
                }else if(msgType == MsgType.VOICE.value()){
                    //length为秒
                    recordService.persistenceVOICE(sendId,targetId,token,chatType,msgType,dto.getLength(),filePath);
                }else if(msgType == MsgType.FILE.value()){
                    recordService.persistenceFILE(sendId,targetId,token,chatType,msgType,dto.getLength(),filePath);
                }
                success(jsonObject,"result.success",dto.getLang());
            }
        } catch( Exception ex ){
        }
        writeJson(jsonObject, response);
    }
    
}