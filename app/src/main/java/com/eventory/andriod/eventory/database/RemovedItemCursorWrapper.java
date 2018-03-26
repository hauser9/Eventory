package com.eventory.andriod.eventory.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.eventory.andriod.eventory.RemovedItem;
import com.eventory.andriod.eventory.database.RemovedItemDbSchema.RemovedItemTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Michael on 3/22/2018.
 */

public class RemovedItemCursorWrapper extends CursorWrapper {

    public RemovedItemCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public RemovedItem getRemovedItem(){
        String uuidString = getString(getColumnIndex(RemovedItemTable.Cols.UUID));
        String name = getString(getColumnIndex(RemovedItemTable.Cols.ITEM_NAME));
        int quantity = getInt(getColumnIndex(RemovedItemTable.Cols.QUANTITY));
        double totalPrice = getDouble(getColumnIndex(RemovedItemTable.Cols.TOTAL_PRICE));
        long date = getLong(getColumnIndex(RemovedItemTable.Cols.DATE));
        int waste = getInt(getColumnIndex(RemovedItemTable.Cols.WASTE));
        String username = getString(getColumnIndex(RemovedItemTable.Cols.USERNAME));

        RemovedItem removedItem = new RemovedItem(UUID.fromString(uuidString));
        removedItem.setName(name);
        removedItem.setQuantity(quantity);
        removedItem.setPrice(totalPrice);
        removedItem.setDate(new Date(date));
        removedItem.setWaste(waste != 0);
        removedItem.setUsername(username);

        return removedItem;

    }
}
