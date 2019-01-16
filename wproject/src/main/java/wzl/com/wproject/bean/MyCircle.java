package wzl.com.wproject.bean;

public class MyCircle {

    /**
     * commodityId : 1
     * content : 盘他
     * createTime : 1547516647000
     * greatNum : 3
     * headPic : http://172.17.8.100/images/small/default/user.jpg
     * id : 430
     * image : http://172.17.8.100/images/small/circle_pic/2019-01-14/3521920190114194407.jpg
     * nickName : 4y_J8Q2o
     * userId : 956
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

    @Override
    public String toString() {
        return "MyCircle{" +
                "commodityId=" + commodityId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", greatNum=" + greatNum +
                ", headPic='" + headPic + '\'' +
                ", id=" + id +
                ", image='" + image + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
