package wzl.com.wproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.adapter.MyCircleAdapter;
import wzl.com.wproject.bean.MyCircle;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.MyCirclePresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class MyCircleActivity extends AppCompatActivity {
    private List<User> list = new ArrayList<>();
    private int userId;
    private String sessionId;
    private RecyclerView mrecycler_circle;
    private MyCirclePresenter myCirclePresenter;
    private MyCircleAdapter myCircleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        //初始化控件
        mrecycler_circle = findViewById(R.id.mrecycler_circle);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            //获取id
            userId = user.getUserId();
            //获取用户登录凭证
            sessionId = user.getSessionId();
        }
        //设置适配器
        myCircleAdapter = new MyCircleAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecycler_circle.setLayoutManager(layoutManager);
        mrecycler_circle.setAdapter(myCircleAdapter);
        //设置数据
        myCirclePresenter = new MyCirclePresenter(new MyCircleCall());
        myCirclePresenter.mLogin(userId,sessionId,1,20);
    }
    //实现我的圈子接口
    class MyCircleCall implements DataCall<Result<List<MyCircle>>>{
        @Override
        public void success(Result<List<MyCircle>> data) {
            if (data.getStatus()==0000){
                List<MyCircle> result = data.getResult();
                myCircleAdapter.addAll(result);
                myCircleAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
