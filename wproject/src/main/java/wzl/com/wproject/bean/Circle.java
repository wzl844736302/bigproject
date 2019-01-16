package wzl.com.wproject.bean;

public class Circle {

    /**
     * commodityId : 3
     * content : 好用，就是太贵
     * createTime : 1542639261000
     * greatNum : 0
     * headPic : http://172.17.8.100/images/small/head_pic/2018-11-17/20181117120315.jpg
     * id : 4
     * image : http://172.17.8.100/images/small/circle_pic/2018-11-19/3509720181119085421.jpg,http://172.17.8.100/images/small/circle_pic/2018-11-19/7196220181119085421.jpg
     * nickName : 风情的人
     * userId : 1
     * whetherGreat : 2
     */
    private int commodityId;
    private String content;
    private long createTime;
    private int greatNum;
    private String headPic;
    private int id;
    private String image;
    private String nickName;
    private int userId;
    private int whetherGreat;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGreatNum() {
        return greatNum;
    }

    public void setGreatNum(int greatNum) {
        this.greatNum = greatNum;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWhetherGreat() {
        return whetherGreat;
    }

    public void setWhetherGreat(int whetherGreat) {
        this.whetherGreat = whetherGreat;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "commodityId=" + commodityId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", greatNum=" + greatNum +
                ", headPic='" + headPic + '\'' +
                ", id=" + id +
                ", image='" + image + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userId=" + userId +
                ", whetherGreat=" + whetherGreat +
                '}';
    }
}
