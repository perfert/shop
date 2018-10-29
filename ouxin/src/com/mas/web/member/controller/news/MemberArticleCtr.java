/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.news;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.mas.user.domain.entity.news.Article;
import com.mas.user.domain.entity.news.ArticleGood;
import com.mas.user.domain.entity.news.Comment;
import com.mas.user.service.news.ArticleGoodService;
import com.mas.user.service.news.ArticleService;
import com.mas.user.service.news.CommentService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.news.ArticleDto;
import com.mas.web.member.controller.dto.news.Image;
import com.mas.web.util.ServletUtil;

/**
 * 文章Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberArticleCtr.URI_PREFIX)
public class MemberArticleCtr extends JsonCtr<ArticleDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/article";
    @Autowired private ArticleService articleService;
    @Autowired private ArticleGoodService articleGoodService;
    @Autowired private CommentService commentService;
    private static final Logger log = LogManager.getLogger( MemberArticleCtr.class );
    
    /** 
     *  查出好友所有文章
     *  @param   username   账号
     *  @param   friends   好友账号
     */
    @RequestMapping( value = "friends", method = RequestMethod.POST )
    public void friends_list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ArticleDto.class,log,new DoYourBuniness<ArticleDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleDto dto)  {
                JSONArray array = new JSONArray();
                List<Article> list = articleService.queryByMemberId(false,dto.getFriendId(),dto.getPagenum(),dto.getPagesize());
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        Article article = list.get(i);
                        JSONObject mJson = new JSONObject();
                        mJson.put("id", article.getId());
                        mJson.put("nickname",article.getNick());
                        mJson.put("portraitUri", HOST + article.getAvatar());
                        
                        mJson.put("type",article.getType());
                        mJson.put("content", article.getContent());
                        if (article.getType() == Article.Type.IMAGE.getValue()) {
                            //图片列表,用prefix分隔0文本消息,1图文,2视频
                            JSONArray imageArray = new JSONArray();
                            if(StringUtils.isNotEmpty(article.getImgPath())){
                                String[] images = article.getImgPath().split(Article.Article_IMAGE_PATH_SPLIT);
                                for(int j = 0;j< images.length;j++){
                                    JSONObject imageJ = new JSONObject();
                                    imageJ.put("small",HOST + article.getPrefix()+ images[j]);
                                    imageJ.put("big", HOST + article.getPrefix() + Article.BIG_IMAGE_PATH_PREFIX  + images[j]);
                                    imageArray.add(j, imageJ);
                                }
                            }
                            mJson.put("photos", imageArray);
                        }else if(article.getType() == Article.Type.VIDEO.getValue()){
                            if(StringUtils.isNotEmpty(article.getVideoPath())){
                                mJson.put("thumb", HOST + article.getPrefix()+ article.getThumbPath());
                                mJson.put("videoPath", HOST + article.getPrefix()+ article.getVideoPath());
                            }
                        }
                        
                        mJson.put("userId", article.getMemberId()); 
                        mJson.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,article.getCreateDate()));
                        mJson.put("token", article.getToken());
                        mJson.put("location", article.getLocation());
                      
                        List<Comment> commentList = commentService.queryByArticleId(dto.getMid(),article.getId());
                        JSONArray commentArray = new JSONArray();
                        if(null != commentList && commentList.size() > 0){
                            for (int j = 0; j < commentList.size(); j++) {
                                JSONObject commentJ = new JSONObject();
                                Comment comment = commentList.get(j);
                                commentJ.put("content", comment.getContent());
                                commentJ.put("userId", comment.getMemberId());
                                commentJ.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,comment.getCreateDate()) );
                                commentJ.put("token", comment.getToken());
                                commentJ.put("tag", comment.getTag());
                                commentJ.put("id", comment.getId());
                                commentJ.put("articleId", article.getId());
                                commentJ.put("nickname", comment.getNick());  //评论人昵称
                                commentJ.put("portraitUri", comment.getAvatar());
                                commentArray.add(j, commentJ);
                            }
                        }
                        mJson.put("countComment", null != commentList ? commentList.size() : 0);
                        mJson.put("comment", commentArray);
                        
                        List<ArticleGood> gList = articleGoodService.queryByArticleId(dto.getMid(),article.getId());
                        JSONArray goodArray = new JSONArray();
                        if(null != gList && gList.size() > 0){
                            for (int j = 0; j < gList.size(); j++) {
                                JSONObject goodJson = new JSONObject();
                                ArticleGood good = gList.get(j);
                                goodJson.put("id", good.getId());
                                goodJson.put("userID", good.getAccount());
                                goodJson.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,good.getCreateDate()) );
                                goodJson.put("token", good.getToken());
                                goodJson.put("state", good.getGoodState());
                                goodJson.put("articleId", good.getArticleId());
                                goodJson.put("nickname", good.getNick());  //点赞人昵称
                                goodJson.put("portraitUri", good.getAvatar());
                                goodArray.add(j,goodJson);
                            }
                        }
                        mJson.put("countLike", null != gList ? gList.size() : 0);
                        mJson.put("likes", goodArray);
                        array.add(i,mJson);
                    }
                }                    
                jsonObject.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                
                JSONObject pageData = new JSONObject();
                pageData.put("pageData", array);
                pageData.put("pagesize", dto.getPagesize());
                pageData.put("pagenum", dto.getPagenum());
                jsonObject.put(KEY_RESULT, pageData);
                success(jsonObject,null,dto.getLang());
            }
        });

    }
    
    /**
     * @TODO nickname cbNickname cbUserId
     * 说说列表
     * @param   username   账号
     */
    @RequestMapping( value = "list", method = RequestMethod.POST )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ArticleDto.class,log,new DoYourBuniness<ArticleDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleDto dto) {
                JSONArray array = new JSONArray();
                List<Article> list = articleService.queryByMemberId(true,dto.getMid(),dto.getPagenum(),dto.getPagesize());
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        Article article = list.get(i);
                        JSONObject mJson = new JSONObject();
                        mJson.put("id", article.getId());
                        mJson.put("nickname",article.getNick());
                        mJson.put("portraitUri", HOST + article.getAvatar());
                        mJson.put("prefix", article.getPrefix());
                        if(StringUtils.isNotEmpty(article.getVideoPath())){
                            mJson.put("thumb", HOST + article.getPrefix()+ article.getThumbPath());
                            mJson.put("videoPath", HOST + article.getPrefix()+ article.getVideoPath());
                        }
                        mJson.put("content", article.getContent());
                        mJson.put("userId", article.getMemberId()); 
                        mJson.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,article.getCreateDate()));
                        mJson.put("token", article.getToken());
                        mJson.put("location", article.getLocation());
                        //图片列表,用prefix分隔
                        JSONArray imageArray = new JSONArray();
                        if(StringUtils.isNotEmpty(article.getImgPath())){
                            String[] images = article.getImgPath().split(Article.Article_IMAGE_PATH_SPLIT);
                            for(int j = 0;j< images.length;j++){
                                JSONObject imageJ = new JSONObject();
                                imageJ.put("small",HOST + article.getPrefix()+ images[j]);
                                imageJ.put("big", HOST + article.getPrefix() + Article.BIG_IMAGE_PATH_PREFIX  + images[j]);
                                imageArray.add(j, imageJ);
                            }
                        }
                        mJson.put("photos", imageArray);
                       
                        List<Comment> commentList = commentService.queryByArticleId(dto.getMid(),article.getId());
                        JSONArray commentArray = new JSONArray();
                        if(null != commentList && commentList.size() > 0){
                            for (int j = 0; j < commentList.size(); j++) {
                                JSONObject commentJ = new JSONObject();
                                Comment comment = commentList.get(j);
                                commentJ.put("content", comment.getContent());
                                commentJ.put("userId", comment.getMemberId());
                                commentJ.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,comment.getCreateDate()) );
                                commentJ.put("token", comment.getToken());
                                commentJ.put("tag", comment.getTag());
                                commentJ.put("id", comment.getId());
                                commentJ.put("articleId", article.getId());
                                commentJ.put("nickname", comment.getNick());  //评论人昵称
                                commentJ.put("portraitUri", comment.getAvatar());
                                commentArray.add(j, commentJ);
                            }
                        }
                        mJson.put("countComment", null != commentList ? commentList.size() : 0);
                        mJson.put("comment", commentArray);
                        
                        List<ArticleGood> gList = articleGoodService.queryByArticleId(dto.getMid(),article.getId());
                        JSONArray goodArray = new JSONArray();
                        if(null != gList && gList.size() > 0){
                            for (int j = 0; j < gList.size(); j++) {
                                JSONObject goodJson = new JSONObject();
                                ArticleGood good = gList.get(j);
                                goodJson.put("id", good.getId());
                                goodJson.put("userID", good.getAccount());
                                goodJson.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,good.getCreateDate()) );
                                goodJson.put("token", good.getToken());
                                goodJson.put("state", good.getGoodState());
                                goodJson.put("articleId", good.getArticleId());
                                goodJson.put("nickname", good.getNick());  //点赞人昵称
                                goodJson.put("portraitUri", good.getAvatar());
                                goodArray.add(j,goodJson);
                            }
                        }
                        mJson.put("countLike", null != gList ? gList.size() : 0);
                        mJson.put("likes", goodArray);
                        array.add(i,mJson);
                    }
                }                    
                jsonObject.put("time", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                
                JSONObject pageData = new JSONObject();
                pageData.put("pageData", array);
                pageData.put("pagesize", dto.getPagesize());
                pageData.put("pagenum", dto.getPagenum());
                jsonObject.put(KEY_RESULT, pageData);
                success(jsonObject,null,dto.getLang());
            }
        });

    }
    
    public static void main(String[] args) {
        ArticleDto dto = new ArticleDto();
        List<Image> images = new ArrayList<Image>();
        for (int i = 0; i < 3; i++) {
            Image image = new Image();
            image.setSmall("abc.jpg");
            image.setBig("ffbig.jpg");
            images.add(image);
        }
        dto.setImages(images);
        dto.setArticleId("22");
        
        double max = 12.1;
        System.out.println(getNumFormat("11.21"));
    }
    
    public static String getNumFormat(String num){
        int index = num.indexOf(".");
        String num2 = num;
        String result = "";
        if (index > 0) {
            num2 = num2.substring(index + 1, num.length());
            if (num2.length() < 2) {
                while (num2.length() < 2) {
                    num2 = num2 + "0";
                }
            } else if (num2.length() > 2) {
                System.out.println(num2.substring(0,2));
                while (num2.length() > 2) {
                    num2 = num2.substring(0, num2.length() - 1);
                }
            }
            result = num.substring(0, index) + "." + num2;
        } else {
            result = num + ".00";
        }
        return result;
    }
    
    
    /**
     * 发表说说图文
     * @param body[content:内容,location:地址,imageStr图片集合,split:图片集合分隔符,num图片数量]
     * 
     * @param request
     * @param response
     */
     @RequestMapping( value = "text" )
     public void text(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
         doInCallBack(response,body,ArticleDto.class,log,new DoYourBuniness<ArticleDto>() {
             
             @Override
             public void handleBusiness(JSONObject jsonObject,ArticleDto dto) {
                 Object id = articleService.createText(dto.getMid(),dto.getContent(),dto.getLocation());
                 JSONObject result = new JSONObject();
                 result.put("id",id);
                 jsonObject.put(KEY_RESULT, result);
                 success(jsonObject,null,dto.getLang());
             }
         });
     }
    
     
   /**
    * 发表说说图文
    * @param body[content:内容,location:地址,imageStr图片集合,split:图片集合分隔符,num图片数量]
    * 
    * @param request
    * @param response
    */
    @RequestMapping( value = "photo" )
    public void photo(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                String body = (String) multipart.getParameter("data");
                ArticleDto dto = (ArticleDto) parseObject(body, ArticleDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                String prefix = null;
                String imageStr = "";
                
                String uploadPath = "upload/" + dto.getMid() + SPLIT +  Article.Article_IMAGE_PATH_PREFIX + SPLIT;
                Long time = System.currentTimeMillis();
                
                for (int i = 0; i < dto.getImages().size(); i++) {
                    Image image = dto.getImages().get(i);
                    if(StringUtils.isNotEmpty(image.getSmall())){
                        MultipartFile multipartFile = multipart.getFile(image.getSmall());
                        MultipartFile bigMultipartFile = multipart.getFile(image.getBig());
                        String imgPath = getNowTime(time);
                        time = time + 1;
                        imageStr = imageStr + imgPath + Article.Article_IMAGE_PATH_SPLIT;
                        
                        File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), imgPath);
                        //FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                        Thumbnails.of(multipartFile.getInputStream())
                        .scale(1f).outputQuality(0.8f)
                        .outputFormat("jpg")
                        .toFile(outFile);
                        
                        File outFile2 = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath),  Article.BIG_IMAGE_PATH_PREFIX  + imgPath);
                        //FileCopyUtils.copy(bigMultipartFile.getBytes(),outFile2);
                        Thumbnails.of(bigMultipartFile.getInputStream())
                        .scale(1f).outputQuality(0.8f)
                        .outputFormat("jpg")
                        .toFile(outFile2);
                    }else
                        continue;
                }    
                prefix = uploadPath;
                if(null != imageStr)
                    imageStr = imageStr.substring(0, imageStr.lastIndexOf(Article.Article_IMAGE_PATH_SPLIT));
                
                Object id = articleService.create(dto.getMid(),dto.getContent(),dto.getLocation(),prefix,imageStr);
                JSONObject result = new JSONObject();
                result.put("id",id);
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject,null,dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    /**
     * 发表说说视频
     */
    @RequestMapping( value = "video" )
    public void video(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                String body = (String) multipart.getParameter("data");
                ArticleDto dto = (ArticleDto) parseObject(body, ArticleDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                String prefix = null;
                String video = dto.getVideo();
                String thumb = dto.getThumb();
                
                String uploadPath = "upload/" + dto.getMid() + SPLIT +  Article.Article_IMAGE_PATH_PREFIX + SPLIT;
                MultipartFile multipartFile0 = multipart.getFile("video");
                if(null != multipartFile0){
                    video = UUID.randomUUID().toString() + ".mp4";//video.substring(video.lastIndexOf("."));
                    File outFile2 = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), video );
                    FileCopyUtils.copy(multipartFile0.getBytes(),outFile2);
                    /*Thumbnails.of(multipartFile0.getInputStream())
                    .scale(1f).outputQuality(0.8f)
                    .outputFormat("mp4")
                    .toFile(outFile2);*/
                }
                
                MultipartFile multipartFile1 = multipart.getFile("thumb");
                if(null != multipartFile1){
                    thumb = UUID.randomUUID().toString() + ".jpg";//thumb.substring(thumb.lastIndexOf("."));
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), thumb);
                    //FileCopyUtils.copy(multipartFile1.getBytes(),outFile);
                    Thumbnails.of(multipartFile1.getInputStream())
                    .scale(1f).outputQuality(0.8f)
                    .outputFormat("jpg")
                    .toFile(outFile);
                }
                prefix = uploadPath;
                
                Object id = articleService.createVideo(dto.getMid(),dto.getContent(),dto.getLocation(),prefix,thumb,video);
                JSONObject result = new JSONObject();
                result.put("id",id);
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject,null,dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    /**
     * 删除说说
     * @param body[mid:用户ID,articleId:文章ID]
     * @param request
     * @param response
     */
    @RequestMapping( value = "delete", method = RequestMethod.POST )
    public void social_delete(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ArticleDto.class,log,new DoYourBuniness<ArticleDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleDto dto)  {
                boolean success = articleService.delete(dto.getMid(),dto.getArticleId());
                if( success ){
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,"delete.error",dto.getLang());
                }
            }
        });
    }
    
    /**
     * 获取当前时间
     * @param millis
     * @return
     */
    public String getNowTime(Long millis){
        Date date = new Date(millis+1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS") ;
        return dateFormat.format(date) + ".jpg";
    }
    
    /*if(null != dto.getNum() && dto.getNum() > 0 && StringUtils.isNotEmpty(dto.getImageStr())){
        //图片列表
        String[] images = dto.getImageStr().split(dto.getSplit());//Article.Article_IMAGE_PATH_SPLIT
        Long time = System.currentTimeMillis();
        for (int i = 0; i < dto.getNum(); i++) {
            MultipartFile multipartFile = multipart.getFile("file_" + i);
            MultipartFile bigMultipartFile = multipart.getFile("file_" + i + "_big");
            String uploadPath = "upload/" + dto.getMid() + SPLIT +  Article.Article_IMAGE_PATH_PREFIX + SPLIT;
            
            String imgPath = getNowTime(time);
            time = time + 1;
            imageStr = imageStr + imgPath + Article.Article_IMAGE_PATH_SPLIT;
            File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), imgPath);
            FileCopyUtils.copy(multipartFile.getBytes(),outFile);
            
            File outFile2 = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath),  Article.BIG_IMAGE_PATH_PREFIX  + imgPath);
            FileCopyUtils.copy(bigMultipartFile.getBytes(),outFile2);
            prefix = uploadPath;
        }
        if(null != imageStr)
            imageStr = imageStr.substring(0, imageStr.lastIndexOf(Article.Article_IMAGE_PATH_SPLIT));
    }*/
}