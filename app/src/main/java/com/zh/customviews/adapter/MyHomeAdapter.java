package com.zh.customviews.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zh.customviews.models.HomeListModel;
import com.zh.zhcustomviews.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHomeAdapter extends RecyclerView.Adapter <MyHomeAdapter.MyViewHolder>{

    private List<HomeListModel> activityList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    public MyHomeAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addList(HomeListModel model){
        activityList.add(model);
        notifyDataSetChanged();
    }

    public void addList(List<HomeListModel> activityList){
        activityList.addAll(activityList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_home,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final HomeListModel model = activityList.get(position);
        holder.textView.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> activityClass = model.getActivityClass();
                Intent intent = new Intent();
                intent.setClass(context,activityClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_text);
        }
    }
}
