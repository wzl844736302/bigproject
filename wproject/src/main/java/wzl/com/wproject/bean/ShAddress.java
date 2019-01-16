package wzl.com.wproject.bean;

public class ShAddress {

    /**
     * address : 北京
     * createTime : 1542474327000
     * id : 6
     * phone : 18736970210
     * realName : 诸葛
     * userId : 12
     * whetherDefault : 1
     * zipCode : 100001
     */

    private String address;
    private long createTime;
    private int id;
    private String phone;
    private String realName;
    private int userId;
    private int whetherDefault;
    private String zipCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWhetherDefault() {
        return whetherDefault;
    }

    public void setWhetherDefault(int whetherDefault) {
        this.whetherDefault = whetherDefault;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "ShAddress{" +
                "address='" + address + '\'' +
                ", createTime=" + createTime +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", userId=" + userId +
                ", whetherDefault=" + whetherDefault +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
