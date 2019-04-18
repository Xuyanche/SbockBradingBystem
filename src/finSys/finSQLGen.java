package finSys;
import java.util.Scanner;

public class finSQLGen {
	static String financeTableName = "financeTable";
	static String IDColumnName = "financeID";
	
	
	public static void main() {
		Scanner input = new Scanner(System.in);
		String read = input.next();
		
		System.out.println(sqlGenerateSearch(read));
		return;
	}

	
	
	
	public static String sqlGenerateSearch(String FinanceID) {
		String sqlselect = " select " + "*";
		String sqlfrom = " from " + financeTableName;
		String sqlwhere =  " where " + IDColumnName + " = " + FinanceID + ";";
		
		
		return sqlselect + sqlfrom + sqlwhere;
	}
	
}
