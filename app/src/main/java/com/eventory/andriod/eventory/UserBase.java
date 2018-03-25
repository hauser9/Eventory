package com.eventory.andriod.eventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eventory.andriod.eventory.database.UserBaseHelper;
import com.eventory.andriod.eventory.database.UserCursorWrapper;
import com.eventory.andriod.eventory.database.UserDbSchema;
import com.eventory.andriod.eventory.database.UserDbSchema.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 3/25/2018.
 */

public class UserBase {

    private static UserBase sUserBase;
    private static User sCurrentUser;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserBase get(Context context){
        if(sUserBase == null){
            sUserBase = new UserBase(context);
        }

        return sUserBase;
    }

    private UserBase(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new UserBaseHelper(mContext).getWritableDatabase();
    }

    public void addUser(User user){
        ContentValues values = getContentValues(user);

        mDatabase.insert(UserTable.Name,null,values);
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();

        UserCursorWrapper cursor = queryUsers(null,null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return users;
    }

    public void updateUser(User user){
        String username = user.getUsername();
        ContentValues values = getContentValues(user);

        mDatabase.update(UserTable.Name,values, UserTable.Cols.USERNAME + "= ?",new String[]{username});
    }

    public User getUser(String username){
        UserCursorWrapper cursor = queryUsers(UserTable.Cols.USERNAME + " = ?",new String[]{username});

        try{
            if(cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUser();
        }finally{
            cursor.close();
        }
    }

    public static void setCurrentUser(User user){
        sCurrentUser = user;
    }

    public static User getCurrentUser(){
        return sCurrentUser;
    }

    private static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.USERNAME, user.getUsername());
        values.put(UserTable.Cols.PASSWORD, user.getPassword());
        values.put(UserTable.Cols.FULL_NAME, user.getName());
        values.put(UserTable.Cols.EMAIL,user.getEmail());

        return values;
    }

    private UserCursorWrapper queryUsers(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                UserTable.Name,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new UserCursorWrapper(cursor);
    }
}
