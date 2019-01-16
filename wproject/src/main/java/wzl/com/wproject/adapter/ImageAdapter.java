package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.utils.util.UIUtils;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mList;
    private Activity activity;
    private LayoutInflater inflater;
    private int sign;//0:普通点击，1自定义
    public ImageAdapter(List<Object> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    public void setSign(int sign){
        this.sign = sign;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.circle_image_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        if (mList.get(i) instanceof String) {
            String imageUrl = (String) mList.get(i);
            if (imageUrl.contains("http:")) {//加载http
                myHolder.draweeView.setImageURI(Uri.parse(imageUrl));
            } else {//加载sd卡
                Uri uri = Uri.parse("file://" + imageUrl);
                myHolder.draweeView.setImageURI(uri);
            }
        } else {//加载资源文件
            int id = (int) mList.get(i);
            Uri uri = Uri.parse("res:///" + id);
            myHolder.draweeView.setImageURI(uri);
        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign == 1) {//自定义点击
                    if (i == 0) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                                activity.startActivityForResult(intent,101);
                    } else {
                        UIUtils.showToastSafe("点击了图片");
                    }
                }else{
                    UIUtils.showToastSafe("点击了图片");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.circle_image);
        }
    }
}
