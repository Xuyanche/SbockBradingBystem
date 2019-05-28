package StockTradingSystem.controller;


import sts_server.FinsysToServer;
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
    
  
	public void close() {
        getApp().stage.close();
    }

    @SuppressWarnings("unused")
	public void login() throws Exception {
    	String userpwd=UserPwd.getText();
    	long userid=Long.valueOf(UserId.getText());
    	
    	
    	if(FinsysToServer.FinsysLogin(userid, userpwd)) {
    		
            getApp().gotofinworkUI();
    	}
    	else
    		System.out.println("Ivalid UserID or Password!");
    	
    }
}
