package com.mas.common.web;
import java.sql.DriverManager;
import java.sql.SQLException;

/** 
* @ClassName: OurDataSource 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author  
*/
public class OurDataSource extends org.apache.commons.dbcp2.BasicDataSource {
    
    @Override
    public synchronized void close() throws SQLException {
        DriverManager.deregisterDriver(DriverManager.getDriver(getUrl()));
        super.close();
    }
}