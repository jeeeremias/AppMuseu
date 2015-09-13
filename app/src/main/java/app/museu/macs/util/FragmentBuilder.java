package app.museu.macs.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import app.museu.macs.R;
import app.museu.macs.activities.HomeActivity;
import app.museu.macs.fragments.AgendaFragment;
import app.museu.macs.fragments.GalleryFragment;
import app.museu.macs.fragments.GetAgendaFragment;
import app.museu.macs.fragments.HomeFragment;
import app.museu.macs.fragments.ImageDisplayFragment;
import app.museu.macs.fragments.LocationFragment;
import app.museu.macs.fragments.PostYourPhotoFragment;

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

    public Fragment newFragment(int index) {
        switch (index) {
            case EnumFragment.HOME_FRAGMENT:
                fragment = HomeFragment.newInstance();
                break;
            case EnumFragment.GET_AGENDA_FRAGMENT:
                fragment = GetAgendaFragment.newInstance(homeActivity);
                break;
            case EnumFragment.GALLERY_FRAGMENT:
                fragment = GalleryFragment.newInstance(homeActivity);
                break;
            case EnumFragment.LOCATION_FRAGMENT:
                fragment = LocationFragment.newInstance();
                break;
            case EnumFragment.POST_YOUR_PHOTO_FRAGMENT:
                fragment = PostYourPhotoFragment.newInstance(homeActivity);
                break;
            case EnumFragment.AGENDA_FRAGMENT:
                fragment = AgendaFragment.newInstance(homeActivity);
                break;
            case EnumFragment.DISPLAY_IMAGE_FRAGMENT:
                fragment = ImageDisplayFragment.newInstance(homeActivity);
        }

        homeActivity.setCurrentFragment(fragment);

        if (fragment != null){
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        return fragment;
    }
}
