/**    
 * 文件名：RpInfoModel.java    
 *    
 * 版本信息：    
 * 日期：2017-12-5    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-5  
 */
public class RpInfoModel {
    
    private int status; //红包状态
    private int type;   //是否拼手气红包
    private int isSelf; //是群红包
    private int hasLeft;//还剩下几个,领完为0;
    private String totalMoney;
    private String recTotalMoney;
    private int total;
    private int receTotal;
    private String username;
    private String avatar;
    private String content;

    List<RpItemModel> itemList = new ArrayList<RpItemModel>();
    
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public int getHasLeft() {
        return hasLeft;
    }

    public void setHasLeft(int hasLeft) {
        this.hasLeft = hasLeft;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRecTotalMoney() {
        return recTotalMoney;
    }

    public void setRecTotalMoney(String recTotalMoney) {
        this.recTotalMoney = recTotalMoney;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getReceTotal() {
        return receTotal;
    }

    public void setReceTotal(int receTotal) {
        this.receTotal = receTotal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<RpItemModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<RpItemModel> itemList) {
        this.itemList = itemList;
    }
    
}
