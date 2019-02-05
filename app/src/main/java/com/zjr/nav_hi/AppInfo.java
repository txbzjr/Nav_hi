package com.zjr.nav_hi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppInfo {
    private Context context;
    private List<Map<String,Object>> Data;
    AppInfo(Context context){
        this.context = context;
        GetAppInfo();
    }
    private void GetAppInfo() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        this.Data = new ArrayList<>();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = this.context.getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (ResolveInfo info : apps) {
            /*String packageName = info.activityInfo.packageName;
            String cls = info.activityInfo.name;
            String name = info.activityInfo.loadLabel(this.context.getPackageManager()).toString();
            Drawable Icon = info.activityInfo.loadIcon(this.context.getPackageManager());
            Log.e("！！！！！", name + "----" + packageName + "----" + cls + "-----" + Icon);*/
            Map<String,Object> tmp_map = new HashMap<>();
            tmp_map.put("AppName",info.activityInfo.loadLabel(this.context.getPackageManager()).toString());
            tmp_map.put("AppPackageName",info.activityInfo.packageName);
            tmp_map.put("AcName",info.activityInfo.name);
            tmp_map.put("AppIcon",info.activityInfo.loadIcon(this.context.getPackageManager()));
            this.Data.add(tmp_map);
        }
    }
    public List<Map<String,Object>> GetData(){
        return this.Data;
    }

}
