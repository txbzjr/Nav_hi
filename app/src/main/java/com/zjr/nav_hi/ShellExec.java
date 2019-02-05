package com.zjr.nav_hi;

import android.os.Handler;

import java.io.DataOutputStream;

class ShellExec {
    static void RootCommand(final String Command, final Handler handler){
        new Thread(){
            public void run(){
                Process process = null;
                DataOutputStream dataOutputStream = null;
                try{
                    process = Runtime.getRuntime().exec("su");
                    dataOutputStream = new DataOutputStream(process.getOutputStream());
                    dataOutputStream.writeBytes(Command+"\n");
                    dataOutputStream.writeBytes("exit\n");
                    dataOutputStream.flush();
                    process.waitFor();
                    sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try{
                        if(dataOutputStream!=null){
                            dataOutputStream.close();
                        }
                        process.destroy();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(handler!=null){
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }
}
