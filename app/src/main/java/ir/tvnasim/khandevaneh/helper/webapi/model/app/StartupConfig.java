package ir.tvnasim.khandevaneh.helper.webapi.model.app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/3/17.
 */

public class StartupConfig {

    public static final int VERSION_STATE_NEED_UPDATE = 0;
    public static final int VERSION_STATE_VALID_VERSION = 1;

    @SerializedName("versionState")
    private int versionState;

    @SerializedName("latestApk")
    private String latestApk;

    public int getVersionState() {
        return versionState;
    }

    public void setVersionState(int versionState) {
        this.versionState = versionState;
    }

    public String getLatestApk() {
        return latestApk;
    }

    public void setLatestApk(String latestApk) {
        this.latestApk = latestApk;
    }
}
