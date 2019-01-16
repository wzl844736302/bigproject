package wzl.com.wproject.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wzl.com.wproject.view.AddressActivity;
import wzl.com.wproject.view.FootPrintActivity;
import wzl.com.wproject.view.MyCircleActivity;
import wzl.com.wproject.view.MyProfileActivity;
import wzl.com.wproject.R;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.view.SetUpActivity;
import wzl.com.wproject.view.WalletActivity;

public class Frag_04 extends Fragment {

    private List<User> list = new ArrayList<>();
    private SimpleDraweeView msimple,msim_head;
    private TextView mtv_name;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_04,container,false);
        bind = ButterKnife.bind(this, view);
        //初始化组件
        msimple = view.findViewById(R.id.mSimple);
        msim_head = view.findViewById(R.id.mSim_head);
        mtv_name = view.findViewById(R.id.mTV_name);
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        list.addAll(userDao.loadAll());
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            //背景
            String headPic = user.getHeadPic();
            msimple.setImageURI(headPic);
            //头像
            msim_head.setImageURI(headPic);
            //用户名
            String nickName = user.getNickName();
            mtv_name.setText(nickName);
        }
        return view;
    }
    //点击跳转到我的资料
    @OnClick(R.id.mTV_ziliao)
    public void ziliao(){
        //跳转
        Intent intent = new Intent(getActivity(), MyProfileActivity.class);
        startActivity(intent);
    }
    //点击跳转到我的圈子
    @OnClick(R.id.mTV_circle)
    public void circle(){
        //跳转
        Intent intent = new Intent(getActivity(), MyCircleActivity.class);
        startActivity(intent);
    }
    //点击跳转到我的钱包
    @OnClick(R.id.mTV_qianbao)
    public void qianbao(){
        Intent intent = new Intent(getActivity(), WalletActivity.class);
        startActivity(intent);
    }
    //点击跳转到我的足迹
    @OnClick(R.id.mTV_zuji)
    public void zuji(){
        Intent intent = new Intent(getActivity(), FootPrintActivity.class);
        startActivity(intent);
    }
    //点击跳转到我的地址
    @OnClick(R.id.mTV_address)
    public void address(){
        Intent intent = new Intent(getActivity(), AddressActivity.class);
        startActivity(intent);
    }
    //点击跳转到设置
    @OnClick(R.id.mTV_tui)
    public void tui(){
        Intent intent = new Intent(getActivity(), SetUpActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}
