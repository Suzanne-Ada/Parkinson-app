package com.example.parkinson.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parkinson.R;
import com.example.parkinson.audio.AudioListAdapter;
import com.example.parkinson.bean.MemoryBean;

import java.util.List;

public class MemoryListAdapter extends BaseAdapter {
    private static final String TAG = "MemoryListAdapter";
    private List<MemoryBean> data;
    private Context mContext;

    public MemoryListAdapter(Context context,List<MemoryBean> data) {
        this.data = data;
        this.mContext = context;
    }
    //item的数量
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //item的视图
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view ==null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_memory, viewGroup, false);
        }
        TextView item = view.findViewById(R.id.tv_forcast);
        item.setText(data.get(i).getName());
        return view;
    }
}
