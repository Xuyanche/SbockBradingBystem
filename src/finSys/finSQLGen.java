package finSys;
import java.util.Scanner;

public class finSQLGen {
	
	// values of fintable
	static double interestRate;
	static String finTabName = "financeTable";
	static String IDColName = "FinID";
	static String pwdColName = "password";
	static String balColName = "balance";
	static String interestColName = "interest";
	static String stateColName = "state";
	
	// values of finlog
	static String finLogTabName = "financeLog";
	static String actIDColName = "actionID";
	static String finIDColName = "financeID";
	static String timeColName = "actionTime";
	static String amountColName = "changeAmount";
	static String commentColName = "comment";
	
	
	
	/*
	public static void main() {
		Scanner input = new Scanner(System.in);
		String read = input.next();
		
		System.out.println(finSearch(read));
		return;
	}
	 */
	
	//---------------- sql for fintable ---------------------------------------
	
	// select * from fintable where id = FinID;
	public static String finSearch(String FinID) {
		String sqlselect = " select " + "*";
		String sqlfrom = " from " + finTabName;
		String sqlwhere =  " where " + IDColName + " = " + FinID + ";";
		
		return sqlselect + sqlfrom + sqlwhere;
	}

	// update fintable set pwd = newpwd where id = finID and password = pwd;
	public static String finUpdatepwd(String FinID, String pwd, String newpwd) {
		String sqlwhere =  " where " + IDColName + " = " + FinID + " and " + pwdColName + " = " + pwd + " ;";
		return "update " + finTabName + " set " + pwdColName + " = " + newpwd + sqlwhere;
	}
	
	// select pwd from fintable where id = finID and password = pwd;
	public static String finCheckpwd(String FinID, String pwd) {
		String sqlselect = " select " + pwdColName;
		String sqlfrom = " from " + finTabName;
		String sqlwhere =  " where " + IDColName + " = " + FinID + " and " + pwdColName + " = " + pwd + " ;";
		
		return sqlselect + sqlfrom + sqlwhere;
	}
	
	// update fintable set balance = balance + (amount) where id = finID;
	public static String finUpateBalance(String FinID, double amount) {
		String sqlwhere =  " where " + IDColName + " = " + FinID + ";";
		
		return "update " + finTabName + " set "+ balColName + " = " + balColName + " + (" + Double.toString(amount) + ") " + sqlwhere;
	}
	
	// insert into fintable values (finID, SecID, pwd, balance, 0, true);
	public static String finNewAccount(String FinID, String SecID, String pwd, String balance) {
		String sqlValues = " values ( " 
				+ FinID + " , " + SecID + " , " + pwd + " , " + balance + " ,  0 , true" + " ); ";
		return "insert into "+ finTabName + sqlValues;
	}
	
	// update fintable set state = false/true where id = FinID;
	public static String finSetState(String FinID, boolean statevalue) {
		String sqlwhere =  " where " + IDColName + " = " + FinID + ";";
		return "udpate " + finTabName + " set "+ stateColName + " = " + ((statevalue) ? "true" : "false") + sqlwhere;
	}
	
	// use to calculate interest, use multiquery to execute this sql
	public static String[] finCalInterest() {
		
		String q[] = new String[3];
		// update fintable set interest = interest + balance * rate;
		String sqlset1 =  " set " + interestColName + " = " + interestColName + " + " + balColName + " * " + Double.toString(interestRate) + " ;";
		q[0] = "update " + finTabName + sqlset1;
		// update fintable set balance = balance + floor(interest);
		String sqlset2 =  " set " + balColName + " = " + balColName + " + floor(" + interestColName + " ); " ;
		q[1] = "update " + finTabName + sqlset2;
		// update fintable set interest = interest - floor(interest);
		String sqlset3 =  " set " + interestColName + " = " + interestColName + " - floor(" + interestColName + " ); " ;
		q[2] = "update " + finTabName + sqlset3;
		
		return q;
	}
	
	
	
	
	//----------------- sql for finlog ------------------------------------------
	
	// insert into finlog values (actionID, FinID, actiontime, amount, comment);
	public static String logNewEntry(String FinID, double amount, String comment){
		// select (max(actionID)+1) from finLog;
		String getActionID = "select (max(" + actIDColName + ")+1) from " + finLogTabName + ");";
		//values (getactionID, FinID, GETDATE(), amount, comment);
		String sqlvalue = " values ( " + getActionID + " , " + FinID + " , GETDATE(), " + Double.toString(amount) + " , " + comment+ ");";
		
		return "insert into " + finLogTabName + sqlvalue;
	}
	
	// select * from finLog where id = FinID;
	public static String logCheck(String FinID) {
		return "select * from " + finLogTabName + " where " + finIDColName + " = " + FinID + ";";
	}
	
	
	
	
	
	
}
