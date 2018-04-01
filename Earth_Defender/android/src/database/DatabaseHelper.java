package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ali on 15/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
    super(context, DataBaseAndroid.getDatabaseName(), null, DataBaseAndroid.getDatabaseVersion());
    /*public static final String DATABASE_NAME ="earthdefender.db";
    public static final String TABLE_NAME ="score_table";
    public static final String COL_1 ="Best_score";
    public DatabaseHelper(Context context) {

        SQLiteDatabase db = this.getWritableDatabase();*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table "+TABLE_NAME+" (BEST_SCORE INTEGER)");
        db.execSQL(DataBaseAndroid.getDatabaseCreationQuery());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL(DataBaseAndroid.getDatabaseUpdateQuery());
    }
}
