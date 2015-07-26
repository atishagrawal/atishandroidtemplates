package day.tab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Stores information used when connecting to 
 * the database and when updating the information 
 * stored in the database.
 */
public class NotesDbAdapter {

	/**Used for database title field*/
    public static final String KEY_TITLE = "title";
    /**Used for database body field*/
    public static final String KEY_BODY = "body";
    /**Used for the row id in the database*/
    public static final String KEY_ROWID = "_id";
    public static final String KEY_DAY = "day";

    /**Stores tag for database log*/
    private static final String TAG = "NotesDbAdapter";
    /**Creates database helper to be used*/
    private DatabaseHelper mDbHelper;
    /**Creates database to be used*/
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table notes (_id integer primary key autoincrement, "
        + "title text not null, body text not null, day text not null);";
    
    private static String DATABASE_QUERY="";
    private static String DATABASE_NOTE="";

    /**Stores the database name*/
    private static final String DATABASE_NAME = "data";
    /**Stores the table name*/
    private static final String DATABASE_TABLE = "notes";
    /**Stores the current version of the database*/
    private static final int DATABASE_VERSION = 100;
    /**Provides the context to be used with the database*/
    private final Context mCtx;

    /**Contains information about the database itself.*/
    private static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Constructs the database helper and gets the name and version of the database.
         * 
         * @param conext The context to work in
         */
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        
        /**Called when the database is created for the first time
         * @param db The database*/
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        /**Writes to the log when the database is updated
         * 
         * @param db The database itself.
         * @param oldVersion The old version of the database.
         * @param newVersion The new version of the database.
         * 
         * */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public NotesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public NotesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    /**Closes the connection to the database*/
    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public long createNote(String title, String body, String day) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_DAY, day);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteNote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllNotes(String day) {

        //return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                //KEY_BODY}, null, null, null, null, null);
    	DATABASE_QUERY="select * from notes where day ='"+day+"'";
    	return mDb.rawQuery(DATABASE_QUERY,null);
    	
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchNote(String day, String time) throws SQLException {
    	
    	DATABASE_NOTE="select * from notes where day ='"+day+"' and title='"+time+"'";
        /*Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_TITLE, KEY_BODY, KEY_DAY}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);*/
    	
    	Cursor mCursor = mDb.rawQuery(DATABASE_NOTE, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateNote(long rowId, String title, String body, String day) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);
        args.put(KEY_DAY, day);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
