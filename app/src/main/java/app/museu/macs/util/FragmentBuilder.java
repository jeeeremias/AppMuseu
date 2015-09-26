package app.museu.macs.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Stack;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.fragments.AgendaFragment;
import app.museu.macs.fragments.DevInformationFragment;
import app.museu.macs.fragments.GalleryFragment;
import app.museu.macs.fragments.HomeFragment;
import app.museu.macs.fragments.ImageDisplayFragment;
import app.museu.macs.fragments.LocationFragment;
import app.museu.macs.fragments.PostYourPhotoFragment;
import app.museu.macs.fragments.ProjectInformationFragment;

/**
 * Created by jeremias on 06/06/15.
 */
public class FragmentBuilder {

    public FragmentBuilder(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        fragmentManager = homeActivity.getSupportFragmentManager();
    }

    private HomeActivity homeActivity;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private Stack<Integer> fragmentStack = new Stack<Integer>();

    public boolean doBack() {
        if(fragmentStack.size() > 1) {
            fragmentStack.pop();
            newFragment(fragmentStack.pop());
            return true;
        } else {
            return false;
        }

    }

    public void  newFragment(int index) {
        switch (index) {
            case EnumFragment.HOME_FRAGMENT:
                fragmentStack.push(index);
                fragment = HomeFragment.newInstance();
                break;
            case EnumFragment.GALLERY_FRAGMENT:
                fragmentStack.push(index);
                fragment = GalleryFragment.newInstance(homeActivity);
                break;
            case EnumFragment.LOCATION_FRAGMENT:
                fragmentStack.push(index);
                fragment = LocationFragment.newInstance(homeActivity);
                break;
            case EnumFragment.POST_YOUR_PHOTO_FRAGMENT:
                fragment = PostYourPhotoFragment.newInstance(homeActivity);
                break;
            case EnumFragment.AGENDA_FRAGMENT:
                fragmentStack.push(index);
                fragment = AgendaFragment.newInstance(homeActivity);
                break;
            case EnumFragment.DISPLAY_IMAGE_FRAGMENT:
                fragmentStack.push(index);
                fragment = ImageDisplayFragment.newInstance(homeActivity);
                break;
            case EnumFragment.INFO_APP_FRAGMENT:
                fragmentStack.push(index);
                fragment = DevInformationFragment.newInstance(homeActivity);
                break;
            case EnumFragment.INFO_PROJECT_FRAGMENT:
                fragmentStack.push(index);
                fragment = ProjectInformationFragment.newInstance(homeActivity);
                break;
            case EnumFragment.LOGIN_FACEBOOK:
                homeActivity.loginFacebook();
                break;
        }

        if (fragment != null){
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            fragmentStack.pop();
        }
    }

    private void setLoading(String tittle, String message) {

    }
}
