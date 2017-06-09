package ir.iconish.khandevaneh.account.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.account.User;
import ir.iconish.khandevaneh.app.AboutAppActivity;
import ir.iconish.khandevaneh.app.BaseActivity;
import ir.iconish.khandevaneh.helper.imageloading.FrescoHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiHelper;
import ir.iconish.khandevaneh.helper.webapi.WebApiRequest;
import ir.iconish.khandevaneh.app.ScoresContainer;
import ir.iconish.khandevaneh.view.KhandevanehDialog;
import ir.iconish.khandevaneh.view.XeiButton;
import ir.iconish.khandevaneh.view.XeiTextView;

public class ProfileActivity extends BaseActivity {

    private static final String TAG_REQUEST_SEND_PARTICIPATE_REQUEST = "requestTag_profileActivity_sendParticipateRequest";

    private ImageButton mEditProfileImageButton;
    private SimpleDraweeView mAvatarSimpleDraweeView;
    private XeiTextView mFirstNameTextView;
    private XeiTextView mLastNameTextView;
    private XeiTextView mPhoneNoTextView;
    private XeiTextView mEmailAddressTextView;
    private XeiTextView mPostalAddressTextView;
    private XeiButton mParticipateButton;
    private XeiButton mAboutAppButton;
    private XeiButton mLogoutButton;


    public static void start(Context starter) {
        Intent intent = new Intent(starter, ProfileActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_SEND_PARTICIPATE_REQUEST);
        return tags;
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();
        setOnClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillFields();
    }

    private void findViews() {
        mEditProfileImageButton = (ImageButton) findViewById(R.id.toolbarHome_imageButton_edit);
        mAvatarSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.activityProfile_simpleDraweeView_avatar);
        mFirstNameTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_firstName);
        mLastNameTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_lastName);
        mPhoneNoTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_phoneNo);
        mEmailAddressTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_emailAddress);
        mPostalAddressTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_postalAddress);
        mParticipateButton = (XeiButton) findViewById(R.id.activityProfile_xeiButton_participate);
        mAboutAppButton = (XeiButton) findViewById(R.id.activityProfile_xeiButton_aboutApp);
        mLogoutButton = (XeiButton) findViewById(R.id.activityProfile_xeiButton_logout);

    }

    private void fillFields() {
        User user = User.getInstance();
        FrescoHelper.setImageUrl(mAvatarSimpleDraweeView, user.getAvatar());
        mFirstNameTextView.setText(user.getFirstName());
        mLastNameTextView.setText(user.getLastName());
        mPhoneNoTextView.setText(user.getPhoneNo());
        mEmailAddressTextView.setText(user.getEmailAddress());
        mPostalAddressTextView.setText(user.getPostalAddress());
    }

    private void setOnClickListeners() {
        mEditProfileImageButton.setOnClickListener(this);
        mParticipateButton.setOnClickListener(this);
        mAboutAppButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
    }

    private void participate() {
        WebApiHelper.sendParticipateRequest(TAG_REQUEST_SEND_PARTICIPATE_REQUEST, new WebApiRequest.WebApiListener<Boolean>() {
            @Override
            public void onResponse(Boolean response, final ScoresContainer scoresContainer) {

                new KhandevanehDialog(ProfileActivity.this, "اسمت ثبت شد. ایشالا می‌بینیمت.", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (scoresContainer != null) {
                            updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), null);
                        }
                    }
                }).show();
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                new KhandevanehDialog(ProfileActivity.this, errorMessage, null).show();
            }
        }, null).send();
    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.toolbarHome_imageButton_edit:
                ProfileCompletionActivity.start(this);
                break;

            case R.id.activityProfile_xeiButton_participate:
                participate();
                break;

            case R.id.activityProfile_xeiButton_aboutApp:
                AboutAppActivity.start(this);
                break;

            case R.id.activityProfile_xeiButton_logout:
                User.getInstance().logout();
                finish();
                break;

            default:
        }
    }
}
