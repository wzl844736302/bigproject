package wzl.com.wproject.view;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.PayPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class PayActivity extends AppCompatActivity {
    private List<User> list = new ArrayList<>();
    private int userId;
    private String sessionId;
    private String orderid;
    private double price;
    private PayPresenter presenter;
    private Button mbt_zf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(PayActivity.this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            userId = user.getUserId();
            sessionId = user.getSessionId();
        }
        //初始化控件
        mbt_zf = findViewById(R.id.mbt_zf);
        //接收值
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        price = intent.getDoubleExtra("price", 0);
        
        //设置余额支付
        mbt_zf.setText("余额支付"+price +""+"元");
        //点击支付
        mbt_zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置数据
                presenter = new PayPresenter(new PayCall());
                presenter.mLogin(userId,sessionId,orderid,1);
            }
        });
    }
  
    //实现支付接口
    class PayCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Message message = new Message();
                message.arg1 = 102;
                EventBus.getDefault().postSticky(message);
                finish();
                Toast.makeText(PayActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
