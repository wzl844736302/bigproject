package wzl.com.wproject.adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.ShopCar;

public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<ShopCar> list;
    private LayoutInflater inflater;
    public ShopCarAdapter(Activity activity, List<ShopCar> list) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
    }

    //自定义价格变量
    private TotalListener totalListener;
    //自定义接口
    public interface TotalListener{
        void totalprice(double sum);
    }

    @NonNull //set方法
    public void setTotalListener(TotalListener totalListener) {
        this.totalListener = totalListener;
    }
    private OndeleteListenter ondeleteListenter;
    public interface OndeleteListenter{
        void delete(int i);
    }

    public void setOndeleteListenter(OndeleteListenter ondeleteListenter) {
        this.ondeleteListenter = ondeleteListenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.shopcar_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        final ShopCar shopCar = list.get(i);
        String pic = shopCar.getPic();
        myHolder.imageView.setImageURI(pic);
        myHolder.textView.setText(shopCar.getCommodityName());
        myHolder.textView2.setText("￥"+shopCar.getPrice()+"");
        myHolder.textView3.setText(shopCar.getCount()+"");

        //点击选中，计算价格
        myHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                shopCar.setIscheck(checked?1:0);
                //计算价格
                SumPrice();
            }
        });
        if (shopCar.getIscheck()==0){
            myHolder.checkBox.setChecked(false);
        }else {
            myHolder.checkBox.setChecked(true);
        }
        //点击删除
        myHolder.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ondeleteListenter.delete(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        CheckBox checkBox;
        TextView textView4;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mSim_shop);
            textView = itemView.findViewById(R.id.mTV_name_shop);
            textView2 = itemView.findViewById(R.id.mTV_price_shop);
            textView3 = itemView.findViewById(R.id.mTV_shu_shop);
            checkBox = itemView.findViewById(R.id.mCB_xuan);
            textView4 = itemView.findViewById(R.id.mTV_shanchu);
        }
    }
    //计算价格
    public void SumPrice(){
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            ShopCar shopCar = list.get(i);
            if (shopCar.getIscheck()==1){
                sum=sum+shopCar.getPrice();
            }
        }
        if (totalListener!=null){
            totalListener.totalprice(sum);
        }
    }
}
