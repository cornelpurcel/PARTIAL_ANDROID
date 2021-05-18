package com.example.rssfeedviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userItemAdapter extends RecyclerView.Adapter<userItemViewHolder>{

    List<String> usernameList;
    UserItemClickListener listener;
    Context context;

    public userItemAdapter(List<String> usernameList, Context context, UserItemClickListener listener){
        this.usernameList = usernameList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public userItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tmpView = inflater.inflate(R.layout.user_item, parent, false);

        userItemViewHolder viewHolder = new userItemViewHolder(tmpView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull userItemViewHolder viewHolder, int position) {
        viewHolder.itemUsername.setText(usernameList.get(position));
        viewHolder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "User is " + usernameList.get(position), Toast.LENGTH_SHORT).show();
                listener.onClickItem(view, position);
//                // TODO CHANGE USER
//                SharedPreferences usersPref = context.getSharedPreferences("users", 0);
//                SharedPreferences.Editor userEditor = usersPref.edit();
//                MyApplication app = (MyApplication) context.getApplicationContext();
//                userEditor.putBoolean(app.getUsername(), false);
//                userEditor.putBoolean(usernameList.get(position), true);
//
//                userEditor.commit();
                }
        });
        viewHolder.view.findViewById(R.id.user_item_delete).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // delete user
                listener.onClickDelete(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usernameList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setUsernameList(List<String> newUsernameList){
        this.usernameList = newUsernameList;
    }
}
