/**    
 * 文件名：ArticleGoodService.java    
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

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.news.ArticleGood;
import com.mas.user.repository.dao.news.ArticleGoodDao;
import com.mas.user.repository.query.news.ArticleGoodQueryDao;
import com.mas.user.service.MemberService;

/**    
 * 项目名称：chat    
 * 类名称：    点赞Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ArticleGoodService extends BaseServiceImpl<ArticleGood> {
    
    /**
     * 查出articleId文章与我相关的点赞(包括好友的赞)
     * @param userId     用户ID
     * @param articleId  文章ID
     * @return
     */
    public List<ArticleGood> queryByArticleId(String userId, Object articleId) {
        return queryDao.queryByArticleId(userId,articleId);
    }
    
    /**
     * 点赞文章
     * @param userId   用户ID
     * @param articleId 文章ID
     * @return
     */
    public Object good(String userId,Object articleId) {
        Member member = memberService.get(userId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        ArticleGood articleGood = queryDao.queryByMemberArticleId(member.getId(),articleId);
        if(null != articleGood){
            articleGood.setGoodState(ArticleGood.GOOD);
            return dao.persistence(articleGood); 
        }
        ArticleGood good = new ArticleGood();
        good.setArticleId(articleId);
        good.setMemberId(member.getId());
        good.setAccount(member.getUsername());
        good.setToken("token");
        good.setGoodState(ArticleGood.GOOD);
        return dao.persistence(good);
    }

    /**
     * 取消点赞
     * @param userId     用户ID
     * @param articleId  文章ID
     * @return
     */
    public Object cancel(String userId, String articleId) {
        Member member = memberService.get(userId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        ArticleGood articleGood = queryDao.queryByMemberArticleId(member.getId(),articleId);
        if(null == articleGood)
            throw new ServiceException("article.like.noexist");
        
        articleGood.setGoodState(ArticleGood.GOOD_CANCEL);
        return dao.persistence(articleGood);
    }
   
    @Override
    protected CrudDao<ArticleGood> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<ArticleGood> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private ArticleGoodDao dao;
    @Autowired private ArticleGoodQueryDao queryDao;
    @Autowired private MemberService memberService;
    
    
}
