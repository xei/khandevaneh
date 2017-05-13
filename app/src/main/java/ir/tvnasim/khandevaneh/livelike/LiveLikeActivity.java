package ir.tvnasim.khandevaneh.livelike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.app.LaunchActivity;
import ir.tvnasim.khandevaneh.exception.InValidDestinationException;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.app.Banner;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;
import ir.tvnasim.khandevaneh.view.XeiEditText;
import ir.tvnasim.khandevaneh.view.XeiTextView;


public class LiveLikeActivity extends BaseActivity {

    private static final String TAG_REQUEST_GET_SECTION = "requestTag_liveLikeListActivity_getLiveLike";
    private static final String TAG_REQUEST_GET_BANNER = "requestTag_liveLikeListActivity_getBanner";
    private static final String TAG_REQUEST_LIKE = "requestTag_liveLikeListActivity_like";
    private static final String TAG_REQUEST_COMMENT = "requestTag_liveLikeListActivity_comment";

    private RelativeLayout mLiveLikeSectionRelativeLayout;
    private XeiTextView mTitleTextView;
    private ImageView mBackHeartImageView;
    private SimpleDraweeView mLikeSimpleDraweeView;
    private SimpleDraweeView mBannerSimpleDraweeView;
    private RelativeLayout mCommentSection;
    private XeiEditText mCommentEditText;
    private ImageView mSendBtnImageView;

    private Section mSection;

    public static void start(Context starter) {
        Intent intent = new Intent(starter, LiveLikeActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_SECTION);
        tags.add(TAG_REQUEST_GET_BANNER);
        tags.add(TAG_REQUEST_LIKE);
        tags.add(TAG_REQUEST_COMMENT);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_like);

        findViews();
        FrescoHelper.loadGifFromResources(mLikeSimpleDraweeView, R.drawable.heart);
        setOnClickListeners();

        fetchSectionFromApi();
    }

    private void findViews() {
        mLiveLikeSectionRelativeLayout = (RelativeLayout) findViewById(R.id.activityLiveLike_relativeLayout_liveLikeSection);
        mTitleTextView = (XeiTextView) findViewById(R.id.activityLiveLike_xeiTextView_title);
        mBackHeartImageView = (ImageView) findViewById(R.id.activityLiveLike_imageView_backHeart);
        mLikeSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.activityLiveLike_simpleDraweeView_like);
        mBannerSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.activityLiveLike_simpleDraweeView_banner);
        mCommentSection = (RelativeLayout) findViewById(R.id.activityLiveLike_relativeLayout_commentSection);
        mCommentEditText = (XeiEditText) findViewById(R.id.activityLiveLike_xeiEditText_comment);
        mSendBtnImageView = (ImageView) findViewById(R.id.activityLiveLike_imageView_sendBtn);
    }

    private void setOnClickListeners() {
        mLikeSimpleDraweeView.setOnClickListener(this);
        mSendBtnImageView.setOnClickListener(this);
        mBannerSimpleDraweeView.setOnClickListener(this);
    }

    private void fetchSectionFromApi() {
        WebApiHelper.getLiveLike(TAG_REQUEST_GET_SECTION, new WebApiRequest.WebApiListener<SectionContainer>() {
            @Override
            public void onResponse(SectionContainer sectionContainer, ScoresContainer scoresContainer) {

                if (sectionContainer != null && (mSection = sectionContainer.getSection()) != null) {
                    renderLiveLikeSection();
                } else {
                    WebApiHelper.getBanners(TAG_REQUEST_GET_BANNER, new WebApiRequest.WebApiListener<ArrayList<Banner>>() {
                        @Override
                        public void onResponse(ArrayList<Banner> allBanners, ScoresContainer scoresContainer) {
                            Banner banner = findAppropriateBanner(allBanners);
                            if (banner != null) {
                                renderBanner(banner);
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorMessage) {
                            LogHelper.logError(TAG_DEBUG, "getBanners request failed: " + errorMessage);
                            finish();
                        }
                    }, null).send();
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getLiveLike request failed: " + errorMessage);

                WebApiHelper.getBanners(TAG_REQUEST_GET_BANNER, new WebApiRequest.WebApiListener<ArrayList<Banner>>() {
                    @Override
                    public void onResponse(ArrayList<Banner> allBanners, ScoresContainer scoresContainer) {
                        Banner banner = findAppropriateBanner(allBanners);
                        if (banner != null) {
                            renderBanner(banner);
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorMessage) {
                        LogHelper.logError(TAG_DEBUG, "getBanners request failed: " + errorMessage);
                        finish();
                    }
                }, null).send();
            }
        }, null).send();
    }

    private void renderLiveLikeSection() {
        mTitleTextView.setText(mSection.getTitle());
        mLiveLikeSectionRelativeLayout.setVisibility(View.VISIBLE);
        if (!mSection.isCommentSent()) {
            mCommentSection.setVisibility(View.VISIBLE);
        }
    }

    private Banner findAppropriateBanner(ArrayList<Banner> banners) {
        if (banners != null) {
            for (Banner banner : banners) {
                if(banner.getLocation().equals(Banner.LOCATION_LIVE_LIKE)) {
                    return banner;
                }
            }
        }
        return null;
    }

    private void renderBanner(Banner banner) {
        FrescoHelper.setImageUrl(mBannerSimpleDraweeView, banner.getImageUrl());
        mBannerSimpleDraweeView.setTag(banner.getDestinationParam());
        mBannerSimpleDraweeView.setVisibility(View.VISIBLE);
    }

    private void animateHeart() {
        mBackHeartImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_back_livelike_heart);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBackHeartImageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBackHeartImageView.startAnimation(animation);
    }

    private void like() {

        animateHeart();

        if (mSection != null && mSection.getId() != null) {
            WebApiHelper.likeSection(mSection.getId(), TAG_REQUEST_LIKE, new WebApiRequest.WebApiListener<LikeResult>() {
                @Override
                public void onResponse(LikeResult response, ScoresContainer scoresContainer) {
                    if (scoresContainer != null) {
                        updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), null);
                    }
                }

                @Override
                public void onErrorResponse(String errorMessage) {
                    LogHelper.logError(TAG_DEBUG, "like request failed: " + errorMessage);
                }
            }, null).send();
        }
    }

    private void sendComment(String msg) {
        if (msg.isEmpty() || mSection == null || mSection.getId() == null) {
            return;
        }

        WebApiHelper.commentOnSection(mSection.getId(), msg, TAG_REQUEST_COMMENT, new WebApiRequest.WebApiListener<LikeResult>() {
            @Override
            public void onResponse(LikeResult response, final ScoresContainer scoresContainer) {

                new KhandevanehDialog(LiveLikeActivity.this, "نظرت ثبت شد رفیق!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (scoresContainer != null) {
                            updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), null);
                        }
                    }
                }).show();
                mCommentEditText.setText("");
                mCommentSection.setVisibility(View.GONE);

            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "comment request failed: " + errorMessage);
                new KhandevanehDialog(LiveLikeActivity.this, errorMessage, null).show();
            }
        }, null).send();
    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityLiveLike_simpleDraweeView_like:
                like();
                break;

            case R.id.activityLiveLike_imageView_sendBtn:
                sendComment(mCommentEditText.getText().toString());
                break;

            case R.id.activityLiveLike_simpleDraweeView_banner:
                try {
                    LaunchActivity.goTo(this, LaunchActivity.DESTINATION_BROWSER, clickedView.getTag().toString());
                } catch (InValidDestinationException ignored) {}
                break;
        }
    }
}
