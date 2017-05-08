package ir.tvnasim.khandevaneh.account.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;
import ir.tvnasim.khandevaneh.view.XeiButton;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class ProfileActivity extends BaseActivity {

    private SimpleDraweeView mAvatarSimpleDraweeView;
    private XeiTextView mFirstNameTextView;
    private XeiTextView mLastNameTextView;
    private XeiTextView mPhoneNoTextView;
    private XeiTextView mEmailAddressTextView;
    private XeiTextView mPostalAddressTextView;
    private XeiButton mEditProfileButton;
    private XeiButton mSubscribeButton;
    private XeiButton mParticipateButton;


    public static void start(Context starter) {
        Intent intent = new Intent(starter, ProfileActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();
        fillFields();
        setOnClickListeners();
    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_profile;
    }

    private void findViews() {
        mAvatarSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.activityProfile_simpleDraweeView_avatar);
        mFirstNameTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_firstName);
        mLastNameTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_lastName);
        mPhoneNoTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_phoneNo);
        mEmailAddressTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_emailAddress);
        mPostalAddressTextView = (XeiTextView) findViewById(R.id.activityProfile_xeiTextView_postalAddress);
        mEditProfileButton = (XeiButton) findViewById(R.id.activityProfile_xeiButton_editProfile);
        mSubscribeButton = (XeiButton) findViewById(R.id.activityProfile_xeiButton_subscribe);
        mParticipateButton = (XeiButton) findViewById(R.id.activityProfile_xeiButton_participate);

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
        mEditProfileButton.setOnClickListener(this);
        mSubscribeButton.setOnClickListener(this);
        mParticipateButton.setOnClickListener(this);
    }

    private void subscribe() {
        // TODO: call api
    }

    private void participate() {

    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityProfile_xeiButton_editProfile:
                new KhandevanehDialog(this, getString(R.string.inform_notImplemented), null).show();
                break;

            case R.id.activityProfile_xeiButton_subscribe:
                subscribe();
                break;

            case R.id.activityProfile_xeiButton_participate:
                participate();
                break;

            default:
        }
    }
}
