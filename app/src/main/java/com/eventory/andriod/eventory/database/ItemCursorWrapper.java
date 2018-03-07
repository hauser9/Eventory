package com.eventory.andriod.eventory.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.eventory.andriod.eventory.Item;

import java.util.UUID;

/**
 * Created by Michael on 3/1/2018.
 */

public class ItemCursorWrapper extends CursorWrapper {

    public ItemCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Item getItem(){
        String uuidString = getString(getColumnIndex(ItemDbSchema.ItemTable.Cols.UUID));
        String name = getString(getColumnIndex(ItemDbSchema.ItemTable.Cols.NAME));
        int quantity = getInt(getColumnIndex(ItemDbSchema.ItemTable.Cols.QUANTITY));

        Item item = new Item(UUID.fromString(uuidString));
        item.setName(name);
        item.setQuantity(quantity);

        return item;
    }
}

