package com.example.nav_when;

/*import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db = SQLiteDatabase.openOrCreateDatabase("365.db", , null);

        } catch (Exception e) {
//
        }


    }

    // DB가 있나 체크하기
    public boolean isCheckDB(Context mContext){
        String filePath = "/data/data/" + MiniAppConstants.PACKAGE_NAME + "/databases/" + MiniAppConstants.DB_NAME;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    } // DB를 복사하기
    // assets의 /db/xxxx.db 파일을 설치된 프로그램의 내부 DB공간으로 복사하기

    public void copyDB(Context mContext){
        Log.d("MiniApp", "copyDB");
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/" + MiniAppConstants.PACKAGE_NAME + "/databases";
        String filePath = "/data/data/" + MiniAppConstants.PACKAGE_NAME + "/databases/" + MiniAppConstants.DB_NAME; File folder = new File(folderPath);
        File file = new File(filePath); FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = manager.open("db/" + MiniAppConstants.DB_NAME);
            BufferedInputStream bis = new BufferedInputStream(is);
            if (folder.exists()) {

            }else{ folder.mkdirs();

            }

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.flush();
            bos.close();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("ErrorMessage : ", e.getMessage());
        } }

}*/

