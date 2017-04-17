package ir.tvnasim.khandevaneh.helper.webapi;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a custom Volley request that returns a design-model object that parsed with Google Gson JSON Parser.
 * <p>
 * Created by hamidreza on 1/4/16.
 */
public class GsonRequest<T> extends Request<T> {

    private static final String TAG_DEBUG = GsonRequest.class.getSimpleName();

    private static final int INITIAL_TIMEOUT_MS_GET = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2;
    private static final int INITIAL_TIMEOUT_MS_POST = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 6;
    private static final int MAX_RETRIES_GET = DefaultRetryPolicy.DEFAULT_MAX_RETRIES + 2;
    private static final int MAX_RETRIES_POST = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
    private static final float BACKOFF_MULT_GET = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    private static final float BACKOFF_MULT_POST = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

    private static Gson sGson;

    private final Type mResponseType;
    private final Response.Listener<T> mResponseListener;
    private final HashMap<String, String> mParams;

    public GsonRequest(int method, Type responseType, String url, Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mResponseType = responseType;
        this.mParams = null;
        this.mResponseListener = responseListener;

        initGsonInstance(false);
        setRetryPolicies(method);
    }

    public GsonRequest(int method, Type responseType, String url, HashMap<String, String> params, Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mResponseType = responseType;
        this.mParams = params;
        this.mResponseListener = responseListener;

        initGsonInstance(false);
        setRetryPolicies(method);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseJsonStr = new String(response.data, "UTF-8");
            T result = sGson.fromJson(responseJsonStr, mResponseType);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
            return Response.error(new ParseError(uee));
        } catch (JsonSyntaxException jse) {
            Log.e("mmm", jse.getMessage());
            jse.printStackTrace();
            return Response.error(new ParseError(jse));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        try {
            mResponseListener.onResponse(response);
        } catch (NullPointerException npe) {
            Log.e(TAG_DEBUG, "responseListener is not initialized. exception message: " + npe, npe);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
//        headers.put("AppVersion", String.valueOf(Util.getAppVersionCode(App.getApplication())));
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return (mParams != null) ? mParams : super.getParams();
    }

    /**
     * This helper method instantiate a Gson object and assign it to sGson static member.
     * The Gson instance is compatible with Realm database.
     */
    private void initGsonInstance(boolean compatibleWithRealm) {
        if (compatibleWithRealm) {
//            sGson = new GsonBuilder()
//                    .setExclusionStrategies(new ExclusionStrategy() {
//                        @Override
//                        public boolean shouldSkipField(FieldAttributes f) {
//                            return f.getDeclaringClass().equals(RealmObject.class);
//                        }
//
//                        @Override
//                        public boolean shouldSkipClass(Class<?> clazz) {
//                            return false;
//                        }
//                    })
//                    .create();
        } else {
            sGson = new Gson();
        }
    }

    /**
     * This helper function sets HTTP request retry policies for each type of HTTP method.
     *
     * @param method indicates the HTTP method and can be Method.Get, Method.POST or etc.
     */
    private void setRetryPolicies(int method) {
        int initialTimeoutMs = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
        int maxNumRetries = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
        float backoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        if (method == Method.GET) {
            initialTimeoutMs = INITIAL_TIMEOUT_MS_GET;
            maxNumRetries = MAX_RETRIES_GET;
            backoffMultiplier = BACKOFF_MULT_GET;
        } else if (method == Method.POST) {
            initialTimeoutMs = INITIAL_TIMEOUT_MS_POST;
            maxNumRetries = MAX_RETRIES_POST;
            backoffMultiplier = BACKOFF_MULT_POST;
        }
        setRetryPolicy(new DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, backoffMultiplier));
    }
}