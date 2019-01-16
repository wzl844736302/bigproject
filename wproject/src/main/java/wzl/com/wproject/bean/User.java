package wzl.com.wproject.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
        /**
         * headPic : http://172.17.8.100/images/small/head_pic/2018-11-21/20181121100733.jpg
         * nickName : OP_8mY65
         * phone : 16619958760
         * sessionId : 154276714558512
         * sex : 1
         * userId : 12
         */
        @Id(autoincrement = true)
        private long id;
        private String headPic;
        private String nickName;
        private String phone;
        private String sessionId;
        private int sex;
        private int userId;

        @Generated(hash = 411892283)
        public User(long id, String headPic, String nickName, String phone, String sessionId,
                int sex, int userId) {
            this.id = id;
            this.headPic = headPic;
            this.nickName = nickName;
            this.phone = phone;
            this.sessionId = sessionId;
            this.sex = sex;
            this.userId = userId;
        }

        @Generated(hash = 586692638)
        public User() {
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

    @Override
    public String toString() {
        return "User{" +
                "headPic='" + headPic + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", sex=" + sex +
                ", userId=" + userId +
                '}';
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
