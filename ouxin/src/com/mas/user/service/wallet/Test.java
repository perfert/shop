/**    
 * 文件名：Test.java    
 *    
 * 版本信息：    
 * 日期：2017-12-20    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.wallet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-20  
 */
public class Test {

    public static void main(String args[]) {
        for(int i = 0;i <400; i++) {//设置400个线程
            String money = "";//new Random().nextInt(1, 5)+"";
            System.out.println(money);
            //模拟几个用户
            String userId = "2aefe0c4c88f4ea1b39a68b6e33208d8";
            String otherId="1bb4fc7d35c342a3a19bc23171aa3d22";
            String userId1="1b638bd7dbb445a3bfe1efbec1a61e4e";
            String userId2="551926783cb64524b3127ec02529d371";
            Account a = null;
            //设置不同的转账
            if("1".equals(money)) {
                 a = new Account(money,userId,otherId);
            }
            else if("2".equals(money)) {
                 a = new Account(money,userId1,userId);
            }else if("3".equals(money)) {
                 a = new Account(money,userId2,userId);
            }
            else {
                 a = new Account(money,userId1,otherId);
            }
            Thread t = new Thread(a);
            t.start();
        }
    }
    static class Account implements Runnable {
        String money = "0";
        String userId = "";
        String otherId="";
        public Account(String money,String userId,String otherId) {
            this.money = money;
            this.otherId = otherId;
            this.userId = userId;
        }

        @Override
        public void run() {
            long begintime = System.currentTimeMillis();
              try {
               URL url = new URL("http:xxx?userId="
                       +userId+"&otherId="+otherId+"&remarks=xxx"
                       +"&money="+money);
               HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
               urlcon.connect();         //获取连接
               InputStream is = urlcon.getInputStream();
               BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
               StringBuffer bs = new StringBuffer();
               String l = null;
               while((l=buffer.readLine())!=null){
                   bs.append(l).append("/n");
               }
               System.out.println(bs.toString());//打印出信息

               System.out.println("总共执行时间为："+(System.currentTimeMillis()-begintime)+"毫秒");
            }catch(IOException e){
               System.out.println(e);
           }
        }
}
}