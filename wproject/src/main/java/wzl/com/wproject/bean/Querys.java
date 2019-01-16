package wzl.com.wproject.bean;

public class Querys {


    /**
     * amount : 9
     * consumerTime : 1547377578000
     * orderId : 20190109142912409956
     * userId : 956
     */

    private int amount;
    private long consumerTime;
    private String orderId;
    private int userId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getConsumerTime() {
        return consumerTime;
    }

    public void setConsumerTime(long consumerTime) {
        this.consumerTime = consumerTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Querys{" +
                "amount=" + amount +
                ", consumerTime=" + consumerTime +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
