package sts_server;

public class FinsysAccount {
	 private Long id=(long)-1;
	 private String password="";
	 private double changeamount=0;
	 
	 public FinsysAccount(Long id, String pwd) {
		 this.id=id;
		 password=pwd;
	 }
	 public void setID(Long newID) {
		 id=newID;
	 }
	 public void setPassword(String newpassword) {
		 password=newpassword;
	 }
	 public Long getID() {
		 return id;
	 }
	 public void setAmount(double amount) {
		 changeamount=amount;
	 }

}
