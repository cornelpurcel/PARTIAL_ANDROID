package com.example.rssfeedviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class rssItemFeedAdapter extends RecyclerView.Adapter<rssItemViewHolder> {

    List<RSSItem> list = Collections.emptyList();

    Context context;
    MyClickListener listener;
    SimpleDateFormat formatter;

    public void setList(List<RSSItem> newList){
        this.list = newList;
    }

    public rssItemFeedAdapter(List<RSSItem> list, Context context, MyClickListener listener){
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.formatter = new SimpleDateFormat("dd MMM yyyy");
    }

    @Override
    public rssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tmpView = inflater.inflate(R.layout.rss_item_v2, parent, false);

        rssItemViewHolder viewHolder = new rssItemViewHolder(tmpView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final rssItemViewHolder viewHolder, final int position){
        final int index = viewHolder.getAdapterPosition();
        viewHolder.itemTitle.setText(list.get(position).getTitle());
        viewHolder.itemDescription.setText(list.get(position).getDescription());
        viewHolder.itemDate.setText(formatter.format(list.get(position).getDate()));
        viewHolder.itemSite.setText(getSite(list.get(position).getLink()));
        viewHolder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                listener.click(index);
            }
        });
    }

    private static String getSite(String link){
        int begin = link.indexOf("www.");
        if (begin < 0){
            begin = link.indexOf("://") + 3;
        } else
            begin += 4;
        int lastPoint = begin;
        for (int i = begin; i<link.length(); i++){
            if (link.charAt(i) == '.')
                lastPoint = i;
            if (link.charAt(i) == '/')
                return link.substring(begin, i);
        }
        return link.substring(begin, link.length());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
