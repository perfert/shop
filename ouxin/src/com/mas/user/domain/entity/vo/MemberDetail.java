/**    
 * 文件名：MemberDetail.java    
 *    
 * 版本信息：    
 * 日期：2017-11-30    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

import com.mas.user.domain.entity.Member;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-30  
 */
public class MemberDetail extends Member{

    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = -7306403900726883157L;
    
    //朋友圈数据
    private Integer type;
    private String thumbPath;
    private String imgPath;
    private String prefix;
    
    //好友数据
    private String alias;
    private String phone;
    private String detail;
    private String img;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }    
    
    
}
