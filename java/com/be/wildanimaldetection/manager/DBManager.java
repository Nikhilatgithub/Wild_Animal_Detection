package com.be.wildanimaldetection.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WildAnimalDetect.db";
    private static final String TABLE_L1 = "userlogin";

    public DBManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_l1_TABLE = "CREATE TABLE " + TABLE_L1 + "(email TEXT)";
        db.execSQL(CREATE_l1_TABLE);
        db.execSQL("CREATE TABLE catlog (msg TEXT,id TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L1);
        db.execSQL("DROP TABLE IF EXISTS catlog");
        onCreate(db);
    }

    public boolean insertL1(String email)								// Insert into login;
    {
        ContentValues values = new ContentValues();
        values.put("email",email);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_L1, null, values);
        db.close();
        return true;
    }
    public Cursor getLoginData()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_L1,null);


        return res;

    }

    public Cursor getCatData()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from catlog",null);

        return res;

    }

    public boolean insertCatlog(String msg,String user,String id)								// Insert into login;
    {
        ContentValues values = new ContentValues();
        values.put("msg",msg);
        values.put("id",id);
        values.put("user",user);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("catlog", null, values);
        db.close();
        return true;
    }

    public boolean deleteRowById(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] arg = {id};
        int result = db.delete("catlog","id_no = ?",arg);
        db.close();
        return result!=0;
    }

}
