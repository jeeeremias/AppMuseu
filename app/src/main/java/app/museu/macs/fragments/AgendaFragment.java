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
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.async.CalendarEvents;
import app.museu.macs.util.EnumFragment;


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
    private OnFragmentInteractionListener mListener;
    private HomeActivity homeActivity;

    public static AgendaFragment newInstance(HomeActivity homeActivity) {
        if (homeActivity.getWeekViewEvents() != null) {
            AgendaFragment fragment = new AgendaFragment();
            fragment.setHomeActivity(homeActivity);
            fragment.setEvents(homeActivity.getWeekViewEvents());
            return fragment;
        } else {
            homeActivity.showLoading("Aguarde", "Carregando a agenda...");
            new CalendarEvents(homeActivity).execute();
        }
        return null;
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
        if(populated) {
            return eventsEmpty;
        } else {
            populated = true;
            return events;
        }
    }

    public List<WeekViewEvent> getEvents() {
        return events;
    }

    public void setEvents(List<WeekViewEvent> events) {
        this.events = events;
    }

    public HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

}
