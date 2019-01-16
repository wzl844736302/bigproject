package wzl.com.wproject.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.frag.Frag_01;
import wzl.com.wproject.frag.Frag_02;
import wzl.com.wproject.frag.Frag_03;
import wzl.com.wproject.frag.Frag_04;
import wzl.com.wproject.frag.Frag_center;

public class HomePageActivity extends AppCompatActivity {

    private Frag_01 frag_01;
    private Frag_02 frag_02;
    private Frag_03 frag_03;
    private Frag_04 frag_04;
    private RadioGroup mradio;
    private Frag_center frag_center;
    private ImageView mimages;
    private FragmentTransaction beginTransaction1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_home_page);
        //点击显示和隐藏
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frag_01 = new Frag_01();
        frag_02 = new Frag_02();
        frag_center = new Frag_center();
        frag_03 = new Frag_03();
        frag_04 = new Frag_04();
        transaction.add(R.id.mFL,frag_01);
        transaction.add(R.id.mFL,frag_02);
        transaction.add(R.id.mFL,frag_03);
        transaction.add(R.id.mFL,frag_04);
        transaction.add(R.id.mFL,frag_center);
        transaction.show(frag_01);
        transaction.hide(frag_02);
        transaction.hide(frag_center);
        transaction.hide(frag_03);
        transaction.hide(frag_04);
        transaction.commit();
        //点击按钮显示和隐藏
        mradio = findViewById(R.id.mRadio);
        //默认选中第一个
        mradio.check(mradio.getChildAt(0).getId());
        mradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                switch (checkedId){
                    case R.id.mHome:
                        transaction1.show(frag_01);
                        transaction1.hide(frag_02);
                        transaction1.hide(frag_center);
                        transaction1.hide(frag_03);
                        transaction1.hide(frag_04);
                        break;
                    case R.id.mCirle:
                        transaction1.show(frag_02);
                        transaction1.hide(frag_01);
                        transaction1.hide(frag_center);
                        transaction1.hide(frag_03);
                        transaction1.hide(frag_04);
                        break;
                    case R.id.mList:
                        transaction1.show(frag_03);
                        transaction1.hide(frag_02);
                        transaction1.hide(frag_center);
                        transaction1.hide(frag_01);
                        transaction1.hide(frag_04);
                        break;
                    case R.id.My:
                        transaction1.show(frag_04);
                        transaction1.hide(frag_02);
                        transaction1.hide(frag_center);
                        transaction1.hide(frag_03);
                        transaction1.hide(frag_01);
                        break;
                }
                transaction1.commit();
            }
        });
        mimages = findViewById(R.id.mImages);
        mimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mradio.clearCheck();
                beginTransaction1 = getSupportFragmentManager().beginTransaction();
                beginTransaction1.show(frag_center);
                beginTransaction1.hide(frag_01);
                beginTransaction1.hide(frag_02);
                beginTransaction1.hide(frag_03);
                beginTransaction1.hide(frag_04);
                beginTransaction1.commit();
            }
        });
    }
}
