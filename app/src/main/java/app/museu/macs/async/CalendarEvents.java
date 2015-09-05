package app.museu.macs.async;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.fragments.AgendaFragment;
import app.museu.macs.util.FragmentBuilder;
import app.museu.macs.util.GetMetaData;
import app.museu.macs.util.ParseWeekViewEvent;

/**
 * Created by jeremias on 17/08/15.
 */
public class CalendarEvents extends AsyncHomeActivityTasks {

    public CalendarEvents(HomeActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Calendar timeMin = Calendar.getInstance();
        List<WeekViewEvent> weekViewEvents = new ArrayList<WeekViewEvent>();
        WeekViewEvent weekViewEvent = null;
        timeMin.add(Calendar.DAY_OF_MONTH, -10);
        Calendar timeMax = Calendar.getInstance();
        timeMax.add(Calendar.DAY_OF_MONTH, 40);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            URL url = new URL("https://www.googleapis.com/calendar/v3/calendars/com.macs@gmail.com/events?" +
                    "singleEvents=true&" +
                    "timeMin=" + sdf.format(timeMin.getTime()) +
                    "&" + "timeMax=" + sdf.format(timeMax.getTime()) +
                    "&" + "key=" + GetMetaData.getGoogleKeyAPI());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONObject responseJson = new JSONObject(responseStrBuilder.toString());
            JSONArray events = new JSONArray(responseJson.get("items").toString());
            int length = events.length();
            JSONObject event = null;
            for (int i = 0; i < length; i ++) {
                event = events.getJSONObject(i);
                weekViewEvent = ParseWeekViewEvent.parseToWeekViewEvent(event, i);
                if (weekViewEvent != null) {
                    weekViewEvents.add(weekViewEvent);
                }
            }
            callAgendaFragment(weekViewEvents);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void callAgendaFragment(List<WeekViewEvent> weekViewEvents) {
        FragmentManager mFragmentManager = getHomeActivity().getSupportFragmentManager();
        Fragment mFragment = AgendaFragment.newInstance("Agenda", weekViewEvents);

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
    }
}
