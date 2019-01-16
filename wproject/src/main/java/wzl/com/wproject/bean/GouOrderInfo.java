package wzl.com.wproject.bean;

public class GouOrderInfo {

    /**
     * commodityId : 3
     * count : 1
     */

    private int commodityId;
    private int count;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return count;
    }

    public void setAmount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "commodityId=" + commodityId +
                ", amount=" + count +
                '}';
    }

    public GouOrderInfo(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }
}
