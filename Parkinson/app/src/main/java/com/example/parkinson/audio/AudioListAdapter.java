package com.example.parkinson.audio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parkinson.R;
import com.example.parkinson.bean.AudioBean;
import com.example.parkinson.databinding.ItemAudioBinding;

import java.util.List;
//每一个AudioItem的适配器，每一个录音文件的播放和暂停
public class AudioListAdapter extends BaseAdapter {
    private Context context;
    private List<AudioBean>mDatas;
    private TextView tvResult;
    //点击每一个itemView当中的playIv能够回调的接口
    public interface OnItemPlayClickListener{
        void onItemPlayClick(AudioListAdapter adapter,View convertView,View playView,int position);
    }
    private OnItemPlayClickListener onItemPlayClickListener;
    public void setOnItemPlayClickListener(OnItemPlayClickListener onItemPlayClickListener){
        this.onItemPlayClickListener=onItemPlayClickListener;
    }
    //点击每一个itemView当中的resultIv能够回调的接口
    public interface OnItemResultClickListener{
        void onItemResultClick(AudioListAdapter adapter,View convertView,View resultView,int position);
    }
    private OnItemResultClickListener onItemResultClickListener;

    public void setOnItemResultClickListener(OnItemResultClickListener onItemResultClickListener){
        this.onItemResultClickListener=onItemResultClickListener;
    }
    public AudioListAdapter(Context context, List<AudioBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        //设置监听时间
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_audio,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//        获取指定位置的数据对于控件进行设置
        AudioBean audioBean = mDatas.get(position);
        holder.ab.tvTime.setText(audioBean.getTime());
        holder.ab.tvDuration.setText(audioBean.getDuration());
        holder.ab.tvTitle.setText(audioBean.getTitle());
        if(audioBean.isPlaying()){//当前这条正在播放中
            holder.ab.lyControll.setVisibility(View.VISIBLE);
            holder.ab.pb.setMax(100);
            holder.ab.pb.setProgress(audioBean.getCurrentProgress());
            holder.ab.ivPlay.setImageResource(R.mipmap.red_pause);
        }else {
            holder.ab.ivPlay.setImageResource(R.mipmap.red_play);
            holder.ab.lyControll.setVisibility(View.GONE);
        }
        View itemView = convertView;
//        点击图标可以播放或者暂停录音
        holder.ab.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemPlayClickListener!=null){
                    onItemPlayClickListener.onItemPlayClick(AudioListAdapter.this,itemView,v,position);
                }
            }
        });
//        点击预测进入预测页面
        tvResult = holder.ab.tvResult;
        holder.ab.ivResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemResultClickListener!=null){
//                    tvResult.setText("您的身体健康");
                    onItemResultClickListener.onItemResultClick(AudioListAdapter.this,itemView,v,position);
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        ItemAudioBinding ab;
        public ViewHolder(View v){
            ab=ItemAudioBinding.bind(v);
        }
    }
}
