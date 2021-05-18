package com.example.rssfeedviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    rssItemFeedAdapter adapter;
    RecyclerView recyclerView;
    MyClickListener listener;
    Handler handler;

    List<String> sites;
    
    Map<String, String> siteMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSiteMap();

        handler = new Handler();
        initSiteList();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        startRssDownloadThread();



    }

    private void getSiteMap() {
        siteMap = new HashMap<>();
        siteMap.put("site_techcrunch", "https://techcrunch.com/feed/");
        siteMap.put("site_wired", "https://www.wired.com/feed/rss");
        siteMap.put("site_nytimes", "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml");
        siteMap.put("site_macworld", "https://www.macworld.com/feed");
        siteMap.put("site_pcworld", "https://www.pcworld.com/index.rss");
        siteMap.put("site_computerworld", "https://www.computerworld.com/index.rss");
        siteMap.put("site_lifehacker", "https://lifehacker.com/rss");

        siteMap.put("site_wsj", "https://feeds.a.dj.com/rss/RSSWorldNews.xml");
        siteMap.put("site_bbc", "https://feeds.bbci.co.uk/news/world/rss.xml");
        siteMap.put("site_euronews", "https://feeds.feedburner.com/euronews/en/home?format=xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh){
            initSiteList();
            startRssDownloadThread();
//            finish();
//            overridePendingTransition(0, 0);
//            startActivity(getIntent());
//            overridePendingTransition(0, 0);
            return true;
        } else if (item.getItemId() == R.id.menu_users){
            startActivity(new Intent(this, UsersActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_preferences){
            startActivity(new Intent(this, SitesActivity.class));
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void initSiteList() {
        sites = new ArrayList<String>();
        String username = ((MyApplication) this.getApplication()).getUsername();


        SharedPreferences pref = getSharedPreferences("sites_pref_" + username, 0);
        Set set = siteMap.entrySet();
        Iterator itr=set.iterator();
        while(itr.hasNext()){
            Map.Entry entry=(Map.Entry)itr.next();
            if (pref.getBoolean(entry.getKey().toString(), false)){
                sites.add(entry.getValue().toString());
            }
        }
//        sites.add("https://www.wired.com/feed/rss");
//        sites.add("https://techcrunch.com/feed/");
    }

    private void startRssDownloadThread(){
        try{
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    final List<RSSItem> list = getData();
//                    Log.d("rssparse", list.get(0).getTitle());
//                    Log.d("rssparse", list.get(1).getTitle());

                    handler.post(new Runnable() {
                        @Override
                        public void run(){
                            try{

                                listener = new MyClickListener(){
                                    @Override
                                    public void click(int index){
//                                if (index == 0){
//                                    Log.d("rssparse", "hallelujah");
//                                    HandleRSS a = new HandleRSS("https://www.wired.com/feed/rss");
//                                    a.fetchXML();
//                                }
                                        Toast.makeText(MainActivity.this, "clicked item index is " + String.valueOf(index), Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(list.get(index).getLink()));
                                        startActivity(i);

                                    }
                                };
                                adapter = new rssItemFeedAdapter(list, getApplication(), listener);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            } catch (Exception e){
                                Log.d("rssparse", e.getMessage());
                            }
                        }
                    });


                }
            };
            new Thread(runnable).start();
        }
        catch (Exception e){
            Log.d("rssparse", e.getMessage());
        }
    }

    private List<RSSItem> getData(){
        HandleRSS rssHandler = new HandleRSS();

        return rssHandler.fetchXML(sites);
    }
}