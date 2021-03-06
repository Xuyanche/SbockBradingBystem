package sts_server;

import com.google.gson.Gson;
import java.io.IOException;
import sts_server.utils.*;



public class FinsysToServer {

	private static FundAccount customer;
	
	public static boolean FinsysLogin(Long customerID,String pwd) throws IOException{
		FundAccount user=new FundAccount();
		user.setFundId(customerID.intValue());
		user.setPassword(pwd);
		
		String json=new Gson().toJson(user);
		
		System.out.println(json);

	    CustomResp cr = new HttpCommon().doHttp("/client/login", "POST", json);
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
		FundAccount newaccount=new FundAccount(-1,Integer.valueOf(stockID),password,money,0,true);
		String json=new Gson().toJson(newaccount);
		CustomResp cr = new HttpCommon().doHttp("/fund/new/", "GET", json);
		String res=cr.getResultJSON();
		
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println(res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus.equals("true")) {
	    	String obj=cr.getObjectJSON();
	    	int start=obj.indexOf("\"FinsysID\":")+11;
	    	String ID=obj.substring(start,obj.indexOf(',',start));
	    	int id=Integer.valueOf(ID);
	    	customer.setFundId(id);
	    	customer.setPassword(password);
	    	return (long)id;
	    }	
	    else
	    	return (long)-1;
	}
	
	public static String SearchLog() {
		
		
		String json=new Gson().toJson(customer);
		CustomResp cr = new HttpCommon().doHttp("/fund/"+customer.getFundId(), "GET", json);
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
		if(customer.getFundId()==-1) {
			return -1;
		}
		TransactionLog log=new TransactionLog();
		log.setChangeAmount(amount);
		log.setComment("transaction");
		String json=new Gson().toJson(log);
		CustomResp cr = new HttpCommon().doHttp("/fund/transfer/", "POST", json);
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
	
	
	////////////////////////////////////
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
		if(customer.getFundId()==-1) {
			return false;
		}
		//"/fund/update/password/{fundId}/{newPassword}"
		CustomResp cr = new HttpCommon().doHttp("/fund/update/password/"+customer.getFundId()+"/"+newpwd, "POST", null);
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
		if(customer.getFundId()==-1) {
			return "ERROR";
		}
		
		//String json=new Gson().toJson(customer);
	//@RequestMapping(value = "/fund/change/state/{fundId}", method = POST)
		CustomResp cr = new HttpCommon().doHttp("/fund/check/state/"+customer.getFundId(), "GET", null);
		String res2=cr.getResultJSON();
		System.out.print("res2 = "+res2);
		
		//CustomResp
		cr = new HttpCommon().doHttp("/fund/change/state/"+Integer.toString(customer.getFundId()), "POST", null);
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println("res = "+res);
	    System.out.println(cr.getObjectJSON());
	    if(resStatus=="true") {
	    	return cr.getObjectJSON();
	    }
	    else 
	    	return "ERROR";   
	}
	

	public static boolean DeletAccount() {
		if(customer.getFundId()==-1) {
			return false;
		}
		String json=new Gson().toJson(customer);
		
		CustomResp cr = new HttpCommon().doHttp("/fund/delete/"+customer.getFundId(), "POST", json);
		String res=cr.getResultJSON();
		String resStatus = res.substring(res.lastIndexOf("\"status\":")+9, res.indexOf(','));
	    System.out.println("res="+res);
	    System.out.println("cr="+cr.getObjectJSON());
	    
	    if(resStatus.equals("true")) {
	    	System.out.println("Successfully delete Account "+Integer.toString(customer.getFundId()));
	    	customer.setFundId(-1);
	    	return true;
	    }
	    else {
	    	System.out.println("fail to delete account");
	    	return false;
	    }
	    
	}
	
	
	public static String CustomerInfo() {
		if(customer.getFundId()==-1) {
			return "ERROR";
		}
		CustomResp cr = new HttpCommon().doHttp("/fund/"+customer.getFundId(), "GET", null);
		String res=cr.getResultJSON();
		if(res.indexOf("true")>=0) {
			return cr.getObjectJSON();
		}
		else {
			return "CANNOT GET CUSTOMER INFO";
		}
		
		
	}
	
	public static boolean SetCustomer(int fid,int sid,String pwd,double bal,double itst,boolean stat) {
		customer=new FundAccount(fid,sid,pwd,bal,itst,stat);
		return true;
	}
	
	
	
	
	
	
}
