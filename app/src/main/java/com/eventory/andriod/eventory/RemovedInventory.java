package com.eventory.andriod.eventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eventory.andriod.eventory.database.RemovedItemBaseHelper;
import com.eventory.andriod.eventory.database.RemovedItemCursorWrapper;
import com.eventory.andriod.eventory.database.RemovedItemDbSchema;
import com.eventory.andriod.eventory.database.RemovedItemDbSchema.RemovedItemTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Michael on 3/22/2018.
 */

public class RemovedInventory {

    private static RemovedInventory sRemovedInventory;

    private List<RemovedItem> mRemovedItems;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static RemovedInventory get(Context context){
        if(sRemovedInventory == null){
            sRemovedInventory = new RemovedInventory(context);
        }
        return sRemovedInventory;
    }

    public RemovedItem getRemovedItem(UUID id){
        RemovedItemCursorWrapper cursor = queryRemovedItems(
                RemovedItemTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getRemovedItem();
        }finally {
            cursor.close();
        }
    }

    public void addRemovedItem(RemovedItem removedItem){
        ContentValues values = getContentValues(removedItem);

        mDatabase.insert(RemovedItemTable.NAME,null, values);
    }

    private RemovedInventory(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new RemovedItemBaseHelper(mContext).getWritableDatabase();
    }

    private RemovedItemCursorWrapper queryRemovedItems(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                RemovedItemTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new RemovedItemCursorWrapper(cursor);
    }

    public void updateRemovedItem (RemovedItem item){
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);
        mDatabase.update(RemovedItemTable.NAME,values,
                RemovedItemTable.Cols.UUID + "= ?",
                new String[]{uuidString});
    }

    public void deleteRemovedItem(RemovedItem item){
        mDatabase.delete(RemovedItemTable.NAME,RemovedItemTable.Cols.UUID + "= ?",new String[]{item.getId().toString()});
    }


    public List<RemovedItem> getRemovedItems(){
        List<RemovedItem> removedItems = new ArrayList<>();

        RemovedItemCursorWrapper cursor = queryRemovedItems(null,null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                removedItems.add(cursor.getRemovedItem());
                cursor.moveToNext();
            }
            }
            finally{
                cursor.close();
            }

            return removedItems;
    }

    public static ContentValues getContentValues(RemovedItem removedItem){
        ContentValues values = new ContentValues();
        values.put(RemovedItemTable.Cols.UUID,removedItem.getId().toString());
        values.put(RemovedItemTable.Cols.ITEM_NAME,removedItem.getName());
        values.put(RemovedItemTable.Cols.QUANTITY,removedItem.getQuantity());
        values.put(RemovedItemTable.Cols.TOTAL_PRICE,removedItem.getPrice());
        values.put(RemovedItemTable.Cols.DATE,removedItem.getDate().getTime());
        values.put(RemovedItemTable.Cols.WASTE,removedItem.getWaste()? 1 : 0);

        return values;
    }

    public static List<RemovedItem> inDateRange(List<RemovedItem> removedItems, Date startDate, Date endDate){
        int size = removedItems.size();
        List<RemovedItem> inRangeList = new ArrayList<RemovedItem>();
        long start,end,item;
        for(int counter = 0; counter < size; counter ++)
        {
            RemovedItem tempRemovedItem = removedItems.get(counter);
            item = tempRemovedItem.getDate().getTime();
            start = startDate.getTime();
            end = endDate.getTime();
            if(item >= start && item <= end)
            {
                inRangeList.add(tempRemovedItem);
            }

        }
        return inRangeList;
    }
}
