package com.be.wildanimaldetection.manager;

import android.database.Cursor;

import com.be.wildanimaldetection.MainActivity;

public class SignInManager
{
    public boolean isSigned()
    {
        boolean flag=false;
        Cursor res= MainActivity.dbHelper.getLoginData();

        try{
            if(res!=null)
            {

                flag=res.moveToFirst();

            }
            else{
                flag=false;
            }
            res.close();
        }catch (Exception e)
        {

        }
        return flag;
    }

}
