package app.museu.macs.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jeremias on 25/06/15.
 */
public class ProfilePhoto extends AsyncTask<Void, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(Void... params) {

        GraphRequest picGraphRequest = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/picture", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

            }
        });
        Bundle picParameters = new Bundle();
        picParameters.putString("redirect", "false");
        picParameters.putInt("height", 200);
        picParameters.putInt("width", 200);
        picGraphRequest.setParameters(picParameters);
        GraphResponse picGraphResponse = picGraphRequest.executeAndWait();
        Bitmap bitmap = null;
        try {
            JSONObject jsonObject = (JSONObject) picGraphResponse.getJSONObject().get("data");
            URL url = new URL((String) jsonObject.getString("url"));
            InputStream inputStream = url.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
