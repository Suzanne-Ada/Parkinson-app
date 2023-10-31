package com.example.parkinson.utils;

import android.os.Environment;

import java.io.File;
//创建SD卡目录工具类
public class SDCardUtils {
    private SDCardUtils(){

    }
    private static SDCardUtils sdCardUtils;
    public static SDCardUtils getInstance(){
        if(sdCardUtils==null){
            synchronized (SDCardUtils.class){
                if(sdCardUtils == null){
                    sdCardUtils = new SDCardUtils();
                }
            }
        }
        return sdCardUtils;
    }
    public boolean isHaveSDCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    //创建文件公共目录
    public File createAppPublicDir(){
        if (isHaveSDCard()){
            File sdDir = Environment.getExternalStorageDirectory();
            File appDir =new File(sdDir,IFileInter.APP_DIR);
            if (!appDir.mkdir()){
                appDir.mkdir();
            }
            Contants.PATH_APP_DIR = appDir.getAbsolutePath();
            return appDir;
        }
        return null;
    }
    //创建文件分支目录
    public File createAppFetchDir(String dir){
        File publicDir = createAppPublicDir();
        if (publicDir!=null){
            File fetchDir = new File(publicDir, dir);
            if (!fetchDir.exists()){
                fetchDir.mkdirs();
            }
            return fetchDir;
        }
        return null;
    }
}
