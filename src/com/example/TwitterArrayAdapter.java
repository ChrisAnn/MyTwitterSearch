package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TwitterArrayAdapter extends ArrayAdapter<Tweet> {

      private static final String tag = "TwitterArrayAdapter";
      private Context context;

      private ImageView profileIcon;
      private TextView userName;
      private TextView text;
      private List<Tweet> tweets = new ArrayList<Tweet>();

      public TwitterArrayAdapter(Context context, 
                   int textViewResourceId, List<Tweet> objects) {
             super(context, textViewResourceId, objects);
             this.context = context;
             this.tweets = objects;
      }

      public int getCount() {
            return this.tweets.size();
      }

      public Tweet getItem(int index) {
            return this.tweets.get(index);
      }

      public View getView(int position, View convertView, 
             ViewGroup parent) {
             View row = convertView;
             if (row == null) {
                   LayoutInflater inflater = 
                   (LayoutInflater) this.getContext()
                   .getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(R.layout.tweetrowlayout,
                    parent, false);
            }

          Tweet tweet = getItem(position);
          profileIcon = (ImageView) row.findViewById(R.id.icon);
          userName = (TextView) row.findViewById(R.id.userProfile);
          text = (TextView) row.findViewById(R.id.userText);

            userName.setText(tweet.getFromUser());

        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        Bitmap bm = LoadImage(tweet.getProfileImageUrl(), 
        bmOptions);
        profileIcon.setImageBitmap(bm);

             text.setText(tweet.getText());

             return row;
      }

    private Bitmap LoadImage(String URL, 
        BitmapFactory.Options options) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
        }
        catch (IOException e1) {
            Log.e("LoadImage", e1.getMessage());
        }
        return bitmap;
    }

    private InputStream OpenHttpConnection(String strURL) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == 
                HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
         }
         catch (Exception ex) {
             Log.e("OpenHttpConnection", ex.getMessage());
         }
         return inputStream;
    }
}