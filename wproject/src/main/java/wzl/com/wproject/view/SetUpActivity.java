package wzl.com.wproject.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.JumpActivity;
import wzl.com.wproject.MainActivity;
import wzl.com.wproject.R;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;


public class SetUpActivity extends AppCompatActivity {

    private Button mbt_tui;
    private List<User> list = new ArrayList<>();
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        sp = getSharedPreferences("login",Context.MODE_PRIVATE);
        //初始化控件
        mbt_tui = findViewById(R.id.mBt_tui);
        mbt_tui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询数据库
                DaoSession daoSession = DaoMaster.newDevSession(SetUpActivity.this, UserDao.TABLENAME);
                UserDao userDao = daoSession.getUserDao();
                for (int i = 0; i < list.size(); i++) {
                    User user = list.get(i);
                    userDao.delete(user);
                }
                Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("zidong",false);
                edit.commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
