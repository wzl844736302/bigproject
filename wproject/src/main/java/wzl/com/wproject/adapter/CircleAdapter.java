package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.Circle;
import wzl.com.wproject.utils.recyclerview.SpacingItemDecoration;
import wzl.com.wproject.utils.util.StringUtils;
import wzl.com.wproject.view.PublishActivity;

public class CircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Circle> circleList;
    private List<Object> mList = new ArrayList<>();
    private LayoutInflater inflater;
    private int num;

    public CircleAdapter(Activity activity,List<Circle> circleList) {
        this.activity = activity;
        this.circleList = circleList;
        inflater = LayoutInflater.from(activity);
    }
    private DianZan dianZan;
    public interface DianZan{
        void dianzan(int id,int ischeck);
    }
    public void setDianZan(DianZan dianZan) {
        this.dianZan = dianZan;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.circle_item,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final MyHolder holder1 = (MyHolder) holder;
        final Circle circle = circleList.get(i);
        String headPic = circleList.get(i).getHeadPic();
        holder1.imageView.setImageURI(Uri.parse(headPic));
        holder1.textView.setText(circleList.get(i).getNickName());
        long time = circleList.get(i).getCreateTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format.format(date);
        holder1.textView2.setText(s+"");
        holder1.textView3.setText(circleList.get(i).getContent());
        num = circleList.get(i).getGreatNum();
        holder1.textView4.setText(num +"");
        if (StringUtils.isEmpty(circleList.get(i).getImage())){
            holder1.recyclerView.setVisibility(View.GONE);
        }else {
            holder1.recyclerView.setVisibility(View.VISIBLE);
            String image = circleList.get(i).getImage();
            String[] split = image.split(",");

            int colNum;//列数
            if (split.length == 1){
                colNum = 1;
            }else if (split.length == 2||split.length == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            holder1.gridLayoutManager.setSpanCount(colNum);//设置列数
            mList.clear();//清空
            mList.addAll(Arrays.asList(split));
            holder1.imageAdapter.notifyDataSetChanged();
        }
        //点赞
       holder1.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CheckBox checkBox = (CheckBox) v;
               boolean checked = checkBox.isChecked();
               if (checked){
                   circle.setIscheck(checked?1:0);
                   int greatNum = circle.getGreatNum();
                   int i1 = greatNum+1;
                   circle.setGreatNum(i1);
                   notifyDataSetChanged();
                   int id = circle.getId();
                   dianZan.dianzan(id,circle.getIscheck());
               }else {
                   circle.setIscheck(checked?1:0);
                   int greatNum = circle.getGreatNum();
                   int i1 = greatNum-1;
                   circle.setGreatNum(i1);
                   notifyDataSetChanged();
                   int id = circle.getId();
                   dianZan.dianzan(id,circle.getIscheck());
               }
           }
       });
        if (circle.getIscheck()==0){
            holder1.checkBox.setChecked(false);
        }else {
            holder1.checkBox.setChecked(true);
        }
        //点击发表跳转
        holder1.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), PublishActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return circleList.size();
    }
    //清除
    public void clear() {
        circleList.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageAdapter imageAdapter;
        ImageView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        RecyclerView recyclerView;
        GridLayoutManager gridLayoutManager;
        TextView textView4;
        CheckBox checkBox;
        TextView textView5;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mSim_circle);
            textView = itemView.findViewById(R.id.mTV_name_circle);
            textView2 = itemView.findViewById(R.id.mTV_date_circle);
            textView3 = itemView.findViewById(R.id.mTV_text_circle);
            recyclerView = itemView.findViewById(R.id.mrecycler);
            imageAdapter = new ImageAdapter(mList,activity);
            gridLayoutManager = new GridLayoutManager(activity,3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(imageAdapter);
            textView4 = itemView.findViewById(R.id.mTV_num_circle);
            checkBox = itemView.findViewById(R.id.mCb_dz);
            textView5 = itemView.findViewById(R.id.mTV_fabiao);
        }
    }
}
