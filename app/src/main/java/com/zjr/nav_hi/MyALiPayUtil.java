package com.zjr.nav_hi;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.URLEncoder;

public class MyALiPayUtil {

    // 支付宝包名
    private static final String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";

    //第一步：检查支付宝是否安装
    public static boolean hasInstalledAlipayClient(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //第二步：调用者调用此方法跳转到支付宝
    public static boolean startAlipayClient(Activity activity, String urlCode) {
        return startIntentUrl(activity, doFormUri(urlCode));
    }

    //格式化urlCode
    private static String doFormUri(String urlCode) {
        try {
            urlCode = URLEncoder.encode(urlCode, "utf-8");
        } catch (Exception e) {
        }
        final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + urlCode;
        String openUri = alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis();
        return openUri;
    }

    //主要功能代码：跳转到支付宝
    private static boolean startIntentUrl(Activity activity, String intentFullUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentFullUrl));
            activity.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
