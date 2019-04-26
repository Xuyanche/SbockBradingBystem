package finSys;
import java.util.Scanner;

public class finSQLGen {
	static String financeTableName = "financeTable";
	static String IDColumnName = "FinID";
	static String pwdColumnName = "password";
	static String balanceColumnName = "balance";
	static String stateColumnName = "state";
	
	public static void main() {
		Scanner input = new Scanner(System.in);
		String read = input.next();
		
		System.out.println(sqlGenSearch(read));
		return;
	}

	////////////////////////////////////
		
	
	
	public static String sqlGenSearch(String FinID) {
		String sqlselect = " select " + "*";
		String sqlfrom = " from " + financeTableName;
		String sqlwhere =  " where " + IDColumnName + " = " + FinID + ";";
		
		
		return sqlselect + sqlfrom + sqlwhere;
	}
	
	
	
	public static String sqlGenUpdatepwd(String FinID, String newpwd) {
		String sqlwhere =  " where " + IDColumnName + " = " + FinID + ";";
		return "update " + pwdColumnName + " = " + newpwd + sqlwhere;
	}
	

	public static String sqlGenCheckpwd(String FinID, String pwd) {
		String sqlselect = " select " + pwdColumnName;
		String sqlfrom = " from " + financeTableName;
		String sqlwhere =  " where " + IDColumnName + " = " + FinID + ";";
		
		return sqlselect + sqlfrom + sqlwhere;
	}
	

	public static String sqlGenUpateBalance(String FinID, String amount) {
		String sqlwhere =  " where " + IDColumnName + " = " + FinID + ";";
		
		return "update " + balanceColumnName + " = " + balanceColumnName + " + " + amount + sqlwhere;
	}
	
	
	public static String sqlGenNewAccount(String FinID, String SecID, String pwd, String balance) {
		String sqlValues = " values ( " 
				+ FinID + " , " + SecID + " , " + pwd + " , " + balance + " , " + "true" + " ); ";
		
		return "insert into "+ financeTableName + sqlValues;
	}
	
	
	public static String sqlGenLock(String FinID) {
		String sqlwhere =  " where " + IDColumnName + " = " + FinID + ";";
		return "udpate " + stateColumnName + " = false " + sqlwhere;
	}
	
	public static String sqlGenUnlock(String FinID) {
		String sqlwhere =  " where " + IDColumnName + " = " + FinID + ";";
		return "udpate " + stateColumnName + " = true " + sqlwhere;
	}
}
