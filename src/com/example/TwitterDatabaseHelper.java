package com.example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TwitterDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TwitterSearcher";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_TABLE_1 = "CREATE TABLE History (searchText text not null);";

    public TwitterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_TABLE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS History");
        onCreate(sqLiteDatabase);
    }
}
