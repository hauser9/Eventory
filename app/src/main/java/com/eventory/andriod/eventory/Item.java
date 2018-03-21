package com.eventory.andriod.eventory;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Michael on 2/19/2018.
 */

public class Item {
    private UUID mId;
    private String mName;
    private int mQuantity;
    private double mPrice;
    private Date mDate;

    public Item(){
        this(UUID.randomUUID());
    }

    public Item(UUID id){
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

    public void setPrice(double price){mPrice = price;}

    public double getPrice(){ return mPrice;}



}
