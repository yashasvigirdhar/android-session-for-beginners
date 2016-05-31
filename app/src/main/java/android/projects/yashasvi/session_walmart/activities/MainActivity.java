package android.projects.yashasvi.session_walmart.activities;

import android.os.Bundle;
import android.projects.yashasvi.session_walmart.R;
import android.projects.yashasvi.session_walmart.adapters.ContactsAdapter;
import android.projects.yashasvi.session_walmart.models.Contact;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView contactsRView;
    ContactsAdapter contactsAdapter;

    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDataset();
        initializeViews();

    }

    private void initializeDataset() {
        contacts = new ArrayList<>();
        contacts.add(new Contact("Rafiq", "Ooty"));
        contacts.add(new Contact("Manju", "Hampi"));
        contacts.add(new Contact("Prem", "Ooty"));
        contacts.add(new Contact("Vashishth", "Rishikesh"));
        contacts.add(new Contact("Patang", "Gokarna"));

    }

    private void initializeViews() {
        contactsRView = (RecyclerView) findViewById(R.id.recyclerviewContacts);
        contactsAdapter = new ContactsAdapter(contacts);
        contactsRView.setAdapter(contactsAdapter);
        contactsRView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
