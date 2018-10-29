/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.service;

import io.rong.models.CodeSuccessResult;
import io.rong.service.impl.IUserService;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.common.file.util.FileUtil;
import com.mas.common.orm.util.Page;
import com.mas.common.util.DateTimeUtil;
import com.mas.common.util.DesUtil;
import com.mas.common.verify.VerifyUtil;
import com.mas.core.service.ServiceException;
import com.mas.security.util.PasswordUtil;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.service.impl.CountryService;
import com.mas.system.service.impl.SmsConfigService;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.MemberTicket;
import com.mas.user.domain.entity.vo.MemberDetail;
import com.mas.user.domain.entity.vo.MemberVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.dao.MemberDao;
import com.mas.user.repository.query.MemberQueryDao;
import com.mas.user.service.image.ImageService;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WealthTypeService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.util.FileTypeUtil;
import com.mas.web.util.ServletUtil;

/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class MemberService {

    /**
     * 根据token获取会员
     * @param token
     * @return
     */
    public Member getByToken(String token) {
        if(StringUtils.isNotEmpty(token))
            return queryDao.getByToken(token);
        else
            return null;
    }
    
    /**
     * 获取用户详情[包括最近的朋友圈]
     * @param mid
     */
    public List<MemberDetail> getMemberDetail(String mid,int num) {
        if(StringUtils.isNotEmpty(mid))
            return queryDao.getMemberDetail(mid,num);
        else
            return null;
    }

    /**
     * 获取好友详情
     * @param mid      我的ID
     * @param friendId 好友ID
     */
    public List<MemberDetail> getFriendDetail(String mid, String friendId,int num) {
        if(StringUtils.isNotEmpty(mid) && StringUtils.isNotEmpty(friendId))
            return queryDao.getFriendDetail(mid,friendId,num);
        else
            return null;
    }
    
    public Member get(Object id) throws ServiceException {
        if(null != id)
            return dao.get(id);
        else
            return null;
    }
    
    /**
     * 通过电话或Wxid查询用户
     * 
     * @param account
     * @return
     */
    public Member getByPhoneOrWxid(String ouxinId) {
        return queryDao.getByPhoneOrWxid(ouxinId);
    }
    
    public Member getByUsername(String username) {
        return queryDao.getByUsername(username);
    }
    
    /**
     * 登录
     * @param code
     * @param account
     * @param password[account+password加密]
     * @return
     * @throws ServiceException
     */
    public Member login(String code, String account, String password) throws ServiceException {
        if (VerifyUtil.isBlank(code))
            throw new ServiceException("login.error.code");
        if (VerifyUtil.isBlank(account) || VerifyUtil.isBlank(password))
            throw new ServiceException("login.error.account.password.empty");
        
        Member result = queryBy(code, account);
        if(null == result)
            throw new ServiceException("user.error.noexist");
        if (!PasswordUtil.isPassword(account, password, result.getPassword())) {
            throw new ServiceException("login.error.account.password");
        }
        String token = result.getId() + FileTypeUtil.getUUID();
        result.setToken(token);
        result.setTokenDate(new Date());
        updateToken(result.getId().toString(),token,result.getTokenDate());
        //dao.persistence(result);
        return result;
    }
    
    /**
     * 获取TOKEN[做缓存]
     * @param mid  用户ID
     * @param date 当前时间,截取@todo
     * @param day  有效天数
     * @return
     */
    @Cacheable(value="member",key="#mid") //使用自定义注解
    public String getToken(String mid, Date date, int day) {
        if(StringUtils.isEmpty(mid))
            return null;
        return queryDao.getToken(mid,date,day);
    }
    
    public boolean updateToken(String mid,String token,Date date){
        cacheManager.getCache("member").evict(mid);
        return dao.updateToken(mid,token,date);
    }
    
    /**
     * 更新用户KEY
     * @param code
     * @param mobile
     * @return
     */
    public String updateSafeKey(String code, String mobile) {
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile))
            throw new ServiceException("register.error.phone");
        Member result = queryBy(code, mobile);
        if(null == result)
            throw new ServiceException("user.error.noexist");
        result.setSafeKey(buildPasswordRecoverKey());
        dao.persistence(result);
        return result.getSafeKey();
    }
    
    /**
     * 设置密码
     * @param code
     * @param mobile
     * @param key
     * @param password
     * @return
     */
    public boolean settingPassword(String code, String mobile, String key, String password) {
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile))
            throw new ServiceException("register.error.phone");
        Member result = queryBy(code, mobile);
        String safeKey = result.getSafeKey();
        Date old = getPasswordRecoverKeyBuildDate(safeKey);
        if(StringUtils.isNotEmpty(safeKey) && null != old){
            if(DateTimeUtil.addMinutes(old,60).before(new Date()))
                throw new ServiceException("code.error.time");
            if(!safeKey.equals(key))
                throw new ServiceException("code.error.key");
            return updatePassword(result.getId(), password);
        }
        return false;
    }
    
    /**
     * 更新用户支付密码KEY
     * @param code    国家码
     * @param mobile  手机
     * @param captcha 短信验证码
     * @return
     */
    public String verifyPayPassword(String code, String mobile, String captcha) {
        boolean success = configService.checkCode(SmsCode.TYPE_PAY_PASSWORD, code, mobile, captcha);
        if(!success)
            throw new ServiceException( "code.error" );
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile))
            throw new ServiceException("parameter.error");
        Member result = queryBy(code, mobile);
        result.setPaySafeKey(buildPasswordRecoverKey());
        dao.persistence(result);
        return result.getPaySafeKey();
    }
    
    /**
     * 更新用户支付密码KEY[跟找回密码是同一个Key]
     * @param memberId    用户ID
     * @param payPassword 用户支付密码
     * @return
     */
    public String verifyPayPassword(String memberId, String payPassword) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(payPassword))
            throw new ServiceException("parameter.error");
        Member member = get(memberId);
        String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
        if (!PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) {
            throw new ServiceException("wallet.error.paypwd.verify");
        }
        member.setPaySafeKey(buildPasswordRecoverKey());
        dao.persistence(member);
        return member.getPaySafeKey();
    }
    
    /**
     * 设置支付密码(未验证)
     * @param memberId
     * @param payPassword
     * @return
     */
    public boolean settingPayPassword(String memberId, String payPassword) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(payPassword))
            throw new ServiceException("parameter.error");
        Member member = get(memberId);
        if(StringUtils.isNotEmpty(member.getPayPassword()) && !member.getPayPassword().equals("0") ){
            throw new ServiceException("wallet.error.paypwd.exist");
        }
        String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
        if(realPwd.length() != 6) 
            throw new ServiceException("wallet.error.paypwd.length");
        return updatePayPassword(memberId,realPwd);
    }

    /**
     * 设置支付密码(重置)
     * @param memberId
     * @param key
     * @param password
     * @return
     */
    public boolean settingPayPassword(String memberId, String key, String payPassword) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(payPassword))
            throw new ServiceException("parameter.error");
        Member member = get(memberId);
        String safeKey = member.getPaySafeKey();
        Date old = getPasswordRecoverKeyBuildDate(safeKey);
        if(StringUtils.isNotEmpty(safeKey) && null != old){
            if(DateTimeUtil.addMinutes(old,60).before(new Date()))
                throw new ServiceException("code.error.time");
            if(!safeKey.equals(key))
                throw new ServiceException("code.error.key");
            String realPwd = DesUtil.decrypt(payPassword, member.getMobile()) ;
            if(realPwd.length() != 6) 
                throw new ServiceException("wallet.error.paypwd.length");
            if (PasswordUtil.isPassword(member.getAccount(), realPwd, member.getPayPassword())) 
                throw new ServiceException("wallet.error.paypwd.same");
            
            updatePayFailure(memberId,0,null);
            return updatePayPassword(memberId,realPwd);
        }
        return false;
    }
   
    /**
     * 判断会员是否存在[code和account同时存在]
     * @param code
     * @param account
     * @param wxid
     * @return
     */
    public boolean existMember(String code, String account, String wxid) {
        if ((VerifyUtil.isBlank(account) || VerifyUtil.isBlank(code)) && VerifyUtil.isBlank(wxid))
            throw new ServiceException("ueer.error.account.ouxinid.noexist");
        return dao.existMember(code, account, wxid);
    }
    
    /**
     * 获取用户二维码
     * @param mid
     * @return
     */
    public String getQrcode(String mid) {
        Member member = get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        MemberTicket tick = ticketService.queryByMemberId(mid,MemberTicket.Type.MINE.value());
        String codePath = null;
        StringBuilder sb = new StringBuilder(JsonCtr.HOST + "download/ouxinchat"); 
        sb.append("?otype=" + MemberTicket.Type.MINE.value());
        sb.append("&username=" + member.getUsername());
        sb.append("&portraitUri="+ member.getAvatar());
        String content = sb.toString();
        if(null == tick || !new File( ServletUtil.realPath() + tick.getImage() ).exists() || !tick.getContent().equals(content)){
            codePath = imageService.uploadMineQrcode(member.getUsername(),content,member.getAvatar());
            if(null == tick){
                tick = new MemberTicket();
                tick.setState(1);
                tick.setMemberId(mid);
            }
            tick.setImage(codePath);
            tick.setContent(content);
            tick.setOtype(MemberTicket.Type.MINE.value());
            ticketService.persistence(tick);
        }else
            codePath = tick.getImage();
        return codePath;
    }
   
    /**
     * 更新用户信息
     * @param mid
     * @param key
     * @param value
     * @return
     */
    public boolean updateInfo(String mid,String key, String value) {
        Member member = get(mid);
        if(null == member)
            return false;
        String field = "";
        if(StringUtils.isNotBlank(key)){
            if(key.equalsIgnoreCase(Member.JSON_KEY_NICK)){
                member.setNickName(value.toString());
                field = "NICK_NAME";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_SEX)){
                member.setSex(value);
                field = "SEX";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_AVATAR)){
                if(!member.getAvatar().equals(JsonCtr.LOGO))
                    FileUtil.remove(member.getAvatar());
                member.setAvatar(value);
                field = "AVATAR";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_FXID) || key.equalsIgnoreCase(Member.JSON_KEY_OXID)){
                if(dao.existMember(null, null, value))
                    throw new ServiceException("info.ouxinid.exist");
                member.setWxid(value);
                field = "WXID";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_CITY)){
                member.setCity(value);
                field = "CITY";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_PROVINCE)){
                member.setProvince(value);
                field = "PROVINCE";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_SIGN)){
                member.setSign(value);
                field = "SIGN";
            }else if(key.equalsIgnoreCase(Member.JSON_KEY_PASSWORD)){
                if (VerifyUtil.isBlank(value) || 6 > value.length())
                    throw new ServiceException("register.error.password.length");
                String savePw = PasswordUtil.encrypt(member.getAccount(), value);
                boolean success = dao.updatePassword(member.getId(), savePw);
                return success;
            }
        }
        boolean success = dao.updateField(mid,field,value);
        if(key.equalsIgnoreCase(Member.JSON_KEY_NICK) || key.equalsIgnoreCase(Member.JSON_KEY_AVATAR)){
            try {
                CodeSuccessResult result = userService.refresh(mid, member.getNickName(), JsonCtr.HOST + member.getAvatar());
            } catch (Exception e) {
                System.out.println("MemberService:刷新用户信息失败!");
            }
        }
        return success;
    }
    
    /**
     * 注册账户。
     * 
     * @param code  国际码
     * @param account 账号
     * @param password 密码
     * 
     * @throws ServiceException
     */
    public void register(String code,String account, String password,String nickName, String image)throws ServiceException {
        if (VerifyUtil.isBlank(code) || !countryService.exist(code))
            throw new ServiceException("register.error.country");
        if (VerifyUtil.isBlank(account))
            throw new ServiceException("register.error.account");
        if (VerifyUtil.isBlank(nickName))
            throw new ServiceException("register.error.nick");
        if (VerifyUtil.isBlank(password) || 6 > password.length())
            throw new ServiceException("register.error.password.length");
        Member bean = new Member();
        boolean exist = existMember(code, account, null);
        if (exist)
            throw new ServiceException("register.error.exist");
        if (VerifyUtil.isNotBlank(account)) {
            if (!VerifyUtil.isMobile(account))
                throw new ServiceException("register.error.phone");
            bean.setMobile(account.trim());
        }

        bean.setCode(code);
        bean.setAccount(account);
        /**
         * 用户名为:code_account
         */
        bean.setUsername(code + Member.CODE_ACCOUNT_SPLIT + account);
        bean.setPassword(PasswordUtil.encrypt(account, password));
        bean.setNickName(nickName);
        bean.setAvatar(image);
        bean.setState(1);
        Object result = null;
        synchronized (this) {
            result = dao.persistence(bean);
        }
        
        List<WealthType> list = wealthTypeService.getAll();
        for (WealthType type : list) {
            Wealth wealth = new Wealth();
            wealth.setCash(new BigDecimal(0));
            wealth.setFreeze(new BigDecimal(0));
            wealth.setWealthType(type.getId());
            wealth.setMemberId(result);
            Object res = wealthService.persistence(wealth);
            if (VerifyUtil.isBlank(res) || 0 >= Integer.valueOf(res.toString()))
                throw new ServiceException("register.error");
        }
    }
    
    public static void main(String[] args) {
        System.out.println(PasswordUtil.encrypt("mas", "love7788520."));
    }


    /**
     * 根据账号或wxid，获取数据。1b07e12236456725725746888e5c333f
     * 
     * @param accountOrwxid
     *            账号 or wxid
     * 
     * @return {@link Member} or null
     * 
     * @throws ServiceException
     */
    public Member queryBy(String code, String accountOrwxid) throws ServiceException {
        return dao.queryBy(code, accountOrwxid);
    }

    public void queryPage(Page page, MemberVo query) throws ServiceException {
        queryDao.queryPage(page, query);
    }

    public boolean isMySub(Object id, Object subId) {
        return queryDao.isMySub(id, subId);
    }

    public Member queryById(Object id) {
        return queryDao.queryById(id);
    }

    
    /**
     * 更新密码
     * @param id
     * @param newPwd
     * @return
     */
    public boolean updatePassword(Object id, String newPwd) {
        Member member = get(id);
        if (null == member)
            throw new ServiceException("user.error.noexist");
        if (VerifyUtil.isBlank(newPwd) || 6 > newPwd.length())
            throw new ServiceException("register.error.password.length");
        return dao.updatePassword(id, PasswordUtil.encrypt(member.getAccount(), newPwd));
    }
    
    /**
     * 设置支付密码错误次数和时间
     * @param memberId
     * @param count
     * @param date
     * @return
     */
    public boolean updatePayFailure(Object memberId,int count, Date date) {
        return dao.updatePayFailure(memberId, count, date);
    }
    

    /**
     * 更新支付密码密码
     * @param id
     * @param newPwd
     * @return
     */
    public boolean updatePayPassword(Object id, String newPwd) {
        Member member = get(id);
        if (null == member)
            throw new ServiceException("user.error.noexist");
        if (VerifyUtil.isBlank(newPwd) || 6 > newPwd.length())
            throw new ServiceException("register.error.password.length");
        return dao.updatePayPassword(id, PasswordUtil.encrypt(member.getAccount(), newPwd));
    }

    private String buildPasswordRecoverKey() {
        String key = FileTypeUtil.getUUID() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(10));
        return System.currentTimeMillis() + Member.CODE_ACCOUNT_SPLIT + key;
    }
    
    private Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
        long time = Long.valueOf(StringUtils.substringBefore(passwordRecoverKey, Member.CODE_ACCOUNT_SPLIT));
        return new Date(time);
    }
    
    
    @Autowired
    private MemberDao dao;

    @Autowired
    private MemberQueryDao queryDao;
    
    @Autowired private CountryService countryService;
    @Autowired private IUserService userService;
    @Autowired private ImageService imageService;
    @Autowired private MemberTicketService ticketService;
    @Autowired private SmsConfigService configService;
    @Autowired private WealthService wealthService;
    @Autowired private WealthTypeService wealthTypeService;
    @Autowired private org.springframework.cache.ehcache.EhCacheCacheManager cacheManager;
}