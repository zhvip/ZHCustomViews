package com.zh.customviews.activitys;

import android.os.Bundle;

import com.zh.customviews.adapter.SlideDeleteAdapter;
import com.zh.zhcustomviews.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideDeleteActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private SlideDeleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_delete);
        ButterKnife.bind(this);

        adapter = new SlideDeleteAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 10){

                }
            }
        });
    }
}
