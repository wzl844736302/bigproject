package wzl.com.wproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.Evaluation;

public class EvalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Evaluation> list = new ArrayList<>();
    private Context context;
    public EvalAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.eval_item,null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.imageView.setImageURI(Uri.parse(list.get(i).getHeadPic()));
        myHolder.textView.setText(list.get(i).getNickName());
        long time = list.get(i).getCreateTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format.format(date);
        myHolder.textView2.setText(s+"");
        myHolder.textView3.setText(list.get(i).getContent());
        String[] split = list.get(i).getImage().split(",");
        myHolder.imageView2.setImageURI(Uri.parse(split[0]));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Evaluation> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        ImageView imageView2;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mSim_eval);
            textView = itemView.findViewById(R.id.mTV_name_eval);
            textView2 = itemView.findViewById(R.id.mTV_date_eval);
            textView3 = itemView.findViewById(R.id.mTV_text_eval);
            imageView2 = itemView.findViewById(R.id.mgrid);
        }
    }
}
