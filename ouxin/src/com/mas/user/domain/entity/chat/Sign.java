/**    
 * 文件名：Sign.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.chat;

import com.mas.core.domain.entity.Entity;

/**
 * 
 * 项目名称：chat 类名称： 好友标签表 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class Sign extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_SIGN";

    private Object memberId;
    
    private String username;
    
    private String name;

    private Integer num;
    
    private String detailName;

    public Sign(String id) {
        setId(id);
    }
    
    public Sign() {
        super();
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }
    
    

}
