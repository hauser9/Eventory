package com.eventory.andriod.eventory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eventory.andriod.eventory.database.ItemDbSchema.ItemTable;

/**
 * Created by Michael on 3/1/2018.
 */

public class ItemBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "itemBase.db";

    public ItemBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+ ItemTable.NAME + "(" +
            " _id integer primary key autoincrement," +
            ItemTable.Cols.UUID + "," +
            ItemTable.Cols.ITEM_NAME + "," +
            ItemTable.Cols.QUANTITY + "," +
            ItemTable.Cols.DATE + "," +
            ItemTable.Cols.PRICE + ","+
            ItemTable.Cols.USERNAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
