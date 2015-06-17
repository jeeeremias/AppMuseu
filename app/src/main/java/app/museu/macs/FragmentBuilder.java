package app.museu.macs;

import android.support.v4.app.Fragment;

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
                mFragment = AgendaFragment.newInstance(name);
                break;
            case 3:
                mFragment = GalleryFragment.newInstance(name);
                break;
        }
        return mFragment;
    }
}
