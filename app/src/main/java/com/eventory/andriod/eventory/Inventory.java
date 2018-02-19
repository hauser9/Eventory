package com.eventory.andriod.eventory;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Michael on 2/19/2018.
 */

public class Inventory {
    private static Inventory sInventory;

    public static Inventory get(Context context){
        if(sInventory == null)
        {
            sInventory = new Inventory(context);
        }
        return sInventory;
    }

    private Inventory(Context context){
        
    }
}
