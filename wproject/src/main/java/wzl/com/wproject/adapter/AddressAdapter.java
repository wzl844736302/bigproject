package wzl.com.wproject.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.ShAddress;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<ShAddress> list = new ArrayList<>();
    public AddressAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(activity,R.layout.address_item,null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getRealName());
        myHolder.textView2.setText(list.get(i).getPhone());
        myHolder.textView3.setText(list.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ShAddress> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        RadioButton button;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTV_name_add);
            textView2 = itemView.findViewById(R.id.mTV_phone_add);
            textView3 = itemView.findViewById(R.id.mTV_dizhi_add);
        }
    }
}
