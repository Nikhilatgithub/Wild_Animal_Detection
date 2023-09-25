package com.be.wildanimaldetection.services;

import static com.be.wildanimaldetection.MainActivity.dbHelper;

import android.database.Cursor;

public class DatabaseFetch
{
    Cursor catlog= dbHelper.getCatData();

}
