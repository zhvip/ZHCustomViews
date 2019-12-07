package com.zh.customviews.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zh.customviews.models.HomeListModel;
import com.zh.customviews.adapter.MyHomeAdapter;
import com.zh.zhcustomviews.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyHomeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        adapter = new MyHomeAdapter(this.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setAdapter(adapter);

        intList();
    }

    private void intList() {
        adapter.addList(new HomeListModel(MenuActivity.class,"仿照微信弹出菜单框"));
        adapter.addList(new HomeListModel(IOSUISwitchActivity.class,"仿iOS的UISwitch"));
        adapter.addList(new HomeListModel(DialogDemoActivity.class,"Android弹框"));
        adapter.addList(new HomeListModel(SlideDeleteActivity.class,"RecycleView左滑删除单元项"));
    }


}
