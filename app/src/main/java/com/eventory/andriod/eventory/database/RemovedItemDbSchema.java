package com.eventory.andriod.eventory.database;

/**
 * Created by Michael on 3/22/2018.
 */

public class RemovedItemDbSchema {

    public static final class RemovedItemTable{
        public static final String NAME = "removedItems";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String ITEM_NAME = "itemName";
            public static final String QUANTITY = "quantity";
            public static final String TOTAL_PRICE = "total_price";
            public static final String DATE = "date";
            public static final String WASTE = "waste";
        }
    }
}

