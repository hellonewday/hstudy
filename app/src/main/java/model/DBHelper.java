package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private int dbversion = 1;
    private static final String dbname = "Hstudy";
    private static final String tabledb = "Account";
    private static final String id_column = "id";
    private static final String username_column = "username";
    private static final String password_column = "password";

    public DBHelper(Context context){
        super(context,dbname,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
