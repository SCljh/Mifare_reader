package test;

import java.sql.*;

import mifare.Sql_tool;

public class Sql_test {  
  
    public static void main(String[] args) throws SQLException {  
    	
    	
    	Sql_tool st = new Sql_tool();
    	String uid = "12345677";
    	st.insert("12345678", "db_uid");
    	st.insert(uid, "db_uid");
    	
//    		try {  
//    			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
//    			String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=db_uid";  
//    			Connection con = DriverManager.getConnection(url,"sa","ACMer072319");  
//    			System.out.println("数据库连接成功");  
//    			con.close();  
//    		}  
//    		catch(Exception e) {  
//            System.out.println("数据库连接失败\n" + e.toString());  
//    		}
    }
 
}
	
        
//    	Sql_tool st = new Sql_tool();
//    	
//    	st.insert("12345678", "uidTable");
    	    