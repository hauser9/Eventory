package com.eventory.andriod.eventory.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.eventory.andriod.eventory.User;
import com.eventory.andriod.eventory.database.UserDbSchema.UserTable;

/**
 * Created by Michael on 3/25/2018.
 */

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public User getUser(){
        String username = getString(getColumnIndex(UserTable.Cols.USERNAME));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));
        String name = getString(getColumnIndex(UserTable.Cols.FULL_NAME));
        String email = getString(getColumnIndex(UserTable.Cols.EMAIL));

        User user = new User(username);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);

        return user;
    }
}
