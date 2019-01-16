package wzl.com.wproject.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.MyCircle;
import wzl.com.wproject.utils.util.StringUtils;

public class MyCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<MyCircle> list = new ArrayList<>();
    private LayoutInflater inflater;
    private List<Object> mList = new ArrayList<>();
    public MyCircleAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.mycircle_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getNickName());
        long time = list.get(i).getCreateTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format.format(date);
        myHolder.textView2.setText(s);
        if (StringUtils.isEmpty(list.get(i).getImage())){
            myHolder.recyclerView.setVisibility(View.GONE);
        }else {
            myHolder.recyclerView.setVisibility(View.VISIBLE);
            String image = list.get(i).getImage();
            String[] split = image.split(",");

            int colNum;//列数
            if (split.length == 1){
                colNum = 1;
            }else if (split.length == 2||split.length == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            myHolder.gridLayoutManager.setSpanCount(colNum);//设置列数
            mList.clear();//清空
            mList.addAll(Arrays.asList(split));
            myHolder.imageAdapter.notifyDataSetChanged();
        }
        myHolder.textView3.setText(list.get(i).getGreatNum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<MyCircle> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        RecyclerView recyclerView;
        ImageAdapter imageAdapter;
        GridLayoutManager gridLayoutManager;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cont);
            textView2 = itemView.findViewById(R.id.shijian);
            recyclerView = itemView.findViewById(R.id.iv11);
            textView3 = itemView.findViewById(R.id.shu);
            imageAdapter = new ImageAdapter(mList,activity);
            gridLayoutManager = new GridLayoutManager(activity,3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(imageAdapter);
        }
    }
}
