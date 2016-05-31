package android.projects.yashasvi.session_walmart.viewholders;

import android.projects.yashasvi.session_walmart.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yashasvi on 5/31/16.
 */
public class ContactsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, description;

    public ContactsViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tvName);
        description = (TextView) itemView.findViewById(R.id.tvDescription);
    }
}
