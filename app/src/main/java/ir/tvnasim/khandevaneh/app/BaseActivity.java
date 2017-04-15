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
import ir.tvnasim.khandevaneh.account.LoginActivity;
import ir.tvnasim.khandevaneh.account.ProfileActivity;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.leaderboard.LeaderBoardActivity;

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


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View baseActivityLayout = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        ViewStub viewStub = (ViewStub) baseActivityLayout.findViewById(R.id.activityBase_viewStub_pageContainer);
        viewStub.setLayoutResource(layoutResID);
        viewStub.inflate();
        super.setContentView(baseActivityLayout);

        findViews();
        setOnClickListeners();

    }

    private void findViews() {
        RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.activityBase_relativeLayout_toolbar);
        mProfileImageButton = (ImageButton) toolbar.findViewById(R.id.activityBase_imageButton_profile);
        mScoreSectionLinearLayout = (LinearLayout) toolbar.findViewById(R.id.activityBase_linearLayout_scoreSection);
        mMelonScoreLinearLayout = (LinearLayout) toolbar.findViewById(R.id.activityBase_linearLayout_melon);
        mMelonScoreTextView = (TextView) mMelonScoreLinearLayout.findViewById(R.id.layoutToolbarScore_textView_score);
        mExperienceScoreLinearLayout = (LinearLayout) toolbar.findViewById(R.id.activityBase_linearLayout_experience);
        mExperienceScoreTextView = (TextView) mExperienceScoreLinearLayout.findViewById(R.id.layoutToolbarScore_textView_score);

        ImageView melonScoreImageView = (ImageView) toolbar.findViewById(R.id.activityBase_linearLayout_melon).findViewById(R.id.layoutToolbarScore_imageView_icon);
        melonScoreImageView.setImageResource(R.drawable.ic_toolbar_melon);
        ImageView experienceScoreImageView = (ImageView) toolbar.findViewById(R.id.activityBase_linearLayout_experience).findViewById(R.id.layoutToolbarScore_imageView_icon);
        experienceScoreImageView.setImageResource(R.drawable.ic_toolbar_experience);

    }

    @Override
    protected void onResume() {
        super.onResume();

        showScoresForLoggedInUses();
    }

    private void setOnClickListeners() {
        mProfileImageButton.setOnClickListener(this);
        mMelonScoreLinearLayout.setOnClickListener(this);
        mExperienceScoreLinearLayout.setOnClickListener(this);
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
            mMelonScoreTextView.setText(String.valueOf(melon));
            mExperienceScoreTextView.setText(String.valueOf(experience));
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
            case R.id.activityBase_imageButton_profile:
                if (!(this instanceof ProfileActivity)) {
                    ProfileActivity.start(this);
                }
                break;
            case R.id.activityBase_linearLayout_melon:
                break;
            case R.id.activityBase_linearLayout_experience:
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
        }
    }
}
