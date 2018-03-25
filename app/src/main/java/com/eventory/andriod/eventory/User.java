package com.eventory.andriod.eventory;

import javax.xml.namespace.NamespaceContext;

/**
 * Created by Michael on 3/25/2018.
 */

public class User {

    private String mUsername;
    private String mPassword;
    private String mName;
    private String mEmail;

    public User(String username)
    {
        //get list of usernames and check if it is unique
        mUsername = username;
    }

    public void setPassword(String password){
        mPassword = password;
    }

    public void setName(String name){
        mName = name;
    }

    public void setEmail(String email){
        mEmail = email;
    }

    public String getName(){
        return mName;
    }

    public String getPassword(){
        return mPassword;
    }

    public String getUsername(){
        return mUsername;
    }

    public String getEmail(){
        return mEmail;
    }
}
