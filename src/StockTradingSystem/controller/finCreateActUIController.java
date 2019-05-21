package StockTradingSystem.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import finSys.finSysDB;


public class finCreateActUIController extends AdminUIController{

	@FXML
	private JFXTextField StockID;
	private JFXTextField InitialMoney;
	private JFXTextField Password;

	private finSysDB myDB=finSysDB.getInstence();
    @FXML
    public void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }

    @FXML
    public void confirm() throws Exception {
    	String stockid=StockID.getText();
    	String money=InitialMoney.getText();
    	String password=Password.getText();
    	
    	myDB.getDB().createNewFinAccount(stockid, password, Double.valueOf(money));
    	getApp().gotofinMainUI();
    	
    }
   
    	

}
