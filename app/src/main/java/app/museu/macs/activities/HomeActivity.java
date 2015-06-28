package app.museu.macs.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import app.museu.macs.R;
import app.museu.macs.async.ProfilePhoto;
import app.museu.macs.util.FragmentBuilder;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;


public class HomeActivity extends NavigationLiveo implements br.liveo.interfaces.OnItemClickListener {

    private HelpLiveo mHelpLiveo;
    private String sourcePhoto = "userPhoto";

    /**
     * Required variables to login facebook
     */
    private AccessToken accessToken;
    private Profile profile;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker tokenTracker;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        profile = Profile.getCurrentProfile();

        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
                profile = Profile.getCurrentProfile();
                updateMenu();
                updateHeaderFooter();
            }
        };

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        updateMenu();

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
        updateHeaderFooter();
        updateMenu();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
        profileTracker.stopTracking();
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
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = new FragmentBuilder().newFragment(mHelpLiveo.get(position).getName(), position);

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

    public void updateMenu() {
        boolean value;
        if (accessToken == null) {
            value = false;
        } else {
            value = true;
        }
        this.setVisibleItemNavigation(1, value);
        this.setVisibleItemNavigation(2, value);
        this.setVisibleItemNavigation(3, value);
        if (this.getCurrentPosition() != 0) {
            onItemClick(0);
        }

    }

    public void updateHeaderFooter() {
        if (accessToken == null) {
            this.userName.setText("User");
            this.userPhoto.setImageResource(R.drawable.noprofilephoto);
            this.footerItem(R.string.login, R.drawable.fbicon);
        } else {
            this.footerItem(R.string.logout, R.drawable.fbicon);
            this.userName.setText(profile.getFirstName());
            Bitmap bitmapFromArchive = BitmapFactory.decodeFile(getApplicationContext().getFilesDir() + "/" + sourcePhoto);
            if (bitmapFromArchive == null) {
                getProfilePhoto();
            } else {
                this.userPhoto.setImageBitmap(bitmapFromArchive);
            }
        }
    }

    public void loginFacebook() {
        if(accessToken == null) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_photos"));
        } else {
            LoginManager.getInstance().logOut();
        }
    }

    private void getProfilePhoto() {
        ProfilePhoto profilePhoto = new ProfilePhoto();
        try {
            Bitmap userPhoto = profilePhoto.execute().get();
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(sourcePhoto, Context.MODE_PRIVATE);
            userPhoto.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            this.userPhoto.setImageBitmap(userPhoto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}