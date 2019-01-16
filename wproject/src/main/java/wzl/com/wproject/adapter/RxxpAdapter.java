package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
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

import wzl.com.wproject.view.GoodsdetailsActivity;
import wzl.com.wproject.R;
import wzl.com.wproject.bean.shops.Commodity;

public class RxxpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<Commodity> list = new ArrayList<>();
    private LayoutInflater inflater;
    public RxxpAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.rxxp_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getCommodityName());
        myHolder.textView2.setText("￥"+list.get(i).getPrice()+"");
        String masterPic = list.get(i).getMasterPic();
        myHolder.imageView.setImageURI(Uri.parse(masterPic));
        //点击跳转
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
    //添加
    public void addAll(List<Commodity> commodityList) {
        if (commodityList!=null){
            list.addAll(commodityList);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mSim_rxxp);
            textView = itemView.findViewById(R.id.mTV_rxxp);
            textView2 = itemView.findViewById(R.id.mTV_price);
        }
    }
}
