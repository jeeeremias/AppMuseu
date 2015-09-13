package app.museu.macs.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.model.PostFacebook;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostYourPhotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostYourPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostYourPhotoFragment extends Fragment {

    private CallbackManager callbackManager;
    private OnFragmentInteractionListener mListener;
    private static final int CHOOSE_PHOTO = 899;
    private Bitmap yourSelectedImage = null;
    private HomeActivity homeActivity;
    private BootstrapEditText bootstrapEditText;
    private ImageView imageView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostYourPhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostYourPhotoFragment newInstance(HomeActivity homeActivity) {
        PostYourPhotoFragment fragment = new PostYourPhotoFragment();
        fragment.setHomeActivity(homeActivity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PostYourPhotoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_post_you_photo, container, false);
        BootstrapButton bootstrapButton = (BootstrapButton) view.findViewById(R.id.buttonPublish);
        bootstrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
                LoginManager.getInstance().logInWithPublishPermissions(getActivity(), Arrays.asList("publish_actions"));
            }
        });
        imageView = (ImageView) view.findViewById(R.id.selectedPicture);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourSelectedImage == null) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_PHOTO);
                } else {
                    yourSelectedImage = null;
                    imageView.setImageResource(R.drawable.imgnotavailable);
                }
            }
        });
        bootstrapEditText = (BootstrapEditText) view.findViewById(R.id.messagePicture);
        callbackManager = CallbackManager.Factory.create();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void publish(){
        PostFacebook postFacebook = new PostFacebook();
        postFacebook.setMessage(bootstrapEditText.getText().toString());
        postFacebook.setYourSelectedImage(yourSelectedImage);
        getHomeActivity().setPostFacebook(postFacebook);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(resultCode, resultCode, data);
        switch(requestCode) {
            case CHOOSE_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = (Uri) data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(yourSelectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
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

}
