package wzl.com.wproject.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import wzl.com.wproject.R;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;

public class MyProfileActivity extends AppCompatActivity {

    private List<User> list = new ArrayList<>();
    private SimpleDraweeView msim_tou;
    private TextView mtv_nicheng;
    private TextView mtv_mima;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initView();
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(MyProfileActivity.this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            //获取头像
            String headPic = user.getHeadPic();
            msim_tou.setImageURI(headPic);
            //获取昵称
            String nickName = user.getNickName();
            mtv_nicheng.setText(nickName);
        }
        //获取密码
        sp = getSharedPreferences("login",Context.MODE_PRIVATE);
        String pass = sp.getString("pass", "");
        mtv_mima.setText(pass);
    }
    //初始化组件
    public void initView() {
        msim_tou = findViewById(R.id.mSim_tou);
        mtv_nicheng = findViewById(R.id.mTV_nicheng);
        mtv_mima = findViewById(R.id.mTV_mima);
    }
}
