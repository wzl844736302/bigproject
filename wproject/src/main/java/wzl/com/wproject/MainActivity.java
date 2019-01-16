package wzl.com.wproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.LoginPresenter;
import wzl.com.wproject.utils.util.MD5Utils;
import wzl.com.wproject.utils.util.UIUtils;
import wzl.com.wproject.view.HomePageActivity;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.mPhone)
    EditText mphone;
    @BindView(R.id.mPass)
    EditText mpass;
    private LoginPresenter presenter;
    private String p;
    private String m;
    private SharedPreferences sp;
    private CheckBox mnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login",Context.MODE_PRIVATE);
        //绑定
        ButterKnife.bind(this);
        //初始化组件
        mnum = findViewById(R.id.mNum);
        //判断
        if (sp.getBoolean("zidong",false)){
            //跳转
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
        if (sp.getBoolean("bool",false)){
            String phone = sp.getString("phone", p);
            mphone.setText(phone);
            String pass = sp.getString("pass", m);
            mpass.setText(pass);
            mnum.setChecked(true);
        }
    }

    //点击登录
    @OnClick(R.id.mLogin)
    public void oclick() {
        //获取手机号和密码的值
        p = mphone.getText().toString().trim();
        //判断
        if (TextUtils.isEmpty(p)){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        m = mpass.getText().toString().trim();
        if (TextUtils.isEmpty(m)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter = new LoginPresenter(new LoginCall());
        presenter.mLogin(p, MD5Utils.md5(m));
        //点击记住密码
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phone",p);
        edit.putString("pass",m);
        edit.putBoolean("bool",mnum.isChecked());
        edit.putBoolean("zidong",true);
        edit.commit();
    }
    //登录实现接口
    class LoginCall implements DataCall<Result<User>> {

        @Override
        public void success(Result<User> data) {
            if (data.getStatus()==0000){
                //添加数据库
                User result = data.getResult();
                DaoSession daoSession = DaoMaster.newDevSession(MainActivity.this, UserDao.TABLENAME);
                UserDao userDao = daoSession.getUserDao();
                userDao.insertOrReplace(result);
                //跳转
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }

    //点击跳转到注册页面
    @OnClick(R.id.mzhuce)
    public void click() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    //点击密码显示和隐藏
    @OnClick(R.id.mShow)
    public void onshow(){
        //调用方法
        ShowAndHide(mpass);

    }
    //点击密码显示和隐藏方法
    private void ShowAndHide(EditText pwds) {
        //记住光标开始的位置
        int start = pwds.getSelectionStart();
        //判断隐藏密码
        if (pwds.getInputType()!=(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
            pwds.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else {//显示密码
            pwds.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        pwds.setSelection(start);
    }
}
