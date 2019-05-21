package StockTradingSystem.controller;


import finSys.finSysDB;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class finCreateAccountUIController extends AdminUIController {

	    @FXML
	    private JFXTextField InitialAmount;

	    @FXML
	    private JFXTextField Password;

	    @FXML
	    private JFXTextField StockAccount;

	private finSysDB myDB=finSysDB.getInstence();
	
    public void close() {
        getApp().stage.close();
    }

    public void confirm() throws Exception {
        
    	String stockSysAccount=StockAccount.getText();
    	String password= Password.getText();
    	String balance= InitialAmount.getText();
    	
    	
    	System.out.println("creating new account....");
    	long newFinID = myDB.getDB().createNewFinAccount(stockSysAccount, password, Double.valueOf(balance));
    	System.out.println("Create new Accout at: " + newFinID + "/n associate stock account : "+ stockSysAccount);    	
    	
        getApp().gotofinworkUI();
    }
}
