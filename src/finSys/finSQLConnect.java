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
	public Connection con; // declare connect subject
	
	
	
	
	
	public void getConnection(String[] args) {
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
	
	
	// use for checking
	public void executeQuery(String sql) throws SQLException {
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
	
	// use to change password. the return value is 1 if the change is succeed
	public int changePwd(String FinID, String pwd, String newpwd) throws SQLException {
		Statement statement = con.createStatement();
		String sql = finSQLGen.finUpdatepwd(FinID, pwd, newpwd);
		
		
		try {
			int result = statement.executeUpdate(sql);
			if (result == 1) {
				System.out.println("change succeed");
			}
			else if (result == 0) {
				System.out.println("confirm failed");
			}
			else {
				System.out.println("error: multiple change pwd subject");
			}
			return result;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
		}
		return 0;
		
		
	}
	
	// return true if have a return; return false otherwise
	public boolean checkPwd(String FinID, String pwd) throws SQLException {
		Statement statement = con.createStatement();
		String sql = finSQLGen.finCheckpwd(FinID, pwd);
		try {
			return statement.execute(sql);	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
		}
		return false;
			
	}
	
	//return row number that affected, normal case return 1
	public int changeBal(String FinID, double amount) throws SQLException {
		Statement statement = con.createStatement();
		String sql = finSQLGen.finUpateBalance(FinID, amount);
		
		try {
			int result = statement.executeUpdate(sql);	
			if(result == 1) {
				System.out.println("change balance succeed");
			}
			else {
				System.out.println("error int change balance");
			}
		return result;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
		}
		return 0;
		
	}
	
	// return true if operation success, false otherwise
	public boolean createNewFinAccount(String FinID, String SecID, String pwd, String balance) throws SQLException {
		Statement statement = con.createStatement();
		String sql = finSQLGen.finNewAccount(FinID, SecID, pwd, balance);
		
		try {
			return statement.execute(sql);	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
		}
		return false;
	}
	
	// return affected rows, normal case 1
	public int setState(String FinID, boolean statevalue) throws SQLException {
		Statement statement = con.createStatement();
		String sql = finSQLGen.finSetState(FinID, statevalue);

		try {
			int result = statement.executeUpdate(sql);
			if (result != 1)
				System.out.println("error in set state");
			return result;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
		}
		return 0;
	}
	
	
	public int[] calcInterest() throws SQLException{
		int numOfSqlLine = 3;
		
		Statement statement = con.createStatement();
		String sql[] = finSQLGen.finCalInterest();
		
		for(int i=0; i<numOfSqlLine; i++) {
			statement.addBatch(sql[i]);
		}

		try {
			return statement.executeBatch();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
		}
	
		return null;
	}
	
	
	
	
	public void closeConnection() throws SQLException {
		con.close();
		return;
	}
	
	
	
	
}




