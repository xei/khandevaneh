package ir.iconish.khandevaneh.account.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.account.User;
import ir.iconish.khandevaneh.account.UserInfo;
import ir.iconish.khandevaneh.app.BaseActivity;
import ir.iconish.khandevaneh.app.ScoresContainer;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;
import ir.iconish.khandevaneh.view.KhandevanehDialog;

public class ProfileCompletionActivity extends BaseActivity {

    private static final String TAG_REQUEST_EDIT_USER_INFO = "requestTag_profileCompletionActivity_editUserInfo";

    private int mCurrentFragment = UserInfoFragment.WHICH_FRAGMENT_FIRST_NAME;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, ProfileCompletionActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_EDIT_USER_INFO);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_completion);

        changeFragment(R.id.activityProfileCompletion_frameLayout_fragmentContainer, UserInfoFragment.newInstance(mCurrentFragment), false);
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
                User.getInstance().setAvatarEncodedBitmap(enteredValue);
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

        if (whichFragment == UserInfoFragment.WHICH_FRAGMENT_LAST_NAME) {
            mCurrentFragment++;
            changeFragment(R.id.activityProfileCompletion_frameLayout_fragmentContainer, UserAvatarFragment.newInstance(), true);
        } else if (whichFragment < UserInfoFragment.WHICH_FRAGMENT_POSTAL_ADDRESS) {
            changeFragment(R.id.activityProfileCompletion_frameLayout_fragmentContainer, UserInfoFragment.newInstance(++mCurrentFragment), true);
        } else if (whichFragment == UserInfoFragment.WHICH_FRAGMENT_POSTAL_ADDRESS) {
            sendUpdatedUserInfoToApi();
        } else {
            Log.e(TAG_DEBUG, "invalid fragment number!");
        }

    }

    @Override
    public void onBackPressed() {
        mCurrentFragment--;
        super.onBackPressed();
    }

    private void sendUpdatedUserInfoToApi() {
        WebApiHelper.editUserInfo(User.getInstance(), TAG_REQUEST_EDIT_USER_INFO, new WebApiRequest.WebApiListener<UserInfo>() {
            @Override
            public void onResponse(UserInfo userInfo, final ScoresContainer scoresContainer) {

                if (userInfo != null) {
                    User.getInstance().setUserInfo(userInfo);
                    User.getInstance().setAvatarEncodedBitmap(null);

                    new KhandevanehDialog(ProfileCompletionActivity.this, "مشخصات شما ثبت شد.", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (scoresContainer != null) {
                                updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), new OnShakingFinishedListener() {
                                    @Override
                                    public void onShakingFinish() {
                                        finish();
                                    }
                                });
                            } else {
                                finish();
                            }
                        }
                    }).show();
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "editUserInfo request failed: " + errorMessage);
                new KhandevanehDialog(ProfileCompletionActivity.this, "یه مشکلی در ارتباط با سرور وجود داره :(", null).show();
            }
        }, null).send();
    }
}
