package StockTradingSystem.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sts_server.FinsysToServer;
import finSys.*;


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

        finSQLConnect Con = finSysDB.getInstence().getDB();
        boolean c = Con.checkState(FinsysToServer.customer.getFundId());
        if(c == false) {
            getApp().FinSysWarningUI("account locked");
            getApp().gotofinworkUI();
        }


    	if(Con ==null)System.out.println("con = null");
    	Con.changeBal(FinsysToServer.customer.getFundId(), Double.valueOf(Amount), "draw or save");
//    	FinsysToServer.Reposit_Withdraw(Double.valueOf(Amount));
		System.out.println("Transaction amount: "+Amount);
		getApp().FinSysWarningUI("Transaction amount: "+Amount);
    	getApp().gotofinworkUI();
    }

}
