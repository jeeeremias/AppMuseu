package app.museu.macs.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;

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
import app.museu.macs.R;
import app.museu.macs.async.ProfilePhoto;
import app.museu.macs.util.FragmentBuilder;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;


public class HomeActivity extends NavigationLiveo implements br.liveo.interfaces.OnItemClickListener {

    private HelpLiveo mHelpLiveo;
    final private String sourcePhoto = "userPhoto";
    private ImageLoader imageLoader;

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
                updateDrawerNavigation();
            }
        };
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
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
                .memoryCacheSize(2 * 1024)
                .diskCacheSize(10 * 1024)
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInt(Bundle bundle) {
        createMenu();
        updateDrawerNavigation();
    }

    @Override
    public void onItemClick(int position) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = new FragmentBuilder().newFragment(this, mHelpLiveo.get(position).getName(), position);

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginFacebook();
            closeDrawer();
        }
    };

    private void createMenu() {
        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.home_item));
        //mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader
        mHelpLiveo.add(getString(R.string.news_item));
        mHelpLiveo.add(getString(R.string.agenda_item));
        mHelpLiveo.add(getString(R.string.gallery_item));
        mHelpLiveo.addSeparator(); // Item separator

        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .footerItem(R.string.login, R.drawable.fbicon)
                .setOnClickFooter(onClickFooter)
                .build();
    }

    public void updateDrawerNavigation() {
        boolean value;
        if (accessToken == null) {
            this.userName.setText("User");
            this.userPhoto.setImageResource(R.drawable.noprofilephoto);
            this.footerItem(R.string.login, R.drawable.fbicon);
            value = false;
        } else {
            this.userName.setText(profile.getFirstName());
            Bitmap bitmapFromArchive = BitmapFactory.decodeFile(getApplicationContext().getFilesDir() + "/" + sourcePhoto);
            if (bitmapFromArchive == null) {
                new ProfilePhoto(this).execute();
            } else {
                this.userPhoto.setImageBitmap(bitmapFromArchive);
            }
            this.footerItem(R.string.logout, R.drawable.fbicon);
            value = true;
        }
        this.setVisibleItemNavigation(1, value);
        this.setVisibleItemNavigation(2, value);
        this.setVisibleItemNavigation(3, value);
        if (this.getCurrentPosition() != 0) {
            this.onItemClick(0);
        }

    }

    public void loginFacebook() {
        if(accessToken == null) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        } else {
            LoginManager.getInstance().logOut();
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}