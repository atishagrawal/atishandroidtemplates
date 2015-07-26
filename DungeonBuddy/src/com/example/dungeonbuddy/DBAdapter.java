package com.example.dungeonbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
    static final String DATABASE_NAME = "buddydb";
    static final String DATABASE_TABLE_BUDDY = "buddy";
    static final String DATABASE_TABLE_EQUIP = "equip";
    static final int DATABASE_VERSION = 2;
    
    static final String KEY_ROWID = "buddyid";
    static final String KEY_NAME = "name";
    static final String KEY_CLASS = "class";
    static final String KEY_CREATEDDATE = "createdDate";
    static final String KEY_MIGHT = "might";
    static final String KEY_MAGIC = "magic";
    static final String KEY_MARKS = "marks";
    static final String KEY_MONEY = "wallet";
    static final String KEY_TIER = "tierclass";
    static final String TAG = "DBAdapter";


    static final String DATABASE_CREATE =
        "CREATE TABLE Buddy ( buddyid INTEGER PRIMARY KEY, name TEXT, class TEXT, createdDate TEXT, might NUMERIC, magic NUMERIC, marksman NUMERIC, wallet NUMERIC, tierclass NUMERIC );";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Buddy");
            db.execSQL("DROP TABLE IF EXISTS Equip");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }

    //Save a new buddy to the buddy table
    public long saveBuddy(String name, String buddyclass, String createdDate, String might, String magic, String marks, String money, String tier ) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_CLASS, buddyclass);
        initialValues.put(KEY_CREATEDDATE, createdDate);
        initialValues.put(KEY_MIGHT, might);
        initialValues.put(KEY_MAGIC, magic);
        initialValues.put(KEY_MARKS, marks);
        initialValues.put(KEY_MONEY, money);
        initialValues.put(KEY_TIER, tier);
        return db.insert(DATABASE_TABLE_BUDDY, null, initialValues);
    }

    //Clear a buddy save slot
    public boolean deleteBuddy(long rowId) 
    {
        return db.delete(DATABASE_TABLE_BUDDY, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //Get all saved buddies
    public Cursor getAllBuddies()
    {
        return db.query(DATABASE_TABLE_BUDDY, new String[] {KEY_ROWID, KEY_NAME, KEY_CLASS,
                KEY_CREATEDDATE, KEY_MIGHT, KEY_MAGIC, KEY_MARKS, KEY_MONEY, KEY_TIER}, null, null, null, null, null);
    }

    //retrieves a particular buddy
    public Cursor getBuddy(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_BUDDY, new String[] {KEY_ROWID, KEY_NAME, KEY_CLASS,
                        KEY_CREATEDDATE, KEY_MIGHT, KEY_MAGIC, KEY_MARKS, KEY_MONEY, KEY_TIER}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //updates a buddy
    public boolean updateBuddy(long rowId, String name, String buddyclass, String createdDate, String might, String magic, String marks, String money, String tier ) 
    {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_CLASS, buddyclass);
        values.put(KEY_CREATEDDATE, createdDate);
        values.put(KEY_MIGHT, might);
        values.put(KEY_MAGIC, magic);
        values.put(KEY_MARKS, marks);
        values.put(KEY_MONEY, money);
        values.put(KEY_TIER, tier);
        return db.update(DATABASE_TABLE_BUDDY, values, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
