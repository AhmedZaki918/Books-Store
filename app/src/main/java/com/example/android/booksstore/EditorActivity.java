package com.example.android.booksstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.booksstore.data.BookContract.BookEntry;
import com.example.android.booksstore.data.BookDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {


    /**
     * EditText field to enter the book name
     */
    @BindView(R.id.book_name)
    EditText mProductEditText;

    /**
     * EditText field to enter the book price
     */
    @BindView(R.id.book_price)
    EditText mPriceEditText;

    /**
     * EditText field to enter the quantity of books
     */
    @BindView(R.id.book_quantity)
    EditText mQuantityEditText;

    /**
     * EditText field to enter the supplier name
     */
    @BindView(R.id.supplier_name)
    EditText mSupplierEditText;

    /**
     * EditText field to enter the supplier phone number
     */
    @BindView(R.id.supplier_phone)
    EditText mPhoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editior);
        ButterKnife.bind(this);
    }


    /**
     * Get user input from editor and save new book into database.
     */
    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String productString = mProductEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierString = mSupplierEditText.getText().toString().trim();
        String phoneString = mPhoneEditText.getText().toString().trim();

        // Create database helper
        BookDbHelper mDbHelper = new BookDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and book attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_PRODUCT, productString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, priceString);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantityString);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER, supplierString);
        values.put(BookEntry.COLUMN_BOOK_PHONE, phoneString);

        // Insert a new row for book in the database, returning the ID of that new row.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving a book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save a book to database
                insertBook();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}