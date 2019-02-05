package com.zjr.nav_hi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewAdaper recyclerViewAdaper;
    private EditText editText;
    private RecyclerView AppListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();
        AppInfo appInfo = new AppInfo(this);
        final List<Map<String,Object>> AppsData = appInfo.GetData();
        recyclerViewAdaper = new RecyclerViewAdaper(this,AppsData);
        AppListRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        AppListRecyclerView.setAdapter(recyclerViewAdaper);
        recyclerViewAdaper.setOnItemClickLitener(new RecyclerViewAdaper.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                new Immersive_Dialog(MainActivity.this,
                        recyclerViewAdaper.getmFilterList().get(position).get("AppPackageName")+"",
                        false);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recyclerViewAdaper.getFilter().filter(s.toString());
            }
        });

    }
    private void inti(){
        AppListRecyclerView = findViewById(R.id.AppListView);
        editText = findViewById(R.id.editText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_global:
                        new Immersive_Dialog(MainActivity.this, null, true);
                        break;
                    case R.id.menu_reboot_ui:
                        ShellExec.RootCommand("pkill -f com.android.systemui",null);
                        break;
                    case R.id.menu_about:
                        Intent intent = new Intent(MainActivity.this,About.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
}
