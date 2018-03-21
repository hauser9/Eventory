package com.eventory.andriod.eventory.database;

/**
 * Created by Michael on 3/1/2018.
 */

public class ItemDbSchema
{
    public static final class ItemTable{
        public static final String NAME = "item";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String ITEM_NAME = "name";
            public static final String QUANTITY = "quantity";
            public static final String DATE = "date";
            public static final String PRICE = "price";
        }
    }
}
