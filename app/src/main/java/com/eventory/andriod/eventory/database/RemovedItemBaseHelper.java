package com.eventory.andriod.eventory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eventory.andriod.eventory.RemovedItem;
import com.eventory.andriod.eventory.database.RemovedItemDbSchema.RemovedItemTable;

/**
 * Created by Michael on 3/22/2018.
 */

public class RemovedItemBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "removedItemBase.db";

    public RemovedItemBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ RemovedItemTable.NAME + "(" +
        "_id integer primary key autoincrement," +
        RemovedItemTable.Cols.UUID + "," +
        RemovedItemTable.Cols.ITEM_NAME + "," +
        RemovedItemTable.Cols.QUANTITY + "," +
        RemovedItemTable.Cols.TOTAL_PRICE + "," +
        RemovedItemTable.Cols.DATE + "," +
        RemovedItemTable.Cols.WASTE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
