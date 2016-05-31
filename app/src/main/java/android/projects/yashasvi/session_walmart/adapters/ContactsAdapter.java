package android.projects.yashasvi.session_walmart.adapters;

import android.projects.yashasvi.session_walmart.R;
import android.projects.yashasvi.session_walmart.models.Contact;
import android.projects.yashasvi.session_walmart.viewholders.ContactsViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yashasvi on 5/31/16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    List<Contact> contacts;


    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        notifyDataSetChanged();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_row, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.description.setText(contact.getDescription());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
