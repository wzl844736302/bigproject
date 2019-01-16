package wzl.com.wproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.adapter.FootPrintAdapter;
import wzl.com.wproject.bean.FootPrint;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.FootPrintPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class FootPrintActivity extends AppCompatActivity {

    private List<User> list = new ArrayList<>();
    private FootPrintPresenter presenter;
    private RecyclerView mrecycler_zuji;
    private int userId;
    private String sessionId;
    private FootPrintAdapter footPrintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print);
        //初始化组件
        mrecycler_zuji = findViewById(R.id.mrecycler_zuji);
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
        footPrintAdapter = new FootPrintAdapter(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mrecycler_zuji.setLayoutManager(layoutManager);
        mrecycler_zuji.setAdapter(footPrintAdapter);
        //设置数据
        presenter = new FootPrintPresenter(new FootPrintCall());
        presenter.mLogin(userId,sessionId,1,20);

    }
    //实现足迹接口
    class FootPrintCall implements DataCall<Result<List<FootPrint>>>{
        @Override
        public void success(Result<List<FootPrint>> data) {
            if (data.getStatus()==0000){
                List<FootPrint> result = data.getResult();
                footPrintAdapter.addAll(result);
                footPrintAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
