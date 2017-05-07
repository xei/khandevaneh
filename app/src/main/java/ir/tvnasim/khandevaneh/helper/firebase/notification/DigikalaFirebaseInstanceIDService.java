package ir.tvnasim.khandevaneh.helper.firebase.notification;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by hamidreza on 11/13/16.
 */

public class DigikalaFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG_DEBUG = DigikalaFirebaseInstanceIDService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG_DEBUG, "FCM Token Refreshed: " + refreshedToken);

        sendRegistrationTokenToBackOffice(refreshedToken);
    }

    /**
     * Associate the new token with the Digikala user table.
     * @param refreshedToken The refreshed FCM InstanceID token
     */
    private void sendRegistrationTokenToBackOffice(final String refreshedToken) {
//        WebApi.makeSetFCMTokenRequest(refreshedToken, new GApiRequest.GWebListener<Boolean>() {
//            @Override
//            public void onResponce(Boolean succeed) {
//                if (succeed) {
//                    Log.i(TAG_DEBUG, "FCM Token has set successfully.");
//                } else {
//                    Log.i(TAG_DEBUG, "FCM Token has not set successfully.");
//                }
//            }
//
//            @Override
//            public void onError(String string) {
//                Log.i(TAG_DEBUG, "There is an error while setting FCM to the corresponding user");
//
//                // There is a bug on the server side that don't let us set token more than once.
//                // It force us to edit the token.
//                WebApi.makeEditFCMTokenRequest(refreshedToken, new GApiRequest.GWebListener<Boolean>() {
//                    @Override
//                    public void onResponce(Boolean succeed) {
//                        if (succeed) {
//                            Log.i(TAG_DEBUG, "FCM Token has edit successfully.");
//                        } else {
//                            Log.i(TAG_DEBUG, "FCM Token has not edit successfully.");
//                        }
//                    }
//
//                    @Override
//                    public void onError(String string) {
//                        Log.i(TAG_DEBUG, "There is an error while editing FCM for the corresponding user.");
//                    }
//                }).send();
//            }
//        }).send();
    }
}
