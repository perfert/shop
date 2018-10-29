/**    
 * 文件名：SyncThread.java    
 *    
 * 版本信息：    
 * 日期：2017-12-5    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.redpacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**    
 * 类名称：测试对象锁
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-5  
 */
public class SyncThread implements Runnable {
    
    private RedPacketService service;
    
    public SyncThread(RedPacketService service) {
        this.service = service;
    }

    public SyncThread() {
    }
    
    public  void run() {
         try {
            int i = new Random().nextInt(5);
            String rpId = i + "";
            service.openGroupRedPacket(rpId,Thread.currentThread().getName());
         } catch (Exception e) {
            e.printStackTrace();
         }
    }
    
    public static void main(String[] args) {
        RedPacketService service = new RedPacketService();
        SyncThread syncThread = new SyncThread(service);
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(syncThread, "SyncThread" + i);
            list.add(thread);
        }
        for (int i = 0; i < 10; i++) {
            Thread thread = list.get(i);
            thread.start();
        }
        
        
        Object i = "fdsa";
        String rpId = i.toString();
        
        Object i2 = "fdsa";
        String rpId2 = i.toString();
        
        System.out.println(rpId.equals(rpId2));
    }

 }
