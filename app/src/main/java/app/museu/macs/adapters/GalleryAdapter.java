package app.museu.macs.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.List;

import app.museu.macs.R;
import app.museu.macs.model.GalleryPhoto;

/**
 * Created by jeremias on 24/08/15.
 */
public class GalleryAdapter extends BaseAdapter {
    private List<GalleryPhoto> galleryPhotos;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public GalleryAdapter(Context context, List<GalleryPhoto> galleryPhotos, ImageLoader imageLoader) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.galleryPhotos = galleryPhotos;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return galleryPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GalleryPhoto galleryAdapter = galleryPhotos.get(position);
        ImageView imageView;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_image, null);
        }
        imageView = (ImageView) convertView.findViewById(R.id.galleryImageItem);

        imageLoader.displayImage(galleryAdapter.getUri(), imageView, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.i("ImageLoading", "onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.i("ImageLoading", "onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.i("ImageLoading", "onLoadingComplete");
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.i("ImageLoading", "onLoadingCancelled");
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                Log.i("Progress", current + "/" + total);
            }
        });

        return convertView;
    }
}
