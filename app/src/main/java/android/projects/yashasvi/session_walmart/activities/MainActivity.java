package android.projects.yashasvi.session_walmart.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.projects.yashasvi.session_walmart.R;
import android.projects.yashasvi.session_walmart.Utils.ServerHelperFunctions;
import android.projects.yashasvi.session_walmart.adapters.ContactsAdapter;
import android.projects.yashasvi.session_walmart.database.ContactsDAO;
import android.projects.yashasvi.session_walmart.models.Contact;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "MainActivity";
    ContactsDAO contactsDAO;

    RecyclerView contactsRView;
    ContactsAdapter contactsAdapter;

    FloatingActionButton addContactFAButton;

    AlertDialog.Builder dialogBuilder;
    AlertDialog addContactDialog;

    EditText etName, etDescription;

    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        initializeDatabase();
        initializeViews();
        initializeAddContactDialog();
    }

    private void initializeAddContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_contact_dialog, null);
        dialogView.findViewById(R.id.bOk).setOnClickListener(this);
        dialogView.findViewById(R.id.bCancel).setOnClickListener(this);
        etName = (EditText) dialogView.findViewById(R.id.etName);
        etDescription = (EditText) dialogView.findViewById(R.id.etDescription);
        dialogBuilder.setView(dialogView);
        addContactDialog = dialogBuilder.create();
    }

    private void initializeDatabase() {
        contactsDAO = new ContactsDAO(this);
        contactsDAO.open();
        contacts = contactsDAO.getAllContacts();
    }

    private void initializeViews() {
        contactsRView = (RecyclerView) findViewById(R.id.recyclerviewContacts);
        contactsAdapter = new ContactsAdapter(contacts);
        contactsRView.setAdapter(contactsAdapter);
        contactsRView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        addContactFAButton = (FloatingActionButton) findViewById(R.id.fabAddContact);
        addContactFAButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddContact:
                addContactDialog.show();
                break;
            case R.id.bOk:
                if (validateInput()) {
                    Contact contact = contactsDAO.addContact(etName.getText().toString(), etDescription.getText().toString());
                    contactsAdapter.addContact(contact);
                } else
                    break;
            case R.id.bCancel:
                etName.setText("");
                etDescription.setText("");
                etName.requestFocus();
                if (addContactDialog.isShowing())
                    addContactDialog.dismiss();
                break;
        }
    }

    private boolean validateInput() {
        if (etName.getText().toString().length() > 0 && etDescription.getText().toString().length() > 0)
            return true;
        Toast.makeText(MainActivity.this, "Name or Description Can't be empty", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onPause() {
        contactsDAO.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        contactsDAO.open();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                syncContactsWithServer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void syncContactsWithServer() {
        String url = "https://raw.githubusercontent.com/itsyash/SimpleTextHosting/master/contacts.json";
        new GetPlacesAsyncTask(url).execute();
    }

    public class GetPlacesAsyncTask extends AsyncTask<Void, Void, String> {

        private final String mUrl;

        public GetPlacesAsyncTask(String url) {
            mUrl = url;
        }

        @Override
        protected String doInBackground(Void... params) {
            String resultString;
            resultString = ServerHelperFunctions.getJSON(mUrl);
            return resultString;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);
            Log.i(LOG_TAG, "onPostExecute");
            Log.i(LOG_TAG, jsonString);
            if (jsonString.contains("Exception")) {
                return;
            }
            Type collectionType = new TypeToken<List<Contact>>() {
            }.getType();
            try {
                List<Contact> receivedContacts = (ArrayList<Contact>) new Gson().fromJson(jsonString, collectionType);
                boolean exists;
                for (int i = 0; i < receivedContacts.size(); i++) {
                    exists = false;
                    Contact curContact = receivedContacts.get(i);
                    for (int j = 0; j < contacts.size(); j++) {
                        if (curContact.getName().equals(contacts.get(j).getName())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        Log.d(LOG_TAG, "doesn't exists : " + curContact.getName());
                        Contact contact = contactsDAO.addContact(curContact.getName(), curContact.getDescription());
                        contactsAdapter.addContact(contact);
                    }
                }

                for (int i = 0; i < contacts.size(); i++)
                    Log.i(LOG_TAG, contacts.get(i).getName());

            } catch (JsonSyntaxException e) {
                //not able to parse response, after requesting all places

            }

        }
    }

}
