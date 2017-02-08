package com.example.greatbook.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.MainJokBean;
import com.example.greatbook.utils.ScreenUtils;

import java.util.List;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public class JokAdapter extends BaseAdapter{
    private List<MainJokBean.JokBean> data;
    private Context context= App.getInstance().getContext();
    private LayoutInflater inflater=LayoutInflater.from(context);

    public JokAdapter(List<MainJokBean.JokBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainJokBean.JokBean jokBean=data.get(position);
        ViewHolder viewHolder;
        if (convertView==null){

            convertView=inflater.inflate(R.layout.item_jok,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tvJokContent= (TextView) convertView.findViewById(R.id.tv_jok_content);
            viewHolder.tvJokTime= (TextView) convertView.findViewById(R.id.tv_jok_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvJokTime.setText(jokBean.getUpdatetime());
        viewHolder.tvJokContent.setText(jokBean.getContent());
        return convertView;
    }
    public class ViewHolder{
        TextView tvJokContent;
        TextView tvJokTime;
    }

    public void addData(List<MainJokBean.JokBean> data){
        this.data=data;
        notifyDataSetChanged();
    }
}
