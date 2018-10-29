/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.store;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.orm.util.Page;
import com.mas.core.service.ServiceException;
import com.mas.shops.domain.entity.Shops;
import com.mas.shops.domain.entity.ShopsAttention;
import com.mas.shops.service.ShopsService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.store.ShopsDto;

/**
 * 点赞Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberShopsCtr.URI_PREFIX)
public class MemberShopsCtr extends JsonCtr<ShopsDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/store/shops";
    
    @Autowired private ShopsService shopsService;
    
    private static final Logger log = LogManager.getLogger( MemberShopsCtr.class );
    
    //查询店铺
    @RequestMapping( value = "search", method = RequestMethod.POST )
    public void index(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,ShopsDto.class,log,new DoYourBuniness<ShopsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ShopsDto dto)  {
                JSONObject result = new JSONObject();
                Page page = new Page(dto.getPagenum(), DEFAULT_SIZE);
                shopsService.queryPage(page,dto.getQuery());
                if (dto.getPagenum() == 1) {
                    result.put("maxPage", page.getTotalPageNo());
                }

                @SuppressWarnings("unchecked")
                List<Shops> itemList = (List<Shops>) page.getResult();
                if (null != itemList && itemList.size() > 0) {
                    JSONArray itemArray = new JSONArray();
                    if (null != itemList && itemList.size() > 0) {
                        for (int i = 0; i < itemList.size(); i++) {
                            Shops bean = itemList.get(i);
                            JSONObject mJson = new JSONObject();
                            mJson.put("id", bean.getId());
                            mJson.put("name",bean.getTitle());
                            mJson.put("portraitUri", bean.getLogo());
                            mJson.put("describe",bean.getDetail());
                            mJson.put("link",bean.getLink());
                            itemArray.add(i, mJson);
                        }
                    }
                    result.put("pageData", itemArray);
                    result.put("pagesize", dto.getPagesize());
                    result.put("pagenum", dto.getPagenum());
                }
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject, null, dto.getLang());
            }
        });
    }
    
    //查询关注店铺
    @RequestMapping( value = "list", method = RequestMethod.POST )
    public void attention(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,ShopsDto.class,log,new DoYourBuniness<ShopsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ShopsDto dto)  {
                JSONArray array = new JSONArray();
                List<ShopsAttention> list = shopsService.queryAttention(dto.getMid());
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        ShopsAttention bean = list.get(i);
                        JSONObject mJson = new JSONObject();
                        mJson.put("id", bean.getId());
                        mJson.put("name",bean.getTitle());
                        mJson.put("portraitUri", bean.getLogo());
                        mJson.put("describe",bean.getDetail());
                        mJson.put("link",bean.getLink());
                        array.add(mJson);
                    }
                }
                JSONObject pageData = new JSONObject();
                pageData.put("pageData", array);
                pageData.put("pagesize", dto.getPagesize());
                pageData.put("pagenum", dto.getPagenum());
                jsonObject.put(KEY_RESULT, pageData);
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    @RequestMapping( value = "info", method = RequestMethod.POST )
    public void info(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,ShopsDto.class,log,new DoYourBuniness<ShopsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ShopsDto dto) {
                //验证是否登录
                Shops bean = shopsService.get(dto.getMid(),dto.getShopsId());
                JSONObject result = new JSONObject();
                result.put("id", bean.getId());
                result.put("name",bean.getTitle());
                result.put("portraitUri", null != bean.getLogo() ? bean.getLogo() :"");
                result.put("describe",bean.getDetail());
                
                result.put("storeCategory",bean.getStoreCategory());
                result.put("mobile",bean.getMobile());
                result.put("authStatus",bean.getAuthStatus());
                result.put("attention",bean.getAttention());
                result.put("company",bean.getName());
                result.put("link",bean.getLink());
                
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });

    }
    
    //关注
    @RequestMapping( value = "attention", method = RequestMethod.POST )
    public void checkAttention(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,ShopsDto.class,log,new DoYourBuniness<ShopsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ShopsDto dto) {
                boolean success = shopsService.setAttention(dto.getMid(),dto.getShopsId(),true);
                JSONObject result = new JSONObject();
                if(success)
                    result.put("id", dto.getShopsId());
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });

    }    
    
    //取关
    @RequestMapping( value = "cancel", method = RequestMethod.POST )
    public void cancel(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,ShopsDto.class,log,new DoYourBuniness<ShopsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ShopsDto dto) {
                boolean success = shopsService.setAttention(dto.getMid(),dto.getShopsId(),false);
                JSONObject result = new JSONObject();
                if(success)
                    result.put("id", dto.getShopsId());
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });

    }
    

    /**
     * 店铺押金
     * @param body
     * @param request
     * @param response
     *//*
    @RequestMapping( value = "mortagage", method = RequestMethod.POST )
    public void group(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ShopsDto.class,log,new DoYourBuniness<ShopsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ShopsDto dto) throws JSONException {
                List<MortagageConfig> list = configService.getList(dto.getMid());
                if( null != list && list.size() > 0 ){
                    JSONObject result = new JSONObject();
                    JSONArray typeJsonArray = new JSONArray();
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject bean = new JSONObject(); 
                        MortagageConfig type = list.get(i);
                        bean.put("id", type.getId());
                        bean.put("wealthTypeId", type.getWealthTypeId());
                        bean.put("cash", type.getCash());
                        bean.put("name", type.getName());
                        bean.put("balance", type.getBalance());
                        typeJsonArray.put(i, bean);
                    }
                    result.put("configList", typeJsonArray);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "mortagage.config.error",dto.getLang());
                }
            }
        });
    }*/
    
    /**
     * 上传店铺资料
     */
    //@RequestMapping( value = "apply", method = RequestMethod.POST )
    /*public void apply(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        String lang = LANG;
        try {
            if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                String body = (String) multipart.getParameter("data");
                ShopsDto dto = (ShopsDto) parseObject(body, ShopsDto.class);
                lang = dto.getLang();
                
                String businessLicensePath = dto.getBusinessLicensePath();
                String cardFacePath = dto.getCardFacePath();
                String cardReversePath = dto.getCardReversePath();
                
                String uploadPath = ImageService.SHOPS_APPLY_PATH + dto.getMid() + SPLIT;
                MultipartFile multipartFile0 = multipart.getFile("businessLicensePath");
                if(null != multipartFile0){
                    String fileName = UUID.randomUUID().toString() + ".jpg";
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), fileName);
                    imageService.saveImage(multipartFile0.getInputStream(), outFile, 0.9f, "jpg");
                    businessLicensePath = uploadPath + fileName;
                }
                
                MultipartFile multipartFile1 = multipart.getFile("cardFacePath");
                if(null != multipartFile1){
                    String fileName = UUID.randomUUID().toString() + ".jpg";
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), fileName);
                    imageService.saveImage(multipartFile1.getInputStream(), outFile, 0.9f, "jpg");
                    cardFacePath = uploadPath + fileName;
                }
                
                MultipartFile multipartFile2 = multipart.getFile("cardReversePath");
                if(null != multipartFile2){
                    String fileName = UUID.randomUUID().toString() + ".jpg";
                    File outFile = new File(FileUtil.mkdir(ServletUtil.realPath() + uploadPath), fileName);
                    imageService.saveImage(multipartFile2.getInputStream(), outFile, 0.9f, "jpg");
                    cardReversePath = uploadPath + fileName;
                }
                Object id = shopsService.createShops(dto.getMid(),businessLicensePath,cardFacePath,cardReversePath,dto.getName(),dto.getLegalPerson(),dto.getCardNo(),
                        dto.getStoreCategory(),dto.getTitle(),dto.getManageName(),dto.getMobile(),dto.getAddress());
                JSONObject result = new JSONObject();
                result.put("id",id);
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject,null,dto.getLang());
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject,lang);
        }
        writeJson(jsonObject, response);
    }
   */
    
}