package ir.tvnasim.khandevaneh.helper.firebase.notification;//package com.digikala.xei.analytics.firebase.notification;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//
///**
// * Created by hamidreza on 11/29/16.
// */
//
//public class FirebaseSubscriptionHandler {
//
//    private static final String TOPIC_ALL_USERS = "all_users";
//    private static final String TOPIC_SANDBOX = "sandbox";
//    private static final String TOPIC_LOGGED_INS = "logged_ins";
//    private static final String TOPIC_PURCHASERS = "purchasers";
//
//    /**
//     *
//     */
//    public static void subscribeToAllUsers() {
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_ALL_USERS);
//    }
//
//    /**
//     *
//     */
//    public static void unsubscribeFromAllUsers() {
//        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_ALL_USERS);
//    }
//
//    /**
//     *
//     */
//    public static void subscribeToSandbox() {
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_SANDBOX);
//    }
//
//    /**
//     *
//     */
//    public static void unsubscribeFromSandbox() {
//        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_SANDBOX);
//    }
//
//    /**
//     *
//     */
//    public static void subscribeToLoggedIns() {
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_LOGGED_INS);
//    }
//
//    /**
//     *
//     */
//    public static void unsubscribeFromLoggedIns() {
//        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_LOGGED_INS);
//    }
//
//    /**
//     *
//     */
//    public static void subscribeToPurchasers() {
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_PURCHASERS);
//    }
//
//    /**
//     *
//     */
//    public static void unsubscribeFromPurchasers() {
//        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_PURCHASERS);
//    }
//}
