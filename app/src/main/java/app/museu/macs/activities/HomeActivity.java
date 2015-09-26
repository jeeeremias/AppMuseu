package app.museu.macs.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Arrays;
import java.util.List;

import app.museu.macs.R;
import app.museu.macs.model.GalleryPhoto;
import app.museu.macs.model.PostFacebook;
import app.museu.macs.util.EnumFragment;
import app.museu.macs.util.FragmentBuilder;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;


public class HomeActivity extends NavigationLiveo implements br.liveo.interfaces.OnItemClickListener {

    private HelpLiveo mHelpLiveo;
    private ImageLoader imageLoader;
    private PostFacebook postFacebook = null;
    private ProgressDialog progressDialog;
    private List<WeekViewEvent> weekViewEvents;
    private FragmentBuilder fragmentBuilder = new FragmentBuilder(this);
    private List<GalleryPhoto> galleryPhotos;
    private int imageToDisplay;
    private Toast toast;

    /**
     * Required variables to login Facebook
     */
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        // Facebook implementations
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if(loginResult.getRecentlyGrantedPermissions().contains("public_profile")) {
                            fragmentBuilder.newFragment(EnumFragment.GALLERY_FRAGMENT);
                        }
                    }

                    @Override
                    public void onCancel() {
                        showToast("Erro ao fazer login com o Facebook");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        showToast("Erro ao fazer login com o Facebook");
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
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(15 * 1024 * 1024)
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
        weekViewEvents = null;
        galleryPhotos = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        this.userBackground.setImageResource(R.drawable.logo_macs2);
    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_footer));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if(sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(sendIntent);
        } else {
            showToast("Aplicativo WhatsApp não encontrado");
        }
    }

    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showLoading(String title, String message) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void loginFacebook() {
        if(!isDeviceOnline()) {
            showToast("Sem conexão com a internet");
        } else {
            if (galleryPhotos != null) {
                fragmentBuilder.newFragment(EnumFragment.GALLERY_FRAGMENT);
            } else {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            }
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
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