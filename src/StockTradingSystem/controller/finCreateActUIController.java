package StockTradingSystem.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sts_server.FinsysToServer;


public class finCreateActUIController extends AdminUIController{

	@FXML
	private JFXTextField StockID;
	private JFXTextField InitialMoney;
	private JFXTextField Password;

	
    @FXML
    public void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }

    @FXML
    public void confirm() throws Exception {
    	String stockid=StockID.getText();
    	String money=InitialMoney.getText();
    	String password=Password.getText();
    	
    	System.out.println("creating new account....");
    	long newFinID =FinsysToServer.CreateAccount(stockid, password, Double.valueOf(money));
    	
    	if(newFinID<0) {
    		System.out.println("Error");
    	}
    	else {
    		System.out.println("Create new Accout at: " + newFinID + "/n associate stock account : "+ stockid);    	
            getApp().gotofinworkUI();
    	}
    	getApp().gotofinMainUI();
    	
    }
   
    	

}
