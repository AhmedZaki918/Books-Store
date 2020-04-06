package com.example.android.booksstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.booksstore.data.BookContract.BookEntry;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {


    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        final TextView qtyTextView = view.findViewById(R.id.quantity);

        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRODUCT);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int qtyColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);
        final String bookQty = cursor.getString(qtyColumnIndex);

        // If the book name is empty string or null, then use some default text
        // that says "Unknown", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookName)) {
            bookName = context.getString(R.string.unknown_book);
        }

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        priceTextView.setText(bookPrice);
        qtyTextView.setText(bookQty);

        // Find reference for that view
        Button sale = view.findViewById(R.id.sale);

        //Get the current product URI
        int productIdColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int productId = cursor.getInt(productIdColumnIndex);
        final Uri currentProductUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, productId);

        //Set onClick Listener on sale button
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productQuantityInteger = Integer.parseInt(bookQty);

                //Decrease the quantity of the product by 1
                if (productQuantityInteger > 0) {
                    productQuantityInteger -= 1;
                } else {
                    productQuantityInteger = 0;
                }
                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_BOOK_QUANTITY, productQuantityInteger);

                //Pass the Content Resolver the updated product quantity
                int rowsAffected = context.getContentResolver()
                        .update(currentProductUri, values, null, null);
            }
        });
    }
}