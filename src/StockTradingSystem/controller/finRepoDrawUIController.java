package StockTradingSystem.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sts_server.FinsysToServer;


public class finRepoDrawUIController extends AdminUIController {

   
    @FXML
    private Text welcome;
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
    	FinsysToServer.Reposit_Withdraw(Double.valueOf(Amount));
		System.out.println("Change Money Successfully!");
    	getApp().gotofinworkUI();
    }

}
