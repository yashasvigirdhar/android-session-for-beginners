package android.projects.yashasvi.session_walmart.services;

import android.app.IntentService;
import android.content.Intent;
import android.projects.yashasvi.session_walmart.Utils.ServerHelperFunctions;
import android.util.Log;

/**
 * Created by yashasvi on 5/31/16.
 */
public class ContactsDownloadService extends IntentService {

    String LOG_TAG = "ContactsDownloadService";

    public static final String RECEIVER_FILTER = "android.projects.yashasvi.session_walmart.activities.receiver";

    public static String CONTACTS_KEY = "contacts";
    public static String CONTACTS_URL = "url";


    public ContactsDownloadService() {
        super("ContactsDownloadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(CONTACTS_URL);
        Log.d(LOG_TAG, url);
        String resultString = ServerHelperFunctions.getJSON(url);
        publishResults(resultString);
    }

    private void publishResults(String result) {
        Intent intent = new Intent(RECEIVER_FILTER);
        intent.putExtra(CONTACTS_KEY, result);
        sendBroadcast(intent);
    }
}
