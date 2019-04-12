package mifare;

import java.sql.*;

public class Sql_tool {
	
	
	private Connection getConnection(String dbname) {
		 
        Connection con = null;
 
        try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
            String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=" + dbname;  
            con = DriverManager.getConnection(url,"sa","ACMer072319");  
            System.out.println("数据库连接成功");  
 
        } catch (ClassNotFoundException e) {
        	System.out.println("数据库连接失败");
            return null;
        } catch (SQLException e) {
        	System.out.println("数据库连接失败");
            return null;
        }
 
        return con;
    }
	
	public Boolean hasUid(String uid, String dbname){
		
String sql = null;
		
        ResultSet res = null;
 
        Connection con = getConnection(dbname);
 
        Statement state = null;
 
        if (!(con == null)) {
        	
        	sql = "select * from uidTable where uid = '"+uid+"'";
 
            try {
                state = con.createStatement();
                res = state.executeQuery(sql);
                if (res.next()){
    				System.out.println("数据存在");
    				return true;
                }
                else {System.out.println("not found"); return false;}
            } catch (SQLException e) {
                return null;
            }
        }
        
        try {
			if (res.next()){
				System.out.println("数据存在");
				return true;
			}
			else {System.out.println("数据不存在"); return false;}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	

	
	
	public int insert(String uid, String dbname) {
		
		String sql = null;
		
        int iId = -1;
 
        Connection con = getConnection(dbname);
 
        Statement state = null;
        
        if (hasUid(uid, dbname)){
        	System.out.println("此卡已存在");
        	return -2;
        }
        
        
       
        sql = "insert into uidTable values (" +uid+ ")";
        if (con != null) {
            try {
                state = con.createStatement();
 
                int res1 = state.executeUpdate(sql);
                
                System.out.println("插入成功");
 
                return 1;
            } catch (SQLException e) {
            	System.out.println("插入失败"+e.getMessage());
                iId = -1;
            }
 
        }
 
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
            }
        }
 
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
 
        return iId;
    }
	
//	private String url;
//	private Connection con;
//
//	public Sql_tool(String dbname){
//		try {  
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
//            this.url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=" + dbname;  
//            this.con = DriverManager.getConnection(url,"sa","ACMer072319");  
//            System.out.println("数据库连接成功");  
//            con.close();  
//        }  
//        catch(Exception e) {  
//            System.out.println("数据库连接失败\n" + e.toString());  
//        }
//	}
	
}
