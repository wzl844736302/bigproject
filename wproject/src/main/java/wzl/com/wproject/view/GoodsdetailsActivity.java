package wzl.com.wproject.view;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.EvalAdapter;
import wzl.com.wproject.bean.Evaluation;
import wzl.com.wproject.bean.Goodsdetails;
import wzl.com.wproject.bean.GouOrderInfo;
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
import wzl.com.wproject.presenter.EvaluationPresenter;
import wzl.com.wproject.presenter.GoodsdetailsPresenter;
import wzl.com.wproject.presenter.OrderPresenter;
import wzl.com.wproject.presenter.ShopCarPresenter;
import wzl.com.wproject.presenter.ShopPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class GoodsdetailsActivity extends AppCompatActivity {


    private List<User> list = new ArrayList<>();
    private GoodsdetailsPresenter presenter;
    private EvaluationPresenter pjpresenter;
    private EvaluationPresenter evaluationPresenter;
    private int userId;
    private String sessionId;
    private int commid;
    private CustomScrollView mscroll;
    private RelativeLayout details_relative_changer
            ,details_relat_changecolor;
    private TextView details_text_goods
            ,details_text_details
            ,details_text_comments
            ,mtv_price_goods
            ,mtv_jian_goods
            ,mtv_title_goods
            ,mtv_weight_goods
            ,mtv_js_goods
            ,mtv_qd;
    private SimpleDraweeView miv_goods,miv_xq_goods,miv_3_goods;
    private RecyclerView mrecycler_pj;
    private int commodityId;
    private EvalAdapter evalAdapter;
    private ImageView miv_return;
    private OrderPresenter orderPresenter;
    private double price;
    private AddressPresenter addressPresenter;
    private int addressId;
    private List<OrderInfo> infoList = new ArrayList<>();
    private List<GouOrderInfo> gouOrderInfos = new ArrayList<>();
    private ShopPresenter shopPresenter;
    private ShopCarPresenter shopCarPresenter;
    private List<ShopCar> shopCars = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetails);
        ButterKnife.bind(this);
        //初始化组件
        initView();
        //滑动
        mscroll.setScrollviewListener(new MyScrollView());
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
        //接收值
        Intent intent = getIntent();
        commid = intent.getIntExtra("commid", 0);
        presenter = new GoodsdetailsPresenter(new GoodsBacks());
        presenter.mLogin(userId,sessionId, commid);
        //评论适配器
        evalAdapter = new EvalAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecycler_pj.setLayoutManager(layoutManager);
        mrecycler_pj.setAdapter(evalAdapter);
        //地址列表
        addressPresenter = new AddressPresenter(new AddressCall());
        addressPresenter.mLogin(userId,sessionId);
        //查询购物车
        shopCarPresenter = new ShopCarPresenter(new ShopCarCall());
        shopCarPresenter.mLogin(userId,sessionId);
    }
    //实现查询购物车列表接口
    class ShopCarCall implements DataCall<Result<List<ShopCar>>>{
        @Override
        public void success(Result<List<ShopCar>> data) {
            if (data.getStatus()==0000){
                List<ShopCar> result = data.getResult();
                shopCars.addAll(result);
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现商品详情接口
    class GoodsBacks implements DataCall<Result<Goodsdetails>> {
        @Override
        public void success(Result<Goodsdetails> data) {
            if (data.getStatus()==0000){
                Goodsdetails result = data.getResult();
                String picture = result.getPicture();
                String[] split = picture.split(",");
                miv_goods.setImageURI(Uri.parse(split[0]));
                price = result.getPrice();
                mtv_price_goods.setText("￥"+ price +"");
                int num = result.getCommentNum();
                mtv_jian_goods.setText("已售"+num+""+"件");
                String name = result.getCommodityName();
                mtv_title_goods.setText(name);
                int weight = result.getWeight();
                mtv_weight_goods.setText(weight+""+"kg");
                miv_xq_goods.setImageURI(split[1]);
                mtv_js_goods.setText(name);
                miv_3_goods.setImageURI(split[2]);
                commodityId = result.getCommodityId();
                //实现评论列表
                pjpresenter = new EvaluationPresenter(new EvalCall());
                pjpresenter.mLogin(commodityId,1,20);
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现评论接口
    class EvalCall implements DataCall<Result<List<Evaluation>>>{
        @Override
        public void success(Result<List<Evaluation>> data) {
            if (data.getStatus()==0000){
                List<Evaluation> result = data.getResult();
                evalAdapter.addAll(result);
                if (result.size()==0){
                    mtv_qd.setVisibility(View.VISIBLE);
                }else {
                    mrecycler_pj.setVisibility(View.VISIBLE);
                }
                evalAdapter.notifyDataSetChanged();

            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //内部详情类
    class MyScrollView implements CustomScrollView.ScrollviewListener{
        @Override
        public void OnScrollChange(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
            if (t <= 0) {
                //标题显示
                details_relative_changer.setVisibility(View.GONE);
                //设置标题所在的背景颜色为透明
                details_relat_changecolor.setBackgroundColor(Color.argb(0, 0, 0, 0));
            } else if (t > 0 && t < 200) {
                details_relative_changer.setVisibility(View.VISIBLE);
                //获取ScrollView想下滑动,图片消失部分的比例
                float scale = (float) t / 200;
                //根据比例,让标题的颜色由浅入深
                float alpha = 255 * scale;
                //设置标题布局的颜色
                details_relat_changecolor.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            }
            if (0 < t && t < 300) {
                details_text_goods.setBackgroundColor(Color.RED);
                details_text_details.setBackgroundColor(Color.WHITE);
                details_text_comments.setBackgroundColor(Color.WHITE);
            } else if (301 < t && t < 1000) {
                details_text_goods.setBackgroundColor(Color.WHITE);
                details_text_details.setBackgroundColor(Color.RED);
                details_text_comments.setBackgroundColor(Color.WHITE);
            } else if (t > 1000) {
                details_text_goods.setBackgroundColor(Color.WHITE);
                details_text_details.setBackgroundColor(Color.WHITE);
                details_text_comments.setBackgroundColor(Color.RED);
            }
        }
    }
    //点击购买
    @OnClick(R.id.btn_buy)
    public void buy(){
        OrderInfo orderInfo = new OrderInfo(commid,1);
        infoList.add(orderInfo);
        String s = new Gson().toJson(infoList);
        orderPresenter = new OrderPresenter(new OrderCall());
        orderPresenter.mLogin(userId,sessionId,s,price,addressId);
    }
    //点击添加购物车

    @OnClick(R.id.btn_car)
    public void car(){
        //定义布尔值
        boolean a = true;
        for (int i = 0; i < shopCars.size(); i++) {
            ShopCar shopCar = shopCars.get(i);
            if (commid==shopCar.getCommodityId()){
                int num = shopCar.getCount() + 1;
                shopCar.setCount(num);
                GouOrderInfo gouOrderInfo = new GouOrderInfo(shopCar.getCommodityId(),shopCar.getCount());
                gouOrderInfos.add(gouOrderInfo);
                a = false;
            }else {
                GouOrderInfo gouOrderInfo = new GouOrderInfo(shopCar.getCommodityId(),shopCar.getCount());
                gouOrderInfos.add(gouOrderInfo);
            }
        }
        if (a){
            GouOrderInfo gouOrderInfo = new GouOrderInfo(commid,1);
            gouOrderInfos.add(gouOrderInfo);
        }
        String s = new Gson().toJson(gouOrderInfos);
        shopPresenter = new ShopPresenter(new ShopCall());
        shopPresenter.mLogin(userId,sessionId,s);
    }
    //实现同步购物车接口
    class ShopCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Toast.makeText(GoodsdetailsActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                //传
                Message message = new Message();
                message.arg1 = 3;
                EventBus.getDefault().postSticky(message);
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现创建订单接口
    class OrderCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Toast.makeText(GoodsdetailsActivity.this, "创建订单成功", Toast.LENGTH_SHORT).show();
                //传
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
    //初始化组件
    private void initView() {
        mscroll = findViewById(R.id.mscroll);
        details_relative_changer = findViewById(R.id.details_relative_changer);
        details_relat_changecolor = findViewById(R.id.details_relat_changecolor);
        details_text_goods = findViewById(R.id.details_text_goods);
        details_text_details = findViewById(R.id.details_text_details);
        details_text_comments = findViewById(R.id.details_text_comments);
        miv_goods = findViewById(R.id.mIV_goods);
        mtv_price_goods = findViewById(R.id.mTV_price_goods);
        mtv_jian_goods = findViewById(R.id.mTV_jian_goods);
        mtv_title_goods = findViewById(R.id.mTV_title_goods);
        mtv_weight_goods = findViewById(R.id.mTV_weight_goods);
        miv_xq_goods = findViewById(R.id.mIV_xq_goods);
        mtv_js_goods = findViewById(R.id.mTV_js_goods);
        miv_3_goods = findViewById(R.id.mIV_3_goods);
        mrecycler_pj = findViewById(R.id.mrecycler_pj);
        mtv_qd = findViewById(R.id.mTV_qd);
        miv_return = findViewById(R.id.mIV_return);
    }
    //点击关闭当前页面
    @OnClick(R.id.mIV_return)
    public void miv(){
        finish();
    }
}
