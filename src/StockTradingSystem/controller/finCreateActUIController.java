package StockTradingSystem.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sts_server.FinsysToServer;
import finSys.*;


public class finCreateActUIController extends AdminUIController{

	@FXML
	private JFXTextField StockID;
	@FXML
	private JFXTextField InitialMoney;
	@FXML
	private JFXTextField Password;

	
    @FXML
    public void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }

    @FXML
    public void confirm() throws Exception {
    	
    	String stockid=StockID.getText();
		String money= InitialMoney.getText();
		String password=Password.getText();
    	finSQLConnect con = finSysDB.getInstence().getDB();
    	if(con == null) System.out.println("con = null");
    	System.out.println("creating new account....");
//    	long newFinID =FinsysToServer.CreateAccount(stockid, password, Double.valueOf(money));
    	long newFinID = con.createNewFinAccount(stockid, password, Double.valueOf(money));
//    	System.out.println(newFinID);

 
    	if(newFinID<0) {
    		//System.out.println("Error");
    		getApp().FinSysWarningUI("CANNOT CREATE ACCOUNT!");
    		getApp().gotofinMainUI();
    	}
    	else {
    		//System.out.println("Create new Accout at: " + newFinID + "\n associate stock account : "+ stockid); 
    		getApp().FinSysWarningUI("Create new Accout at: " + newFinID + "\n associate stock account : "+ stockid);
    		System.out.println(money+"  "+password);
            getApp().gotofinworkUI();
    	}
    
    	
    }
   
    	

}
