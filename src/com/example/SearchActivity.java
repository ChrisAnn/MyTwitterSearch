package com.example;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity {
	
	private Button cmdSearch;
	private Button cmdSave;
	private EditText txtSearch;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cmdSearch = (Button)findViewById(R.id.cmdSearch);
        cmdSave = (Button)findViewById(R.id.cmdSave);
        txtSearch = (EditText)findViewById(R.id.txtSearch);
        
        cmdSave.setEnabled(false);
        
        cmdSearch.setOnClickListener(new Button.OnClickListener() { 
        	public void onClick (View v) { search(); }});
    }
    
    public void search() {
    	String searchText = txtSearch.getText().toString();
    	TwitterSearcher searcher = new TwitterSearcher();
    	ArrayList<Tweet> tweets = searcher.getTweetsByText(searchText);
    	
    	if (tweets.size() > 0) {
    		cmdSave.setEnabled(true);
    	}
    	
    	TwitterArrayAdapter adapter = new TwitterArrayAdapter(getApplicationContext(), R.layout.main, tweets);
    	ListView lv = (ListView)findViewById(R.id.lstViewTweet);
    	lv.setAdapter(adapter);
    }
}