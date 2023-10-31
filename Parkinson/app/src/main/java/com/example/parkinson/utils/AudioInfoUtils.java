package com.example.parkinson.utils;

import android.media.MediaMetadataRetriever;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioInfoUtils{
    //获取音频文件相关内容的工具类
    private MediaMetadataRetriever mediaMetadataRetriever;
    private AudioInfoUtils(){}
    private static AudioInfoUtils utils;
    public static AudioInfoUtils getInstance(){
        if(utils==null){
            synchronized (AudioInfoUtils.class){
                if (utils == null){
                    utils = new AudioInfoUtils();
                }
            }
        }
        return utils;
    }
//    获取文件的毫秒数
    public long getAudioFileDuration(String filePath){
        long duration = 0;
        if (mediaMetadataRetriever==null){
            mediaMetadataRetriever = new MediaMetadataRetriever();
        }
        mediaMetadataRetriever.setDataSource(filePath);
        String s = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        duration = Long.parseLong(s);
        return duration;
    }
    //转换成固定类型的时长HH:mm:ss
    public String getAudioFileFormatDuration(String format,long durlong){
        durlong-=8*60*60*1000;//减去8小时时差
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(durlong));
    }
    public String getAudioFileFormatDuration(long durlong){
        return getAudioFileFormatDuration("HH:mm:ss",durlong);
    }
    //    获取多媒体文件的艺术家
    public String getAudioFileArtist(String filepath){
        if (mediaMetadataRetriever==null){
            mediaMetadataRetriever = new MediaMetadataRetriever();
        }
        mediaMetadataRetriever.setDataSource(filepath);
        String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        return artist;
    }
//    释放
    public void releseRetriever(){
        if (mediaMetadataRetriever!=null){
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaMetadataRetriever = null;
        }
    }
}
