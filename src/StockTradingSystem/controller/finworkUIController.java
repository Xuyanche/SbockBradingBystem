package StockTradingSystem.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import sts_server.FinsysToServer;
import java.util.*;

public class finworkUIController extends AdminUIController {
	  	@FXML
	    private StackPane SearchLogBtn;

	    @FXML
	    private StackPane ChangeBalance;

	    @FXML
	    private StackPane ChangeState;

	    @FXML
	    private StackPane ChangePwd;

	    @FXML
	    private StackPane DeleteAccount;
    @FXML
    void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }
    

    /* 按钮特效 */
    //=================SearchLog=============
   
    public void moveSearchLog( ) {   ControllerUtils.btnMove(SearchLogBtn);	 }
    public void pressSearchLog( ) {	ControllerUtils.btnPress(SearchLogBtn);  }
    public void exitSearchLog( ) { ControllerUtils.btnRelease(SearchLogBtn);    }
    public void releaseSearchLog( ) throws Exception {  
    	ControllerUtils.btnRelease(SearchLogBtn);
    	
    	String result=FinsysToServer.SearchLog();
    	System.out.println(result);
    	
    	getApp().gotofinLogUI(result);
    }
    
    //================ChangBalance=============
    public  void exitChangeBalance( ) {	ControllerUtils.btnRelease(ChangeBalance);   }
    public void moveChangeBalance( ) { ControllerUtils.btnMove(ChangeBalance); }
    public void pressChangeBalance( ) {  ControllerUtils.btnPress(ChangeBalance);  }
    public void releaseChangeBalance( ) throws Exception {
    	ControllerUtils.btnRelease(ChangeBalance);
    	getApp().gotofinRepoDrawUI();
    }

    
    
    
    //============changePwd============
   
    public void exitChangePwd( ) {ControllerUtils.btnRelease(ChangePwd);    }
    public void moveChangePwd( ) {ControllerUtils.btnMove(ChangePwd);    }
    public void pressChangePwd( ) {ControllerUtils.btnPress(ChangePwd);    }
    public  void releaseChangePwd( ) throws Exception {
    	ControllerUtils.btnRelease(ChangePwd);
    	getApp().gotofinChangePwdUI();
    }
    
    
    
    //==================changeState===============
    public void exitChangeState( ) {ControllerUtils.btnRelease(ChangeState);    }
    public void moveChangeState( ) {ControllerUtils.btnMove(ChangeState);    }
    public void pressChangeState( ) {  ControllerUtils.btnPress(ChangeState);   }
    public void releaseChangeState( ) throws Exception {
    	ControllerUtils.btnRelease(ChangeState);
    	String result=FinsysToServer.ChangeState();
    	
    	//System.out.println("customer state: "+result);
    	getApp().FinSysWarningUI("customer state: "+result);
    }
   
    
    
   //==============deleteAccount==============
    public void moveDeleteAccount( ) {ControllerUtils.btnMove(DeleteAccount);    }
    public void exitDeleteAccount( ) {ControllerUtils.btnRelease(DeleteAccount);    }
    public void pressDeleteAccount( ) {ControllerUtils.btnPress(DeleteAccount);    }
    public void releaseDeleteAccount( ) throws Exception {
    	ControllerUtils.btnRelease(DeleteAccount);
    	if(FinsysToServer.DeletAccount()) {
    		getApp().FinSysWarningUI("Successfully Delete Account");
    		getApp().gotofinMainUI();
    	}    	
    	
    	
    }

    

}
