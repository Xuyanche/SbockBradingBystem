package StockTradingSystem.controller;


import finSys.finSysDB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class finLoginUIController extends AdminUIController {
	
    @FXML
    private JFXPasswordField UserPwd;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField UserId;
    
	private finSysDB myDB=finSysDB.getInstence();
  
	public void close() {
        getApp().stage.close();
    }

    @SuppressWarnings("unused")
	public void login() throws Exception {
    	String userpwd=UserPwd.getText();
    	long userid=Long.valueOf(UserId.getText());
    	
    	
    	if(true/*myDB.getDB().checkPwd(userid, userpwd)*/) {
    		myDB.setfinID(userid);
            getApp().gotofinworkUI();
    	}
    	else
    		System.out.println("Ivalid UserID or Password!");
    	
    }
}
