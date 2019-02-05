package com.zjr.nav_hi;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class ProgressBar {
    private AlertDialog alertDialog;
    ProgressBar(Context context){
        View progressBarView = LayoutInflater.from(context).inflate(R.layout.progressbar_alertdialog_layout, null);
        this.alertDialog = new AlertDialog.Builder(context).create();
        this.alertDialog.setTitle("正在执行操作");
        this.alertDialog.setCancelable(false);
        this.alertDialog.setView(progressBarView);
    }
    public void show(){
        this.alertDialog.show();
    }
    public void dismiss(){
        this.alertDialog.dismiss();
    }
}
