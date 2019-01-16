package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.FirstCate;
import wzl.com.wproject.bean.Result;

public class FirstCateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<FirstCate> list = new ArrayList<>();
    public FirstCateAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(activity,R.layout.first_item,null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getName());
        //点击跳转
       /* myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Intent(activity.getBaseContext(),)
                list.get(i).getId();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<FirstCate> result) {
        if (result!=null){
            list.addAll(result);
        }
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTV_first);
        }
    }
}
