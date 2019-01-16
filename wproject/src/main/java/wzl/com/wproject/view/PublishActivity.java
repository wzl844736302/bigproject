package wzl.com.wproject.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wzl.com.wproject.R;
import wzl.com.wproject.adapter.ImageAdapter;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.ApiException;
import wzl.com.wproject.dao.DaoMaster;
import wzl.com.wproject.dao.DaoSession;
import wzl.com.wproject.dao.UserDao;
import wzl.com.wproject.frag.Frag_02;
import wzl.com.wproject.presenter.PublishPresenter;
import wzl.com.wproject.utils.util.StringUtils;
import wzl.com.wproject.utils.util.UIUtils;

import static android.media.MediaRecorder.VideoSource.CAMERA;


public class PublishActivity extends AppCompatActivity implements DataCall<Result> {
    public final static int CAMERA = 1;// 拍照
    private List<User> list = new ArrayList<>();
    @BindView(R.id.bo_text)
    EditText mText;
    @BindView(R.id.bo_image_list)
    RecyclerView mImageList;
    private PublishPresenter publishPresenter;
    private ImageAdapter mImageAdapter;
    private List<Object> list1 = new ArrayList<>();
    private int userId;
    private String sessionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        //绑定
        ButterKnife.bind(this);
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
        //设置适配器
        mImageAdapter = new ImageAdapter(list1,this);
        mImageAdapter.setSign(1);
        list1.add(R.drawable.mask_01);
        mImageList.setLayoutManager(new GridLayoutManager(this,3));
        mImageList.setAdapter(mImageAdapter);
    }
    @OnClick(R.id.send)
    public void publish(){
         publishPresenter = new PublishPresenter(this);
         publishPresenter.mLogin(userId, sessionId, 1,mText.getText().toString(),list1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
                list1.add(filePath);
                mImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void success(Result data) {
        if (data.getStatus()==0000){
           Frag_02.addCircle = true;
           finish();
        }else {
            UIUtils.showToastSafe(data.getStatus()+"  "+data.getMessage());
        }
    }

    @Override
    public void fail(ApiException e) {
        UIUtils.showToastSafe(e.getCode()+"  "+e.getDisplayMessage());
    }
    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == 101) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                actualimagecursor.close();
            return img_path;
        }
        return null;
    }
}
