package app.museu.macs.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import app.museu.macs.R;
import app.museu.macs.async.CalendarEvents;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgendaFragment extends Fragment implements WeekView.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener{

    /**
     * Required variables to Calendar
     */
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private boolean populated = false;

    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    private List<WeekViewEvent> eventsEmpty = new ArrayList<WeekViewEvent>();


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AgendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgendaFragment newInstance(String titlle, List<WeekViewEvent> events) {
        AgendaFragment fragment = new AgendaFragment();
        fragment.setEvents(events);
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, titlle);
        fragment.setArguments(args);
        return fragment;
    }

    public AgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agenda, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        Log.d("Agenda", "Vai criar essa fita");

//        WeekView.MonthChangeListener monthChangeListener = new WeekView.MonthChangeListener() {
//            @Override
//            public List<WeekViewEvent> onMonthChange(int i, int i1) {
//                List<WeekViewEvent> weekViewEventList = null;
//                try {
//                    weekViewEventList = new CalendarMacs(fragment).execute().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                return weekViewEventList;
//            }
//        };


        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
        
    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Populate the week view with some events.
//        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//
//        java.util.Calendar startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 3);
//        startTime.set(java.util.Calendar.MINUTE, 0);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        java.util.Calendar endTime = (java.util.Calendar) startTime.clone();
//        endTime.add(java.util.Calendar.HOUR, 1);
//        endTime.set(Calendar.DAY_OF_MONTH, 20);
//        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 3);
//        startTime.set(java.util.Calendar.MINUTE, 30);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.set(java.util.Calendar.HOUR_OF_DAY, 4);
//        endTime.set(java.util.Calendar.MINUTE, 30);
//        endTime.set(java.util.Calendar.MONTH, newMonth-1);
//        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 4);
//        startTime.set(java.util.Calendar.MINUTE, 20);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.set(java.util.Calendar.HOUR_OF_DAY, 5);
//        endTime.set(java.util.Calendar.MINUTE, 0);
//        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 5);
//        startTime.set(java.util.Calendar.MINUTE, 30);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.add(java.util.Calendar.HOUR_OF_DAY, 2);
//        endTime.set(java.util.Calendar.MONTH, newMonth-1);
//        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 5);
//        startTime.set(java.util.Calendar.MINUTE, 0);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        startTime.add(java.util.Calendar.DATE, 1);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.add(java.util.Calendar.HOUR_OF_DAY, 3);
//        endTime.set(java.util.Calendar.MONTH, newMonth - 1);
//        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.DAY_OF_MONTH, 15);
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 3);
//        startTime.set(java.util.Calendar.MINUTE, 0);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.add(java.util.Calendar.HOUR_OF_DAY, 3);
//        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.DAY_OF_MONTH, 1);
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 3);
//        startTime.set(java.util.Calendar.MINUTE, 0);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.add(java.util.Calendar.HOUR_OF_DAY, 3);
//        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
//
//        startTime = java.util.Calendar.getInstance();
//        startTime.set(java.util.Calendar.DAY_OF_MONTH, startTime.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
//        startTime.set(java.util.Calendar.HOUR_OF_DAY, 15);
//        startTime.set(java.util.Calendar.MINUTE, 0);
//        startTime.set(java.util.Calendar.MONTH, newMonth-1);
//        startTime.set(java.util.Calendar.YEAR, newYear);
//        endTime = (java.util.Calendar) startTime.clone();
//        endTime.add(java.util.Calendar.HOUR_OF_DAY, 3);
//        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.nliveo_red_colorPrimary));
//        events.add(event);
        if(populated == false) {
            populated = true;
            return events;
        } else {
            return eventsEmpty;
        }
    }

    private String getEventTitle(java.util.Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(java.util.Calendar.HOUR_OF_DAY), time.get(java.util.Calendar.MINUTE), time.get(java.util.Calendar.MONTH)+1, time.get(java.util.Calendar.DAY_OF_MONTH));
    }


    public List<WeekViewEvent> getEvents() {
        return events;
    }

    public void setEvents(List<WeekViewEvent> events) {
        this.events = events;
    }

}
