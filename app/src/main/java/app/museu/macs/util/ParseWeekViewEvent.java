package app.museu.macs.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import app.museu.macs.R;

/**
 * Created by jeremias on 18/08/15.
 */
public class ParseWeekViewEvent {

    public static WeekViewEvent parseToWeekViewEvent(JSONObject json, int id) {
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        String start = null;
        Calendar startDateTime = null;
        Calendar endDateTime = null;
        String end = null;
        weekViewEvent.setId(id);
        try {
            weekViewEvent.setName(json.getString("summary"));
        } catch (JSONException e) {
            e.printStackTrace();
            weekViewEvent.setName("Error: Name empty");
        }

        try {
            start = json.getJSONObject("start").getString("dateTime");
            startDateTime = parseToDateTime(start);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                start = json.getJSONObject("start").getString("date");
                startDateTime = parseToDate(start);
            } catch (JSONException e1) {
                e1.printStackTrace();
                return null;
            }
        }

        weekViewEvent.setStartTime(startDateTime);
        weekViewEvent.setColor(R.color.nliveo_red_colorPrimary);

        try {
            end = json.getJSONObject("end").getString("dateTime");
            endDateTime = parseToDateTime(end);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                end = json.getJSONObject("end").getString("date");
                endDateTime = parseToDate(end);
            } catch (JSONException e1) {
                e1.printStackTrace();
                endDateTime = startDateTime;
            }
            endDateTime.set(Calendar.HOUR_OF_DAY, 23);
        }
        weekViewEvent.setEndTime(endDateTime);

        Log.i("Event " + id, "Inicio: " + weekViewEvent.getStartTime() + " - " + "Fim: " + weekViewEvent.getEndTime());

        return weekViewEvent;
    }

    public static Calendar parseToDateTime(String datetime) {
        Calendar calendar = Calendar.getInstance();
        if (datetime != null) {
            String[] date = datetime.split("T")[0].split("-");
            String[] time = datetime.split("T")[1].split("-")[0].split(":");
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
        } else {
            return null;
        }

        return calendar;
    }

    public static Calendar parseToDate(String datetime) {
        Calendar calendar = Calendar.getInstance();
        if (datetime != null) {
            String[] date = datetime.split("-");
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), 0, 0, 0);
        } else {
            return null;
        }

        return calendar;
    }
}
