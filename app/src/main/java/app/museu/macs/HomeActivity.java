package app.museu.macs;


import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;

import com.facebook.Profile;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;


public class HomeActivity extends NavigationLiveo implements br.liveo.interfaces.OnItemClickListener {

    private HelpLiveo mHelpLiveo;
    Profile profile;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        profile = Profile.getCurrentProfile();
    }

    @Override
    public void onInt(Bundle bundle) {
        // User Information
        String name = "Jeremias";
//        if (profile != null) {
//            name = profile.getName();
//        } else {
//            name = "Não logado";
//        }
        this.userName.setText(name);
        this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

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
                .footerItem(R.string.action_settings, R.mipmap.ic_launcher)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();
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
            closeDrawer();
        }
    };
}