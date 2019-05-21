package finSys;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class finSQLConnect {	
	
	public static String driver = "com.mysql.jdbc.Driver"; // driver name
	public static String url = "jdbc:mysql://localhost:3306/stockdb"; // database url
	public String user; // MySQL user name
	public String password; // MySQL userpwd
	public Connection con; // declare connect subject
	
	
	//-------------------- constructor --------------------
	finSQLConnect(){
		user = "stockadmin";
		password = "123456";
	}
	
	finSQLConnect(String userValue, String pwd){
		user = userValue;
		password = pwd;
	}
	
	//--------------------- set function -----------------------
	public void setPassword(String pwd) {
		password = pwd;
	}
	
	public void setUserName(String userNameValue) {
		user = userNameValue;
	}
	
	public void setUser(String userValue, String pwd) {
		user = userValue;
		password = pwd;
	}
	
	
	
	//------------------- database connection ------------------------
	
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
			System.out.println("connected");
		}
		return;
	}
	
	public void closeConnection() throws SQLException {
		con.close();
		return;
	}
	
	// use for checking
	public void executeQuery(String sql) throws SQLException {
		// create statement class to excute query
		Statement statement = con.createStatement();
		// result set save the result
		ResultSet rs = statement.executeQuery(sql);
		System.out.println("-----------------");
		System.out.println("checking :");
		System.out.println("-----------------");
        /*     
		
		*/
            rs.close();
	}
	

	//------------------- fin table operation ------------------------------
	
	/**
	 *  print finID, secID, balance, interest, state
	 * @return return the number of rows found
	 * */
	public int finSearch(long FinID) throws SQLException {
		String sql = finSQLGen.finSearch();
		java.sql.PreparedStatement ps  = con.prepareStatement(sql);
		int flag = 0;
		try {
			
			ps.setLong(1, FinID);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("finID\t secID\t balance\t interest\t state");
			while(rs.next()){
				//(finID, SecID, pwd, balance, 0, true);
				String ID = rs.getString(finSQLGen.IDColName);
				String secID = rs.getString(finSQLGen.secIDColName); 
				double balance = rs.getDouble(finSQLGen.balColName);
				double interest = rs.getDouble(finSQLGen.interestColName);
				boolean state = rs.getBoolean(finSQLGen.stateColName);
				
				System.out.println( ID + "\t" + secID + "\t" + Double.toString(balance) + "\t" 
						+ Double.toString(interest) + "\t" + state );
				flag++;
			}
			return flag;
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ps.close();
		}
		return flag;
		
	}
	
	/** use to change password. 
	 * @return return 1 if the change is succeed
	 * */
	public int changePwd(long FinID, String pwd, String newpwd) throws SQLException {
		String sql = finSQLGen.finUpdatepwd();
		java.sql.PreparedStatement ps = con.prepareStatement(sql);
		
		try {
			ps.setString(1, newpwd);
			ps.setLong(2, FinID);
			ps.setString(3, pwd);
			
			int result = ps.executeUpdate();
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
			ps.close();
		}
		return 0;
		
		
	}
	
	/** 
	 * check if the password and id is correct
	 * @return return true if have a return; return false otherwise
	 * */
	public boolean checkPwd(long FinID, String pwd) throws SQLException {
		String sql = finSQLGen.finCheckpwd();
		java.sql.PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = null;
		int count = 0;
		try {
			ps.setLong(1, FinID);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ps.close();
			rs.close();
		}
		return (count>0)? true : false;
			
	}
	
	
	/**
	 * @return the FinID of the new account in the format of long. if error, return -1
	 * */
	public long createNewFinAccount(String SecID, String pwd, double balance) throws SQLException {
		long FinID = 0;
		java.sql.PreparedStatement ps = null;
		String s = finSQLGen.finGetGreatestFinID();
		Statement statement = con.createStatement();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(s);
			while(rs.next()){
				FinID = rs.getLong(0);
			}
			FinID++;
			
			String sql = finSQLGen.finNewAccount();
			ps = con.prepareStatement(sql);
			ps.setLong(1, FinID);
			ps.setString(2, SecID);
			ps.setString(3, pwd);
			ps.setDouble(4, balance);
			ps.executeUpdate();
			return (FinID);	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			statement.close();
			ps.close();
			rs.close();
		}
		return -1;
	}
	
	/** 
	 * set the state of an account
	 * @param statevalue true - available, false - freeze
	 * @return return affected rows, normal case 1
	 * */
	public int setState(long FinID, boolean statevalue) throws SQLException {
		String sql = finSQLGen.finSetState();
		java.sql.PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setLong(1, FinID);
			ps.setBoolean(2, statevalue);
			int result = ps.executeUpdate();
			if (result != 1)
				System.out.println("error in set state");
			return result;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ps.close();
		}
		return 0;
	}
	
	/** 
	 * to calculate interest, the return is a array of statements return
	 * this action have no parameter, so no need to use prepared statement
	 * */
	public int[] calcInterest() throws SQLException{
		int numOfSqlLine = 3;
		double rate = 0;
		String s = finSQLGen.getInterestRate();
		Statement statement = con.createStatement();
		
		try {
			
			ResultSet rs = statement.executeQuery(s);
			while(rs.next()){
				rate = rs.getDouble(0);
			}
			
			String sql[] = finSQLGen.finCalInterest(rate);
			
			for(int i=0; i<numOfSqlLine; i++) {
				statement.addBatch(sql[i]);
			}
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
	
	/** 
	 * do change balance and record in the log, can write comment in the same time if needed
	 * @param comment use for record the reason of the change of this log.
	 *  Normally is the transaction number, or things like "draw from counter"
	 * */
	public int[] changeBal(long FinID, double amount, String comment) throws SQLException {
		int numOfSqlLine = 2;
		String sql[] = new String[numOfSqlLine];
		try {
			sql[0] = finSQLGen.finUpateBalance();
			sql[1] = finSQLGen.logNewEntry();
			java.sql.PreparedStatement ps = con.prepareStatement(sql[0]);
			ps.setLong(1, FinID);
			ps.setDouble(2, amount);
			ps.executeUpdate();
			ps.close();
			ps = con.prepareStatement(sql[1]);
			ps.setLong(1, FinID);
			ps.setDouble(2, amount);
			ps.setString(3, comment);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		}
		return null;
		
	}

	//------------------- fin Log operation ------------------------


	/** 
	 * print actID, finID, amount, time, comment
	 */
	public void logSearch(long FinID) throws SQLException {
		java.sql.PreparedStatement ps = con.prepareStatement(finSQLGen.logSearch());
		
		try {
			ps.setLong(1, FinID);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("actID\t finID\t amount\t time\t comment");
			while(rs.next()){
				//(finID, SecID, pwd, balance, 0, true);
				String actID = rs.getString(finSQLGen.actIDColName);
				String finID = rs.getString(finSQLGen.logFinIDColName); 
				double amount = rs.getDouble(finSQLGen.amountColName);
				Date time = rs.getDate(finSQLGen.timeColName);
				String comment = rs.getString(finSQLGen.logComColName);
				
				System.out.println( actID + "\t" + finID + "\t" + Double.toString(amount) + "\t" 
						+ time + "\t" + comment );
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ps.close();
		}
		
	}
	
	/** 
	 * to insert a new log
	 * @param comment use for record the reason of the change of this log.
	 *  Normally is the transaction number, or things like "draw from counter"
	 *  */
	public boolean logInsert(long FinID, double amount, String comment) throws SQLException {
		java.sql.PreparedStatement ps = con.prepareStatement(finSQLGen.logNewEntry());
		
		try {
			ps.setLong(1, FinID);
			ps.setDouble(2, amount);
			ps.setString(3, comment);
			return ps.execute();	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ps.close();
		}
		return false;
	}
	
	// -----------------------interest rate---------------------------
	
	/** 
	 * to change the rate of interest
	 * */
	public int changeInterestRate(double newrate) throws SQLException {
		java.sql.PreparedStatement ps = con.prepareStatement(finSQLGen.updateInterestRate());
		try {
			ps.setDouble(1, newrate);
			int result = ps.executeUpdate();
			return result;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ps.close();
		}
		return 0;
	}
	
	/**
	 * this function is use to get interest rate
	 * @return the rate use for calculate interest
	 * */
	public double getInterestRate() throws SQLException {
		Statement statement = con.createStatement();
		double rate = 0;
		String sql = finSQLGen.getInterestRate();
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				rate = rs.getDouble(0);
			}
			return rate;
			
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
	
	
	
	
	
}




