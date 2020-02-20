package com.example.notesapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseNotes extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NotesData";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMEOFNOTE = "nameofnotes";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";


    public DatabaseNotes(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID +" INTEGER NOT NULL CONSTRAINT person_pk PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAMEOFNOTE + " varchar(200) NOT NULL," +
                COLUMN_DESCRIPTION + " varchar(200) NOT NULL," +
                COLUMN_DATE + " varchar(200) NOT NULL," +
                COLUMN_TIME + " varchar(200) NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);

    }

    boolean addNote(String nameofnote,String description,String date, String time){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAMEOFNOTE, nameofnote);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);

        return   sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;

    }

    Cursor getAllNote(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


    boolean updateNote(int id , String nameofnote,String description,String date, String time) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMEOFNOTE,nameofnote);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);

        //this method returns the number of rows effected

        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;

    }
    boolean deleteNote(int id){

        SQLiteDatabase sqLiteDatabase  = getWritableDatabase();

        //the delete method returns the  number of rows effected
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID +"=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Notes getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {COLUMN_ID,COLUMN_NAMEOFNOTE,COLUMN_DESCRIPTION,COLUMN_DATE,COLUMN_TIME};
       Cursor cursor=  db.query(TABLE_NAME,query,COLUMN_ID +"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Notes(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
   }
//
}
