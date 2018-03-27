package com.eventory.andriod.eventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eventory.andriod.eventory.database.ItemBaseHelper;
import com.eventory.andriod.eventory.database.ItemCursorWrapper;
import com.eventory.andriod.eventory.database.ItemDbSchema.ItemTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Michael on 2/19/2018.
 */

public class Inventory {
    private static Inventory sInventory;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Inventory get(Context context){
        if(sInventory == null)
        {
            sInventory = new Inventory(context);
        }
        return sInventory;
    }

    private Inventory(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();

        ItemCursorWrapper cursor = queryItems(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }

        return items;
    }

    public List<Item> getCurrentUserItems(){
        List<Item> allItems = getItems();
        List<Item> userItems = new ArrayList<>();
        User currentUser = UserBase.getCurrentUser();
        String username = currentUser.getUsername();

        if(username != null) {
            for (int counter = 0; counter < allItems.size(); counter++) {
                Item tempItem = allItems.get(counter);
                String itemUsername = tempItem.getUsername();
                if (itemUsername != null && itemUsername.equals(username)) {
                    userItems.add(tempItem);
                }

            }
        }

        return userItems;
    }

    public Item getItem(UUID id){
        ItemCursorWrapper cursor = queryItems(
                ItemTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getItem();
        }finally {
            cursor.close();
        }
    }

    public void addItem(Item item){
        ContentValues values = getContentValues(item);

        mDatabase.insert(ItemTable.NAME,null,values);
    }

    public void updateItem (Item item){
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);
        mDatabase.update(ItemTable.NAME,values,
                ItemTable.Cols.UUID + "= ?",
                new String[]{uuidString});
    }

    public void deleteItem(Item item){
        mDatabase.delete(ItemTable.NAME,ItemTable.Cols.UUID + "= ?",new String[]{item.getId().toString()});
    }

    private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ItemTable.NAME,
                null,//null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ItemCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Item item){
        ContentValues values = new ContentValues();
        values.put(ItemTable.Cols.UUID,item.getId().toString());
        values.put(ItemTable.Cols.ITEM_NAME,item.getName());
        values.put(ItemTable.Cols.QUANTITY,item.getQuantity());
        values.put(ItemTable.Cols.DATE,item.getDate().getTime());
        values.put(ItemTable.Cols.PRICE,item.getPrice());
        values.put(ItemTable.Cols.USERNAME,item.getUsername());

        return values;
    }
}
