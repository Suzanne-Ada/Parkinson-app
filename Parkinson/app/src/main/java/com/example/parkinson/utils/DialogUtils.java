package com.example.parkinson.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {
    public interface OnLeftClickListener{
        public void onLeftClick();
    }
    public interface OnRightClickListener{
        public void onRightClick();
    }
    //    展示权限申请对话框
    public static void showNormalDialog(Context context,String title
        ,String msg,String leftBtn,OnLeftClickListener leftListener
        ,String rightBtn,OnRightClickListener rightListener){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg);
        //        左边按钮
        builder.setNegativeButton(leftBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (leftListener!=null){
                    leftListener.onLeftClick();
                    dialog.cancel();
                }
            }
        });
        //        右边按钮
        builder.setPositiveButton(rightBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rightListener!=null){
                    rightListener.onRightClick();
                    dialog.cancel();
                }
            }
        });
        builder.create().show();
    }
}
