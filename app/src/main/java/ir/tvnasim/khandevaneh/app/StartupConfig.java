package ir.tvnasim.khandevaneh.app;

/**
 * Created by hamidreza on 4/14/17.
 */

public class StartupConfig {

    public static final int VERSION_STATE_NEED_UPDATE = 0;
    public static final int VERSION_STATE_VALID_VERSION = 1;

    // SerializedName("version_state")
    private int versionState;

    // SerializedName("latest_apk")
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
