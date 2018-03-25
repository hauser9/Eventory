package com.eventory.andriod.eventory.database;

/**
 * Created by Michael on 3/25/2018.
 */

public class UserDbSchema {
    public static final class UserTable {
        public static final String Name = "users";

        public static final class Cols{
            public static final String USERNAME = "username";
            public static final String FULL_NAME = "fullname";
            public static final String PASSWORD = "password";
            public static final String EMAIL = "EMAIL";
        }
    }
}
