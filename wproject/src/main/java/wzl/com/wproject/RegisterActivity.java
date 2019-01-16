package wzl.com.wproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.presenter.RegisterPresenter;
import wzl.com.wproject.utils.util.MD5Utils;
import wzl.com.wproject.utils.util.UIUtils;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.mPhone_reg)
    EditText mphone_reg;
    @BindView(R.id.mPass_reg)
    EditText mpass_reg;
    private RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        //绑定
        ButterKnife.bind(this);
    }
    //点击注册
    @OnClick(R.id.mRegister)
    public void register(){
        //获取值
        String p = mphone_reg.getText().toString().trim();
        //判断
        if (TextUtils.isEmpty(p)){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = mpass_reg.getText().toString().trim();
        if (TextUtils.isEmpty(s)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter = new RegisterPresenter(new RegisterCall());
        presenter.mLogin(p,MD5Utils.md5(s));
    }
    //实现注册接口
    class RegisterCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                //跳转页面
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //点击隐藏或显示
    @OnClick(R.id.mShow_reg)
    public void mshow(){
        ShowAndHide(mpass_reg);
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
