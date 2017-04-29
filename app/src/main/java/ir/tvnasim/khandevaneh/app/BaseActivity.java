package ir.tvnasim.khandevaneh.app;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.login.LoginActivity;
import ir.tvnasim.khandevaneh.account.ProfileActivity;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.leaderboard.LeaderBoardActivity;
import ir.tvnasim.khandevaneh.store.StoreActivity;

/**
 * Created by hamidreza on 4/14/17.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG_DEBUG = BaseActivity.class.getSimpleName();

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
        return R.layout.toolbar_default;
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

        showScoresForLoggedInUses();
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

    public void showScoresForLoggedInUses() {
        int melonScore = User.getInstance().getMelonScore();
        int experienceScore = User.getInstance().getExperienceScore();
        if (melonScore >= 0 && experienceScore >= 0) {
            showScores(melonScore, experienceScore);
        }
    }

    public void showScores(int melon, int experience) {
        if (mScoreSectionLinearLayout != null) {
            mMelonScoreTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(melon)));
            mExperienceScoreTextView.setText(HelperFunctions.convertNumberStringToPersian(String.valueOf(experience)));
            mScoreSectionLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    public void goneScoreSection() {
        if (mScoreSectionLinearLayout != null) {
            mScoreSectionLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.toolbarHome_imageButton_profile:
                if (!(this instanceof ProfileActivity)) {
                    User.getInstance().isLoggedIn(new User.IsLoggedInListener() {
                        @Override
                        public void isLoggedIn(boolean isLoggedIn) {
                            if (isLoggedIn) {
                                ProfileActivity.start(BaseActivity.this);
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
