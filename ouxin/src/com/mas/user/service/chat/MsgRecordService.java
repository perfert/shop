/**    
 * 文件名：MsgRecordService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.chat.Msg;
import com.mas.user.domain.entity.chat.MsgRecord;
import com.mas.user.repository.dao.chat.MsgRecordDao;
import com.mas.user.repository.query.chat.MsgRecordQueryDao;

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
public class MsgRecordService extends BaseServiceImpl<MsgRecord> {

    public MsgRecord getByToken(String from, String token) {
        return queryDao.getByToken(from,token);
    }
    
    public MsgRecord getAllInfo(Object id) {
        if(null != id)
         return queryDao.getAllInfo(id);
        return null;
    }
    
    
    public void persistenceTxt(String fromAccount, String toAccount,String token,  Integer chatType, Integer msgType, String content) {
        Msg msg = new Msg();
        msg.setType(msgType);
        msg.setContent(content);
        msg.setState(StateVo.ENABLE.value());
        Object object = msgService.persistence(msg);
        MsgRecord msgRecord = new MsgRecord();
        msgRecord.setMsgId(object);
        msgRecord.setToAccount(toAccount);
        msgRecord.setFromAccount(fromAccount);
        msgRecord.setChatType(chatType);
        msgRecord.setToken(token);
        msgRecord.setState(StateVo.ENABLE.value());
        dao.persistence(msgRecord);
    }
    
    public void persistenceIMAGE(String fromAccount, String toAccount,String token,  Integer chatType, Integer msgType,String uri) {
        Msg msg = new Msg();
        msg.setType(msgType);
        msg.setThumb(uri);
        msg.setState(StateVo.ENABLE.value());
        Object object = msgService.persistence(msg);
        MsgRecord msgRecord = new MsgRecord();
        msgRecord.setMsgId(object);
        msgRecord.setToAccount(toAccount);
        msgRecord.setFromAccount(fromAccount);
        msgRecord.setChatType(chatType);
        msgRecord.setToken(token);
        msgRecord.setState(StateVo.ENABLE.value());
        dao.persistence(msgRecord);
    }



    public void persistenceLOCATION(String fromAccount, String toAccount, String token, Integer chatType, Integer msgType,
            String address, Double lat, Double lng) {
        Msg msg = new Msg();
        msg.setType(msgType);
        msg.setLat(lat);
        msg.setLng(lng);
        msg.setAddress(address);
        msg.setState(StateVo.ENABLE.value());
        Object object = msgService.persistence(msg);
        MsgRecord msgRecord = new MsgRecord();
        msgRecord.setMsgId(object);
        msgRecord.setToAccount(toAccount);
        msgRecord.setFromAccount(fromAccount);
        msgRecord.setChatType(chatType);
        msgRecord.setToken(token);
        msgRecord.setState(StateVo.ENABLE.value());
        dao.persistence(msgRecord);
    }

    public void persistenceVOICE(String fromAccount, String toAccount, String token, Integer chatType, Integer msgType,Integer length, String uri) {
        Msg msg = new Msg();
        msg.setType(msgType);
        msg.setLength(length);
        msg.setUri(uri);
        msg.setState(StateVo.ENABLE.value());
        Object object = msgService.persistence(msg);
        MsgRecord msgRecord = new MsgRecord();
        msgRecord.setMsgId(object);
        msgRecord.setToAccount(toAccount);
        msgRecord.setFromAccount(fromAccount);
        msgRecord.setChatType(chatType);
        msgRecord.setToken(token);
        msgRecord.setState(StateVo.ENABLE.value());
        dao.persistence(msgRecord);
    }

    public void persistenceVIDEO(String fromAccount, String toAccount, String token, Integer chatType, Integer msgType,String uri, String thumb) {
        Msg msg = new Msg();
        msg.setType(msgType);
        msg.setThumb(thumb);
        msg.setUri(uri);
        msg.setState(StateVo.ENABLE.value());
        Object object = msgService.persistence(msg);
        MsgRecord msgRecord = new MsgRecord();
        msgRecord.setMsgId(object);
        msgRecord.setToAccount(toAccount);
        msgRecord.setFromAccount(fromAccount);
        msgRecord.setChatType(chatType);
        msgRecord.setToken(token);
        msgRecord.setState(StateVo.ENABLE.value());
        dao.persistence(msgRecord);
        
    }

    public void persistenceFILE(String sendId, String targetId, String token, int chatType, int msgType,Integer length, String filePath) {
        Msg msg = new Msg();
        msg.setType(msgType);
        msg.setUri(filePath);
        msg.setLength(length);
        msg.setState(StateVo.ENABLE.value());
        Object object = msgService.persistence(msg);
        MsgRecord msgRecord = new MsgRecord();
        msgRecord.setMsgId(object);
        msgRecord.setToAccount(sendId);
        msgRecord.setFromAccount(targetId);
        msgRecord.setChatType(chatType);
        msgRecord.setToken(token);
        msgRecord.setState(StateVo.ENABLE.value());
        dao.persistence(msgRecord);
        
    }
    
    @Override
    protected CrudDao<MsgRecord> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<MsgRecord> queryDao()
    {
        return queryDao;
    }
    @Autowired private MsgRecordDao dao;
    @Autowired private MsgRecordQueryDao queryDao;
    @Autowired private MsgService msgService;
    
   

}
