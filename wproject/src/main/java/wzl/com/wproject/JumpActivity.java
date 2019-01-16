package wzl.com.wproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JumpActivity extends AppCompatActivity {

    private TextView mbt_jump,mtv_tiao;
    int count = 3;
    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==200){
                if(count<=0){
                    Intent intent = new Intent(JumpActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                mbt_jump.setText(count-- +"s");
                handler.sendEmptyMessageDelayed(200,1000);
            }

        };
    };
    private TextView mtv_jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        //初始化控件
        mbt_jump = findViewById(R.id.mTV_miao_jump);
        mtv_tiao = findViewById(R.id.mTV_tiao_jump);
        handler.sendEmptyMessageDelayed(200,1000);
        mtv_tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(200);
    }
}
