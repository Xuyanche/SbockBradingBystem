package StockTradingSystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import sts_server.FinsysToServer;
import finSys.*;



public class finChangePwdUIController extends AdminUIController{

    @FXML
    private JFXPasswordField newPassword1;
    @FXML
    private JFXPasswordField newPassword2;

	
    @FXML
    public void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }

    @FXML
    public void confirm() throws Exception {
    	String password1=newPassword1.getText();
    	String password2=newPassword2.getText();
    	finSQLConnect con = finSysDB.getInstence().getDB();
    	if(con == null) System.out.println("con = null");

    	if(password1.equals(password2)) {
//    		if(FinsysToServer.changePassword(password1))
			int flag = -1;
			flag = con.changePwd(FinsysToServer.customer.getFundId(), FinsysToServer.customer.getPassword(), password1);
			System.out.println("flag  = " + flag );
    		if( flag >0 )
    			getApp().gotofinworkUI();
    			
    		else
    			//System.out.println("failed to change password");
    			getApp().FinSysWarningUI("failed to change password");
    	}
    	else {
    		//System.out.println("Please Enter Again");
    		getApp().FinSysWarningUI("Please Enter Again");
    	}
    }
   
    	

}
