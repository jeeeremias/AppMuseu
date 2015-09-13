package app.museu.macs.model;

import android.graphics.Bitmap;

/**
 * Created by jeremias on 07/09/15.
 */
public class PostFacebook {
    private Bitmap yourSelectedImage;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bitmap getYourSelectedImage() {
        return yourSelectedImage;
    }

    public void setYourSelectedImage(Bitmap yourSelectedImage) {
        this.yourSelectedImage = yourSelectedImage;
    }
}
