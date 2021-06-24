package edu.utep.cs.cs4330.textdash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class dbhelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "scoreDB";
    private static final String TABLE_SCORES = "Scores";
    private static final String KEY_ID = "id";
    private static final String KEY_SCORE = "score";
    private static final String KEY_NAME = "name";
    public dbhelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + KEY_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }
    //when adding a new score to the database and list
    void addScore(String score, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, score);
        values.put(KEY_NAME, name);
        long newRowId = db.insert(TABLE_SCORES, null, values);
        db.close();
    }
    //used when checking listview
    public ArrayList<HashMap<String, String>> GetScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> scoreList = new ArrayList<>();
        String query = "SELECT score, name FROM " + TABLE_SCORES;
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()) {
            HashMap<String, String> score = new HashMap<>();
            score.put("score", cursor.getString(cursor.getColumnIndex(KEY_SCORE)));
            score.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            scoreList.add(score);
        }
        return scoreList;
    }
}