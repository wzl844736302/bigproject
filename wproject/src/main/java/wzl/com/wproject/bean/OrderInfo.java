package wzl.com.wproject.bean;

public class OrderInfo {

    /**
     * commodityId : 3
     * amount : 1
     */

    private int commodityId;
    private int amount;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "commodityId=" + commodityId +
                ", amount=" + amount +
                '}';
    }

    public OrderInfo(int commodityId, int amount) {
        this.commodityId = commodityId;
        this.amount = amount;
    }
}
