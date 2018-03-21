package com.eventory.andriod.eventory.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.eventory.andriod.eventory.Item;

import java.util.Date;
import java.util.UUID;

import static com.eventory.andriod.eventory.database.ItemDbSchema.*;

/**
 * Created by Michael on 3/1/2018.
 */

public class ItemCursorWrapper extends CursorWrapper {

    public ItemCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Item getItem(){
        String uuidString = getString(getColumnIndex(ItemTable.Cols.UUID));
        String name = getString(getColumnIndex(ItemTable.Cols.ITEM_NAME));
        int quantity = getInt(getColumnIndex(ItemTable.Cols.QUANTITY));
        long date = getLong(getColumnIndex(ItemTable.Cols.DATE));
        double price = getDouble(getColumnIndex(ItemTable.Cols.PRICE));


        Item item = new Item(UUID.fromString(uuidString));
        item.setName(name);
        item.setQuantity(quantity);
        item.setDate(new Date(date));
        item.setPrice(price);

        return item;
    }
}

