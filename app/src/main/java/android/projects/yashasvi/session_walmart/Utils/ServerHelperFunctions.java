package android.projects.yashasvi.session_walmart.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yashasvi on 5/31/16.
 */
public class ServerHelperFunctions {

    private static final String LOG_TAG = "ServerHelperFunctions";

    public static String getJSON(String url) {
        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.connect();
            int status = connection.getResponseCode();
            Log.i(LOG_TAG, String.format("status for %s : %s", url, status));
            if (status == HttpURLConnection.HTTP_NO_CONTENT) {
                return String.valueOf(HttpURLConnection.HTTP_NO_CONTENT);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            if (connection != null)
                connection.disconnect();
            return sb.toString();

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
            return e.toString();
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception ex) {
                    //disconnect error
                }
            }
        }
    }

}
