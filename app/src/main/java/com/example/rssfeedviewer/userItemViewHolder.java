package com.example.rssfeedviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class userItemViewHolder extends RecyclerView.ViewHolder{
    TextView itemUsername;
    View view;
    String username;

    public void setUsername(String username){
        this.username = username;
    }

    public userItemViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        itemUsername = (TextView)itemView.findViewById(R.id.user_item_username);
        view = itemView;
//        itemView.setOnClickListener(this);

//        itemView.findViewById(R.id.user_item_delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                // TODO DELETE USER
////                SharedPreferences usersPref = context.getSharedPreferences("users", 0);
////                SharedPreferences.Editor userEditor = usersPref.edit();
////                MyApplication app = (MyApplication) context.getApplicationContext();
////                userEditor.putBoolean(app.getUsername(), false);
////                userEditor.putBoolean(usernameList.get(position), true);
////
////                userEditor.commit();
//            }
//        });
    }
}
