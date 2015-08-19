package app.museu.macs.async;

import android.app.Activity;
import android.os.AsyncTask;

import app.museu.macs.activities.HomeActivity;

/**
 * Created by jeremias on 07/07/15.
 */
public class AsyncHomeActivityTasks extends AsyncTask<Void, Void, Void> {

    private HomeActivity activity;

    public AsyncHomeActivityTasks(HomeActivity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    public HomeActivity getHomeActivity(){
        return this.activity;
    }

    public void setHomeActivity(HomeActivity activity){
        this.activity = activity;
    }
}
