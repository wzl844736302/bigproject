package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.Search;
import wzl.com.wproject.view.GoodsdetailsActivity;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Search> list = new ArrayList<>();
    public SearchAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(activity, R.layout.search_item,null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        String masterPic = list.get(i).getMasterPic();
        myHolder.imageView.setImageURI(Uri.parse(masterPic));
        myHolder.textView.setText(list.get(i).getCommodityName());
        myHolder.textView2.setText("￥"+list.get(i).getPrice()+"");
        myHolder.textView3.setText("已售"+list.get(i).getSaleNum()+""+"件");
        //跳转详情页
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), GoodsdetailsActivity.class);
                intent.putExtra("commid",list.get(i).getCommodityId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Search> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mSim_search);
            textView = itemView.findViewById(R.id.mTV_title);
            textView2 = itemView.findViewById(R.id.mTV_price_search);
            textView3 = itemView.findViewById(R.id.mTV_jian);
        }
    }
}
