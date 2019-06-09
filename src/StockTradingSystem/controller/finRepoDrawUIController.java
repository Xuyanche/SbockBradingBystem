package StockTradingSystem.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sts_server.FinsysToServer;


public class finRepoDrawUIController extends AdminUIController {

   
    @FXML
    private Text welcome;
    @FXML
    private JFXTextField MoneyFeild;
    
    @FXML
    public void gotoFinWork() throws Exception {
    	getApp().gotofinworkUI();
    }

    @FXML
    public void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }
    
  
	public void Confirm() throws Exception {
		
    	String Amount=MoneyFeild.getText();
    	double result=FinsysToServer.Reposit_Withdraw(Double.valueOf(Amount));
		if(result<0) {
			getApp().FinSysWarningUI("Transcation failed");
			getApp().gotofinworkUI();
		}
		else {
			System.out.println("Transaction amount: "+Amount);
			getApp().FinSysWarningUI("Transaction amount: "+Amount);
	    	getApp().gotofinworkUI();
		}
    	
    }

}
