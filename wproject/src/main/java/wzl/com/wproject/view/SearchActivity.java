package wzl.com.wproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.MainActivity;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.SearchAdapter;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.Search;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.presenter.SearchPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class SearchActivity extends AppCompatActivity implements XRecyclerView.LoadingListener{

    private XRecyclerView mrecycler_search;
    private TextView mtv_search;
    private EditText msearch;
    private SearchPresenter presenter;
    private SearchAdapter adapter;
    private String search;
    private LinearLayout mll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //初始化组件
        mrecycler_search = findViewById(R.id.mRecycler_search);
        msearch = findViewById(R.id.mSearch);
        mtv_search = findViewById(R.id.mTV_search);
        mll = findViewById(R.id.mLL);
        //点击搜索
        presenter = new SearchPresenter(new SearchCall());
        mtv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = msearch.getText().toString().trim();
                //设置适配器
                adapter = new SearchAdapter(SearchActivity.this);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this,2);
                mrecycler_search.setLayoutManager(gridLayoutManager);
                mrecycler_search.setAdapter(adapter);
                mrecycler_search.setLoadingListener(SearchActivity.this);
                mrecycler_search.refresh();
            }
        });
    }
    //上下拉刷新
    @Override
    public void onRefresh() {
        if (presenter.isRun()){
            mrecycler_search.refreshComplete();
            return;
        }
        presenter.mLogin(search,true);
    }
    @Override
    public void onLoadMore() {
        if (presenter.isRun()){
            mrecycler_search.loadMoreComplete();
            return;
        }
        presenter.mLogin(search,false);
    }
    //实现搜索接口
    class SearchCall implements DataCall<Result<List<Search>>>{
        @Override
        public void success(Result<List<Search>> data) {
            if (data.getStatus()==0000){
                if (data.getResult().size()==0){

                    mrecycler_search.setVisibility(View.GONE);
                    mll.setVisibility(View.VISIBLE);
                }else {
                    mll.setVisibility(View.GONE);
                    mrecycler_search.setVisibility(View.VISIBLE);
                    List<Search> result = data.getResult();
                    adapter.addAll(result);
                    adapter.notifyDataSetChanged();
                    mrecycler_search.loadMoreComplete();
                    mrecycler_search.refreshComplete();
                }
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
