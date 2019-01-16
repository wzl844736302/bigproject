package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.dingdan.OrderBeans;
import wzl.com.wproject.view.PingJiaActivity;
import wzl.com.wproject.view.PublishActivity;

public class InsideShowOrderAdapter_dpj extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderBeans> list;
    private Activity activity;
    private LayoutInflater inflater;
    public InsideShowOrderAdapter_dpj(List<OrderBeans> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.daipj_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        String commodityPic = list.get(i).getCommodityPic();
        String[] split = commodityPic.split(",");
        myHolder.imageView.setImageURI(Uri.parse(split[0]));
        myHolder.textView.setText(list.get(i).getCommodityName());
        myHolder.textView2.setText("￥"+list.get(i).getCommodityPrice()+"");
        myHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), PingJiaActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
        Button button;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mSim_dpj);
            textView = itemView.findViewById(R.id.mTV_name_dpj);
            textView2 = itemView.findViewById(R.id.mTV_price_dpj);
            button = itemView.findViewById(R.id.pingjia);
        }
    }
}
