package com.example.rssfeedviewer;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class rssItemViewHolder extends RecyclerView.ViewHolder {
    TextView itemTitle;
    TextView itemDescription;
    TextView itemDate;
    TextView itemSite;
    View view;

    rssItemViewHolder(View itemView){
        super(itemView);
        itemTitle = (TextView)itemView.findViewById(R.id.txt_title);
        itemDescription = (TextView)itemView.findViewById(R.id.txt_description);
        itemDate = (TextView) itemView.findViewById(R.id.txt_date);
        itemSite = (TextView)itemView.findViewById(R.id.txt_site);
        view = itemView;
    }
}
