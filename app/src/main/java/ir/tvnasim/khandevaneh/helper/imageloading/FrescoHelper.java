package ir.tvnasim.khandevaneh.helper.imageloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import ir.tvnasim.khandevaneh.R;
import okhttp3.OkHttpClient;

//import com.digikala.xei.StethoHelper;

/**
 * Created by hamidreza on 7/28/16.
 */
public class FrescoHelper {

    private static final String TAG_DEBUG = FrescoHelper.class.getSimpleName();

    private static Context sContext;

    public static void init(Context context, NetworkStack networkStack) {
        sContext = context;

        switch (networkStack) {
            case DEFAULT:
                Fresco.initialize(context);
                break;

            case OKHTTP:
//                ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory
//                        .newBuilder(context, StethoHelper.getOkHttpClient())
//                        .build();
//                Fresco.initialize(context, imagePipelineConfig);
                ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory
                        .newBuilder(context, new OkHttpClient())
                        .build();
                Fresco.initialize(context, imagePipelineConfig);
                break;

            default:
                Log.e(TAG_DEBUG, "Invalid Network Stack...");
        }
    }

    public static void loadImageFromNetwork(Context context, String url, boolean progressiveRenderingEnabled, final ImageLoadListener listener) {
        FrescoHelper.sContext = context;
        loadImageFromNetwork(url, progressiveRenderingEnabled, listener);
    }

    public static void loadImageFromNetwork(String url, boolean progressiveRenderingEnabled, final ImageLoadListener listener) {
        loadImageFromNetwork(url, progressiveRenderingEnabled, false, 0, 0, listener);
    }

    public static void loadImageFromNetwork(String url, boolean progressiveRenderingEnabled, final boolean mustScale, final int scaledWidth, final int scaledHeight, final ImageLoadListener listener) {
        if (sContext != null) {
            Uri uri = Uri.parse(url);

            ImagePipeline imagePipeline = Fresco.getImagePipeline();

            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(uri)
                    .setRequestPriority(Priority.HIGH)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setProgressiveRenderingEnabled(progressiveRenderingEnabled)
                    .build();

            final DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, sContext);
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onNewResultImpl(final Bitmap bitmap) {
                    if (bitmap != null) {
                        // We clone the bitmap, because Fresco recycle it and then try to use it.
                        int clonedBitmapWidth;
                        int clonedBitmapHeight;
                        if (mustScale) {
                            clonedBitmapWidth = scaledWidth;
                            clonedBitmapHeight = scaledHeight;
                        } else {
                            clonedBitmapWidth = bitmap.getWidth();
                            clonedBitmapHeight = bitmap.getHeight();
                        }

                        final Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, clonedBitmapWidth, clonedBitmapHeight, false);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                BitmapContainer bitmapContainer = new BitmapContainer(newBitmap, dataSource);
                                listener.onImageLoad(bitmapContainer);
                            }
                        });
                    } else {
                        listener.onFail("Null Bitmap");
                    }
                }

                @Override
                protected void onFailureImpl(final DataSource<CloseableReference<CloseableImage>> dataSource) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFail(dataSource.getFailureCause().getMessage());
                        }
                    });
                }
            }, CallerThreadExecutor.getInstance());

        } else {
            throw new RuntimeException("FrescoHelper must initialize in the Application class...");
        }
    }

    public static void setImageUrl(SimpleDraweeView simpleDraweeView, String imageUrl) {
        if (imageUrl != null) {
            Uri uri = Uri.parse(imageUrl);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(simpleDraweeView.getController())
                    .build();
            simpleDraweeView.setController(controller);
        }
    }

    public static void loadGifFromResources(SimpleDraweeView simpleDraweeView, int resourceId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resourceId))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();

        simpleDraweeView.setController(controller);
    }

}
