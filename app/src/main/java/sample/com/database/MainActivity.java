package sample.com.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import sample.com.database.helper.DatabaseHelper;
import sample.com.database.model.Account;
import sample.com.database.model.Contact;
import sample.com.database.model.Extension;
import sample.com.database.model.ResultModel;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper db;
    Spinner spin;
    ArrayAdapter adapter;
    ArrayList<String> contactId = new ArrayList<String>();
    TextView stagingId;
    TextView context;
    TextView status;
    TextView userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
        spin = (Spinner) findViewById(R.id.spinner);
        stagingId = findViewById(R.id.tv_staging_id);
        context = findViewById(R.id.tv_context);
        status = findViewById(R.id.tv_status);
        userId = findViewById(R.id.tv_user_id);
        spin.setOnItemSelectedListener(this);

        if (db.getAllContacts() == null) {
            insertDataIntoDB();
        }

    }


    private void insertDataIntoDB() {
        // Creating contacts
        Contact contact0 = new Contact("48f3", "1196");
        Contact contact1 = new Contact("3e47", "f1fe");
        Contact contact2 = new Contact("2cac", "036e");

        // Inserting contacts in db
        db.createContact(contact0);
        db.createContact(contact1);
        db.createContact(contact2);


        // Creating extension
        Extension extension0 = new Extension(1, "Gmail");
        Extension extension1 = new Extension(2, "Yahoo");
        Extension extension2 = new Extension(3, "Facebook");

        // Inserting contacts in db
        db.createExtension(extension0);
        db.createExtension(extension1);
        db.createExtension(extension2);


        // Creating account
        Account account0 = new Account(0, "test_one@gmail.com", "Gmail");
        Account account1 = new Account(1, "test_two@gmail.com", "Yahoo");
        Account account2 = new Account(2, "test_three@gmail.com", "Facebook");

        // Inserting contacts in db
        db.createAccount(account0);
        db.createAccount(account1);
        db.createAccount(account2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactId.clear();
        contactId.add("Please select any Contact Id");
        db.getAllContacts();
        db.getAllExtensions();
        db.getAllAccounts();

        for (int i = 0; i < db.getAllContacts().size(); i++) {
            contactId.add(db.getAllContacts().get(i).getContactId());
            Log.e("DBContactId", db.getAllContacts().get(i).getContactId());
        }


        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, contactId);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        db.setOnDataChangeListener(new DatabaseHelper.OnDataChangeListener() {
            @Override
            public void onDataChanged(ResultModel resultModel) {
                stagingId.setText(resultModel.getStagingId());
                context.setText(resultModel.getContext());
                status.setText(resultModel.getStatus());
                userId.setText(resultModel.getUserId());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        db.getData(contactId.get(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
