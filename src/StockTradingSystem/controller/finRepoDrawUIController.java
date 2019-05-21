package StockTradingSystem.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import finSys.finSysDB;


public class finRepoDrawUIController extends AdminUIController {

   
    @FXML
    private Text welcome;
    private JFXTextField MoneyFeild;
    
    private finSysDB myDB=finSysDB.getInstence();

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
    	myDB.getDB().changeBal(myDB.getfinID(), Double.valueOf(Amount), "Changed by FinSys");
		System.out.println("Change Money Successfully!");
    	getApp().gotofinworkUI();
    }

}
