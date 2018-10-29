/**    
 * 文件名：InitListener.java    
 *    
 * 版本信息：    
 * 日期：2017-12-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.mas.common.orm.util.Page;
import com.mas.common.util.DateTimeUtil;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.service.redpacket.RedPacketService;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WithdrawService;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-22  
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

    /** servletContext */
    private ServletContext servletContext;
    private static final Logger logger = Logger.getLogger(InitListener.class.getName());
    
    @Autowired private RedPacketService redPacketService;
    @Autowired private WealthService wealthService;
    private WithdrawOrderCheckHandle handle;
    @Autowired 
    private WithdrawService service;
    
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (servletContext != null && event.getApplicationContext().getParent() == null) {
            logger.info("===========加载提币线程=============");
            handle = new WithdrawOrderCheckHandle("1", service);
            handle.start();
            
            logger.info("===========加载红包退款=============");
            long beginTimeMillis = System.currentTimeMillis();
            Date before = DateTimeUtil.addHours(new Date(), -24);
            Page page = new Page(1,50);
            redPacketService.queryPage(before,page);
            List<RedPacket> rpList =  (List<RedPacket>) page.getResult();
            
            long recycle = 1;
            long totalRecycle = page.getTotalPageNo();
            //不停加载第一页,因为加载的内容会被处理掉,报错就停掉
            while( recycle <= totalRecycle ){
                if(null != rpList && rpList.size() > 0 ){
                    for(RedPacket real : rpList){
                        if(real.getNum() != real.getReceiveNum() && real.getCash().compareTo(real.getReceiveCash()) > 0){
                            BigDecimal backCash = real.getCash().subtract(real.getReceiveCash());
                            wealthService.rpBack(real.getId().toString(),real.getMemberId().toString(),real.getPayType().toString(),backCash);
                        }
                    }
                }
                Page secondPage = new Page(1,50,false);
                redPacketService.queryPage(before, secondPage);
                rpList =  (List<RedPacket>) secondPage.getResult();
                recycle = recycle + 1;  
            }
            
            long buildTime = System.currentTimeMillis() - beginTimeMillis;
            if (buildTime < 60000) {
                logger.info("红包退款总花花费时间:" + Math.round( (buildTime / 1000) ) + "秒");
            } else {
                logger.info("红包退款总花花费时间:" + Math.round( (buildTime / 60000) ) + "分");
            }
        }
    }
    

    public void reHandle() throws InterruptedException {
        if (null != handle) {
            handle.stopTread();
            handle.interrupt();
            handle.join();
            handle = new WithdrawOrderCheckHandle("1", service);
            handle.start();
        }
    }

}
