package com.be.wildanimaldetection.manager;

import static com.be.wildanimaldetection.MainActivity.dbHelper;

import android.database.Cursor;


public class Catlog
{
    public Cursor getRecord()
    {
        Cursor catlog= dbHelper.getCatData();
        return catlog;
    }

    public boolean insertInCatlog(String msg,String user, String id)
    {
        boolean inserted=false;
        try {
            inserted=dbHelper.insertCatlog(msg,user, id);
        } catch (Exception e) {
            inserted=false;
            e.printStackTrace();
        }
        return inserted;
    }




    public boolean deleteRow(String id)
    {
        boolean delete=false;
        try {
            delete= dbHelper.deleteRowById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete;
    }


}
