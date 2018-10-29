package com.mas.web.member.controller.info;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.file.util.FileUtil;
import com.mas.security.util.verify.ApiUtil;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.domain.entity.news.Article;
import com.mas.user.domain.entity.vo.MemberDetail;
import com.mas.user.service.MemberService;
import com.mas.user.service.chat.FriendsService;
import com.mas.user.service.chat.SignService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.info.InfoDto;
import com.mas.web.util.ServletUtil;


/**
 * Controller - 会员中心 - 个人资料
 */
@Controller
@RequestMapping( ProfileCtr.URI_PREFIX )
public class ProfileCtr extends JsonCtr<InfoDto> {

	private static final Logger log = LogManager.getLogger( ProfileCtr.class );
	public static final String URI_PREFIX = JsonCtr.prefix + "/info";
	
	@Autowired private MemberService service;
	@Autowired private FriendsService friendsService;
    @Autowired private SignService signService;
    
    /**
     * 获取二维码
     */
    @RequestMapping( value = "qrcode", method = RequestMethod.POST )
    public void qrcode(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,InfoDto.class,log,new DoYourBuniness<InfoDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,InfoDto dto)  {
                JSONObject result = new JSONObject();
                String code = service.getQrcode(dto.getMid());
                result.put("qrcode",HOST + code);
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
   
    /**
     * 获取个人信息
     */
    @RequestMapping( value = "mine", method = RequestMethod.POST )
    public void userinfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,InfoDto.class,log,new DoYourBuniness<InfoDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,InfoDto dto) {
                //验证是否登录
                Member user = service.get(dto.getMid());
                JSONObject result = new JSONObject();
                result.put(Member.JSON_KEY_ID, user.getId());
                result.put(Member.JSON_KEY_NICK, user.getNickName());
                result.put(Member.JSON_KEY_CODE, user.getCode());
                result.put(Member.JSON_KEY_SPLIT, Member.CODE_ACCOUNT_SPLIT);
                result.put(Member.JSON_KEY_USERNAME, user.getUsername());
                //result.put(Member.JSON_KEY_OXID, user.getWxid());
                result.put(Member.JSON_KEY_SEX, user.getSex());
                if(StringUtils.isEmpty(user.getAvatar()))
                    user.setAvatar(LOGO);
                result.put(Member.JSON_KEY_AVATAR, HOST + user.getAvatar());
                result.put(Member.JSON_KEY_TEL, user.getMobile());
                result.put(Member.JSON_KEY_CITY, null != user.getCity() ? user.getCity() : "");
                //result.put(Member.JSON_KEY_PROVINCE,null != user.getProvince() ? user.getProvince() : "");
                result.put(Member.JSON_KEY_SIGN,user.getSign());
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });

    }
    
    //获取好友信息
    @RequestMapping( value = "friends2", method = RequestMethod.POST )
    public void data2(@RequestBody String body,HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable Map<String, Object> params){
        doInCallBack(response,body,InfoDto.class,log,new DoYourBuniness<InfoDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,InfoDto dto)  {
                String mid = dto.getMid();
                String username = dto.getUsername();
                int num = null != dto.getNum() && dto.getNum() < 10 ? dto.getNum() : 3;
                List<MemberDetail> members = null;
                
                Member mMember = service.get(mid);
                Member fMember = service.getByUsername(username);
                if(StringUtils.isNotEmpty(username) && !mMember.getUsername().equals(username)){
                    //查看朋友的朋友圈以及我设置的好友备注
                    members = service.getFriendDetail(mid,fMember.getId().toString(),3);
                }else{
                    members = service.getMemberDetail(mid,3);
                }
                
                if(null != members && members.size() > 0){
                    JSONObject userJson = new JSONObject();
                    JSONObject userInfo = new JSONObject();
                    MemberDetail member = members.get(0);
                    
                    userJson.put("displayName",member.getNickName()); 
                    if(StringUtils.isNotEmpty(member.getAlias())){
                        userJson.put("displayName",member.getAlias());   
                        //好友个人信息设置
                        JSONObject friendDetail = new JSONObject();
                        friendDetail.put("alias", member.getAlias());
                        friendDetail.put("phone", StringUtils.isNotEmpty(member.getPhone()) ? member.getPhone() : "");
                        friendDetail.put("detail", StringUtils.isNotEmpty(member.getDetail()) ? member.getDetail() : "" );
                        friendDetail.put("img", StringUtils.isNotEmpty(member.getImg()) ? member.getImg() : "");
                        userInfo.put("friendDetail",friendDetail);
                    }
                    userInfo.put(Member.JSON_KEY_ID, member.getId());   
                    userInfo.put(Member.JSON_KEY_NICK, member.getNickName());
                    userInfo.put(Member.JSON_KEY_CODE, member.getCode());
                    userInfo.put(Member.JSON_KEY_SPLIT, Member.CODE_ACCOUNT_SPLIT);
                    userInfo.put(Member.JSON_KEY_USERNAME, member.getUsername());
                    userInfo.put(Member.JSON_KEY_OXID, member.getWxid());
                    userInfo.put(Member.JSON_KEY_SEX, member.getSex());
                    if(StringUtils.isEmpty(member.getAvatar()))
                        member.setAvatar(LOGO);
                    userInfo.put(Member.JSON_KEY_AVATAR,HOST + member.getAvatar());
                    userInfo.put(Member.JSON_KEY_TEL, member.getMobile());
                    userInfo.put(Member.JSON_KEY_CITY, null != member.getCity() ? member.getCity() : "");
                    userInfo.put(Member.JSON_KEY_PROVINCE,null != member.getProvince() ? member.getProvince() : "");
                    userInfo.put(Member.JSON_KEY_SIGN,member.getSign());
                    
                    JSONArray imgArray = new JSONArray();
                    if(null != member.getType()){
                        int size = 0;
                        stop:for(MemberDetail detail : members){
                            String prefix =  detail.getPrefix();
                            if(detail.getType() == Article.Type.IMAGE.getValue()){
                                if(StringUtils.isNotEmpty(detail.getImgPath())){
                                    String[] images = detail.getImgPath().split("split");
                                    for (int i = 0; i < images.length; i++) {
                                        if(size > num - 1)
                                            break stop;
                                        imgArray.add(size, HOST + prefix + images[i]);
                                        size++;
                                    }
                                }
                            }else if(detail.getType() == Article.Type.VIDEO.getValue()){
                                if(StringUtils.isNotEmpty(detail.getThumbPath())){
                                    if(size > num - 1)
                                        break stop;
                                    imgArray.add(size, HOST + prefix + detail.getThumbPath());
                                    size++;
                                }
                            }else{
                                continue;
                            }
                        }
                    }
                    userInfo.put("photos",imgArray);
                    userJson.put("user",userInfo);  
                    jsonObject.put( KEY_RESULT, userJson );
                    success(jsonObject,null,dto.getLang());
                }else {
                    error(jsonObject, "user.error.noexist",dto.getLang());
                }
            }
        });

    }
    
    //获取好友信息
    @RequestMapping( value = "friends", method = RequestMethod.POST )
    public void data(@RequestBody String body,HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable Map<String, Object> params){
        doInCallBack(response,body,InfoDto.class,log,new DoYourBuniness<InfoDto>() {
            
            @Override
            public void handleBusiness(com.alibaba.fastjson.JSONObject jsonObject,InfoDto dto){
                String mid = dto.getMid();
                String friendId = dto.getFriendId();
                int num = null != dto.getNum() && dto.getNum() < 10 ? dto.getNum() : 3;
                List<MemberDetail> members = null;
                if(StringUtils.isNotEmpty(friendId)){
                    //查看朋友的朋友圈以及我设置的好友备注
                    members = service.getFriendDetail(mid,friendId,3);
                }else{
                    members = service.getMemberDetail(mid,3);
                }
                
                if(null != members && members.size() > 0){
                    com.alibaba.fastjson.JSONObject userJson = new com.alibaba.fastjson.JSONObject();
                    com.alibaba.fastjson.JSONObject userInfo = new com.alibaba.fastjson.JSONObject();
                    MemberDetail member = members.get(0);
                    
                    userJson.put("displayName",member.getNickName()); 
                    if(StringUtils.isNotEmpty(member.getAlias())){
                        userJson.put("displayName",member.getAlias());   
                        //好友个人信息设置
                        JSONObject friendDetail = new JSONObject();
                        friendDetail.put("alias", member.getAlias());
                        friendDetail.put("phone", StringUtils.isNotEmpty(member.getPhone()) ? member.getPhone() : "");
                        friendDetail.put("detail", StringUtils.isNotEmpty(member.getDetail()) ? member.getDetail() : "" );
                        friendDetail.put("img", StringUtils.isNotEmpty(member.getImg()) ? member.getImg() : "");
                        userInfo.put("friendDetail",friendDetail);
                    }
                    userInfo.put(Member.JSON_KEY_ID, member.getId());   
                    userInfo.put(Member.JSON_KEY_NICK, member.getNickName());
                    userInfo.put(Member.JSON_KEY_CODE, member.getCode());
                    userInfo.put(Member.JSON_KEY_SPLIT, Member.CODE_ACCOUNT_SPLIT);
                    userInfo.put(Member.JSON_KEY_USERNAME, member.getUsername());
                    userInfo.put(Member.JSON_KEY_OXID, member.getWxid());
                    userInfo.put(Member.JSON_KEY_SEX, member.getSex());
                    if(StringUtils.isEmpty(member.getAvatar()))
                        member.setAvatar(LOGO);
                    userInfo.put(Member.JSON_KEY_AVATAR,HOST + member.getAvatar());
                    userInfo.put(Member.JSON_KEY_TEL, member.getMobile());
                    userInfo.put(Member.JSON_KEY_CITY, null != member.getCity() ? member.getCity() : "");
                    userInfo.put(Member.JSON_KEY_PROVINCE,null != member.getProvince() ? member.getProvince() : "");
                    userInfo.put(Member.JSON_KEY_SIGN,member.getSign());
                    
                    com.alibaba.fastjson.JSONArray imgArray = new com.alibaba.fastjson.JSONArray();
                    if(null != member.getType()){
                        int size = 0;
                        stop:for(MemberDetail detail : members){
                            String prefix =  detail.getPrefix();
                            if(detail.getType() == Article.Type.IMAGE.getValue()){
                                if(StringUtils.isNotEmpty(detail.getImgPath())){
                                    String[] images = detail.getImgPath().split("split");
                                    for (int i = 0; i < images.length; i++) {
                                        if(size > num - 1)
                                            break stop;
                                        imgArray.add(size, HOST + prefix + images[i]);
                                        size++;
                                    }
                                }
                            }else if(detail.getType() == Article.Type.VIDEO.getValue()){
                                if(StringUtils.isNotEmpty(detail.getThumbPath())){
                                    if(size > num - 1)
                                        break stop;
                                    imgArray.add(size, HOST + prefix + detail.getThumbPath());
                                    size++;
                                }
                            }else{
                                continue;
                            }
                        }
                    }
                    userInfo.put("photos",imgArray);
                    userJson.put("user",userInfo);  
                    jsonObject.put( KEY_RESULT, userJson );
                    success(jsonObject,null,dto.getLang());
                }else {
                    error(jsonObject, "user.error.noexist",dto.getLang());
                }
            }
        });

    }
    
    //更新头像
    @RequestMapping( value = "update_head", method = RequestMethod.POST )
    public void update_head(HttpServletRequest request, HttpServletResponse response){//@RequestBody String body
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipart.getFile("icon");
                String fileName = null;
                if(null != multipartFile){
                    fileName = UUID.randomUUID().toString() + ".png";//dto.getHeadImage().substring(dto.getHeadImage().lastIndexOf("."));
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + Member.AVATAR_Path), fileName);
                    //FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    Thumbnails.of(multipartFile.getInputStream())
                    .scale(1f).outputQuality(0.9f)
                    .outputFormat("jpg")
                    .toFile(outFile);
                }
                
                String data = (String) multipart.getParameter("data");
                InfoDto dto = (InfoDto) parseObject(data, InfoDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                boolean success = service.updateInfo(dto.getMid(),Member.JSON_KEY_AVATAR,fileName);
                if( success ){
                    JSONObject result = new JSONObject(); 
                    result.put("portraitUri", fileName);
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                }else 
                    error(jsonObject,null,dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject, lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
	/**
     * 修改个人信息
     */
	@RequestMapping( value = "update", method = RequestMethod.POST )
    public void update(@RequestBody String body, HttpServletRequest request, HttpServletResponse response, ModelMap model){
	    doInCallBack(response,body,InfoDto.class,log,new DoYourBuniness<InfoDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,InfoDto dto) {
                String key = dto.getKey();
                String value = dto.getValue();
                boolean success = service.updateInfo(dto.getMid(),key,value);
                if( success )
                    success(jsonObject,null,dto.getLang());
                else 
                    error(jsonObject,null,dto.getLang());
            }
        });
    }
	
	/**
	 * 修改头像
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping( value = "avatar", method = RequestMethod.POST )
    public void avatar(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipart.getFile("avatar");
                String fileName = null;
                String uploadPath = Member.AVATAR_Path;
                if(null != multipartFile){
                    fileName = UUID.randomUUID().toString() + ".png";
                    uploadPath =  Member.AVATAR_Path;
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + Member.AVATAR_Path), fileName);
                    //FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    Thumbnails.of(multipartFile.getInputStream())
                    .scale(1f).outputQuality(0.9f)
                    .outputFormat("png")
                    .toFile(outFile);
                }
                
                String data = (String) multipart.getParameter("data");
                InfoDto dto = (InfoDto) parseObject(data, InfoDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                boolean success = service.updateInfo(dto.getMid(),Member.JSON_KEY_AVATAR,uploadPath + fileName);
                if( success ){
                    JSONObject result = new JSONObject(); 
                    result.put("portraitUri", HOST + uploadPath + fileName);
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                }else 
                    error(jsonObject,null,dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
      //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
	
	
    //获取好友备注信息
    @RequestMapping( value = "remark", method = RequestMethod.POST )
    public void friends(@RequestBody String body,HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable Map<String, Object> params){
        doInCallBack(response,body,InfoDto.class,log,new DoYourBuniness<InfoDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,InfoDto dto) {
                Friends friend = friendsService.getFriendsByUserId(dto.getMid(), dto.getFriendId());
                if( null != friend ){
                    JSONObject userInfo = new JSONObject();
                    userInfo.put("alias", friend.getAlias());
                    userInfo.put("phone", friend.getPhone());
                    userInfo.put("detail", friend.getDetail());
                    if(StringUtils.isNotEmpty(friend.getImg()))
                        userInfo.put("img", HOST + friend.getImg());
                    
                    /*List<Sign> list = signService.getSignListByUsername(username);
                    List<Sign> friendSign = signService.getSignListByUsername(username,friends);
                    JSONArray mySigns = new JSONArray();
                    if(null != list && list.size() > 0){
                        for (int i = 0; i < list.size(); i++) {
                            Sign sign = list.get(i);
                            JSONObject mJson = new JSONObject();
                            mJson.put("id", sign.getId());
                            mJson.put("name", sign.getName());
                            mJson.put("detail", sign.getDetailName()); 
                            mJson.put("num", sign.getNum());
                            mySigns.put(i,mJson);
                        }
                    }
                    JSONArray friendSignArray = new JSONArray();
                    if(null != friendSign && friendSign.size() > 0){
                        for (int i = 0; i < friendSign.size(); i++) {
                            Sign sign = friendSign.get(i);
                            JSONObject mJson = new JSONObject();
                            mJson.put("id", sign.getId());
                            mJson.put("name", sign.getName());
                            mJson.put("detail", sign.getDetailName()); 
                            mJson.put("num", sign.getNum());
                            friendSignArray.put(i,mJson);
                        }
                    }
                    userInfo.put("mySigns", mySigns);
                    userInfo.put("friendSigns", friendSignArray);*/
                    
                    jsonObject.put( KEY_RESULT, userInfo );
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,"user.error.noexist",dto.getLang());
                }
            }
        });
    }
	
    //更改好友备注信息
    @RequestMapping( value = "remark/update", method = RequestMethod.POST )
    public void friendsUpdate(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        String key = null;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipart.getFile("image");
                
                String data = (String) multipart.getParameter("data");
                InfoDto dto = (InfoDto) parseObject(data, InfoDto.class);
                key = memberService.getToken(dto.getMid(), new Date(), 7);
                lang = dto.getLang();
                String fileName = null;
                String uploadPath = null;
                if(null != multipartFile){
                    uploadPath = "upload/" + dto.getMid() + SPLIT +  "friends" + SPLIT;
                    fileName = UUID.randomUUID().toString() + ".jpg";
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), fileName);
                    //FileCopyUtils.copy(multipartFile.getBytes(),outFile);
                    Thumbnails.of(multipartFile.getInputStream())
                    .scale(1f).outputQuality(0.9f)
                    .outputFormat("jpg")
                    .toFile(outFile);
                }
                
                boolean success = friendsService.updateFriendInfo(dto.getMid(),dto.getFriendId(),dto.getAlias(),dto.getPhone(),dto.getDetail(),uploadPath + fileName);
                if( success ){
                    JSONObject result = new JSONObject(); 
                    if(null != fileName){
                        result.put("img", HOST + uploadPath + fileName);
                        jsonObject.put( KEY_RESULT, result );
                    }
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject, lang);
        }
        //未登陆则不加密
        if(StringUtils.isNotEmpty(key))
            ApiUtil.encode(key, jsonObject);
        writeJson(jsonObject, response);
    }
    
    
    public static void main(String[] args)  {
        int size = 0;
        List<String> imgs = new ArrayList<String>();
        imgs.add(0, "img1splitimg11");
        imgs.add(1, "img2");
        imgs.add(2, "img3");
        imgs.add(3, "img4");
        
        JSONArray imgArray = new JSONArray();
        if(null != imgs && imgs.size() > 0){
            stop:for(String img : imgs){                 
                String[] images = img.split("split");
                for (int i = 0; i < images.length; i++) {
                    if(size > 2){
                        break stop;
                    }
                    imgArray.add(size, images[i]);
                    size++;
                }
            }
        }
        System.out.println(imgArray);
    }
    

}