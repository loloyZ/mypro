package com.wei.cn.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver" ;
    private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:ORCL" ;
    private static final String DBUSER = "wei" ;
    private static final String PASSWORD = "1234" ;
    private Connection conn = null ;
    
    public DatabaseConnection() {
    	try {
			Class.forName(DBDRIVER) ;
			this.conn = DriverManager.getConnection(DBURL,DBUSER,PASSWORD) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
    }
    
    public Connection getConnection() {
    	return this.conn ;
    }
    
    public void close() {
    	if (this.conn != null) {
    		try {
    			this.conn.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
}
