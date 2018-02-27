package com.eventory.andriod.eventory;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Michael on 2/19/2018.
 */

public class Inventory {
    private static Inventory sInventory;
    private List<Item> mItems;

    public static Inventory get(Context context){
        if(sInventory == null)
        {
            sInventory = new Inventory(context);
        }
        return sInventory;
    }

    private Inventory(Context context){
        mItems = new ArrayList<Item>();
    }

    public List<Item> getItems(){
        return mItems;
    }

    public Item getItem(UUID id){
        for(Item item : mItems){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item){
        mItems.add(item);
    }
}
