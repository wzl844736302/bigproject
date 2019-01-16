package wzl.com.wproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.dingdan.DetalList;
import wzl.com.wproject.bean.dingdan.OrderBeans;
import wzl.com.wproject.view.PayActivity;

public class ShowOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity;
    private List<DetalList> list = new ArrayList<>();
    private InsideShowOrderAdapter showOrderAdapter;
    private InsideShowOrderAdapter_dsh insideShowOrderAdapter_dsh;
    private InsideShowOrderAdapter_dpj insideShowOrderAdapter_dpj;
    List<OrderBeans> orderBeans = new ArrayList<>();
    int num = 0;
    double sum = 0;
    private MyHolder myHolder;
    private int commodityCount;

    public ShowOrderAdapter(Activity activity) {
        this.activity = activity;
    }
    //自定义接口
    private TheGoods theGoods;
    public interface TheGoods{
        void goods(String orderid);
    }
    public void setTheGoods(TheGoods theGoods) {
        this.theGoods = theGoods;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1){
            View view = View.inflate(activity, R.layout.showorder_item,null);
            MyHolder holder = new MyHolder(view);
            return holder;
        }else if (i == 2){
            View view = View.inflate(activity,R.layout.showorder_dsh_item,null);
            MyHolder1 holder1 = new MyHolder1(view);
            return holder1;
        }else if (i == 3){
            View view = View.inflate(activity,R.layout.showorder_dpj_item,null);
            MyHolder2 holder2 = new MyHolder2(view);
            return holder2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof MyHolder){
            myHolder = (MyHolder) holder;
            myHolder.textView.setText("订单号   "+list.get(i).getOrderId());
            List<OrderBeans> detailList = list.get(i).getDetailList();
            num = 0;
            sum = 0;
            for (int j = 0; j < detailList.size(); j++) {
                OrderBeans orderBeans = detailList.get(j);
                commodityCount = orderBeans.getCommodityCount();
                num = num + commodityCount;
                sum = sum + orderBeans.getCommodityPrice()*commodityCount;
            }
            myHolder.textView2.setText(num+"");
            myHolder.textView3.setText(sum+"");
            showOrderAdapter = new InsideShowOrderAdapter(detailList,activity);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            myHolder.recyclerView.setLayoutManager(layoutManager);
            myHolder.recyclerView.setAdapter(showOrderAdapter);
            //跳转
            myHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity.getBaseContext(), PayActivity.class);
                    intent.putExtra("orderid",list.get(i).getOrderId());
                    intent.putExtra("price",sum);
                    activity.startActivity(intent);
                }
            });
        }else if (holder instanceof MyHolder1){
            MyHolder1 myHolder1 = (MyHolder1) holder;
            myHolder1.textView.setText("订单号   "+list.get(i).getOrderId());
            myHolder1.textView2.setText(list.get(i).getExpressCompName());
            myHolder1.textView3.setText(list.get(i).getExpressSn());
            List<OrderBeans> detailList = list.get(i).getDetailList();
            insideShowOrderAdapter_dsh = new InsideShowOrderAdapter_dsh(detailList,activity);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            myHolder1.recyclerView1.setLayoutManager(layoutManager);
            myHolder1.recyclerView1.setAdapter(insideShowOrderAdapter_dsh);
            myHolder1.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetalList detalList = list.get(i);
                    String orderId = detalList.getOrderId();
                    theGoods.goods(orderId);
                }
            });
        }else if (holder instanceof MyHolder2){
            MyHolder2 myHolder2 = (MyHolder2) holder;
            myHolder2.textView.setText("订单号   "+list.get(i).getOrderId());
            List<OrderBeans> detailList = list.get(i).getDetailList();
            insideShowOrderAdapter_dpj = new InsideShowOrderAdapter_dpj(detailList,activity);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            myHolder2.recyclerView2.setLayoutManager(layoutManager);
            myHolder2.recyclerView2.setAdapter(insideShowOrderAdapter_dpj);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getOrderStatus() == 1){
            return 1;
        }else if (list.get(position).getOrderStatus()==2){
            return 2;
        }else if (list.get(position).getOrderStatus()==3){
            return 3;
        }
        return 1;
    }

    //添加
    public void addAll(List<DetalList> orderList) {
        if (orderList!=null){
            list.addAll(orderList);
        }
    }

    public void clear() {
        list.clear();
        orderBeans.clear();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        RecyclerView recyclerView;
        Button button;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTV_dingdanhao);
            textView2 = itemView.findViewById(R.id.mTV_jian_order);
            textView3 = itemView.findViewById(R.id.mTV_yuan);
            recyclerView = itemView.findViewById(R.id.mrecycler_inside);
            button = itemView.findViewById(R.id.mBt_zhifu);
        }
    }
    class MyHolder1 extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        RecyclerView recyclerView1;
        Button button;
        public MyHolder1(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTV_dingdanhao);
            textView2 = itemView.findViewById(R.id.mTV_kuaidi);
            textView3 = itemView.findViewById(R.id.mTV_kuaidihao);
            recyclerView1 = itemView.findViewById(R.id.mrecycler_inside1);
            button = itemView.findViewById(R.id.mBt_sh);
        }
    }
    class MyHolder2 extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        RecyclerView recyclerView2;
        public MyHolder2(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTV_dingdanhao);
            textView2 = itemView.findViewById(R.id.mTV_shijian);
            recyclerView2 = itemView.findViewById(R.id.mrecycler_inside1);
        }
    }

}
