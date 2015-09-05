package app.museu.macs.util;

import android.support.v4.app.Fragment;

import app.museu.macs.activities.HomeActivity;
import app.museu.macs.fragments.GalleryFragment;
import app.museu.macs.fragments.GetAgendaFragment;
import app.museu.macs.fragments.HomeFragment;
import app.museu.macs.fragments.LocationFragment;
import app.museu.macs.fragments.NewsFragment;

/**
 * Created by jeremias on 06/06/15.
 */
public class FragmentBuilder {
    public static Fragment newFragment(HomeActivity homeActivity, String name, int index) {
        Fragment mFragment = null;
        switch (index) {
            case 0:
                mFragment = HomeFragment.newInstance(name);
                break;
            case 1:
                mFragment = GetAgendaFragment.newInstance(name);
                break;
            case 2:
                mFragment = GalleryFragment.newInstance(homeActivity, name);
                break;
            case 3:
                mFragment = LocationFragment.newInstance(name);
                break;
        }
        homeActivity.setCurrentFragment(mFragment);
        return mFragment;
    }
}
