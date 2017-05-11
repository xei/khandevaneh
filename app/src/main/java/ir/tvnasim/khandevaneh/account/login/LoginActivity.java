package ir.tvnasim.khandevaneh.account.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.SharedPreferencesHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.model.user.Token;
import ir.tvnasim.khandevaneh.helper.webapi.model.user.UserInfo;

public class LoginActivity extends BaseActivity implements PhoneNoFragment.OnSendButtonClickListener, VerificationCodeFragment.OnSendButtonClickListener {

    private static final String TAG_REQUEST_REGISTER_PHONE_NO = "requestTag_loginActivity_registerPhoneNo";
    private static final String TAG_REQUEST_AUTH_WITH_CREDENTIALS = "requestTag_loginActivity_authWithCredentials";


    public static void start(Context starter) {
        Intent intent = new Intent(starter, LoginActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_REGISTER_PHONE_NO);
        tags.add(TAG_REQUEST_AUTH_WITH_CREDENTIALS);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        changeFragment(R.id.activityLogin_frameLayout_fragmentContainer, PhoneNoFragment.newInstance(), false);
    }

    @Override
    public void onSendPhoneNoButtonClick(final String phoneNo) {

        WebApiHelper.registerPhoneNo(phoneNo, TAG_REQUEST_REGISTER_PHONE_NO, new WebApiRequest.WebApiListener<Object>() {
            @Override
            public void onResponse(Object response) {
                //TODO if ack
                changeFragment(R.id.activityLogin_frameLayout_fragmentContainer, VerificationCodeFragment.newInstance(phoneNo), true);
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                // TODO: remove it
                changeFragment(R.id.activityLogin_frameLayout_fragmentContainer, VerificationCodeFragment.newInstance(phoneNo), true);
            }
        }, null).send();

    }

    @Override
    public void onSendVerificationCodeButtonClick(String phoneNo, String verificationCode) {
        WebApiHelper.authenticateWithCredentials(phoneNo, verificationCode, TAG_REQUEST_AUTH_WITH_CREDENTIALS, new WebApiRequest.WebApiListener<Token>() {
            @Override
            public void onResponse(Token token) {
                User.getInstance().setAccessToken(token.getAcessToken());
                User.getInstance().setRefreshToken(token.getRefreshToken());
                SharedPreferencesHelper.storeAccessToken(token.getAcessToken());
                SharedPreferencesHelper.storeRefreshToken(token.getRefreshToken());
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WebApiHelper.getUserInfo("TAG_REQUEST_GET_USER_INFO", new WebApiRequest.WebApiListener<UserInfo>() {
                            @Override
                            public void onResponse(UserInfo userInfo) {
                                User.getInstance().setFirstName(userInfo.getFirstName());
                                User.getInstance().setLastName(userInfo.getLastName());
                                User.getInstance().setAvatar(userInfo.getAvatar());
                                User.getInstance().setMelonScore(userInfo.getMelonScore());
                                User.getInstance().setExperienceScore(userInfo.getExperienceScore());

                                finish();
                            }

                            @Override
                            public void onErrorResponse(String errorMessage) {

                            }
                        }, null).send();
                    }
                }, 1000);
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();

    }

    private void changeFragment(int fragmentContainerId, Fragment fragment, boolean pushToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, fragment);
        if (pushToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
