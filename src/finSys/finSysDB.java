package finSys;

public class finSysDB {

	
	private static finSysDB finsysdb= new finSysDB();

	private finSQLConnect DB;
	private finSysDB() {
		DB = new finSQLConnect();
		System.out.println("connect to DB");
	}
	
	public static finSysDB getInstence() {
		return finsysdb;
	}
	
	public finSQLConnect getDB() {
		return DB;
	}
	
	private String finID;
	public String getfinID() {
		return finID;
	}
	
	public void setfinID(String newID) {
		finID=newID;
	}
}
