package StockTradingSystem.controller;
import finSys.finSysDB;

public class AdminLoginUIController extends AdminUIController {

	finSysDB myDB=finSysDB.getInstence();
    public void close() {
        getApp().stage.close();
    }

    public void login() throws Exception {
        // TODO: 登陆判断
    	myDB.getDB().getConnection(null);
        getApp().gotofinMainUI();
    }
}
