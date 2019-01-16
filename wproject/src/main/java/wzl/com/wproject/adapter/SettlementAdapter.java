package wzl.com.wproject.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.ShopCar;

public class SettlementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<ShopCar> list;
    private LayoutInflater inflater;
    public SettlementAdapter(Activity activity, List<ShopCar> list) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.settlement_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        String pic = list.get(i).getPic();
        myHolder.draweeView.setImageURI(pic);
        myHolder.textView.setText(list.get(i).getCommodityName());
        myHolder.textView2.setText("￥"+list.get(i).getPrice()+"");
        myHolder.textView3.setText(list.get(i).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.msim_set);
            textView = itemView.findViewById(R.id.mTV_name_set);
            textView2 = itemView.findViewById(R.id.mTV_price_set);
            textView3 = itemView.findViewById(R.id.mTV_shu_set);
        }
    }
}
