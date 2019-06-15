package sample.com.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sample.com.database.model.Account;
import sample.com.database.model.Contact;
import sample.com.database.model.Extension;
import sample.com.database.model.ResultModel;

public class DatabaseHelper extends SQLiteOpenHelper {
    private OnDataChangeListener mOnDataChangeListener;
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_CONTACT = "contacts";
    private static final String TABLE_EXTENSION = "extensions";
    private static final String TABLE_ACCOUNTS = "accounts";

    // CONTACT Table - column names
    private static final String KEY_ID = "_id";
    private static final String CONTACT_ID = "contact_id";
    private static final String STAGING_ID = "staging_id";

    // EXTENSION Table - column names
    private static final String EXTENSION_CONTEXT = "context";
    private static final String PHONE_CONTACT_ID = "phone_contact_id";

    // ACCOUNTS Table - column names
    private static final String STATUS = "status";
    private static final String USER_ID = "user_id";
    private static final String ACCOUNT_CONTEXT = "context";


    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + TABLE_CONTACT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + CONTACT_ID
            + " TEXT," + STAGING_ID + " TEXT " + ")";

    private static final String CREATE_TABLE_EXTENSION = "CREATE TABLE "
            + TABLE_EXTENSION + "(" + PHONE_CONTACT_ID + " INTEGER," + EXTENSION_CONTEXT
            + " TEXT" + ")";

    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE "
            + TABLE_ACCOUNTS + "(" + STATUS + " INTEGER," + USER_ID
            + " TEXT," + ACCOUNT_CONTEXT + " TEXT " + ")";

    private static final String QUERY = "Select staging_id,e.context,a.status,a.user_id, e.phone_contact_id from contacts left join extensions as e on e.phone_contact_id = contacts._id left join accounts as a on a.context = e.context where contact_id = '%s'";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_TABLE_EXTENSION);
        db.execSQL(CREATE_TABLE_ACCOUNTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTENSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "contacts" table methods ----------------//


    public long createContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(KEY_ID, contact.getId());
        values.put(CONTACT_ID, contact.getContactId());
        values.put(STAGING_ID, contact.getStagingId());

        // insert row
        long id = db.insert(TABLE_CONTACT, null, values);

        return id;
    }

    /**
     * getting all todos
     */
    public String getData(String contactId) {
        String userId = "";
        ResultModel resultModel = new ResultModel();
        String selectQuery = String.format(QUERY, contactId);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                userId = (c.getString(3));
                Log.e("Data", "" + (c.getString(0)));
                Log.e("Data", "" + (c.getString(1)));
                Log.e("Data", "" + (c.getString(2)));
                Log.e("Data", "" + (c.getString(3)));
                Log.e("Data", "" + (c.getString(4)));

                resultModel.setStagingId(c.getString(0));
                resultModel.setContext(c.getString(1));
                resultModel.setStatus(c.getString(2));
                resultModel.setUserId(c.getString(3));
                resultModel.setContactId(c.getString(4));

                if(mOnDataChangeListener != null){
                    mOnDataChangeListener.onDataChanged(resultModel);
                }


            } while (c.moveToNext());
        }
        return userId;
    }


    // ------------------------ "extensions" table methods ----------------//


    public long createExtension(Extension extension) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONE_CONTACT_ID, extension.getPhoneContactId());
        values.put(EXTENSION_CONTEXT, extension.getContext());

        // insert row
        long id = db.insert(TABLE_EXTENSION, null, values);

        return id;
    }


    public long createAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS, account.getStatus());
        values.put(USER_ID, account.getUserId());
        values.put(ACCOUNT_CONTEXT, account.getContext());

        // insert row
        long id = db.insert(TABLE_ACCOUNTS, null, values);

        return id;
    }

    /**
     * getting all todos
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                contact.setContactId((c.getString(c.getColumnIndex(CONTACT_ID))));
                contact.setStagingId(c.getString(c.getColumnIndex(STAGING_ID)));

                // adding to todo list
                contacts.add(contact);
            } while (c.moveToNext());
        }

        for (int i = 0; i < contacts.size(); i++) {
            Log.e("DBContactId", contacts.get(i).getContactId());
            Log.e("DBContactStaging", contacts.get(i).getStagingId());
        }

        return contacts;


    }

    public List<Extension> getAllExtensions() {
        List<Extension> extensions = new ArrayList<Extension>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXTENSION;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Extension extension = new Extension();
                extension.setContext(c.getString(c.getColumnIndex(EXTENSION_CONTEXT)));
                extension.setPhoneContactId((c.getInt(c.getColumnIndex(PHONE_CONTACT_ID))));

                // adding to todo list
                extensions.add(extension);
            } while (c.moveToNext());
        }
        for (int i = 0; i < extensions.size(); i++) {
            Log.e("DBExtContext", extensions.get(i).getContext());
        }
        return extensions;
    }

    /*Select staging_id,e.context,a.status,a.userID, e.phoneContactId from contact left join extensions as e on e.phoneContactId = contact.contactId left join accounts as a on a.context = e.phoneContactId where contactiId = ’48f3’*/

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Account account = new Account();
                account.setContext(c.getString(c.getColumnIndex(ACCOUNT_CONTEXT)));
                account.setUserId((c.getString(c.getColumnIndex(USER_ID))));
                account.setStatus((c.getInt(c.getColumnIndex(STATUS))));

                // adding to todo list
                accounts.add(account);
            } while (c.moveToNext());
        }
        for (int i = 0; i < accounts.size(); i++) {
            Log.e("DBActContext", accounts.get(i).getContext());
            Log.e("DBActUserId", accounts.get(i).getUserId());
        }
        return accounts;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public interface OnDataChangeListener{
        public void onDataChanged(ResultModel resultModel);
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


}
