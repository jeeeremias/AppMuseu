package app.museu.macs.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.adapters.GalleryAdapter;
import app.museu.macs.async.GalleryPhotos;
import app.museu.macs.model.GalleryPhoto;
import app.museu.macs.util.EnumFragment;
import app.museu.macs.util.MyApplication;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private GridView gridViewGallery;
    private OnFragmentInteractionListener mListener;
    private HomeActivity homeActivity;

    public static GalleryFragment newInstance(HomeActivity homeActivity) {
        if(homeActivity.getGalleryPhotos() != null) {
            GalleryFragment fragment = new GalleryFragment();
            fragment.setHomeActivity(homeActivity);
            return fragment;
        } else {
            homeActivity.showLoading("Aguarde", "Carregando imagens da galeria...");
            new GalleryPhotos(homeActivity).execute();
        }
        return null;
    }

    public GalleryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        gridViewGallery = (GridView) view.findViewById(R.id.gridViewGalery);
        gridViewGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeActivity.setImageToDisplay(position);
                homeActivity.getFragmentBuilder().newFragment(EnumFragment.DISPLAY_IMAGE_FRAGMENT);
            }
        });

        setAdapterGrid();

        return view;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public void setAdapterGrid() {
        gridViewGallery.setAdapter(new GalleryAdapter(MyApplication.getAppContext(), homeActivity.getGalleryPhotos(), homeActivity.getImageLoader()));
    }

}
