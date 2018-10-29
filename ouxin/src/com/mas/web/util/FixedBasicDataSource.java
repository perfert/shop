package com.mas.web.util;

import java.sql.Driver;
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.util.Enumeration;

public class FixedBasicDataSource extends org.apache.commons.dbcp2.BasicDataSource { 

    @Override 
    public <T> T unwrap(Class<T> iface) throws SQLException { 
        // TODO Auto-generated method stub 
        return null; 
    } 

    @Override 
    public boolean isWrapperFor(Class<?> iface) throws SQLException { 
        // TODO Auto-generated method stub 
        return false; 
    } 
    @Override  
    public synchronized void close() throws SQLException {  
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 
        super.close();  
    }  
}