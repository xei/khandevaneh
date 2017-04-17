package ir.tvnasim.khandevaneh.helper.webapi;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by H.Hosseinkhani on 10/29/2015.
 */
public class VolleyHelper {

    private static final String TAG_REQUEST = "NO TAG";

    private static boolean sInitialized;
    private static Context sContext;
    private static RequestQueue sRequestQueue;

    private VolleyHelper() {}

    public static void init(Context appContext) {
        sContext = appContext;
        sRequestQueue = Volley.newRequestQueue(sContext, new OkHttpStack());
        sInitialized = true;
    }

    public static <T> void addToRequestQueue(Request<T> req) {
        if(sInitialized) {
            req.setTag(TAG_REQUEST);
            sRequestQueue.add(req);
        } else {

        }
    }

    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        if(sInitialized) {
            req.setTag(TextUtils.isEmpty(tag) ? TAG_REQUEST : tag);
            sRequestQueue.add(req);
            VolleyLog.d("Request sent: %s", req.getUrl());
        } else {

        }
    }

    public static void cancelPendingRequests(Object tag) {
        if(sInitialized) {
            sRequestQueue.cancelAll(tag);
        } else {

        }
    }

    public static void clearRequestQueue() {
        if (sInitialized) {
            sRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
            sRequestQueue = Volley.newRequestQueue(sContext, new OkHttpStack());
        } else {

        }
    }

}