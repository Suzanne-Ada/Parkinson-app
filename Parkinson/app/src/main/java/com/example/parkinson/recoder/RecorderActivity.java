package com.example.parkinson.recoder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkinson.R;
import com.example.parkinson.audio.AudioListActivity;
import com.example.parkinson.databinding.ActivityRecoderBinding;
import com.example.parkinson.utils.StartSystemPageUtils;

public class RecorderActivity extends AppCompatActivity {
    private ActivityRecoderBinding binding;
    private RecorderService recorderService;
    RecorderService.OnRefreshUIThreadListener refreshUIListener = new RecorderService.OnRefreshUIThreadListener() {
        @Override
        public void onRefresh(int fenbei, String time) {
            binding.voiceLine.setVolume(fenbei);
            binding.tvDuration.setText(time);
        }
    };
//    连接录音服务
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RecorderService.RecorderBinder binder = (RecorderService.RecorderBinder) service;
            recorderService = binder.getService();
            recorderService.startRecorder();
            recorderService.setOnRefreshUIThreadListener(refreshUIListener);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecoderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(this, RecorderService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_back:
                StartSystemPageUtils.goToHomePage(this);
                break;
            case R.id.iv_stop:
                recorderService.stopRecorder();
                Intent intent = new Intent(this, AudioListActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (connection!=null){
            unbindService(connection);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断点击了返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            StartSystemPageUtils.goToHomePage(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}