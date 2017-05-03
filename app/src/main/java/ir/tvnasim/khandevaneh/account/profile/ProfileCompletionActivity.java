package ir.tvnasim.khandevaneh.account.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;

public class ProfileCompletionActivity extends BaseActivity {

    private int mCurrentFragment = UserInfoFragment.WHICH_FRAGMENT_FIRST_NAME;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, ProfileCompletionActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_completion);

        changeFragment(R.id.activityProfileCompletion_frameLayout_fragmentContainer, UserInfoFragment.newInstance(mCurrentFragment), false);
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_profile;
    }

    private void changeFragment(int fragmentContainerId, Fragment fragment, boolean pushToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, fragment);
        if (pushToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    void onInfoEnter(int whichFragment, String enteredValue) {

        switch (whichFragment) {
            case UserInfoFragment.WHICH_FRAGMENT_FIRST_NAME:
                User.getInstance().setFirstName(enteredValue);
                break;

            case UserInfoFragment.WHICH_FRAGMENT_LAST_NAME:
                User.getInstance().setLastName(enteredValue);
                break;

            case UserInfoFragment.WHICH_FRAGMENT_AVATAR:
                User.getInstance().setAvatar(enteredValue);
                break;

            case UserInfoFragment.WHICH_FRAGMENT_EMAIL_ADDRESS:
                User.getInstance().setEmailAddress(enteredValue);
                break;

            case UserInfoFragment.WHICH_FRAGMENT_POSTAL_ADDRESS:
                User.getInstance().setPostalAddress(enteredValue);
                break;

            default:
                break;
        }

        if (whichFragment < 4) {
            changeFragment(R.id.activityProfileCompletion_frameLayout_fragmentContainer, UserInfoFragment.newInstance(++mCurrentFragment), true);
        } else if (whichFragment == 4) {
            WebApiHelper.editUserInfo(User.getInstance(), "requestTag_profileCompletionActivity_editUserInfo", new WebApiRequest.WebApiListener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    User.getInstance().setIsProfileComplete(true);
                    finish();
                }

                @Override
                public void onErrorResponse(String errorMessage) {

                }
            }, null);
        } else {
            Log.e(TAG_DEBUG, "invalid fragment number!");
        }
    }

}
