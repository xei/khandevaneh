package ir.iconish.khandevaneh.helper.imageloading;

/**
 * Created by hamidreza on 7/28/16.
 */
public interface ImageLoadListener {
    void onImageLoad(BitmapContainer bitmapContainer);
    void onFail(String errorMessage);
}