package com.example;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TwitterSearcher {
	private HttpHost target = new HttpHost(SERVER_HOST, SERVER_PORT, "http");
	private HttpEntity entity = null;
	private HttpClient client = new DefaultHttpClient();
	private HttpGet get = null;
	private HttpResponse response = null;
	
	static String SERVER_HOST = "search.twitter.com";
	static String SEARCH = "/search.json?q=";
	static Integer SERVER_PORT = 80;
	
	private String getKeywords(HttpHost target, String URL) {
		String keywords = null;
		get = new HttpGet(URL);
		
		try {
			response = client.execute(target, get);
			entity = response.getEntity();
			keywords = EntityUtils.toString(entity);
		}
		catch (Exception e) {
			Log.e("INTERNET_CONNECTION", "Error while trying to retrieve data");
		}
		finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				}
				catch (IOException e) {
					Log.e("getKeywords", e.getMessage());
				}
			}
		}
		return keywords;
	}
	
	public ArrayList<Tweet> getTweetsByText(String text) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		String URL1 = SEARCH + text;
		String result  = getKeywords(target, URL1);
		
		try {
			JSONObject jObject = new JSONObject(result);
			JSONArray array = jObject.getJSONArray("results");
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				Tweet tweet = new Tweet();
				tweet.setFromUser(jsonObject.getString("from_user"));
				tweet.setProfileImageUrl(jsonObject.getString("profile_image_url"));
				tweet.setText(jsonObject.getString("text"));
				
				tweets.add(tweet);
			}
		} catch (Exception e1) {
			Log.e("getTweetsByText", e1.getMessage());
		}
		
		return tweets;
	}
}
