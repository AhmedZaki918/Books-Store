package com.example.android.booksstore.data;

import android.provider.BaseColumns;

public class BookContract {

    public static abstract class BookEntry implements BaseColumns {

        // Name of the table
        public static final String TABLE_NAME = "books";

        // Names of the columns of the table
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_PRODUCT = "product";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_SUPPLIER = "supplier";
        public static final String COLUMN_BOOK_PHONE = "phone";
    }
}