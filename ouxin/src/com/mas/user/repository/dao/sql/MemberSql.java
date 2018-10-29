/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.dao.sql;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.Member;
import com.mas.user.repository.dao.MemberDao;

/**
 * 会员
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class MemberSql extends CrudSql<Member> implements MemberDao {
    @Override
    public Object persistence(Member entity) {
        if (VerifyUtil.isBlank(entity.getId()) && existMember(entity.getCode(), entity.getAccount(), entity.getWxid())) {
            log.error("Account or wxid already exist.");
            throw new RuntimeException("Account or wxid already exist.");
        }

        if (VerifyUtil.isBlank(entity.getParentId()) || "0".equals(entity.getParentId().toString())) {
            insertTop(entity);
        } else {
            insertChild(entity);
        }
        return super.persistence(entity);
    }

    /**
     * 删除节点及其所有子节点.
     */
    @Override
    public boolean delete(Object id) {
        Member bean = get(id);
        if (null != bean) {
            return deleteNode(bean, true);
        } else {
            return false;
        }
    }

    /**
     * 仅仅删除节点下面的所有子节点.
     */
    @Override
    public boolean deleteChildren(Object id) {
        Member bean = get(id);
        if (null != bean) {
            return deleteNode(bean, false);
        } else {
            return false;
        }
    }

    /**
     * 检索顶层最右边的一个节点
     */
    public Member searchLastNode() {
        String sql = "SELECT * FROM " + tableName() + " WHERE PARENT_ID = 0 ORDER BY LFT DESC LIMIT 0, 1";

        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(sql,
                new BeanPropertyRowMapper<Member>(Member.class)));
    }

    @Override
    public boolean existMember(String code, String account, String wxid) {
        long accountCount = VerifyUtil.isBlank(account) || "0".equals(account) ? 0 : DataAccessUtils
                .uniqueResult(getJdbcTemplate().query(
                        "SELECT COUNT(*) FROM " + tableName() + " WHERE ACCOUNT = ? AND CODE = ?",
                        new Object[] { account, code }, new SingleColumnRowMapper<Long>(Long.class)));
        long wxidCount = VerifyUtil.isBlank(wxid) || "0".equals(wxid) ? 0 : DataAccessUtils
                .uniqueResult(getJdbcTemplate().query("SELECT COUNT(*) FROM " + tableName() + " WHERE WXID = ?",
                        new Object[] { wxid }, new SingleColumnRowMapper<Long>(Long.class)));
        boolean result = 0 < accountCount || 0 < wxidCount;
        return result;
    }

    @Override
    public Member queryBy(String code, String accountOrwxid) {
        return VerifyUtil.isBlank(accountOrwxid) || "0".equals(accountOrwxid) ? null
                : VerifyUtil.isBlank(code) ? DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                        "SELECT * FROM " + tableName() + " WHERE WXID = ?", new Object[] { accountOrwxid },
                        new BeanPropertyRowMapper<Member>(Member.class))) : DataAccessUtils
                        .uniqueResult(getJdbcTemplate().query(
                                "SELECT * FROM " + tableName() + " WHERE ACCOUNT = ? AND CODE = ?",
                                new Object[] { accountOrwxid, code }, new BeanPropertyRowMapper<Member>(Member.class)));
    }
    
    @Override
    public boolean updateField(String userId, String field, String value) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName() + " SET ");
        Object[] object = null;
        if (StringUtils.isNotBlank(field)) {
            sql.append(field + " = ?");
            object = new Object[] { value.toString(), userId };
        }
        sql.append(" WHERE ID = ?");
        return 0 < getJdbcTemplate().update(sql.toString(), object);
    }

    @Override
    public boolean updateField(String username, String field, String value, String field2, String value2) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName() + " SET ");
        boolean hasNext = false;
        Object[] object = null;
        if (StringUtils.isNotBlank(field)) {
            sql.append(field + " = ?");
            object = new Object[] { value.toString(), username };
            hasNext = true;
        }
        if (StringUtils.isNotBlank(field2)) {
            if (hasNext) {
                sql.append("," + field2 + " = ?");
                object = new Object[] { value.toString(), value2.toString(), username };
            } else {
                sql.append(field2 + " = ?");
                object = new Object[] { value2.toString(), username };
            }
        }
        sql.append(" WHERE USERNAME = ?");
        return 0 < getJdbcTemplate().update(sql.toString(), object);
    }

    @Override
    public boolean updateWxid(Object id, String uuid) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET PUSH_ID = ? WHERE ID = ?",
                new Object[] { uuid, id });
    }

    @Override
    public boolean updatePushid(Object id, String pushId) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET PUSH_ID = ? WHERE ID = ?",
                new Object[] { pushId, id });
    }
    
    @Override
    public boolean updatePayFailure(Object memberId, int count, Date date) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET PAY_FAILURE_COUNT = ?,PAY_LOCK_DATE = ? WHERE ID = ?",
                new Object[] {count , date ,memberId});
    }

    @Override
    public boolean updatePassword(Object id, String newPwd) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET PASSWORD = ? WHERE ID = ?",
                new Object[] { newPwd, id });
    }

    @Override
    public boolean updatePayPassword(Object id, String newPwd) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET PAY_PASSWORD = ? WHERE ID = ?",
                new Object[] { newPwd, id });
    }

    @Override
    public boolean updateToken(String mid, String token, Date date) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET TOKEN = ?,TOKEN_DATE = ? WHERE ID = ?",
                new Object[] { token,date, mid });
    }
    
    @Override
    public boolean change(Object id, Object delId) {
        Member bean = get(id);
        if (null == bean)
            return false;
        Member del = get(delId);
        if (null == del)
            return false;

        UpdateSql sql = new UpdateSql(tableName()).addField("WXID", del.getWxid())
                .addField("NICK_NAME", del.getNickName()).addField("AVATAR", del.getAvatar())
                .addField("QRCODE", del.getQrcode())
                .addField("AVATAR", del.getAvatar()).addField("REAL_NAME", del.getRealName()).addField("SEX", del.getSex())
                .addField("SIGN", del.getSign()).addField("BIRTHDAY", del.getBirthday())
                .addField("CITY", del.getCity()).addField("PROVINCE", del.getProvince())
                .andEqWhere("ID", bean.getId()).andEqWhere("WXID", "0");

        if (0 < getNamedParameterJdbcTemplate().update(sql.sql(), sql.values())) {
            return delete(del.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean bind(Object id, String code, String mobile, String password) {
        return 0 < getJdbcTemplate()
                .update("UPDATE "
                        + tableName()
                        + " SET CODE = ?, ACCOUNT = ?, PASSWORD = ?, MOBILE = ? WHERE ID = ? AND (ACCOUNT IS NULL OR ACCOUNT = ?)",
                        new Object[] { code, mobile, password, mobile, id, "0" });
    }

    @Override
    protected InsertSql insertSql(Member bean) {
        InsertSql result = new InsertSql(tableName()).addField("LFT", bean.getLft()).addField("RGT", bean.getRgt())
                .addField("CODE", bean.getCode()).addField("ACCOUNT", bean.getAccount())
                .addField("USERNAME", bean.getUsername()).addField("PASSWORD", bean.getPassword())
                
                .addField("WXID", bean.getWxid())
                .addField("SAFE_KEY", bean.getSafeKey())
                .addField("PAY_SAFE_KEY", bean.getPaySafeKey())
                .addField("PAY_FAILURE_COUNT", bean.getPayFailureCount())
                .addField("PAY_LOCK_DATE", bean.getPayLockDate())
                .addField("TOKEN", bean.getToken())
                .addField("TOKEN_DATE", bean.getTokenDate())
                .addField("QRCODE", bean.getQrcode())
                                
                .addField("MOBILE", VerifyUtil.isNotBlank(bean.getMobile()) ? bean.getMobile() : "0")
                .addField("NICK_NAME", bean.getNickName()).addField("AVATAR", bean.getAvatar())
                .addField("REAL_NAME", bean.getRealName()).addField("SEX", bean.getSex())
                .addField("SIGN", bean.getSign()).addField("BIRTHDAY", bean.getBirthday())
                .addField("CITY", bean.getCity()).addField("PROVINCE", bean.getProvince());

        if (VerifyUtil.isNotBlank(bean.getParentId()) && !"0".equals(bean.getParentId().toString())) {
            Member parent = get(bean.getParentId());
            if (null == parent) {
                result.addField("PARENT_ID", 0);
            } else {
                result.addField("PARENT_ID", parent.getId());
                result.addField("SECOND_PARENT_ID", parent.getParentId());
            }
        } else {
            result.addField("PARENT_ID", 0);
        }
        return result;
    }

    @Override
    protected UpdateSql updateSql(Member bean) {
        return new UpdateSql(tableName()).addField("PASSWORD", bean.getPassword())
                .addField("WXID", bean.getWxid())
                .addField("TOKEN", bean.getToken())
                .addField("TOKEN_DATE", bean.getTokenDate())
                .addField("SAFE_KEY", bean.getSafeKey())
                .addField("PAY_SAFE_KEY", bean.getPaySafeKey())
                .addField("PAY_FAILURE_COUNT", bean.getPayFailureCount())
                .addField("PAY_LOCK_DATE", bean.getPayLockDate())
                .addField("PAY_PASSWORD", bean.getPayPassword())
                .addField("QRCODE", bean.getQrcode())
                                
                .addField("NICK_NAME", bean.getNickName())
                .addField("AVATAR", bean.getAvatar())
                .addField("REAL_NAME", bean.getRealName()).addField("SEX", bean.getSex())
                .addField("SIGN", bean.getSign()).addField("BIRTHDAY", bean.getBirthday())
                .addField("CITY", bean.getCity()).addField("PROVINCE", bean.getProvince())

                .andEqWhere("ID", bean.getId());
    }

    @Override
    protected String tableName() {
        return Member.TABLE_NAME;
    }

    /**
     * 添加顶层资源
     * 
     * @param entity
     */
    private void insertTop(Member entity) {
        entity.setParentId(0L); // 顶层不能有 parent 节点.

        Member rightTreeInTopLayer = searchLastNode();

        if (null == rightTreeInTopLayer) // 空表, 直接添加为第一个节点
        {
            entity.setLft(1);
            entity.setRgt(2);
        } else // 添加为顶层最右节点
        {
            entity.setLft(rightTreeInTopLayer.getRgt() + 1);
            entity.setRgt(rightTreeInTopLayer.getRgt() + 2);
        }
    }

    /**
     * 添加子资源
     * 
     * @param entity
     */
    private void insertChild(Member entity) {

        Member parent = this.get(entity.getParentId());
        if (null == parent) {
            log.error("dot not exist ID: " + entity.getParentId() + " of parent.");
            throw new RuntimeException("dot not exist ID: " + entity.getParentId() + " of parent.");
        }

        entity.setLft(parent.getRgt());
        entity.setRgt(parent.getRgt() + 1);

        // 更新所有节点(右值)
        String lftQueryString = "UPDATE " + tableName() + " SET RGT = RGT + 2 WHERE RGT >= ?";
        getJdbcTemplate().update(lftQueryString, new Object[] { parent.getRgt() });

        // 更新所有节点左值
        String rgtQueryString = "UPDATE " + tableName() + " SET LFT = LFT + 2 WHERE LFT > ?";
        getJdbcTemplate().update(rgtQueryString, new Object[] { parent.getRgt() });
    }

    /**
     * 删除节点.
     * 
     * @param bean
     *            数据对象
     * @param deleteSelf
     *            是否删除节点自身
     */
    private boolean deleteNode(Member bean, final boolean deleteSelf) {
        if (null == bean.getLft() || 0 >= bean.getLft() || null == bean.getRgt() || 0 >= bean.getRgt()) {
            bean = get(bean.getId());
            if (null == bean) {
                log.error("Tree_deleteNode_notExist");
                return false;
            }
        }

        String sql = "DELETE FROM " + tableName() + " WHERE";
        if (deleteSelf) {
            sql += " LFT >= ? AND RGT <= ?";
        } else {
            sql += " LFT > ? AND RGT < ?";
        }
        int count = getJdbcTemplate().update(sql, new Object[] { bean.getLft(), bean.getRgt() });

        afterDeleteNode(bean, deleteSelf);

        return 0 < count;
    }

    /**
     * 删除节点之后操作.
     * 
     * @param bean
     *            数据对象
     * @param deleteSelf
     *            是否删除节点自身
     */
    private void afterDeleteNode(Member bean, final boolean deleteSelf) {
        int deleteNodSpace = (bean.getRgt() - bean.getLft() + (deleteSelf ? 1 : -1)); // 所有将要删除的节点总占位数

        // 更新节点左值
        String lftQueryString = "UPDATE " + tableName() + " SET LFT = LFT - ? WHERE LFT > ? ";
        getJdbcTemplate().update(lftQueryString, new Object[] { deleteNodSpace, bean.getRgt() });

        // 更新节点(右值)
        String rgtQueryString = "UPDATE " + tableName() + " SET RGT = RGT - ? WHERE";
        if (deleteSelf) {
            rgtQueryString += " RGT > ?";
        } else {
            rgtQueryString += " RGT >= ?";
        }
        getJdbcTemplate().update(rgtQueryString, new Object[] { deleteNodSpace, bean.getRgt() });
    }

    private static final Logger log = LogManager.getLogger(MemberSql.class);

    
    

}