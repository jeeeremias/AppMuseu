package app.museu.macs.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.nostra13.universalimageloader.utils.IoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import app.museu.macs.activities.HomeActivity;
import app.museu.macs.fragments.GalleryFragment;
import app.museu.macs.model.GalleryPhoto;
import app.museu.macs.util.EnumFragment;
import app.museu.macs.util.FragmentBuilder;

/**
 * Created by jeremias on 25/06/15.
 */
public class GalleryPhotos extends AsyncHomeActivityTasks {

    private List<GalleryPhoto> galleryPhotos = new ArrayList<GalleryPhoto>();

    public GalleryPhotos(HomeActivity homeActivity) {
        super(homeActivity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(getHomeActivity().isDeviceOnline()) {

            URL next = null;
            JSONObject jsonObject;
            JSONArray jsonArray;
            InputStream inputStream;

            GraphRequest albumGraphRequest = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/866359736786937", new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    // Finish
                }
            });
            Bundle picParameters = new Bundle();
            picParameters.putString("fields", "photos{id,picture,source}");
            albumGraphRequest.setParameters(picParameters);
            GraphResponse albumGraphResponse = albumGraphRequest.executeAndWait();
            GalleryPhoto galleryPhoto;
            try {
                jsonObject = albumGraphResponse.getJSONObject().getJSONObject("photos");
                String responseJSON;
                while(true) {
                    jsonArray = jsonObject.getJSONArray("data");
                    int size = jsonArray.length();
                    JSONObject temp;
                    for (int i = 0; i < size; i ++) {
                        temp = jsonArray.getJSONObject(i);
                        galleryPhoto = new GalleryPhoto(temp.getString("id"), "", temp.getString("picture"), temp.getString("source"));
                        galleryPhotos.add(galleryPhoto);
                    }
                    try{
                        next = new URL(jsonObject.getJSONObject("paging").getString("next"));
                    } catch (JSONException e){
                        e.printStackTrace();
                        break;
                    }
                    HttpsURLConnection urlConnection = (HttpsURLConnection) next.openConnection();
                    inputStream = urlConnection.getInputStream();
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    responseJSON = streamReader.readLine();
                    jsonObject = new JSONObject(responseJSON);
                }
                getHomeActivity().setGalleryPhotos(galleryPhotos);
                getHomeActivity().getFragmentBuilder().newFragment(EnumFragment.GALLERY_FRAGMENT);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getHomeActivity().showToast("Sem conexão com a internet");
        }
        getHomeActivity().getProgressDialog().cancel();
        return null;
    }
}
