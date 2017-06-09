package ir.iconish.khandevaneh.app;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.account.login.LoginActivity;
import ir.iconish.khandevaneh.account.profile.ProfileActivity;
import ir.iconish.khandevaneh.account.User;
import ir.iconish.khandevaneh.account.profile.ProfileCompletionActivity;
import ir.iconish.khandevaneh.exception.InValidDestinationException;
import ir.iconish.khandevaneh.helper.HelperFunctions;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.helper.webapi.VolleyHelper;
import ir.iconish.khandevaneh.leaderboard.LeaderBoardActivity;
import ir.iconish.khandevaneh.store.StoreActivity;

/**
 * Created by hamidreza on 4/14/17.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG_DEBUG = BaseActivity.class.getSimpleName();

    private ArrayList<String> mRequestTags = new ArrayList<>();

    private ImageButton mProfileImageButton;
    private LinearLayout mScoreSectionLinearLayout;
    private LinearLayout mMelonScoreLinearLayout;
    private TextView mMelonScoreTextView;
    private LinearLayout mExperienceScoreLinearLayout;
    private TextView mExperienceScoreTextView;
    private ImageButton mUpImageButton;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View baseActivityLayout = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        ViewStub viewStub = (ViewStub) baseActivityLayout.findViewById(R.id.activityBase_viewStub_pageContainer);
        viewStub.setLayoutResource(layoutResID);
        viewStub.inflate();
        super.setContentView(baseActivityLayout);

        ViewStub toolbarViewStub = (ViewStub) baseActivityLayout.findViewById(R.id.activityBase_toolbar);
        toolbarViewStub.setLayoutResource(getToolbarViewId());
        toolbarViewStub.inflate();

        findViews();
        setOnClickListeners();

    }

    public int getToolbarViewId() {
        return R.layout.toolbar;
    }

    private void findViews() {

        RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.activityBase_toolbar);
        try {
            mProfileImageButton = (ImageButton) toolbar.findViewById(R.id.toolbarHome_imageButton_profile);
            mScoreSectionLinearLayout = (LinearLayout) toolbar.findViewById(R.id.toolbarHome_linearLayout_scoreSection);
            mMelonScoreLinearLayout = (LinearLayout) toolbar.findViewById(R.id.toolbarHome_linearLayout_melon);
            mExperienceScoreLinearLayout = (LinearLayout) toolbar.findViewById(R.id.toolbarHome_linearLayout_experience);
        } catch (NullPointerException npe) {
            LogHelper.logInfo(TAG_DEBUG, "some views not exist.");
        }
        try {
            mMelonScoreTextView = (TextView) mMelonScoreLinearLayout.findViewById(R.id.layoutToolbarScore_textView_score);
        } catch (NullPointerException npe) {
            LogHelper.logInfo(TAG_DEBUG, "some views not exist.");
        }
        try {
            mExperienceScoreTextView = (TextView) mExperienceScoreLinearLayout.findViewById(R.id.layoutToolbarScore_textView_score);
        } catch (NullPointerException npe) {
            LogHelper.logInfo(TAG_DEBUG, "some views not exist.");
        }
        mUpImageButton = (ImageButton) findViewById(R.id.toolbarHome_imageButton_up);

        try {
            ImageView melonScoreImageView = (ImageView) toolbar.findViewById(R.id.toolbarHome_linearLayout_melon).findViewById(R.id.layoutToolbarScore_imageView_icon);
            melonScoreImageView.setImageResource(R.drawable.ic_toolbar_home_melon);
            ImageView experienceScoreImageView = (ImageView) toolbar.findViewById(R.id.toolbarHome_linearLayout_experience).findViewById(R.id.layoutToolbarScore_imageView_icon);
            experienceScoreImageView.setImageResource(R.drawable.ic_toolbar_home_experience);
        } catch (NullPointerException npe) {
            LogHelper.logInfo(TAG_DEBUG, "some views not exist.");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!HelperFunctions.isNetworkConnected()) {
            try {
                LaunchActivity.goTo(this, LaunchActivity.DESTINATION_NO_NETWORK, null);
            } catch (InValidDestinationException ignored) {}

            finish();
        }

        showScores();
    }

    private void setOnClickListeners(){

        if (mProfileImageButton != null) {
            mProfileImageButton.setOnClickListener(this);
        }

        if (mMelonScoreLinearLayout != null) {
            mMelonScoreLinearLayout.setOnClickListener(this);
        }

        if (mExperienceScoreLinearLayout != null) {
            mExperienceScoreLinearLayout.setOnClickListener(this);
        }

        if (mUpImageButton != null) {
            mUpImageButton.setOnClickListener(this);
        }

    }

    public void showScores() {

        int melonScore = User.getInstance().getMelonScore();
        int experienceScore = User.getInstance().getExperienceScore();

        if (melonScore >= 0 && experienceScore >= 0 && mScoreSectionLinearLayout != null) {
            mMelonScoreTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(melonScore)));
            mExperienceScoreTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(experienceScore)));
            mScoreSectionLinearLayout.setVisibility(View.VISIBLE);
        }

    }

    public void updateScores(int newMelonScore, int newExperienceScore, final OnShakingFinishedListener onShakingFinishedListener) {

        if (newMelonScore >= 0 && newExperienceScore >= 0 && mScoreSectionLinearLayout != null) {

            int oldMelonScore = User.getInstance().getMelonScore();
            int oldExperienceScore = User.getInstance().getExperienceScore();

            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (onShakingFinishedListener != null) {
                        onShakingFinishedListener.onShakingFinish();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            if (newMelonScore == oldMelonScore && newExperienceScore == oldExperienceScore) {
                if (onShakingFinishedListener != null) {
                    onShakingFinishedListener.onShakingFinish();
                }
            } else {
                if (newMelonScore != oldMelonScore) {
                    User.getInstance().setMelonScore(newMelonScore);
                    mMelonScoreTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(newMelonScore)));
                    mMelonScoreLinearLayout.startAnimation(animation);
                }

                if (newExperienceScore != oldExperienceScore) {
                    User.getInstance().setExperienceScore(newExperienceScore);
                    mExperienceScoreTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(newExperienceScore)));
                    mExperienceScoreLinearLayout.startAnimation(animation);
                }
            }

        }

    }

    public interface OnShakingFinishedListener {
        void onShakingFinish();
    }

    protected ArrayList<String> getRequestTags() {
        return mRequestTags;
    }

    @Override
    protected void onStop() {
        super.onStop();

        for (String tag : getRequestTags()) {
            VolleyHelper.cancelPendingRequests(tag);
        }
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.toolbarHome_imageButton_profile:
                if (!(this instanceof ProfileActivity) && !(this instanceof ProfileCompletionActivity)) {
                    User.getInstance().isLoggedIn(new User.IsLoggedInListener() {
                        @Override
                        public void isLoggedIn(boolean isLoggedIn) {
                            if (isLoggedIn) {
                                if (User.getInstance().isProfileComplete()) {
                                    ProfileActivity.start(BaseActivity.this);
                                } else {
                                    ProfileCompletionActivity.start(BaseActivity.this);
                                }
                            } else {
                                LoginActivity.start(BaseActivity.this);
                            }
                        }
                    });
                }
                break;
            case R.id.toolbarHome_linearLayout_melon:
                if (!(this instanceof StoreActivity)) {
                    User.getInstance().isLoggedIn(new User.IsLoggedInListener() {
                        @Override
                        public void isLoggedIn(boolean isLoggedIn) {
                            if (isLoggedIn) {
                                StoreActivity.start(BaseActivity.this);
                            } else {
                                LoginActivity.start(BaseActivity.this);
                            }
                        }
                    });
                }
                break;

            case R.id.toolbarHome_linearLayout_experience:
                if (!(this instanceof LeaderBoardActivity)) {
                    User.getInstance().isLoggedIn(new User.IsLoggedInListener() {
                        @Override
                        public void isLoggedIn(boolean isLoggedIn) {
                            if (isLoggedIn) {
                                LeaderBoardActivity.start(BaseActivity.this);
                            } else {
                                LoginActivity.start(BaseActivity.this);
                            }
                        }
                    });
                }
                break;

            case R.id.toolbarHome_imageButton_up:
                finish();
                break;
        }
    }
}
