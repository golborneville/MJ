package com.example.nav_when;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

public class DBPerson extends SQLiteOpenHelper {

    public static final String ROOT_DIR = "/data/data/com.example.nav_when/";
    private static String DATABASE_NAME = "RealHistory.db";
    // public static final String DATABASE_NAME_PERSON = "historical_people.db";
    String DB_PATH = null;
    private static String DB_NAME = "RealHistory.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBPerson(Context context) {
        super(context, DATABASE_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }


    public static void initialize_PERSON(Context ctx) { // check
        File folder = new File(ROOT_DIR + "databases");
        folder.mkdirs();
        File outfile = new File(ROOT_DIR +"databases/"+ DATABASE_NAME);
        //if (outfile.length() <= 0) {
        /*
         * 테스트중에는 DB 수정이 자주 일어나므로 매 실행때마다 DB 초기화!!!!
         * */
        AssetManager assetManager = ctx.getResources().getAssets();
        try {
            InputStream is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
            long filesize = is.available();
            byte [] tempdata = new byte[(int)filesize];
            is.read(tempdata); is.close();
            outfile.createNewFile();
            FileOutputStream fo = new FileOutputStream(outfile);
            fo.write(tempdata);
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}
    }
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("TIDEPRED", null, null, null, null, null, null);
    }

}
