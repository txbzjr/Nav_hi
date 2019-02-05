package com.zjr.nav_hi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


class Immersive_Dialog {
    private AlertDialog alertDialog;
    private Button cancle,ok,btdefult;
    private CheckBox nv,st;
    private Context context;
    private View view;
    private String PackName;
    private Boolean isAll;
    private ProgressBar progressBar;
    private final static String Add_Nv_Command = "settings put global policy_control immersive.navigation=";
    private final static String Add_all_Nv_Command = "settings put global policy_control immersive.navigation=*";
    private final static String Add_st_Command = "settings put global policy_control immersive.status=";
    private final static String Add_all_st_Command = "settings put global policy_control immersive.status=*";
    private final static String Add_full_Command = "settings put global policy_control immersive.full=";
    private final static String remove_full_Command = "settings put global policy_control immersive.full=-";
    private final static String Add_all_full_Command = "settings put global policy_control immersive.full=*";
    private final static String remove_all_full_Command = "settings put global policy_control null";
    Immersive_Dialog(Context context, String PackName, Boolean isAll){
        this.PackName = PackName;
        this.context = context;
        this.isAll = isAll;
        this.view = LayoutInflater.from(this.context).inflate(R.layout.single_nv_layout,null);
        this.progressBar = new ProgressBar(this.context);
        this.cancle = view.findViewById(R.id.btcancle);
        this.ok = view.findViewById(R.id.btok);
        this.nv = view.findViewById(R.id.s_Nv_cb);
        this.btdefult = view.findViewById(R.id.btdefault);
        this.st = view.findViewById(R.id.s_Status_cb);
        ShowDialog();
    }
    private void ShowDialog(){
        alertDialog = new AlertDialog.Builder(this.context).create();
        alertDialog.setTitle("请选择需要隐藏的部位");
        alertDialog.setCancelable(false);
        alertDialog.setView(view);
        this.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nv.isChecked()&&!st.isChecked()){
                    Toast.makeText(context,"客官没有选择部位哟~~",Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.show();
                    if (isAll)
                        All_Exec();
                    else
                        Single_Exec();
                }
            }
        });
        this.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        this.btdefult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                if (isAll)
                    ShellExec.RootCommand(remove_all_full_Command,handler);
                else
                    ShellExec.RootCommand(remove_full_Command,handler);
            }
        });
    }
    private void Single_Exec(){

        if(nv.isChecked()&&st.isChecked())
            ShellExec.RootCommand(remove_full_Command+this.PackName+"&&"+
                    Add_full_Command+this.PackName,handler);
        else if (nv.isChecked()&&!st.isChecked())
            ShellExec.RootCommand(remove_full_Command+this.PackName+"&&"+
                    Add_Nv_Command+this.PackName,handler);
        else if (!nv.isChecked()&&st.isChecked())
            ShellExec.RootCommand(remove_full_Command+this.PackName+"&&"+
                    Add_st_Command+this.PackName,handler);
        else
            Toast.makeText(context,"客官没有选择部位哟~~",Toast.LENGTH_SHORT).show();
    }
    private void All_Exec(){
        if(nv.isChecked()&&st.isChecked())
            ShellExec.RootCommand(Add_all_full_Command,handler);
        else if (nv.isChecked()&&!st.isChecked())
            ShellExec.RootCommand(Add_all_Nv_Command,handler);
        else if (!nv.isChecked()&&st.isChecked())
            ShellExec.RootCommand(Add_all_st_Command,handler);
        else
            Toast.makeText(context,"客官没有选择部位哟~~",Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                progressBar.dismiss();
                alertDialog.dismiss();
                Toast.makeText(context, "操作已完成", Toast.LENGTH_LONG).show();
            }
        }
    };
}
