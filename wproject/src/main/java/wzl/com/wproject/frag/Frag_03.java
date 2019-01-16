package wzl.com.wproject.frag;

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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.adapter.ShowOrderAdapter;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.bean.dingdan.DetalList;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.ShowOrderPresenter;
import wzl.com.wproject.presenter.TheGoodsPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class Frag_03 extends Fragment implements View.OnClickListener,ShowOrderAdapter.TheGoods {
    private List<User> list = new ArrayList<>();
    private RecyclerView mrecycler_dingdan;
    private ShowOrderPresenter presenter;
    private int userId;
    private String sessionId;
    private ShowOrderAdapter adapter;
    private View view;
    private int status;
    private TheGoodsPresenter goodsPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_03,container,false);
        //初始化控件
        mrecycler_dingdan = view.findViewById(R.id.mrecycler_dingdan);
        EventBus.getDefault().register(this);
        view.findViewById(R.id.mIV1).setOnClickListener(this);
        view.findViewById(R.id.mIV2).setOnClickListener(this);
        view.findViewById(R.id.mIV3).setOnClickListener(this);
        view.findViewById(R.id.mIV4).setOnClickListener(this);
        view.findViewById(R.id.mIV5).setOnClickListener(this);
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
        adapter = new ShowOrderAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mrecycler_dingdan.setLayoutManager(layoutManager);
        mrecycler_dingdan.setAdapter(adapter);
        presenter = new ShowOrderPresenter(new ShowOrderCall());
        presenter.mLogin(userId,sessionId,0,1,20);
        //自定义接口回调
        adapter.setTheGoods(this);
        return view;
    }
    //接收
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void shou(Message message){
        if (message.arg1 == 2){
            adapter.clear();
            presenter.mLogin(userId,sessionId,0,1,20);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mIV1:
                adapter.clear();
                status = 0;
                presenter.mLogin(userId,sessionId,status,1,20);
                adapter.notifyDataSetChanged();
                break;
            case R.id.mIV2:
                adapter.clear();
                status = 1;
                presenter.mLogin(userId,sessionId,status,1,20);
                break;
            case R.id.mIV3:
                adapter.clear();
                status = 2;
                presenter.mLogin(userId,sessionId,status,1,20);
                break;
            case R.id.mIV4:
                adapter.clear();
                status = 3;
                presenter.mLogin(userId,sessionId,status,1,20);
                break;
            case R.id.mIV5:
                adapter.clear();
                status = 9;
                presenter.mLogin(userId,sessionId,status,1,20);
                break;
        }
    }

    @Override
    public void goods(String orderid) {
        //收货
        goodsPresenter = new TheGoodsPresenter(new TheGoodsCall());
        goodsPresenter.mLogin(userId,sessionId,orderid);

    }

    //实现收货接口
    class TheGoodsCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Message message = new Message();
                message.arg1 = 102;
                EventBus.getDefault().postSticky(message);
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现全部订单展示
    class ShowOrderCall implements DataCall<Result<List<DetalList>>>{
        @Override
        public void success(Result<List<DetalList>> data) {
            if (data.getStatus()==0000){
                List<DetalList> orderList = data.getOrderList();
                adapter.addAll(orderList);
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void shou1(Message message){
        if (message.arg1 == 102){
            adapter.clear();
            presenter.mLogin(userId,sessionId,status,1,20);
        }
    }
    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
