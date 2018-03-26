package com.eventory.andriod.eventory;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Michael on 3/22/2018.
 */

public class RemovedItem {
    private UUID mId;
    private String mName;
    private int mQuantity;
    private double mTotalPrice;
    private Date mDate;
    private boolean mWaste;
    private String mUsername;

    public RemovedItem(){
        this(UUID.randomUUID());
    }

    public RemovedItem(UUID id){
        mId = id;
        mDate = new Date();
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

    public void setDate(Date date){ mDate = date;}

    public Date getDate(){ return mDate;}

    public void setPrice(double price){mTotalPrice = price;}

    public double getPrice(){ return mTotalPrice;}

    public void setWaste(boolean waste){
        mWaste = waste;
    }

    public boolean getWaste(){
        return mWaste;
    }

    public String getUsername(){
        return mUsername;
    }

    public void setUsername( String username){
        mUsername = username;
    }


}

