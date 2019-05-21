package finSys;
import java.lang.StringBuilder;

public class finSQLGen {
	
	// values of fintable
	static String finTabName = "financeTable";
	static String IDColName = "FinID"; // string
	static String secIDColName = "securityID"; // string
	static String pwdColName = "password"; // string
	static String balColName = "balance"; // double
	static String interestColName = "interest"; // double
	static String stateColName = "state"; // boolean
	
	// values of finlog
	static String finLogTabName = "financeLog";
	static String actIDColName = "actionID"; // long
	static String logFinIDColName = "financeID";
	static String timeColName = "actionTime"; // timedate
	static String amountColName = "changeAmount"; // double, var
	static String logComColName = "comment";
	
	
	// values of interest rate
	static String rateTabName = "interestRate";
	static String rateColName = "rate";
	
	/*
	public static void main() {
		Scanner input = new Scanner(System.in);
		String read = input.next();
		
		System.out.println(finSearch(read));
		return;
	}
	 */
	
	//---------------- sql for fintable ---------------------------------------
	
	// select * from fintable where id = ?(FinID);
	public static String finSearch() {
		String sqlselect = " select " + "*";
		String sqlfrom = " from " + finTabName;
		String sqlwhere =  " where " + IDColName + " = " + "?" + ";";
		
		return sqlselect + sqlfrom + sqlwhere;
	}
	
	// select * from fintable where id = FinID;
	public static String finGetGreatestFinID() {
		String sqlselect = " select " + "max (" + IDColName + ")";
		String sqlfrom = " from " + finTabName;
		
		return sqlselect + sqlfrom;
	}

	// update fintable set pwd = ?(newpwd) where id = ?(finID) and password = ?(pwd);
	public static String finUpdatepwd() {
		String sqlwhere =  " where " + IDColName + " = ? and " + pwdColName + " = ? ;";
		return "update " + finTabName + " set " + pwdColName + " = ? " + sqlwhere;
	}
	
	// select pwd from fintable where id = ?(finID) and password = ?(pwd);
	public static String finCheckpwd() {
		String sqlselect = " select " + pwdColName;
		String sqlfrom = " from " + finTabName;
		String sqlwhere =  " where " + IDColName + " = ? and " + pwdColName + " = ? ;";
		
		return sqlselect + sqlfrom + sqlwhere;
	}
	
	// update fintable set balance = balance + (amount) where id = finID;
	public static String finUpateBalance() {
		String sqlwhere =  " where " + IDColName + " =  ?;";
		
		return "update " + finTabName + " set "+ balColName + " = " + balColName + " + ( ? ) " + sqlwhere;
	}
	
	// insert into fintable values (finID, SecID, pwd, balance, 0, true);
	public static String finNewAccount() {
		String sqlValues = " values ( ?,?,? ,?, 0 , true ); ";
		return "insert into "+ finTabName + sqlValues;
	}
	
	// update fintable set state = false/true where id = FinID;
	public static String finSetState() {
		StringBuilder sqlwhere = new StringBuilder(" where ").append(IDColName).append("= ?;");
		return new StringBuilder("udpate ").append(finTabName).append(" set ").append(stateColName).append(" = ? ")
				.append(sqlwhere).toString();
	}
	
	// use to calculate interest, use multiquery to execute this sql
	public static String[] finCalInterest(double interestRate) {
		
		String q[] = new String[3];
		// update fintable set interest = interest + balance * rate;
		StringBuilder sqlset1 =  new StringBuilder(" set ").append(interestColName).append("=").append(interestColName).append('+')
				.append(balColName).append('*').append(Double.toString(interestRate)).append(';');
		q[0] = sqlset1.insert(0,"update"+finTabName).toString();
				//"update " + finTabName + sqlset1;
		// update fintable set balance = balance + floor(interest);
		StringBuilder sqlset2 =new StringBuilder("set").append(balColName).append('=').append(balColName)
				.append(" + floor(").append(interestColName).append(");");
		 //set+balColName + " = " + balColName + " + floor(" + interestColName + " ); " ;
		q[1] = sqlset2.insert(0,"update"+finTabName).toString();
				//"update " + finTabName + sqlset2;
		// update fintable set interest = interest - floor(interest);
		StringBuilder sqlset3 = new StringBuilder("set").append(interestColName).append('=').append(interestColName).append(" - floor(")
				.append(interestColName).append(");");
				// " set " + interestColName + " = " + interestColName + " - floor(" + interestColName + " ); " ;
		q[2] = sqlset3.insert(0, "update"+finTabName).toString();
				//"update " + finTabName + sqlset3;
		
		return q;
	}
	
	
	
	
	//----------------- sql for finlog ------------------------------------------
	
	// insert into finlog values (actionID, ?FinID, actiontime, ?amount, ?comment);
	public static String logNewEntry(){
		// select (max(actionID)+1) from finLog;
		//String getActionID = "select (max(" + actIDColName + ")+1) from " + finLogTabName + ");";
		StringBuilder getActionID=new StringBuilder("select (max(").append(actIDColName).append(")+1) from ").append(finLogTabName).append(");");
		//values (getactionID, ?(FinID), GETDATE(), ?(amount), ?(comment));
		StringBuilder sqlvalue = new StringBuilder(" values ( ") .append(getActionID.toString()).append(" , ? ")
				.append( " , GETDATE(), ? , ?);");
		
		return sqlvalue.insert(0,finLogTabName).insert(0, "insert into ").toString();
				//"insert into " + finLogTabName + sqlvalue;
	}
	
	// select * from finLog where id = ?(FinID);
	public static String logSearch() {
		return "select * from " + finLogTabName + " where " + logFinIDColName + " = ? ;";
	}
	
	
	//---------------- sql for interest rate ---------------------------
	public static String getInterestRate(){
		return "select * from " +  rateTabName + ";";
	}
	
	public static String updateInterestRate() {
		return "update " + rateTabName + " set " + rateColName + " =  ? ;";
	}
	
	
}
