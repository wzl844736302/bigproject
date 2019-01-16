package wzl.com.wproject.view;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.AddressAdapter;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.ShAddress;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.AddressPresenter;
import wzl.com.wproject.utils.NetWork;
import wzl.com.wproject.utils.util.UIUtils;

public class AddressActivity extends AppCompatActivity {

    private List<User> list = new ArrayList<>();
    private int userId;
    private String sessionId;
    private AddressPresenter presenter;
    private RecyclerView mrecycler_address;
    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //绑定
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //初始化控件
        mrecycler_address = findViewById(R.id.mrecycler_address);
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
        adapter = new AddressAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecycler_address.setLayoutManager(layoutManager);
        mrecycler_address.setAdapter(adapter);
        presenter = new AddressPresenter(new AddressCall());
        presenter.mLogin(userId,sessionId);
    }
    //实现地址列表接口
    class AddressCall implements DataCall<Result<List<ShAddress>>>{
        @Override
        public void success(Result<List<ShAddress>> data) {
            if (data.getStatus()==0000){
                List<ShAddress> result = data.getResult();
                adapter.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //接收值
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void jieshou(Message message){
        if (message.arg1==1){
            adapter.clear();
            Result result = (Result) message.obj;
            presenter.mLogin(userId,sessionId);
            adapter.notifyDataSetChanged();
        }
    }
    //点击新增按钮跳转
    @OnClick(R.id.mBt)
    public void mbt(){
        Intent intent = new Intent(AddressActivity.this, NewAddressActivity.class);
        startActivity(intent);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
