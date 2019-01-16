package wzl.com.wproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.adapter.QueryListViewAdapter;
import wzl.com.wproject.bean.Querys;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.bean.Wallet;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.WalletPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class WalletActivity extends AppCompatActivity {

    private List<User> list = new ArrayList<>();
    private int userId;
    private String sessionId;
    private WalletPresenter presenter;
    private TextView mtv_money;
    private ListView mlist_query;
    private List<Querys> list1 = new ArrayList<>();
    private QueryListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        //初始化组件
        mtv_money = findViewById(R.id.mTV_money);
        mlist_query = findViewById(R.id.mlist_query);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            userId = user.getUserId();
            sessionId = user.getSessionId();
        }
        presenter = new WalletPresenter(new WalletCall());
        presenter.mLogin(userId,sessionId,1,20);
        //设置适配器
        adapter = new QueryListViewAdapter(this,list1);
        mlist_query.setAdapter(adapter);
    }
    //实现钱包接口
    class WalletCall implements DataCall<Result<Wallet>>{
        @Override
        public void success(Result<Wallet> data) {
            if (data.getStatus()==0000){
                Wallet result = data.getResult();
                int balance = result.getBalance();
                mtv_money.setText(balance+"");
                List<Querys> detailList = result.getDetailList();
                list1.addAll(detailList);
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
