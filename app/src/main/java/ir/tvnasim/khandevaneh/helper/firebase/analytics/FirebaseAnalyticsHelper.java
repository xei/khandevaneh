package ir.tvnasim.khandevaneh.helper.firebase.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by hamidreza on 11/17/16.
 * All rights reserved by Digikala.
 */

public class FirebaseAnalyticsHelper {

    private static final String TAG_DEBUG = FirebaseAnalyticsHelper.class.getSimpleName();

    private static FirebaseAnalytics sFirebaseAnalytics;

    public static void init(Context context) {
        sFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void signUp(String signUpMethod, boolean succeed, String userId, String emailAddress) {

        //        FirebaseSubscriptionHandler.subscribeToLoggedIns();

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, signUpMethod);
        bundle.putString("succeed", String.valueOf(succeed));
        bundle.putString("email_Address", emailAddress);

        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

//    @Override
//    public void signIn(String userId, String userName, String loginMethod, boolean succeed, String emailAddress) {
//
////        FirebaseSubscriptionHandler.subscribeToLoggedIns();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("method", loginMethod);
//        bundle.putString("succeed", String.valueOf(succeed));
//        bundle.putString("email_address", emailAddress);
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
//    }
//
//    @Override
//    public void signOut(String userId, String userName, String logoutMethod, boolean succeed, String emailAddress) {
//
////        FirebaseSubscriptionHandler.unsubscribeFromLoggedIns();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("method", logoutMethod);
//        bundle.putString("succeed", String.valueOf(succeed));
//        bundle.putString("email_address", emailAddress);
//
//        sFirebaseAnalytics.logEvent("sign_out", bundle);
//    }
//
//    @Override
//    public void search(String query, String buttonName) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
//        bundle.putString("search_button", buttonName);
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
//    }
//
//    @Override
//    public void contentView(String productId, String productName, boolean isIncredibleOffer) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productName);
//        bundle.putString("is_incredible_offer", String.valueOf(isIncredibleOffer));
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
//    }
//
//    @Override
//    public void open3DView(String productId, String productTitle) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productTitle);
//
//        sFirebaseAnalytics.logEvent("open_3d_view", bundle);
//    }
//
//    @Override
//    public void playVideo(String productId, String productTitle, String videoTitle, String videoUrl) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productTitle);
//        bundle.putString("video_title", videoTitle);
//        bundle.putString("video_url", videoUrl);
//
//        sFirebaseAnalytics.logEvent("play_video", bundle);
//    }
//
//    @Override
//    public void share(String productId, String deepLink) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString("product_deep_link", deepLink);
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
//    }
//
//    @Override
//    public void shareVideo(String productId, String deepLink) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString("product_deep_link", deepLink);
//
//        sFirebaseAnalytics.logEvent("share_video", bundle);
//    }
//
//    @Override
//    public void addToWishlist(String productId, String productTitle, String addOrRemove) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productTitle);
//        bundle.putString("add_or_remove", addOrRemove);
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, bundle);
//    }
//
//    @Override
//    public void notifyMe(String productId, String productTitle) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productTitle);
//
//        sFirebaseAnalytics.logEvent("notify_me", bundle);
//    }
//
//    @Override
//    public void addToCart(String productId, String productTitle, String configId, String sellerId, String sellerTitle, double price, boolean isIncredibleOffer, boolean hasGift) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productTitle);
//        bundle.putString("variant_id", configId);
//        bundle.putString("seller_id", sellerId);
//        bundle.putString("seller_title", sellerTitle);
//        bundle.putString(FirebaseAnalytics.Param.PRICE, String.valueOf(price));
//        bundle.putString("is_incredible_offer", String.valueOf(isIncredibleOffer));
//        bundle.putString("has_gift", String.valueOf(hasGift));
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);
//    }
//
//    @Override
//    public void startCheckout(String cartId, double totalPrice, int itemCount) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString("cart_id", "DKC-" + cartId);
//        bundle.putString(FirebaseAnalytics.Param.PRICE, String.valueOf(totalPrice));
//        bundle.putString(FirebaseAnalytics.Param.QUANTITY, String.valueOf(itemCount));
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle);
//    }
//
//    @Override
//    public void purchase(boolean successfulPurchase, String cartId, int cartItemsCount, double cartPrice) {
//
////        FirebaseSubscriptionHandler.subscribeToPurchasers();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("succeed", String.valueOf(successfulPurchase));
//        bundle.putString("cart_id", "DKC-" + cartId);
//        bundle.putString(FirebaseAnalytics.Param.QUANTITY, String.valueOf(cartItemsCount));
//        bundle.putString(FirebaseAnalytics.Param.PRICE, String.valueOf(cartPrice));
//
//        sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
//    }

}
