package ir.iconish.khandevaneh.app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/3/17.
 */

public class StartupConfig {

    public static final int VERSION_STATE_DEPRECATED = 1;
    public static final int VERSION_STATE_VALID = 2;
    public static final int VERSION_STATE_OLD = 3;

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
