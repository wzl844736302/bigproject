package wzl.com.wproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wzl.com.wproject.R;
import wzl.com.wproject.bean.Querys;
import wzl.com.wproject.view.WalletActivity;

public class QueryListViewAdapter extends BaseAdapter {
    private Activity activity;
    private List<Querys> list;
    public QueryListViewAdapter(Activity activity, List<Querys> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = View.inflate(activity,R.layout.list_item,null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.mTv_item);
            holder.textView2 = convertView.findViewById(R.id.mTV_item_2);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText("￥"+list.get(position).getAmount()+"");
        Querys querys = list.get(position);
        long time = querys.getConsumerTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = format.format(date);
        holder.textView2.setText(s);
        return convertView;
    }
    class ViewHolder{
        TextView textView;
        TextView textView2;
    }
}
