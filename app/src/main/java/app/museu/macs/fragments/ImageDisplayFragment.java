package app.museu.macs.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.List;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.model.GalleryPhoto;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageDisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageDisplayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private HomeActivity homeActivity;
    private List<GalleryPhoto> galleryPhotos;
    private int position;
    private ImageView imageView;
    private ImageLoader imageLoader;
    private GalleryPhoto galleryPhoto;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageDisplayFragment newInstance(HomeActivity homeActivity, List<GalleryPhoto> galleryPhotos, int position) {
        ImageDisplayFragment fragment = new ImageDisplayFragment();
        fragment.setGalleryPhotos(galleryPhotos);
        fragment.setHomeActivity(homeActivity);
        fragment.setPosition(position);
        return fragment;
    }

    public ImageDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = getHomeActivity().getImageLoader();
        galleryPhoto = galleryPhotos.get(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_display, container, false);
        imageView = (ImageView) view.findViewById(R.id.galleryImageViewFull);
        showImage();
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void showImage() {
        imageLoader.displayImage(galleryPhoto.getUriSource(), imageView, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {

            }
        });
    }

}
