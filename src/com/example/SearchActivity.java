package com.example;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity {
	
	private Button cmdSearch;
	private Button cmdSave;
    private Button cmdSavedSearches;
	private EditText txtSearch;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cmdSearch = (Button)findViewById(R.id.cmdSearch);
        cmdSave = (Button)findViewById(R.id.cmdSave);
        cmdSavedSearches = (Button)findViewById(R.id.cmdSavedSearches);
        txtSearch = (EditText)findViewById(R.id.txtSearch);
        
        cmdSave.setEnabled(false);
        
        cmdSearch.setOnClickListener(new Button.OnClickListener() { 
        	public void onClick (View v) { search(); }});

        cmdSave.setOnClickListener(new Button.OnClickListener() {
           public void onClick(View v) {saveSearch();}
        });

        cmdSavedSearches.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {openSavedSearches();}
        });

        if (this.getIntent() != null && this.getIntent().getExtras() != null) {
            String searchText = this.getIntent().getExtras().getString("searchText");
            if (searchText != null) {
                txtSearch.setText(searchText);
                search();
            }
        }
    }

    private void saveSearch() {
        TwitterDatabaseHelper dbHelper = new TwitterDatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("searchText", txtSearch.getText().toString());
        database.insert("History", null, values);

        database.close();

        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Search Twitter");
        alertDialog.setMessage("Search Saved!");
        alertDialog.show();
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

    private void openSavedSearches() {
        Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
        startActivity(intent);
    }
}