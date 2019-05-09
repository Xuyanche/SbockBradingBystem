package finSys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class finSQLConnect {
	public static String driver = "com.mysql.jdbc.Driver"; // driver name
	public static String url = "jdbc:mysql://localhost:3306/sqltestdb"; // database url
	public static String user = "root"; // MySQL user name
	public static String password = "123456"; // MySQL userpwd
	public static Connection con; // declare connect subject
	
	public static void getConnection(String[] args) {
		try {
			// load driver
			Class.forName(driver);
			// connect to SQL server
			con = DriverManager.getConnection(url,user,password);
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			
			return;
		} catch(ClassNotFoundException e) {   
			//driver problem
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
		} catch(SQLException e) {
			//database connect problem
			e.printStackTrace();  
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			System.out.println("数据库数据成功获取！！");
		}
		return;
	}
	
	
	
	public static void excuteQuery(String sql) throws SQLException {
		// create statement class to excute query
		Statement statement = con.createStatement();
		// result set save the result
		ResultSet rs = statement.executeQuery(sql);
		System.out.println("-----------------");
		System.out.println("执行结果如下所示:");
		System.out.println("-----------------");
        /*     
		String job = null;
		String id = null;
		while(rs.next()){
			//获取stuname这列数据
			job = rs.getString("job");
			//获取stuid这列数据
			id = rs.getString("ename");
			//输出结果
			System.out.println(id + "\t" + job);
		}
		*/
            rs.close();
	}
	
	
	
	
	
	public static void closeConnection() throws SQLException {
		con.close();
		return;
	}
	
	
	
	
}




