package wzl.com.wproject.view;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.SettlementAdapter;
import wzl.com.wproject.bean.OrderInfo;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.ShAddress;
import wzl.com.wproject.bean.ShopCar;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.AddressPresenter;
import wzl.com.wproject.presenter.OrderPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class SettlementActivity extends AppCompatActivity {
    private List<User> list = new ArrayList<>();
    private RecyclerView mrecycler_settelment;
    private List<ShopCar> carList1 = new ArrayList<>();
    private SettlementAdapter adapter;
    private TextView mtv_shu_set;
    private TextView mtv_sum_set;
    private OrderPresenter orderPresenter;
    private List<OrderInfo> infoList = new ArrayList<>();
    private int userId;
    private String sessionId;
    int num = 0;
    double sum = 0;
    private AddressPresenter addressPresenter;
    private int addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        //初始化控件
        mrecycler_settelment = findViewById(R.id.mrecycler_settlement);
        mtv_shu_set = findViewById(R.id.mTV_shu_set);
        mtv_sum_set = findViewById(R.id.mTV_sum_set);
        //绑定
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            userId = user.getUserId();
            sessionId = user.getSessionId();
        }
        //设置适配器
        adapter = new SettlementAdapter(this,carList1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecycler_settelment.setLayoutManager(layoutManager);
        mrecycler_settelment.setAdapter(adapter);
        //设置请求地址
        addressPresenter = new AddressPresenter(new AddressCall());
        addressPresenter.mLogin(userId,sessionId);
    }
    //接收值
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void shou(Message message){
        if (message.arg1==5){
            List<ShopCar> carList = (List<ShopCar>) message.obj;
            carList1.addAll(carList);
            for (int i = 0; i < carList.size(); i++) {
                ShopCar shopCar = carList.get(i);
                num = num + shopCar.getCount();
                sum = sum + shopCar.getPrice();
            }
            mtv_shu_set.setText(num+"");
            mtv_sum_set.setText(sum+"");
            adapter.notifyDataSetChanged();
        }
    }
    //剑姬提交订单
    @OnClick(R.id.mBt_tijiao)
    public void tijiao(){
        for (int i = 0; i < carList1.size(); i++) {
            ShopCar shopCar = carList1.get(i);
            OrderInfo orderInfo = new OrderInfo(shopCar.getCommodityId(),shopCar.getCount());
            infoList.add(orderInfo);
        }
        String s = new Gson().toJson(infoList);
        orderPresenter = new OrderPresenter(new OrderCall());
        orderPresenter.mLogin(userId,sessionId,s,sum,addressId);
    }
    //实现创建订单接口
    class OrderCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Toast.makeText(SettlementActivity.this, "创建订单成功", Toast.LENGTH_SHORT).show();
                Message message = new Message();
                message.arg1 = 2;
                EventBus.getDefault().postSticky(message);
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现地址列表接口
    class AddressCall implements DataCall<Result<List<ShAddress>>>{
        @Override
        public void success(Result<List<ShAddress>> data) {
            if (data.getStatus()==0000){
                List<ShAddress> result = data.getResult();
                for (int i = 0; i < result.size(); i++) {
                    ShAddress shAddress = result.get(i);
                    addressId = shAddress.getId();
                }
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
