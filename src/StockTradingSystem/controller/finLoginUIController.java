package StockTradingSystem.controller;


import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sts_server.FinsysToServer;


public class finLoginUIController extends AdminUIController{


    @FXML
    private JFXTextField AccountFeild;

    @FXML
    private JFXButton ConfirmBtn;

    @FXML
    private JFXPasswordField finsysPwdFeild;

    @FXML
    void Confirm() throws Exception, IOException {
    	String finAccount=AccountFeild.getText();
    	String password=finsysPwdFeild.getText();
    	
    	if(FinsysToServer.FinsysLogin(Long.valueOf(finAccount), password))
    	//FinsysToServer.SetCustomer(1, 1,"123123" , 100, 0.1, true);
    		getApp().gotofinworkUI();
    	else {
    		System.out.println("无法登录");
    		getApp().FinSysWarningUI("账户名或密码错误");
    	}
    		
    }

    @FXML
    void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }



}

