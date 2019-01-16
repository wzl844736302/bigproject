package wzl.com.wproject.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import butterknife.Unbinder;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.ShopCarAdapter;
import wzl.com.wproject.bean.GouOrderInfo;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.ShopCar;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.ShopCarPresenter;
import wzl.com.wproject.presenter.ShopPresenter;
import wzl.com.wproject.utils.util.UIUtils;
import wzl.com.wproject.view.GoodsdetailsActivity;
import wzl.com.wproject.view.SettlementActivity;

public class Frag_center extends Fragment implements ShopCarAdapter.TotalListener,ShopCarAdapter.OndeleteListenter{

    private List<User> list = new ArrayList<>();
    private RecyclerView mrecycler_center;
    private ShopCarPresenter shopCarPresenter;
    private int userId;
    private String sessionId;
    private ShopCarAdapter shopCarAdapter;
    private TextView mtv_sum;
    private List<ShopCar> shopCars = new ArrayList<>();
    private Unbinder bind;
    private CheckBox mcb_quan;
    private ShopPresenter shopPresenter;
    private int commodityId;
    private List<GouOrderInfo> gouOrderInfos = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_center,container,false);
        //初始化控件
        mrecycler_center = view.findViewById(R.id.mrecycler_center);
        mtv_sum = view.findViewById(R.id.mTV_sum);
        mcb_quan = view.findViewById(R.id.mCB_quan);
        bind = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            userId = user.getUserId();
            sessionId = user.getSessionId();
        }
        //设置适配器
        shopCarAdapter = new ShopCarAdapter(getActivity(),shopCars);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mrecycler_center.setLayoutManager(layoutManager);
        mrecycler_center.setAdapter(shopCarAdapter);
        //设置数据
        shopCarPresenter = new ShopCarPresenter(new ShopCarCall());
        shopCarPresenter.mLogin(userId,sessionId);
        //设置总价格
        shopCarAdapter.setTotalListener(this);
        //删除
        shopCarAdapter.setOndeleteListenter(this);
        //点击全选/反选
        mcb_quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                for (int i = 0; i < shopCars.size(); i++) {
                    ShopCar shopCar = shopCars.get(i);
                    shopCar.setIscheck(checked?1:0);
                }
                shopCarAdapter.notifyDataSetChanged();
                shopCarAdapter.SumPrice();
            }
        });
        return view;
    }
    //接收
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shou(Message message){
        if (message.arg1 == 3){
            shopCars.clear();
            shopCarPresenter.mLogin(userId,sessionId);
        }
    }
    //计算总价格
    @Override
    public void totalprice(double sum) {
        mtv_sum.setText("￥"+String.valueOf(sum));
    }
    //点击跳转去结算
    @OnClick(R.id.mBt_center)
    public void center(){
        List<ShopCar> cars = new ArrayList<>();
        for (int i = 0; i < shopCars.size(); i++) {
            ShopCar shopCar = shopCars.get(i);
            if (shopCar.getIscheck()==1){
                cars.add(shopCar);
            }
        }
        if (cars.size()==0){
            return;
        }
        Message message = new Message();
        message.obj = cars;
        message.arg1 = 5;
        EventBus.getDefault().postSticky(message);
        Intent intent = new Intent(getActivity(), SettlementActivity.class);
        startActivity(intent);
    }
    //删除购物车
    @Override
    public void delete(int i) {
        shopCars.remove(i);
       for (int j = 0; j < shopCars.size(); j++) {
            ShopCar shopCar = shopCars.get(j);
            commodityId = shopCar.getCommodityId();
            GouOrderInfo gouOrderInfo = new GouOrderInfo(commodityId, shopCar.getCount());
            gouOrderInfos.add(gouOrderInfo);
        }
        shopCars.clear();
        String s = new Gson().toJson(gouOrderInfos);
        shopPresenter = new ShopPresenter(new ShopCall());
        shopPresenter.mLogin(userId,sessionId,s);
        gouOrderInfos.clear();
    }
    //实现同步购物车接口并查询
    class ShopCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                shopCarPresenter.mLogin(userId,sessionId);
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现查询购物车列表接口
    class ShopCarCall implements DataCall<Result<List<ShopCar>>>{
        @Override
        public void success(Result<List<ShopCar>> data) {
            if (data.getStatus()==0000){
                List<ShopCar> result = data.getResult();
                shopCars.addAll(result);
                shopCarAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        bind = null;
    }
}
