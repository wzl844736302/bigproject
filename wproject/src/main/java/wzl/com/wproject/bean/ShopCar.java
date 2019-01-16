package wzl.com.wproject.bean;

public class ShopCar {

    /**
     * commodityId : 19
     * commodityName : 环球 时尚拼色街拍百搭小白鞋 韩版原宿ulzzang板鞋 女休闲鞋
     * count : 0
     * pic : http://172.17.8.100/images/small/commodity/nx/bx/2/1.jpg
     * price : 78
     */

    private int commodityId;
    private String commodityName;
    private int count;
    private String pic;
    private int price;
    private int ischeck;

    public int getIscheck() {
        return ischeck;
    }

    public void setIscheck(int ischeck) {
        this.ischeck = ischeck;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShopCar{" +
                "commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", count=" + count +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                '}';
    }
}
