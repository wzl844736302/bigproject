package wzl.com.wproject.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.adapter.CircleAdapter;
import wzl.com.wproject.bean.Circle;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.CancelPresenter;
import wzl.com.wproject.presenter.CirclePresenter;
import wzl.com.wproject.presenter.DianZanPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class Frag_02 extends Fragment implements XRecyclerView.LoadingListener, CircleAdapter.DianZan {
    private List<User> list = new ArrayList<>();
    private int userId;
    private String sessionId;
    private CirclePresenter presenter;
    private CircleAdapter adapter;
    private XRecyclerView mrecycler_02;
    private DianZanPresenter zanPresenter;
    private List<Circle> circleList = new ArrayList<>();
    private CancelPresenter cancelPresenter;
    public static boolean addCircle;//如果添加成功，则为true
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_02, container, false);
        //初始化组件
        mrecycler_02 = view.findViewById(R.id.mRecycler_02);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            //获取id
            userId = user.getUserId();
            //获取用户登录凭证
            sessionId = user.getSessionId();
        }
        //设置数据
        presenter = new CirclePresenter(new CircleBacks());
        //设置适配器
        adapter = new CircleAdapter(getActivity(), circleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mrecycler_02.setLayoutManager(layoutManager);
        mrecycler_02.setAdapter(adapter);
        mrecycler_02.setLoadingListener(this);
        presenter = new CirclePresenter(new CircleBacks());
        presenter.mLogin(true, userId, sessionId);
        adapter.setDianZan(this);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (addCircle){
            addCircle = false;
            mrecycler_02.refresh();
        }
    }
    @Override
    public void onRefresh() {
        if (presenter.isRun()) {
            circleList.clear();
            mrecycler_02.refreshComplete();
            return;
        }
        presenter.mLogin(true, userId, sessionId);
    }

    @Override
    public void onLoadMore() {
        if (presenter.isRun()) {
            circleList.clear();
            mrecycler_02.loadMoreComplete();
            return;
        }
        presenter.mLogin(false, userId, sessionId);
    }

    //点赞
    @Override
    public void dianzan(int id, int ischeck) {
        if (ischeck == 1) {
            zanPresenter = new DianZanPresenter(new DianZanCall());
            zanPresenter.mLogin(userId, sessionId, id);
        } else {
            cancelPresenter = new CancelPresenter(new CancelCall());
            cancelPresenter.mLogin(userId, sessionId, id);
        }
    }

    //实现点赞接口
    class DianZanCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus() == 0000) {
                Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }

    //实现取消点赞接口
    class CancelCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus() == 0000) {
                Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }

    //实现圈子接口
    class CircleBacks implements DataCall<Result<List<Circle>>> {
        @Override
        public void success(Result<List<Circle>> data) {
            if (data.getStatus() == 0000) {
                circleList.clear();
                mrecycler_02.refreshComplete();
                mrecycler_02.loadMoreComplete();
                List<Circle> result = data.getResult();
                circleList.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }
}
