package app.museu.macs.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import app.museu.macs.activities.HomeActivity;

/**
 * Created by jeremias on 25/06/15.
 */
public class ProfilePhoto extends AsyncHomeActivityTasks {

    static final private String SOURCE_PHOTO = "userPhoto";

    public ProfilePhoto(HomeActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {

        GraphRequest picGraphRequest = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/picture", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                // Finish
            }
        });
        Bundle picParameters = new Bundle();
        picParameters.putString("redirect", "false");
        picParameters.putInt("height", 150);
        picParameters.putInt("width", 150);
        picGraphRequest.setParameters(picParameters);
        GraphResponse picGraphResponse = picGraphRequest.executeAndWait();
        Bitmap bitmap = null;
        try {
            JSONObject jsonObject = (JSONObject) picGraphResponse.getJSONObject().get("data");
            URL url = new URL((String) jsonObject.getString("url"));
            InputStream inputStream = url.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            FileOutputStream fileOutputStream = getHomeActivity().getApplicationContext().openFileOutput(SOURCE_PHOTO, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            getHomeActivity().userPhoto.setImageBitmap(bitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
