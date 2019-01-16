package wzl.com.wproject.frag;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.FirstCateAdapter;
import wzl.com.wproject.adapter.MlssAdapter;
import wzl.com.wproject.adapter.PzshAdapter;
import wzl.com.wproject.adapter.RxxpAdapter;
import wzl.com.wproject.bean.FirstCate;
import wzl.com.wproject.bean.ImagesBan;
import wzl.com.wproject.bean.shops.Commodity;
import wzl.com.wproject.bean.shops.HomeList;
import wzl.com.wproject.bean.shops.NewProducts;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.presenter.FirstCatePresenter;
import wzl.com.wproject.presenter.ImagesPresenter;
import wzl.com.wproject.presenter.RxxpPresenter;
import wzl.com.wproject.utils.util.UIUtils;
import wzl.com.wproject.view.SearchActivity;

public class Frag_01 extends Fragment {
    private ImagesPresenter presenter;
    private List<String> strings = new ArrayList<>();
    private MZBannerView mban;
    private RxxpPresenter rxxpPresenter;
    private RxxpAdapter rxxpAdapter;
    private RecyclerView mrecycler_r,mrecycler_m,mrecycler_p;
    private MlssAdapter mlssAdapter;
    private PzshAdapter pzshAdapter;
    private EditText msousuo;
    private Unbinder bind;
    private FirstCatePresenter catePresenter;
    private LinearLayout mll_01;
    private RecyclerView mrecycler_first;
    private FirstCateAdapter firstCateAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_01,container,false);
        //绑定
        bind = ButterKnife.bind(this, view);
        //初始化组件
        mban = view.findViewById(R.id.mBan);
        mrecycler_r = view.findViewById(R.id.mrecycler_r);
        mrecycler_m = view.findViewById(R.id.mrecycler_m);
        mrecycler_p = view.findViewById(R.id.mrecycler_p);
        msousuo = view.findViewById(R.id.mSousuo);
        mll_01 = view.findViewById(R.id.mLL_01);
        //点击搜索跳转
        msousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        presenter = new ImagesPresenter(new CallBacks());
        presenter.mLogin();
        //热销新品
        rxxpPresenter = new RxxpPresenter(new MyCall());
        rxxpPresenter.mLogin();
        //设置热销新品适配器
        rxxpAdapter = new RxxpAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrecycler_r.setLayoutManager(linearLayoutManager);
        mrecycler_r.setAdapter(rxxpAdapter);
        //设置魔力时尚适配器
        mlssAdapter = new MlssAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mrecycler_m.setLayoutManager(layoutManager);
        mrecycler_m.setAdapter(mlssAdapter);
        //设置品质生活适配器
        pzshAdapter = new PzshAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        mrecycler_p.setLayoutManager(gridLayoutManager);
        mrecycler_p.setAdapter(pzshAdapter);
        return view;
    }
    //轮播图接口
    class CallBacks implements DataCall<Result<List<ImagesBan>>> {

        @Override
        public void success(Result<List<ImagesBan>> data) {
            if (data.getStatus()==0000){
                List<ImagesBan> result = data.getResult();
                for (int i = 0; i < result.size(); i++) {
                    String url = result.get(i).getImageUrl();
                    strings.add(url);
                }
            }
            //设置轮播图
           mban.setPages(strings, new MZHolderCreator() {
               @Override
               public MZViewHolder createViewHolder() {
                   return new BannerViewHolder();
               }
           });
            mban.start();
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //实现banner的类
    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = view.findViewById(R.id.banner_image);
            return view;
        }
        @Override
        public void onBind(Context context, int i, String s) {
            mImageView.setImageURI(Uri.parse(s));
        }

    }
    //商品展示接口
    class MyCall implements DataCall<Result<NewProducts>>{
        @Override
        public void success(Result<NewProducts> data) {
            if (data.getStatus()==0000){
                //热销新品
                NewProducts result = data.getResult();
                List<HomeList> rxxp = result.getRxxp();
                List<Commodity> commodityList = rxxp.get(0).getCommodityList();
                rxxpAdapter.addAll(commodityList);
                rxxpAdapter.notifyDataSetChanged();
                //魔力时尚
                List<HomeList> mlss = result.getMlss();
                List<Commodity> commodityList1 = mlss.get(0).getCommodityList();
                mlssAdapter.addAll(commodityList1);
                mlssAdapter.notifyDataSetChanged();
                //品质生活
                List<HomeList> pzsh = result.getPzsh();
                List<Commodity> commodityList2 = pzsh.get(0).getCommodityList();
                pzshAdapter.addAll(commodityList2);
                pzshAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //点击pop
    @OnClick(R.id.mIV_pop)
    public void pop(){
        catePresenter = new FirstCatePresenter(new FirstCall());
        catePresenter.mLogin();
        View view = View.inflate(getActivity(),R.layout.pop_item,null);
        PopupWindow window = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        ColorDrawable drawable = new ColorDrawable(0xc1c1c1c1);
        window.setBackgroundDrawable(drawable);
        window.showAsDropDown(mll_01,0,16);
        mrecycler_first = view.findViewById(R.id.mrecycler_first);
        //设置适配器
        firstCateAdapter = new FirstCateAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrecycler_first.setLayoutManager(linearLayoutManager);
        mrecycler_first.setAdapter(firstCateAdapter);
    }
    //实现pop接口
    class FirstCall implements DataCall<Result<List<FirstCate>>>{
        @Override
        public void success(Result<List<FirstCate>> data) {
            if (data.getStatus()==0000){
                List<FirstCate> result = data.getResult();
                firstCateAdapter.addAll(result);
                firstCateAdapter.notifyDataSetChanged();
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
        bind = null;
    }
}
