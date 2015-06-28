package app.museu.macs.async;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by jeremias on 26/06/15.
 */
public class AlbumPhotos extends AsyncTask<URL, Void, List<InputStream>> {

    @Override
    protected List<InputStream> doInBackground(URL... params) {
        List<InputStream> inputStream = null;
        for (URL param : params) {
            try {
                inputStream.add(param.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }
}
