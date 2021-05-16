package com.example.rssfeedviewer;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HandleRSS {
    private String title = "title";
    private String link = "link";
    private String description = "description";
    private String date = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    private List<RSSItem> ArrayList;

    public HandleRSS(){ }

    public String getTitle(){
        return title;
    }

    public String getLink(){
        return link;
    }

    public String getDescription(){
        return description;
    }

    public List<RSSItem> parseXMLandStoreIt(XmlPullParser myParser){
        int event;
        String text = null;
        String sDate = "E, dd MMM HH:mm:ss Z";
        List<RSSItem> items = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

        Log.d("rssparse", "BEGINNING PARSING");
        try {
            event = myParser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch(event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("title")){
                            title = text;
                            Log.d("rssparse", "Title " + text);
                        }
                        else if (name.equals("link")){
                            link = text;
                            Log.d("rssparse", "Link " + text);
                        }
                        else if (name.equals("description")){
                            description = text;
                            Log.d("rssparse", "Description " + text);
                        }
                        else if (name.equals("pubDate")){
                            date = text;
                            Log.d("rssparse", "Date " + text);
                        }
                        else if (name.equals("item")) {
                            items.add(new RSSItem(title, description, link, formatter.parse(date)));
//                            Log.d("rssparse", "added " + title + ".");
                            title = null;
                            description = null;
                            link = null;
                        }
                        else {}
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }
        catch (Exception e){
            Log.d("rssparse", e.toString());
            e.printStackTrace();
        }
    return items;
    }

    public List<RSSItem> fetchXML(List<String> sites){
//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run() {
                Log.d("rssparse", "IN THREAD BEGIN");
                List<RSSItem> items = new ArrayList<RSSItem>();
                for (String site : sites){

                    try {
                        URL url = new URL(site);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setReadTimeout(10000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);

                        conn.connect();
                        InputStream stream = conn.getInputStream();

                        Log.d("rssparse", "GOT INPUT STREAM FOR RSS");

                        xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser = xmlFactoryObject.newPullParser();

                        myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        myparser.setInput(stream, null);

                        items.addAll(parseXMLandStoreIt(myparser));
                        stream.close();
                        conn.disconnect();
                    }

                    catch (Exception e){
                        Log.d("rssparse", "EXCEPTION: " + e.toString());
                        e.printStackTrace();
                    }
                }
            Collections.sort(items, new Comparator<RSSItem>() {
                @Override
                public int compare(RSSItem o1, RSSItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
                return items;
//            }
//        });
//        Log.d("rssparse", "BEFORE START");
//        thread.start();
//        Log.d("rssparse", "AFTER START");
    }
}
