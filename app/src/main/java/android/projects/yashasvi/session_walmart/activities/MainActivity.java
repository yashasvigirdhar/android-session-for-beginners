package android.projects.yashasvi.session_walmart.activities;

import android.os.Bundle;
import android.projects.yashasvi.session_walmart.R;
import android.projects.yashasvi.session_walmart.adapters.ContactsAdapter;
import android.projects.yashasvi.session_walmart.database.ContactsDAO;
import android.projects.yashasvi.session_walmart.models.Contact;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
                }
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
        if (etName.getText().toString().length() > 0 || etDescription.getText().toString().length() > 0)
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
}
