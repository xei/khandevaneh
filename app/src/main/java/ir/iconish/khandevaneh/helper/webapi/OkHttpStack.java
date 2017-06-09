package ir.iconish.khandevaneh.helper.webapi;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.internal.huc.OkHttpsURLConnection;

/**
 * An {@link com.android.volley.toolbox.HttpStack HttpStack} implementation which
 * uses OkHttp as its transport.
 */
class OkHttpStack extends HurlStack {
    private OkHttpClient mOkHttpClient;

    OkHttpStack() {
        this.mOkHttpClient = new OkHttpClient();
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return new OkHttpsURLConnection(url, mOkHttpClient);
    }

}