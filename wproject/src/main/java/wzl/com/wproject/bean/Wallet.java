package wzl.com.wproject.bean;

import java.util.List;

public class Wallet {


    /**
     * balance : 99999418
     * detailList : [{"amount":9,"consumerTime":1547377578000,"orderId":"20190109142912409956","userId":956},{"amount":189,"consumerTime":1547361897000,"orderId":"20190109210616913956","userId":956},{"amount":78,"consumerTime":1547361631000,"orderId":"20190110204112035956","userId":956},{"amount":166,"consumerTime":1547361337000,"orderId":"20190110205932463956","userId":956},{"amount":139,"consumerTime":1547359166000,"orderId":"20190111174950048956","userId":956}]
     */

    private int balance;
    private List<Querys> detailList;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Querys> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Querys> detailList) {
        this.detailList = detailList;
    }
}
