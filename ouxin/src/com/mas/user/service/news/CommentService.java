/**    
 * 文件名：CommentService.java    
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
import com.mas.user.domain.entity.news.Comment;
import com.mas.user.repository.dao.news.CommentDao;
import com.mas.user.repository.query.news.CommentQueryDao;
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
public class CommentService extends BaseServiceImpl<Comment> {

    /**
     * 查出articleId文章跟我相关的评论(包括好友)
     * @param userId     用户ID
     * @param articleId  文章ID
     * @return
     */
    public List<Comment> queryByArticleId(String userId, Object articleId) {
        return queryDao.queryByArticleId(userId,articleId);
    }
    
    /**
     * 创建数据对象。
     * 
     * @param userId 会员ID
     * @param articleId 文章对象ID。
     * @param content 内容。
     * @param tag 客户端用到。
     */
    public Object create(String userId, Object articleId, String content, String tag) {
        Member member = memberService.get(userId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setMemberId(member.getId());
        comment.setAccount(member.getUsername());
        comment.setContent(content);
        comment.setTag(tag);
        comment.setToken("token");
        comment.setState(StateVo.ENABLE.value());
        return dao.persistence(comment);
    }

    /**
     * 删除数据对象。
     * 
     * @param userId 会员ID
     * @param commentId  评论对象ID像
     * @param tag 日期标记(用于发表评论后,还没立即返回评论ID就立刻删除)
     */
    public boolean delete(String userId, Object commentId, String tag) {
        return dao.delete(commentId,userId,tag);
    }
    
    @Override
    protected CrudDao<Comment> crudDao(){
        return dao;
    }

    @Override
    protected QueryDao<Comment> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private CommentDao dao;
    @Autowired private CommentQueryDao queryDao;
    @Autowired private MemberService memberService;
   
   
    
    
}
