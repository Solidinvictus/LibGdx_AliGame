package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.solidinvictus.earthdefender.tools.EarthDefenderDataBase;
import com.solidinvictus.earthdefender.tools.EarthDefenderScores;

/**
 * Created by Ali on 16/03/2018.
 */

public class DataBaseAndroid extends EarthDefenderDataBase {
    private DatabaseHelper openHelper;
    private SQLiteDatabase database;
    public static final String DATABASE_NAME = "earthdefender.db";
    public static final String TABLE_NAME = "score_table";
    public static final String COL_1 = getScoreFieldName();
    int highScore = 5000;

    /**
     * Constructor
     *
     * @param context
     */
    public DataBaseAndroid(Context context) {
        super();
        openHelper = new DatabaseHelper(context);
        this.database = openHelper.getWritableDatabase();


    }

    @Override
    public EarthDefenderScores getBestScore() {
        Preferences prefs = Gdx.app.getPreferences(DATABASE_NAME );
        this.highScore = prefs.getInteger(TABLE_NAME,highScore);

        return new EarthDefenderScores(highScore);
    }

    @Override
    public EarthDefenderScores getCurrentGame(int score) {

        //String[] fields={getScoreFieldName()};
        String[] fields = {getScoreFieldName()};
        String selection = this.COL_1+" =?";
        Cursor c = database.query(getScoreTablename(), fields, selection, null, null, null, null);
        if (c.getCount() == 0) {
            //Si no se obtiene un juego actual, se crea uno nuevo con end Date a null
            Log.d("Getting Current Game", "New Game was created");
            ContentValues cv = new ContentValues();

            cv.put(getScoreFieldName(), ""+score);
            database.insert(getScoreTablename(), null, cv);
            return new EarthDefenderScores(score);
        }

        c.moveToFirst();

        return new EarthDefenderScores(c.getInt(1));

    }

    @Override
    public void endCurrentGame(int score) {
        EarthDefenderScores currentGame = getCurrentGame(score);
        currentGame.setScore(score);

        Log.d("ENDING GAME WITH SCORE", Integer.toString(score));


        ContentValues values = new ContentValues();
        values.put(getScoreFieldName(), currentGame.getScore());

    }

    @Override
    public void saveCurrentGame(int score) {
        EarthDefenderScores currentGame=getCurrentGame(score);
        currentGame.setScore(score);
        Log.d("SAVING GAME WITH SCORE",Integer.toString(score));

        ContentValues values = new ContentValues();
        values.put(getScoreFieldName(),currentGame.getScore());

    }
}
