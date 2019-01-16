package wzl.com.wproject.bean.dingdan;

import java.util.List;

import wzl.com.wproject.bean.dingdan.OrderBeans;

public class DetalList {


    /**
     * detailList : [{"commentStatus":1,"commodityCount":1,"commodityId":11,"commodityName":"盒装葫芦粉扑美妆蛋化妆海绵","commodityPic":"http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/1.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/2.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/3.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/4.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/5.jpg","commodityPrice":9,"orderDetailId":757}]
     * expressCompName : 京东快递
     * expressSn : 1001
     * orderId : 20190108155603369956
     * orderStatus : 1
     * payAmount : 9
     * payMethod : 1
     * userId : 956
     */

    private String expressCompName;
    private String expressSn;
    private String orderId;
    private int orderStatus;
    private int payAmount;
    private int payMethod;
    private int userId;
    private List<OrderBeans> detailList;

    public String getExpressCompName() {
        return expressCompName;
    }

    public void setExpressCompName(String expressCompName) {
        this.expressCompName = expressCompName;
    }

    public String getExpressSn() {
        return expressSn;
    }

    public void setExpressSn(String expressSn) {
        this.expressSn = expressSn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderBeans> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderBeans> detailList) {
        this.detailList = detailList;
    }
}
