/**    
 * 文件名：ArticleService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.news.Article;
import com.mas.user.repository.dao.news.ArticleDao;
import com.mas.user.repository.query.news.ArticleQueryDao;
import com.mas.user.service.MemberService;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ArticleService extends BaseServiceImpl<Article> {

    /**
     * 获取用户与用户好友所有的文章  
     * @param   all  是否包含用户好友文章
     * @param   userId  用户ID
     * @param   pageNo    页面号
     * @param   pageSize
     */
    public List<Article> queryByMemberId(boolean all,String userId, Integer pageNo, Integer pageSize) {
        return queryDao.queryByMemberId(all,userId, pageNo, pageSize);
    }
    
    /**
     * 创建文字说说
     * @param userID
     * @param content
     * @param location
     * @param prefix
     * @param imageStr
     */
    public Object createText(String userID, String content, String location) {
        Member member = memberService.get(userID);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Article article = new Article();
        article.setMemberId(member.getId());
        article.setAccount(member.getUsername());
        article.setType(Article.Type.TEXT.getValue());
        article.setContent(content);
        article.setLocation(location);
        article.setToken("token");
        article.setState(StateVo.ENABLE.value());
        return dao.persistence(article);
    }
    
    /**
     * 创建图文说说
     * @param userID
     * @param content
     * @param location
     * @param prefix
     * @param imageStr
     */
    public Object create(String userID, String content, String location, String prefix,String imageStr) {
        Member member = memberService.get(userID);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Article article = new Article();
        article.setMemberId(member.getId());
        article.setAccount(member.getUsername());
        article.setType(Article.Type.IMAGE.getValue());
        article.setContent(content);
        article.setLocation(location);
        article.setPrefix(prefix);
        article.setImgPath(imageStr);
        article.setToken("token");
        article.setState(StateVo.ENABLE.value());
        return dao.persistence(article);
    }
    
    /**
     * 创建视频说说
     * @param userID
     * @param content
     * @param location
     * @param prefix
     * @param thumb
     * @param video
     */
    public Object createVideo(String userID, String content, String location, String prefix,String thumb, String video) {
        Member member = memberService.get(userID);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Article article = new Article();
        article.setMemberId(member.getId());
        article.setAccount(member.getUsername());
        article.setType(Article.Type.VIDEO.getValue());
        article.setContent(content);
        article.setLocation(location);
        article.setPrefix(prefix);
        article.setThumbPath(thumb);
        article.setVideoPath(video);
        article.setToken("token");
        article.setState(StateVo.ENABLE.value());
        return dao.persistence(article);
    }

    /**
     * 删除文章
     * @param userId
     * @param articleId
     * @return
     */
    public boolean delete(Object userId, String articleId) {
        return dao.delete(userId,articleId);
    }
    
    
    
    public List<Article> getArticleImgs(Object memberId,Integer num) {
        return queryDao.queryArticleImgs(memberId,num);
    }
    
    public String create(Object memberId) {
        Member member = memberService.get(memberId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        return null;
    }
    
    public String modify(String pushGroupId, String groupName, String detail, Long maxuser) {
        return null;
    }
    
    @Override
    protected CrudDao<Article> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Article> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private ArticleDao dao;
    @Autowired private ArticleQueryDao queryDao;
    @Autowired private MemberService memberService;
    
   
    
    
    
}
