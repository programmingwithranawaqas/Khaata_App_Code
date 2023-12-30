package com.example.sql_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KhaataDB {

    public static final String KEY_ROW_ID = "_id";
    public static final String KEY_ROW_TITLE = "_title";
    public static final String KEY_ROW_DESCRIPTION = "_description";
    public static final String KEY_ROW_DATE = "_date";
    public static final String KEY_ROW_PRICE = "_price";

    private final String DATABASE_NAME = "KhaataDB";
    private final String DATABASE_TABLE = "KhaataTable";
    private final int DATABASE_VERSION = 1;

    public Context ourContext;
    public SQLiteDatabase ourDatabase;
    public DBHelper ourHelper;


    public KhaataDB(Context context)
    {
        ourContext = context;
    }

    public KhaataDB open() throws SQLException
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public void insertKhata(String title, String desc, String date, String price)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROW_TITLE,title);
        cv.put(KEY_ROW_DESCRIPTION, desc);
        cv.put(KEY_ROW_DATE, date);
        cv.put(KEY_ROW_PRICE, price);
        ourDatabase.insert(DATABASE_TABLE, null, cv);
    }
    public void deleteKhata(String rowId)
    {
        ourDatabase.delete(DATABASE_TABLE, KEY_ROW_ID+"=?", new String[]{rowId});
    }

    public void updateKhata(String id, String newTitle, String newdesc, String newDate, String newPrice) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROW_TITLE,newTitle);
        cv.put(KEY_ROW_DESCRIPTION, newdesc);
        cv.put(KEY_ROW_DATE, newDate);
        cv.put(KEY_ROW_PRICE, newPrice);
        ourDatabase.update(DATABASE_TABLE, cv, KEY_ROW_ID+"=?", new String[]{id});
    }

    public String readAllKhaatas() {
        String result = "";
        String []columns = new String[]{KEY_ROW_ID, KEY_ROW_TITLE, KEY_ROW_DESCRIPTION,
        KEY_ROW_DATE, KEY_ROW_PRICE};

        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null, null);

        int i=1;
        int indexId = c.getColumnIndex(KEY_ROW_ID);
        int indexTitle = c.getColumnIndex(KEY_ROW_TITLE);
        int indexDesc = c.getColumnIndex(KEY_ROW_DESCRIPTION);
        int indexDate = c.getColumnIndex(KEY_ROW_DATE);
        int indexPrice = c.getColumnIndex(KEY_ROW_PRICE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result = result +"Khata #. "+ i+"\n"+c.getString(indexTitle)+"\n"
                    +c.getString(indexDesc)+"\n"+c.getString(indexDate)+"\n"
                    +c.getString(indexPrice)+"\n\n";
            i++;
        }
        c.close();
        return result;

    }

    private class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            /*
            CREATE TABLE KhaataTable(
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            _title TEXT NOT NULL,
            _description TEXT NOT NULL,
            _date TEXT NOT NULL,
            _price INTEGER NOT NULL);
             */

            String query = "CREATE TABLE "+DATABASE_TABLE+"("
                    +KEY_ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_ROW_TITLE+" TEXT NOT NULL,"
                    +KEY_ROW_DESCRIPTION+" TEXT NOT NULL,"
                    +KEY_ROW_DATE+" TEXT NOT NULL,"
                    +KEY_ROW_PRICE+" INTEGER NOT NULL);";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }




}
