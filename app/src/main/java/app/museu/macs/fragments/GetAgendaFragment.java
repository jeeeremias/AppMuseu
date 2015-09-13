package app.museu.macs.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.async.CalendarEvents;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetAgendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetAgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetAgendaFragment extends Fragment {

    private HomeActivity homeActivity;
    private TextView textView;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetAgendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetAgendaFragment newInstance(HomeActivity homeActivity) {
        GetAgendaFragment fragment = new GetAgendaFragment();
        fragment.setHomeActivity(homeActivity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GetAgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity.getProgressDialog().setTitle("Aguarde");
        homeActivity.getProgressDialog().setMessage("Carregando a agenda...");
        homeActivity.getProgressDialog().setCancelable(false);
        homeActivity.getProgressDialog().show();
        new CalendarEvents((HomeActivity) getActivity()).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_agenda, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public HomeActivity getHomeActivity() {
        return this.homeActivity;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

}
