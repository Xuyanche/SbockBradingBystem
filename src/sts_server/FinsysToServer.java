package sts_server;

import com.google.gson.Gson;
import java.io.IOException;
import sts_server.utils.*;

public class FinsysToServer {

	private static FinsysAccount customer;
	
	public static boolean FinsysLogin(Long customerID,String pwd) throws IOException{
		FinsysAccount user=new FinsysAccount(customerID,pwd);
		String json=new Gson().toJson(user);
		
		System.out.println(json);

	    CustomResp cr = new HttpCommon().doHttp("/fund/login", "POST", json);
	    String res=cr.getResultJSON();
	    String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus.equals("true")) {
	    	customer=user;
	    	customer.setPassword("");
	    	return true;
	    }	
	    else
	    	return false;
	}
	
	public static long CreateAccount(String stockID,String password, double money) {
		FinsysBound newaccount=new FinsysBound(stockID,password,money);
		String json=new Gson().toJson(newaccount);
		CustomResp cr = new HttpCommon().doHttp("/fund/login", "POST", json);
		String res=cr.getResultJSON();
		
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus.equals("true")) {
	    	String obj=cr.getObjectJSON();
	    	int start=obj.indexOf("\"FinsysID\":")+11;
	    	String ID=obj.substring(start,obj.indexOf(',',start));
	    	Long id=Long.valueOf(ID);
	    	customer.setID(id);
	    	customer.setPassword(password);
	    	return id;
	    }	
	    else
	    	return (long)-1;
	}
	
	public static String SearchLog() {
		
		
		String json=new Gson().toJson(customer);
		CustomResp cr = new HttpCommon().doHttp("/fund/account/info", "POST", json);
		String res=cr.getResultJSON();
		
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus.equals("true")) {
	    	
	    	return cr.getObjectJSON();
	    }	
	    else
	    	return "ERROR";
	}
	
	
	public static double Reposit_Withdraw(double amount) {
		if(customer.getID()==-1) {
			return -1;
		}
		customer.setAmount(amount);
		String json=new Gson().toJson(customer);
		CustomResp cr = new HttpCommon().doHttp("/fund/account/transfer", "POST", json);
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    
	    if(resStatus.equals("true")) {
	    	String obj=cr.getObjectJSON();
	    	int start=obj.indexOf("\"amount\":")+9;
	    	String money=obj.substring(start, obj.indexOf('\"', start));
	    	return Double.valueOf(money);
	    }	
	    else
	    	return -1;
	}
	
	public static boolean calcInterests() {
		CustomResp cr = new HttpCommon().doHttp("/fund/update/balance", "POST");
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus=="true") 
	    	return true;
	    else 
	    	return false;
	}
	
	public static boolean changePassword(String newpwd) {
		if(customer.getID()==-1) {
			return false;
		}
		customer.setPassword(newpwd);
		String json=new Gson().toJson(customer);
		CustomResp cr = new HttpCommon().doHttp("/fund/password/update", "POST", json);
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus=="true") {
	    	return true;
	    }
	    else 
	    	return false;   
	}
	
	
	public static String ChangeState() {
		if(customer.getID()==-1) {
			return "ERROR";
		}
		
		String json=new Gson().toJson(customer);
		CustomResp cr = new HttpCommon().doHttp("/fund/state/change", "POST", json);
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus=="true") {
	    	return cr.getObjectJSON();
	    }
	    else 
	    	return "ERROR";   
	}
	

	public static boolean DeletAccount() {
		if(customer.getID()==-1) {
			return false;
		}
		String json=new Gson().toJson(customer);
		CustomResp cr = new HttpCommon().doHttp("/fund/account/delete", "POST", json);
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    
	    if(resStatus=="true") {
	    	System.out.println("Successfully delete Account "+customer.getID().toString());
	    	customer.setID((long)-1);
	    	return true;
	    }
	    else {
	    	System.out.println("fail to delete account");
	    	return false;
	    }
	    
	}
	
	
	
	
}
