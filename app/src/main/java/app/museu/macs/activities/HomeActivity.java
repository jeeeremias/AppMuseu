package app.museu.macs.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.alamkanak.weekview.WeekViewEvent;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import app.museu.macs.R;
import app.museu.macs.async.PostYourPhoto;
import app.museu.macs.async.ProfilePhoto;
import app.museu.macs.model.GalleryPhoto;
import app.museu.macs.model.PostFacebook;
import app.museu.macs.util.FragmentBuilder;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;


public class HomeActivity extends NavigationLiveo implements br.liveo.interfaces.OnItemClickListener {

    private HelpLiveo mHelpLiveo;
    final private String sourcePhoto = "userPhoto";
    private ImageLoader imageLoader;
    private Fragment currentFragment;
    private PostFacebook postFacebook = null;
    private ProgressDialog progressDialog;
    private List<WeekViewEvent> weekViewEvents;
    private FragmentBuilder fragmentBuilder = new FragmentBuilder(this);
    private List<GalleryPhoto> galleryPhotos;
    private int imageToDisplay;

    /**
     * Required variables to login Facebook
     */
    private AccessToken accessToken;
    private Profile profile;
    private CallbackManager callbackManager;
    private AccessTokenTracker tokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);

        // Facebook implementations
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        profile = Profile.getCurrentProfile();
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
                profile = Profile.getCurrentProfile();
            }
        };
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if(loginResult.getRecentlyGrantedPermissions().contains("publish_actions")) {
                            new PostYourPhoto(postFacebook).execute();
                            postFacebook = null;
                        } else {
                            updateLogin();
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.i("Login Facebook", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("Login Facebook", "onError");
                        exception.printStackTrace();
                    }
                });

        //Universal Image Loader
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.imgnotavailable)
                .showImageOnFail(R.drawable.imgnotavailable)
                .showImageOnLoading(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(displayImageOptions)
                .writeDebugLogs()
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(10 * 1024 * 1024)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(imageLoaderConfiguration);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tokenTracker.startTracking();
        profile = Profile.getCurrentProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInt(Bundle bundle) {
        createMenu();
    }

    @Override
    public void onItemClick(int position) {
        fragmentBuilder.newFragment(position);
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    @Override
    public void onBackPressed() {
        if (!fragmentBuilder.doBack()) {
            super.onBackPressed();
        }
    }

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            share();
            closeDrawer();
        }
    };

    private void createMenu() {
        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.home_item), R.drawable.ic_home_black_24dp);
        //mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader
        mHelpLiveo.add(getString(R.string.agenda_item), R.drawable.ic_schedule_black_24dp);
        mHelpLiveo.add(getString(R.string.gallery_item), R.drawable.ic_photo_library_black_24dp);
        mHelpLiveo.add(getString(R.string.location_item), R.drawable.ic_place_black_24dp);
//        mHelpLiveo.add(getString(R.string.check_in_item), R.drawable.ic_check_black_24dp);
//        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.addSubHeader(getString(R.string.info_sub_item));
        mHelpLiveo.add(getString(R.string.app_item), R.drawable.ic_perm_device_information_black_24dp);
        mHelpLiveo.add(getString(R.string.project_item), R.drawable.ic_assignment_black_24dp);


        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();
        this.footerItem(getString(R.string.share_title), R.drawable.ic_people_black_24dp);
        this.userName.setText("MACS - Museu de Arte Contemporânea de Sorocaba");
        this.userPhoto.setImageResource(R.drawable.userphoto);
    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_footer));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void updateLogin() {
        this.userName.setText(profile.getFirstName());
        Bitmap bitmapFromArchive = BitmapFactory.decodeFile(getApplicationContext().getFilesDir() + "/" + sourcePhoto);
        if (bitmapFromArchive == null) {
            new ProfilePhoto(this).execute();
        } else {
            this.userPhoto.setImageBitmap(bitmapFromArchive);
        }
        this.footerItem("Logout", R.drawable.fbicon);
    }

    public void updateLogout() {
        this.footerItem("Login", R.drawable.fbicon);
    }

    public void loginFacebook() {
        if(accessToken == null) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("publish_actions"));
        } else {
            LoginManager.getInstance().logOut();
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public PostFacebook getPostFacebook() {
        return postFacebook;
    }

    public void setPostFacebook(PostFacebook postFacebook) {
        this.postFacebook = postFacebook;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public List<WeekViewEvent> getWeekViewEvents() {
        return weekViewEvents;
    }

    public void setWeekViewEvents(List<WeekViewEvent> weekViewEvents) {
        this.weekViewEvents = weekViewEvents;
    }

    public FragmentBuilder getFragmentBuilder() {
        return fragmentBuilder;
    }

    public void setFragmentBuilder(FragmentBuilder fragmentBuilder) {
        this.fragmentBuilder = fragmentBuilder;
    }

    public List<GalleryPhoto> getGalleryPhotos() {
        return galleryPhotos;
    }

    public void setGalleryPhotos(List<GalleryPhoto> galleryPhotos) {
        this.galleryPhotos = galleryPhotos;
    }

    public int getImageToDisplay() {
        return imageToDisplay;
    }

    public void setImageToDisplay(int imageToDisplay) {
        this.imageToDisplay = imageToDisplay;
    }
}