package com.solidinvictus.earthdefender.tools;

/**
 * Created by Ali on 15/03/2018.
 */

public abstract class EarthDefenderDataBase {

    private static  int databaseVersion;
    private static  String databaseName;
    private static  String SCORE_TABLENAME;
    private static  String SCORE_FIELD ;
    private static  String databaseCreationQuery;
    private static  String databaseUpdateQuery;

    //-----------------------------------------------------------------------------------
    //Métodos para que usen las clases del juego
    //-----------------------------------------------------------------------------------

    public abstract EarthDefenderScores getBestScore();
    public abstract EarthDefenderScores getCurrentGame(int score);
    public abstract void saveCurrentGame(int score);
    public abstract void endCurrentGame(int score);


    //-----------------------------------------------------------------------------------
    //Métodos para que usen los gestores de bases de datos específicos de cada plataforma
    //-----------------------------------------------------------------------------------

    public EarthDefenderDataBase() {
        databaseVersion = 1;
        databaseName = "EarthDefenderDB";
        SCORE_TABLENAME = "bestScore";
        SCORE_FIELD = "score";
        databaseCreationQuery="CREATE TABLE "+SCORE_TABLENAME+" (" +
                SCORE_FIELD + " INT DEFAULT 0 )";

        databaseUpdateQuery="";
    }

    public static int getDatabaseVersion(){
        return databaseVersion;
    }

    public static String getScoreFieldName(){
        return SCORE_FIELD;
    }

    public static String getDatabaseName(){
        return databaseName;
    }
    public static String getDatabaseCreationQuery(){
        return databaseCreationQuery;
    }
    public static String getDatabaseUpdateQuery(){
        return databaseUpdateQuery;
    }
    public static String getScoreTablename(){
        return SCORE_TABLENAME;
    }


}




