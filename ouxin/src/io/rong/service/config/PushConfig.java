/**    
 * 文件名：T.java    
 *    
 * 版本信息：    
 * 日期：2017-10-7    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package io.rong.service.config;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**    
 *     
 * 项目名称：server-sdk-java-master    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public class PushConfig{
    
    private static long lastModified = 0L;
    private static File wordfilter;
    private static Properties charMap = new Properties();
    
    /*
     * Properties
     */
    private static final String API_APP_KEY = "API_APP";

    private static final String APP_APP_SECRET = "API_APP_SECRET";

    
    private Boolean initialized = Boolean.FALSE;
    private static PushConfig context;
    private String appKey;
    private String appSecret;
    
    public static void main(String[] args) throws InterruptedException {
        PushConfig p = PushConfig.getInstance().init();
        p.getAppKey();
    }
    
    private PushConfig() {};

    public static PushConfig getInstance() {
        if( null == context ) {
            context = new PushConfig();
        }
        return context;
    }
    
    public PushConfig init() {
        if( initialized ) 
            return context;
        initFromPropertiesFile();
        initialized = Boolean.TRUE;
        return context;
    }

    private void initFromPropertiesFile() {
        Properties p = new Properties();

        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
            String folder = PushConfig.class.getClassLoader().getResource("config.properties").toString();
            folder = folder.replace("%20"," "); 
            folder = folder.replace("file:/",""); 
            wordfilter = new File( folder);
            //InputStream inputStream = ClientContext.class.getClassLoader().getResourceAsStream("config.properties");
            p.load(inputStream);
        } catch (IOException e) {
            return; // Context not initialized
        }
        
        String appKey = p.getProperty(API_APP_KEY);
        String appSecret = p.getProperty(APP_APP_SECRET);

        context.appKey = appKey;
        context.appSecret = appSecret;
    }
    
    
    public String getAppKey() {
        checkReload();
        return appKey;
    }

    public String getAppSecret() {
        checkReload();
        return appSecret;
    }

    // 是否更新
    private void checkReload(){
        if(wordfilter.lastModified() > lastModified){
            synchronized(PushConfig.class){
                lastModified = wordfilter.lastModified();
                InputStream is = null;
                BufferedReader reader = null;
                try {
                    is = new FileInputStream(wordfilter);
                    reader = new BufferedReader(new InputStreamReader(is));
                    charMap.load(reader);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    close(reader);
                    close(is);
                }
            }
            initFromPropertiesFile();
        }
    }
    
    private void close(Closeable closeable){
        try{
           if (closeable != null)
               closeable.close();
        }catch (IOException ioe){
           ioe.printStackTrace();
        }
    }
    
}
