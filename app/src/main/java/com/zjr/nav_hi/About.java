package com.zjr.nav_hi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class About extends AppCompatActivity {
    private static final String ALIPAY_SHOP = "https://qr.alipay.com/fkx03501soxzsjtzpjbs174";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button alibt = findViewById(R.id.alibt);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("关于作者");
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        alibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyALiPayUtil.hasInstalledAlipayClient(About.this)){
                    MyALiPayUtil.startAlipayClient(About.this,ALIPAY_SHOP);
                }
                else {
                    Toast.makeText(About.this, "未检测到支付宝！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
