package com.eventory.andriod.eventory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eventory.andriod.eventory.database.UserDbSchema.UserTable;

/**
 * Created by Michael on 3/25/2018.
 */

public class UserBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ UserTable.Name + "(" +
                "_id integer primary key autoincrement," +
                UserTable.Cols.USERNAME + "," +
                UserTable.Cols.PASSWORD + "," +
                UserTable.Cols.EMAIL + "," +
                UserTable.Cols.FULL_NAME + ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
