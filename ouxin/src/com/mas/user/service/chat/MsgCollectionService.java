/**    
 * 文件名：MsgRecordService.java    
 *    
 * 版本信息：    
 * 日期：2016-01-14    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.MsgCollection;
import com.mas.user.domain.entity.chat.vo.MsgType;
import com.mas.user.repository.dao.chat.MsgCollectionDao;
import com.mas.user.repository.query.chat.MsgCollectionQueryDao;
import com.mas.user.service.MemberService;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    收藏Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class MsgCollectionService extends BaseServiceImpl<MsgCollection> {

    @Autowired private MemberService memberService;

    public Object text(String mid, String yid, String content) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(yid) && null == memberService.get(yid))
            throw new ServiceException("user.error.noexist");
        
        MsgCollection msgCollection = new MsgCollection();
        msgCollection.setMemberId(member.getId());
        msgCollection.setCoverMemberId(yid);
        msgCollection.setType(0);
        msgCollection.setContent(content);
        return dao.persistence(msgCollection);
       
    }

    public Object image(String mid, String yid, String iamgeUrl) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(yid) && null == memberService.get(yid))
            throw new ServiceException("user.error.noexist");
        
        MsgCollection msgCollection = new MsgCollection();
        msgCollection.setMemberId(member.getId());
        msgCollection.setCoverMemberId(yid);
        msgCollection.setType(1);
        msgCollection.setImageUrl(iamgeUrl);
        return dao.persistence(msgCollection);
        
    }

    public Object voice(String mid, String yid, String voicePath, Long voiceTime) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(yid) && null == memberService.get(yid))
            throw new ServiceException("user.error.noexist");
        
        MsgCollection msgCollection = new MsgCollection();
        msgCollection.setMemberId(member.getId());
        msgCollection.setCoverMemberId(yid);
        msgCollection.setType(2);
        msgCollection.setVoiceUrl(voicePath);
        msgCollection.setVoiceTime(voiceTime);
        return dao.persistence(msgCollection);
        
    }

    
    public Object video(String mid, String yid, String thumb, String videoPath, Long videoTime) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(yid) && null == memberService.get(yid))
            throw new ServiceException("user.error.noexist");
        
        MsgCollection msgCollection = new MsgCollection();
        msgCollection.setMemberId(member.getId());
        msgCollection.setCoverMemberId(yid);
        msgCollection.setType(3);
        msgCollection.setVideoUrl(videoPath);
        msgCollection.setThumb(thumb);
        msgCollection.setVideoTime(videoTime);
        return dao.persistence(msgCollection);
    }

    public Object location(String mid, String yid, Double lat, Double lng, String address,String path) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(yid) && null == memberService.get(yid))
            throw new ServiceException("user.error.noexist");
        
        MsgCollection msgCollection = new MsgCollection();
        msgCollection.setMemberId(member.getId());
        msgCollection.setCoverMemberId(yid);
        msgCollection.setType(4);
        msgCollection.setLat(lat);
        msgCollection.setLng(lng);
        msgCollection.setAddress(address);
        msgCollection.setLocationUrl(path);
        return dao.persistence(msgCollection);
        
    }
    
    public Object file(String mid, String yid, String fileUrl, String fileSize) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(yid) && null == memberService.get(yid))
            throw new ServiceException("user.error.noexist");
        
        MsgCollection msgCollection = new MsgCollection();
        msgCollection.setMemberId(member.getId());
        msgCollection.setCoverMemberId(yid);
        msgCollection.setType(5);
        msgCollection.setFileUrl(fileUrl);
        msgCollection.setFileSize(fileSize);
        return dao.persistence(msgCollection);
        
    }

    public boolean delete(Object msgCollectionId, String memberId) {
        return dao.delete(msgCollectionId,memberId);
    }
    
    /**
     * 获取用户收藏
     * @param   memberId  用户ID
     * @param   pageNo    页面号
     * @param   pageSize
     */
    public List<MsgCollection> queryByMemberUsername(String memberId, Integer pageNo, Integer pageSize) {
        return queryDao.queryByMemberUsername(memberId, pageNo, pageSize);
    }
    
    
    @Override
    protected CrudDao<MsgCollection> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<MsgCollection> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private MsgCollectionDao dao;
    @Autowired private MsgCollectionQueryDao queryDao;

    
   
    
   

}
