package wzl.com.wproject.view;

import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.presenter.NewAddressPresenter;
import wzl.com.wproject.utils.util.UIUtils;

public class NewAddressActivity extends AppCompatActivity {
    private CityPicker mCP;
    private EditText met_diqu;
    private EditText mshou,mshoujihao,mdizhi,mbianma;
    private NewAddressPresenter presenter;
    private List<User> list = new ArrayList<>();
    private int userId;
    private String sessionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        //绑定
        ButterKnife.bind(this);
        initView();
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
        presenter = new NewAddressPresenter(new NewCall());
    }
    //点击保存
    @OnClick(R.id.mBt)
    public void mbt(){
        String s1 = mshou.getText().toString().trim();
        String s2 = mshoujihao.getText().toString().trim();
        String s3 = met_diqu.getText().toString().trim();
        String s4 = mdizhi.getText().toString().trim();
        String s6 = s3+s4;
        String s5 = mbianma.getText().toString().trim();
        presenter.mLogin(userId,sessionId,s1,s2,s6,s5);
    }
    //实现接口
    class NewCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus()==0000){
                Toast.makeText(NewAddressActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                //跳转
                Message message = new Message();
                message.obj = data;
                message.arg1 = 1;
                EventBus.getDefault().postSticky(message);
                finish();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //点击
    @OnClick(R.id.mET_diqu)
    public void diqu(){
        mYunCityPicher();
        mCP.show();
    }
    public void mYunCityPicher() {
        mCP = new CityPicker.Builder(NewAddressActivity.this)
                .textSize(20)
                //地址选择
                .title("地址选择")
                .backgroundPop(0xa0000000)
                //文字的颜色
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滑轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省滑轮是否循环显示
                .provinceCyclic(true)
                //市滑轮是否循环显示
                .cityCyclic(false)
                //地区（县）滑轮是否循环显示
                .districtCyclic(false)
                //滑轮显示的item个数
                .visibleItemsCount(7)
                //滑轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        //监听
        mCP.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省
                String province = citySelected[0];
                //市
                String city = citySelected[1];
                //区。县。（两级联动，必须返回空）
                String district = citySelected[2];
                //邮证编码
                String code = citySelected[3];

                met_diqu.setText(province+"  "+city+"  "+district);
            }

            @Override
            public void onCancel() {

            }
        });
    }
    //初始化控件
    private void initView() {
        met_diqu = findViewById(R.id.mET_diqu);
        mshou = findViewById(R.id.mshou);
        mshoujihao = findViewById(R.id.mshoujihao);
        mdizhi = findViewById(R.id.mdizhi);
        mbianma = findViewById(R.id.mbianma);
    }
}
