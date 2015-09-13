package app.museu.macs.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhotoContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import app.museu.macs.fragments.GalleryFragment;
import app.museu.macs.fragments.PostYourPhotoFragment;
import app.museu.macs.model.GalleryPhoto;
import app.museu.macs.model.PostFacebook;

/**
 * Created by jeremias on 25/06/15.
 */
public class PostYourPhoto extends AsyncTask<Void, Void, Void> {

    private PostFacebook postFacebook;

    public PostYourPhoto(PostFacebook postFacebook) {
        this.postFacebook = postFacebook;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (postFacebook != null) {
            Bundle picParameters = new Bundle();
            String graphPath;

            if (postFacebook.getYourSelectedImage() != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                postFacebook.getYourSelectedImage().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] data = byteArrayOutputStream.toByteArray();
                picParameters.putByteArray("object_attachment", data);
                graphPath = "/me/photos";
            } else {
                graphPath = "/me/feed";
            }

            picParameters.putString("message", postFacebook.getMessage() + " #souMACS #AppMacs");
            picParameters.putString("place", "142526819170236");

            GraphRequest picGraphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), graphPath, picParameters, HttpMethod.POST, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    // Finish
                }
            });

            GraphResponse graphResponse = picGraphRequest.executeAndWait();
            Log.i("response", graphResponse.toString());
        }

        return null;
    }
}
