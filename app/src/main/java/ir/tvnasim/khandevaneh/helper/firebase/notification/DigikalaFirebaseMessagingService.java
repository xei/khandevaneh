package ir.tvnasim.khandevaneh.helper.firebase.notification;//package com.digikala.xei.analytics.firebase.notification;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//
//import com.digikala.R;
//import com.digikala.activities.SplashActivity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Map;
//
///**
// * Created by hamidreza on 11/13/16.
// */
//
//public class DigikalaFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG_DEBUG = DigikalaFirebaseMessagingService.class.getSimpleName();
//
//    /**
//     * Called when message is received form FCM.
//     * There are two types of messages:
//     * 1. data messages: Data messages are the type traditionally used with GCM and are handled here
//     * whether the app is in the foreground or background.
//     * 2. notification messages: Notification messages are only received here when the app is in the
//     * foreground. When the app is in the background an automatically generated notification is displayed.
//     *
//     * Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//     *
//     * @param remoteMessage Object representing the message.
//     */
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.i(TAG_DEBUG, "FCM message is received from: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        Map<String, String> dataPayload = remoteMessage.getData();
//        if (dataPayload.size() > 0) {
//            Log.i(TAG_DEBUG, "Message has data payload: " + dataPayload);
//
//            try {
//                handleDigikalaDataMessage(dataPayload);
//            } catch (Exception e) {
//                Log.d(TAG_DEBUG, "Invalid Data Payload: " + e.getMessage());
//            }
//        }
//
//        // Check if message contains a notification payload.
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        if (notification != null) {
//            Log.d(TAG_DEBUG, "Message has Notification Body: " + notification.getBody());
//
//            handleDigikalaInterstitial(notification);
//        }
//    }
//
//    /**
//     * This method will call when the message is send from the FCM REST API (Digikala BackOffice).
//     * @param dataPayload is a map containing the key-value pairs.
//     */
//    private void handleDigikalaDataMessage(Map<String, String> dataPayload) throws Exception{
//        int id = Integer.parseInt(dataPayload.get("Id"));
//        String contentTitle = dataPayload.get("Title");
//        String contentText = dataPayload.get("Description");
//        String footerText = dataPayload.get("Footer");
//        String notificationImageUrl = dataPayload.get("ImagePath");
//        Bitmap notificationImage = downloadBitmap(notificationImageUrl);
//        int requestCode = id;
//        String linkType = dataPayload.get("LinkType");
//        String linkValue = dataPayload.get("LinkValue");
//
//        if(linkType == null || linkValue == null) return;
//        sendNotification(id, contentTitle, contentText, footerText, notificationImage, requestCode, linkType, linkValue);
//        Log.d(TAG_DEBUG, "Notification Sent:"
//                + "\nID: " + id
//                + "\nContentTitle: " + contentTitle
//                + "\nContent Text: " + contentText
//                + "\nNotification Image Url: " + notificationImageUrl
//                + "\nLink Type: " + linkType
//                + "\nLink Value: " + linkValue);
//    }
//
//    /**
//     * This method will call when the user is in the app and notification message receive.
//     * We can use it as an interstitial banner.
//     * @param notification
//     */
//    private void handleDigikalaInterstitial(RemoteMessage.Notification notification) {
//        // TODO: Handle Interstitial Banner Here...
//    }
//
//    /**
//     * Create and show a notification containing the received FCM message.
//     * @param notificationId we use it to control the number of notifications on the system tray.
//     * @param contentTitle the title of the notification
//     * @param contentText the context of the notification
//     * @param notificationImage use it for Big-Picture-Style notifications.
//     * @param requestCode we use it for pending intent.
//     * @param linkType this indicate the type of notification and handled in SplashActivity
//     * @param linkValue this is a value that passed to the destination for a common use.
//     */
//    private void sendNotification(int notificationId, String contentTitle, String contentText, String footer, Bitmap notificationImage, int requestCode, String linkType, String linkValue) {
//        Intent intent = new Intent(this, SplashActivity.class);
//        intent.putExtra(SplashActivity.KEY_LINK_TYPE, linkType);
//        intent.putExtra(SplashActivity.KEY_LINK_VALUE, linkValue);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setSmallIcon(R.drawable.ic_stat_notification)
//                .setContentTitle(contentTitle)
//                .setContentText(contentText)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//        if(notificationImage != null) {
//            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//                    .setBigContentTitle(contentTitle)
//                    .setSummaryText(contentText)
//                    .bigPicture(notificationImage));
//        } else {
//            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
//                    .setBigContentTitle(contentTitle)
//                    .bigText(contentText)
//                    .setSummaryText(footer));
//        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationId, notificationBuilder.build());
//    }
//
//    /**
//     * Download an image on the service thread (block the thread till completing download).
//     * @param imageUrl The image url
//     * @return
//     */
//    public Bitmap downloadBitmap(String imageUrl) {
//        URL url;
//        try {
//            url = new URL(imageUrl);
//        } catch (MalformedURLException mue) {
//            Log.e(TAG_DEBUG, "Invalid Image Url: " + mue.getMessage());
//            return null;
//        }
//        HttpURLConnection connection;
//        try {
//            connection = (HttpURLConnection) url.openConnection();
//        } catch (IOException ioe) {
//            Log.e(TAG_DEBUG, "Can not open connection: " + ioe.getMessage());
//            return null;
//        }
//        connection.setDoInput(true);
//        try {
//            connection.connect();
//        } catch (IOException ioe) {
//            Log.e(TAG_DEBUG, "Can not connect to image server: " + ioe.getMessage());
//            return null;
//        }
//        InputStream input;
//        try {
//            input = connection.getInputStream();
//        } catch (IOException ioe) {
//            Log.e(TAG_DEBUG, "Can not get input stream: " + ioe.getMessage());
//            return null;
//        }
//        return BitmapFactory.decodeStream(input);
//    }
//
//}