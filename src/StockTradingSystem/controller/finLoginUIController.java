package StockTradingSystem.controller;


import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sts_server.FinsysToServer;
import finSys.*;


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
        finSQLConnect Con = new finSQLConnect();
        FinsysToServer.set(Integer.valueOf(finAccount), -1, password, 0, 0, true);

    	if(Con.checkPwd(Long.valueOf(finAccount), password))
    	    getApp().gotofinworkUI();

    	else
    		//System.out.println("无法登录");
    		getApp().FinSysWarningUI("账户名或密码错误");
    }

    @FXML
    void gotofinMainUI() throws Exception {
    	getApp().gotofinMainUI();
    }



}

