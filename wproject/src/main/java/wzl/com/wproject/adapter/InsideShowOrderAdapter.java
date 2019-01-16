package wzl.com.wproject.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.dingdan.OrderBeans;

public class InsideShowOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderBeans> list;
    private Activity activity;
    private LayoutInflater inflater;
    public InsideShowOrderAdapter(List<OrderBeans> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.insideshoworder_item,viewGroup,false);
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
        myHolder.textView3.setText(list.get(i).getCommodityCount()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.msim_inside);
            textView = itemView.findViewById(R.id.mTV_name_inside);
            textView2 = itemView.findViewById(R.id.mTV_price_inside);
            textView3 = itemView.findViewById(R.id.mTV_shu_inside);
        }
    }
}
