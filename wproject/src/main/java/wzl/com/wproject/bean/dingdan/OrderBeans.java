package wzl.com.wproject.bean.dingdan;

public class OrderBeans {

    /**
     * commentStatus : 1
     * commodityCount : 1
     * commodityId : 11
     * commodityName : 盒装葫芦粉扑美妆蛋化妆海绵
     * commodityPic : http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/1.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/2.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/3.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/4.jpg,http://172.17.8.100/images/small/commodity/mzhf/mzgj/1/5.jpg
     * commodityPrice : 9
     * orderDetailId : 757
     */

    private int commentStatus;
    private int commodityCount;
    private int commodityId;
    private String commodityName;
    private String commodityPic;
    private int commodityPrice;
    private int orderDetailId;

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityPic() {
        return commodityPic;
    }

    public void setCommodityPic(String commodityPic) {
        this.commodityPic = commodityPic;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    @Override
    public String toString() {
        return "OrderBeans{" +
                "commentStatus=" + commentStatus +
                ", commodityCount=" + commodityCount +
                ", commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", commodityPic='" + commodityPic + '\'' +
                ", commodityPrice=" + commodityPrice +
                ", orderDetailId=" + orderDetailId +
                '}';
    }
}
