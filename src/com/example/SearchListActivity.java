package com.example;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchListActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlistlayout);

        buildList();
    }

    private void buildList() {
        final ListView lv = (ListView)findViewById(R.id.lstViewHistory);
        ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        TwitterDatabaseHelper databaseHelper = new TwitterDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.query("History", new String[] {"searchText"}, null, null, null, null, "searchText");

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("text", cursor.getString(0));
            myList.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(this, myList, R.layout.searchlistrowlayout,
                new String[] {"text"}, new int[] {R.id.text});
        lv.setAdapter(mSchedule);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>)lv.getItemAtPosition(position);
                String searchText = map.get("text");

                Intent intent = new Intent(SearchListActivity.this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchText", searchText);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        database.close();
    }
}