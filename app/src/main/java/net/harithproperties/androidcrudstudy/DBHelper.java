package net.harithproperties.androidcrudstudy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	//define all the constants
    static String DATABASE_NAME="workoutdirektori";
    public static final String TABLE_NAME="workout";
    //these are the lit of fields in the table
    public static final String EXERCISEID="exerciseid";
    public static final String EXERCISE="exercise";
    public static final String WEIGHT="weight";
    public static final String REP="rep";
    public static final String DATE="date";
    public static final String TIME="time";


    public DBHelper(Context context) {
    	//create the database
        super(context, DATABASE_NAME, null, 1);
       
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	//create the table
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+EXERCISEID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+EXERCISE+" TEXT, "+WEIGHT+" TEXT, "+REP+" TEXT,"+DATE+" TEXT,"+TIME+" TEXT)";
        db.execSQL(CREATE_TABLE);
        //populate dummy data
        db.execSQL("INSERT INTO workout (exerciseid, exercise, weight, rep, date, time) VALUES('1', 'Push up', '5','6', '7/7/2017','14:56');");
        db.execSQL("INSERT INTO workout (exerciseid, exercise, weight, rep, date, time) VALUES('2', 'pull up', '5','6', '7/7/2017','14:57');");
        db.execSQL("INSERT INTO workout (exerciseid, exercise, weight, rep, date, time) VALUES('3', 'sit up', '5','6', '9/7/2017','14:58');");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	//onUpgrade remove the existing table, and recreate and populate new data
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public void delete(int anInt) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,EXERCISEID+"=?",new String[]{String.valueOf(anInt)});
        db.close();
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM "+TABLE_NAME, null);
        return c;
    }


}


