/**
 *
 */
package ir.tvnasim.khandevaneh.helper.webapi;

import android.os.Environment;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import ir.tvnasim.khandevaneh.BuildConfig;

/**
 * This class is a Web Api request and can attach to an activity or a fragment to send from its context.
 *
 * @author H-Hosseinkhani
 */
public class WebApiRequest<T> {

    private final GsonRequest<?> mGsonRequest;
    private final String mTag;
    private RequestStatus mStatus = RequestStatus.READY;
    private final LoadRequests mLoadRequestsInterface;
    private static OnForceUpdateListener mForceUpdateListener;

    public WebApiRequest(int httpMethod, final String query, HashMap<String, String> params, Type responseType, String requestTag, final WebApiListener webApiListener, LoadRequests loadRequestsInterface) {

        mTag = requestTag;
        mLoadRequestsInterface = loadRequestsInterface;

        Listener<WebApiResponse<T>> responseListener = new Listener<WebApiResponse<T>>() {
            @Override
            public void onResponse(WebApiResponse<T> response) {
//                response.status = WebApiResponse.STATUS_FORCE_UPDATE;
//                response.message = "http://bayanbox.ir/download/7205250095338898835/app-debug3.apk";

                if (response != null && response.status.equals(WebApiResponse.STATUS_FORCE_UPDATE)) {
                    if (query.equalsIgnoreCase(WebApiHelper.ENDPOINT_LOGIN)) {
                        mForceUpdateListener.checkPermission();

                        String[] linkSplits = response.message.split("/");

                        String apkName = linkSplits[linkSplits.length - 1];

                        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS);
                        dir.mkdirs();

                        File apkFile = new File(dir + File.separator + apkName);

                        if (!apkFile.exists()) {
                            mForceUpdateListener.forceUpdate();

//                            if (!DownloadService.isRunning()) {
//                                DownloadManager downloadManager = new DownloadManager();
//                                downloadManager.addTask(App.getApplication(), new DownloadTask(response.message, apkFile.getAbsolutePath()));
//                            }
                        } else {
                            mForceUpdateListener.downloadComplete(apkFile.getAbsolutePath());
                        }

                    } else {

                        webApiListener.onResponse(response.data);
                        mStatus = RequestStatus.SUCCESS;
                    }

                } else if (response != null && response.status.equals(WebApiResponse.STATUS_OPTIONAL_UPDATE)) {
                    if (query.equalsIgnoreCase(WebApiHelper.ENDPOINT_LOGIN)) {
                        mForceUpdateListener.optionalUpdate();
                        mForceUpdateListener.checkPermission();

                        startDownloadAPK(response.message);
                    }

                    webApiListener.onResponse(response.data);
                    mStatus = RequestStatus.SUCCESS;

                } else if (response != null && response.status.equals(WebApiResponse.STATUS_SUCCESS)) {
                    webApiListener.onResponse(response.data);
                    mStatus = RequestStatus.SUCCESS;
                } else {
                    mStatus = RequestStatus.ERROR;
                    String errorMsg = (response != null) ? response.message : "Volley Response is Null";
                    webApiListener.onErrorResponse(errorMsg);
                    if (mLoadRequestsInterface != null) {
                        mLoadRequestsInterface.reloadRequests();
                    }
                }
            }
        };

        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatus = RequestStatus.ERROR;
                webApiListener.onErrorResponse(error.getMessage());
                if (mLoadRequestsInterface != null) {
                    mLoadRequestsInterface.reloadRequests();
                }

                if (error.networkResponse == null && error.getClass().equals(TimeoutError.class)) {
                    // Timeout Error Occurred.
                }
            }
        };

        String url = BuildConfig.URL_WEBAPI + query;
        mGsonRequest = new GsonRequest<WebApiResponse<T>>(httpMethod, responseType, url, params, responseListener, errorListener);
    }

    private void startDownloadAPK(String linkUrl) {
//        String[] linkSplits = linkUrl.split("/");
//
//        String apkName = linkSplits[linkSplits.length - 1];
//
//        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS);
//        dir.mkdirs();
//
//        File apkFile = new File(dir + File.separator + apkName);
//
//        if (!apkFile.exists()) {
//            if (!DownloadService.isRunning()) {
//                DownloadManager downloadManager = new DownloadManager();
//                downloadManager.addTask(App.getApplication(), new DownloadTask(linkUrl, apkFile.getAbsolutePath()));
//            }
//        }
    }


    public RequestStatus getStatus() {
        return mStatus;
    }

    public void setStatus(RequestStatus status) {
        this.mStatus = status;
    }

    public void attach() {
        mLoadRequestsInterface.addRequestToLoadList(this, true);
    }

    public void attach(boolean loadDataOnResume) {
        mLoadRequestsInterface.addRequestToLoadList(this, loadDataOnResume);
    }

    public void send() {
        VolleyHelper.addToRequestQueue(mGsonRequest, mTag);
        mStatus = RequestStatus.SENT;
    }

    public static void setOnForceUpdateReceived(OnForceUpdateListener onForceUpdateListener) {
        mForceUpdateListener = onForceUpdateListener;
    }

    public enum RequestStatus {
        READY, SENT, SUCCESS, ERROR
    }

    /**
     * This class is the design model for all web-api responses.
     * field data is the actual we response.
     * field status is the status of response and can be Success, Fail and ForceUpdate.
     * field Message contains the message of the web server.
     *
     * @param <U>
     */
    static class WebApiResponse<U> {

        private static final String STATUS_SUCCESS = "True";
        private static final String STATUS_ERROR = "Error";
        private static final String STATUS_FAIL = "Fail";
        private static final String STATUS_FORCE_UPDATE = "ForceUpdate";
        private static final String STATUS_OPTIONAL_UPDATE = "OptionalUpdate";


        @SerializedName("result")
        private String status;

        @SerializedName("message")
        private String message;

        @SerializedName("content")
        private U data;

    }

    /**
     * This listener interface is used to listen the web response.
     *
     * @param <D> is the final response model Generics.
     */
    public interface WebApiListener<D> {
        void onResponse(D response);

        void onErrorResponse(String errorMessage);
    }

    public interface OnForceUpdateListener {
        void forceUpdate();

        void downloadComplete(String filePath);

        void checkPermission();

        void optionalUpdate();
    }

    /**
     * This Interface is implemented by XeiActivity and XeiFragment and has two method:
     * addRequestToLoadList: This method adds a WebApiRequest instance to the load queue of Activity(Fragment) with invoking from attach method.
     * loadDataOnResume indicates that the request must be send on resume screen
     * or we send it manually by invoking loadData method in Activity(Fragment).
     * reloadRequests: This method is responsible for reload requests in the queue when any error occurred.
     */
    public interface LoadRequests {
        void addRequestToLoadList(WebApiRequest<?> request, boolean loadDataOnResume);

        void reloadRequests();
    }

}