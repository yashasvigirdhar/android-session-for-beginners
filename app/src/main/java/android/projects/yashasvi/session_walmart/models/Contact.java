package android.projects.yashasvi.session_walmart.models;

/**
 * Created by yashasvi on 5/31/16.
 */
public class Contact {

    String name, description;

    public Contact(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
