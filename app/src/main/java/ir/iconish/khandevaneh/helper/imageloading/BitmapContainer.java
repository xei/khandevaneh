package ir.iconish.khandevaneh.helper.imageloading;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.image.CloseableImage;

/**
 * Created by hamidreza on 8/6/16.
 */
public class BitmapContainer {

    private Bitmap mBitmap;
    private DataSource<CloseableReference<CloseableImage>> mFrescoDataSource;
    private ImageLoader.ImageContainer mVolleyImageContainer;

    public BitmapContainer(Bitmap bitmap, DataSource<CloseableReference<CloseableImage>> frescoDataSource) {
        this.mBitmap = bitmap;
        this.mFrescoDataSource = frescoDataSource;
    }

    public BitmapContainer(Bitmap bitmap, ImageLoader.ImageContainer volleyImageContainer) {
        this.mBitmap = bitmap;
        this.mVolleyImageContainer = volleyImageContainer;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public DataSource<CloseableReference<CloseableImage>> getFrescoDataSource() {
        return mFrescoDataSource;
    }

    public ImageLoader.ImageContainer getVolleyImageContainer() {
        return mVolleyImageContainer;
    }

    public void cancelRequest() {
        if(mFrescoDataSource != null) {
            mFrescoDataSource.close();
            return;
        }
        if(mVolleyImageContainer != null) {
            mVolleyImageContainer.cancelRequest();
            return;
        }
    }

}
