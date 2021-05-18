package com.example.rssfeedviewer;

import android.view.View;

public interface UserItemClickListener {
    public void onClickItem(View view, int position);
    public void onClickDelete(View view, int position);
}
