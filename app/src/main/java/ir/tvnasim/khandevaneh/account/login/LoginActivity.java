package ir.tvnasim.khandevaneh.account.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.Toast;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;

public class LoginActivity extends BaseActivity implements PhoneNoFragment.OnSendButtonClickListener, VerificationCodeFragment.OnSendButtonClickListener {
    public static void start(Context starter) {
        Intent intent = new Intent(starter, LoginActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        changeFragment(R.id.activityLogin_frameLayout_fragmentContainer, PhoneNoFragment.newInstance(), false);
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_login;
    }

    @Override
    public void onSendPhoneNoButtonClick(String phoneNo) {
        // TODO: send phone number to api and get ack
        changeFragment(R.id.activityLogin_frameLayout_fragmentContainer, VerificationCodeFragment.newInstance(phoneNo), true);
    }

    @Override
    public void onSendVerificationCodeButtonClick(String verificationCode) {
        // TODO: send verification code to api and get auth token (login)
        User.getInstance().setAccessToken("accessToken");
        //TODO: get UserInfo from api with access token
        User.getInstance().setName("علیرضا");
        User.getInstance().setAvatar("http://img.bisms.ir/2015/06/rambod-javan-2.jpg");
        User.getInstance().setMelonScore(200);
        User.getInstance().setExperienceScore(300);
        finish();
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
