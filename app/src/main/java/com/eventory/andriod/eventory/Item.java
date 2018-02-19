package com.eventory.andriod.eventory;

import java.util.UUID;

/**
 * Created by Michael on 2/19/2018.
 */

public class Item {
    private UUID mId;
    private String mName;
    private int mQuantity;

    public Item(){
        mId = UUID.randomUUID();
    }

    public UUID getId()
    {
        return mId;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public void setQuantity(int quantity){
        mQuantity = quantity;
    }
}
