package android.projects.yashasvi.session_walmart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.projects.yashasvi.session_walmart.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashasvi on 5/31/16.
 */
public class ContactsDAO {

    SQLiteDatabase database;
    MySQLiteHelper dbHelper;

    String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_DESCRIPTION};

    public ContactsDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Contact addContact(String name, String description) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
        long insertId = database.insert(MySQLiteHelper.TABLE_CONTACTS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACTS, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Contact contact1 = cursorToContact(cursor);
        cursor.close();
        return contact1;
    }

    public void deleteContact(Contact contact) {
        long id = contact.getId();
        database.delete(MySQLiteHelper.TABLE_CONTACTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        System.out.println("Comment deleted with id: " + id);

    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }

    private Contact cursorToContact(Cursor cursor) {
        return new Contact(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
    }
}
