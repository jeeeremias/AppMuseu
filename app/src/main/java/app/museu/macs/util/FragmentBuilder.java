package app.museu.macs.util;

import android.support.v4.app.Fragment;

import app.museu.macs.fragments.GalleryFragment;
import app.museu.macs.fragments.GetAgendaFragment;
import app.museu.macs.fragments.HomeFragment;
import app.museu.macs.fragments.NewsFragment;
import app.museu.macs.fragments.AgendaFragment;

/**
 * Created by jeremias on 06/06/15.
 */
public class FragmentBuilder {
    public static Fragment newFragment (String name, int index) {
        Fragment mFragment = null;
        switch (index) {
            case 0:
                mFragment = HomeFragment.newInstance(name);
                break;
            case 1:
                mFragment = NewsFragment.newInstance(name);
                break;
            case 2:
                mFragment = GetAgendaFragment.newInstance(name);
                break;
            case 3:
                mFragment = GalleryFragment.newInstance(name);
                break;
        }
        return mFragment;
    }
}
