package app.museu.macs.fragments;

import android.app.Activity;
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
    private List<GalleryPhoto> galleryPhotos;
    private OnFragmentInteractionListener mListener;
    private HomeActivity homeActivity;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param titlle Parameter 1.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(HomeActivity homeActivity) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setHomeActivity(homeActivity);
        return fragment;
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
        homeActivity.getProgressDialog().setTitle("Aguarde");
        homeActivity.getProgressDialog().setMessage("Carregando imagens da galeria...");
        homeActivity.getProgressDialog().setCancelable(false);
        homeActivity.getProgressDialog().show();
        new GalleryPhotos(this, homeActivity).execute();
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

    public List<GalleryPhoto> getGalleryPhotos() {
        return galleryPhotos;
    }

    public void setGalleryPhotos(List<GalleryPhoto> galleryPhotos) {
        this.galleryPhotos = galleryPhotos;
    }

    public void setAdapterGrid() {
        gridViewGallery.setAdapter(new GalleryAdapter(MyApplication.getAppContext(), galleryPhotos, getHomeActivity().getImageLoader()));
    }

}
