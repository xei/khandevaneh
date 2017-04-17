package ir.tvnasim.khandevaneh.helper.imageloading;

import android.content.Context;

import ir.tvnasim.khandevaneh.BuildConfig;


/**
 * Created by hamidreza on 7/28/16.
 */
public class ImageLoader {

    private static final String TAG_DEBUG = ImageLoader.class.getSimpleName();

    public static void init(Context context) {
        FrescoHelper.init(context, NetworkStack.OKHTTP);
    }

    public static void loadImageFromNetwork(String url, final ImageLoadListener listener) {
        FrescoHelper.loadImageFromNetwork(url, BuildConfig.ENABLE_PROGRESSIVE_JPEG_RENDERING, listener);
    }

    public static void loadScaledImageFromNetwork(String url, int width, int height, final ImageLoadListener listener) {
        FrescoHelper.loadImageFromNetwork(url, BuildConfig.ENABLE_PROGRESSIVE_JPEG_RENDERING, true, width, height, listener);
    }
}
