package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;
import ir.tvnasim.khandevaneh.app.ScoresContainer;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiRequest;
import ir.tvnasim.khandevaneh.view.KhandevanehDialog;
import ir.tvnasim.khandevaneh.view.XeiButton;
import ir.tvnasim.khandevaneh.view.XeiEditText;
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class ArchiveActivity extends BaseActivity {

    private static final String KEY_EXTRA_ARCHIVE_ID = "KEY_EXTRA_ARCHIVE_ID";
    private static final String TAG_REQUEST_GET_ARCHIVE_ITEM = "requestTag_pollingActivity_getArchiveItem";
    private static final String TAG_REQUEST_LIKE_ARCHIVE = "requestTag_pollingActivity_likeArchive";
    private static final String TAG_REQUEST_COMMENT_ARCHIVE = "requestTag_pollingActivity_commentArchive";

    private static final String TYPE_IMAGE = "2";
    private static final String TYPE_VIDEO = "1";

    private XeiTextView mTitleTextView;
    private ViewStub mContextViewStub;
    private LinearLayout mLikeSectionLinearLayout;
    private XeiTextView mLikeCountTextView;
    private XeiButton mLikeButton;
    private RelativeLayout mCommentSection;
    private XeiEditText mCommentEditText;
    private ImageView mCommentButton;

    private ArchiveItem mArchiveItem;
    private String mArchiveId;


    public static void start(Context starter, String archiveId) {
        Intent intent = new Intent(starter, ArchiveActivity.class);
        intent.putExtra(KEY_EXTRA_ARCHIVE_ID, archiveId);
        starter.startActivity(intent);
    }

    @Override
    protected ArrayList<String> getRequestTags() {
        ArrayList<String> tags = super.getRequestTags();
        tags.add(TAG_REQUEST_GET_ARCHIVE_ITEM);
        tags.add(TAG_REQUEST_LIKE_ARCHIVE);
        tags.add(TAG_REQUEST_COMMENT_ARCHIVE);
        return tags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        this.mArchiveId = getIntent().getStringExtra(KEY_EXTRA_ARCHIVE_ID);

        findViews();
        setOnClickListeners();

        fetchArchiveFromApi();
    }

    private void findViews() {
        mTitleTextView = (XeiTextView) findViewById(R.id.activityArchive_xeiTextView_title);
        mContextViewStub = (ViewStub) findViewById(R.id.activityArchive_viewStub_archiveContext);
        mLikeSectionLinearLayout = (LinearLayout) findViewById(R.id.activityArchive_linearLayout_likeSection);
        mLikeCountTextView = (XeiTextView) findViewById(R.id.activityArchive_xeiTextView_likeCount);
        mLikeButton = (XeiButton) findViewById(R.id.activityArchive_xeiButton_like);
        mCommentSection = (RelativeLayout) findViewById(R.id.activityArchive_relativeLayout_commentSection);
        mCommentEditText = (XeiEditText) findViewById(R.id.activityArchive_xeiEditText_comment);
        mCommentButton = (ImageView) findViewById(R.id.activityArchive_imageView_sendBtn);
    }

    private void setOnClickListeners() {
        mLikeButton.setOnClickListener(this);
        mCommentButton.setOnClickListener(this);
    }

    private void renderArchive() {
        mTitleTextView.setText(mArchiveItem.getTitle());

        switch (mArchiveItem.getType()) {
            case TYPE_IMAGE:
                mContextViewStub.setLayoutResource(R.layout.layout_archive_context_image);
                SimpleDraweeView archiveContextSimpleDraweeView = (SimpleDraweeView) mContextViewStub.inflate();
                FrescoHelper.setImageUrl(archiveContextSimpleDraweeView, mArchiveItem.getContent());
                break;

            case TYPE_VIDEO:
                mContextViewStub.setLayoutResource(R.layout.layout_archive_context_video);
                View videoContextView = mContextViewStub.inflate();
                final VideoView archiveContextVideoView = (VideoView) videoContextView.findViewById(R.id.activityArchive_viewStub_archiveContext);
                final ProgressBar loadingProgreeBar = (ProgressBar) videoContextView.findViewById(R.id.layoutArchiveContextVideo_progressBar_loading);
                final ImageView fullScreenBtn = (ImageView) videoContextView.findViewById(R.id.layoutArchiveContextVideo_imageButton_fullScreen);
                archiveContextVideoView.setVideoPath(mArchiveItem.getContent());
                archiveContextVideoView.setKeepScreenOn(true);

                final MediaController mediaController = new MediaController(this) {
                    @Override
                    public void show(int timeout) {
                        super.show(0);
                    }
                };
                mediaController.getLayoutParams().height = HelperFunctions.dpToPx(this, 80);

                archiveContextVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        loadingProgreeBar.setVisibility(View.GONE);

                        mediaController.setAnchorView(archiveContextVideoView);
                        mediaController.setMediaPlayer(archiveContextVideoView);
                        mediaController.show(0);
                        mediaController.requestFocus();

                        maximizeVideoView(archiveContextVideoView);
                    }
                });

                fullScreenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FullScreenVideoActivity.start(ArchiveActivity.this, mArchiveItem.getContent(), archiveContextVideoView.getCurrentPosition());
                    }
                });

                break;

            default:
                LogHelper.logError(TAG_DEBUG, "invalid archive type!");
                break;
        }

        mLikeCountTextView.setText(String.format(getString(R.string.archive_text_likeCount), HelperFunctions.convertNumberStringToPersian(String.valueOf(mArchiveItem.getLikeCount()))));

        mLikeSectionLinearLayout.setVisibility(View.VISIBLE);
        mCommentSection.setVisibility(View.VISIBLE);
    }

    private void maximizeVideoView(VideoView videoView) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoView.setLayoutParams(params);
    }

    private void minimizeVideoView(VideoView videoView) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width =  (int) (300*metrics.density);
        params.height = (int) (250*metrics.density);
        params.leftMargin = 30;
        videoView.setLayoutParams(params);
    }

    private void fetchArchiveFromApi() {
        WebApiHelper.getArchiveItem(mArchiveId, TAG_REQUEST_GET_ARCHIVE_ITEM, new WebApiRequest.WebApiListener<ArchiveItem>() {
            @Override
            public void onResponse(ArchiveItem archiveItem, ScoresContainer scoresContainer) {
                if (archiveItem != null) {
                    mArchiveItem = archiveItem;
                    renderArchive();
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "getArchiveItem request failed: " + errorMessage);
            }
        }, null).send();
    }

    private void like() {
        WebApiHelper.likeArchive(mArchiveId, TAG_REQUEST_LIKE_ARCHIVE, new WebApiRequest.WebApiListener<Object>() {
            @Override
            public void onResponse(Object response, final ScoresContainer scoresContainer) {

                int likeCount = Integer.parseInt(mArchiveItem.getLikeCount());
                likeCount++;
                mLikeCountTextView.setText(String.format(getString(R.string.archive_text_likeCount), HelperFunctions.convertNumberStringToPersian(String.valueOf(likeCount))));

                new KhandevanehDialog(ArchiveActivity.this, "مرسی :)", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (scoresContainer != null) {
                            updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), null);
                        }
                    }
                }).show();
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                LogHelper.logError(TAG_DEBUG, "likeArchive request failed: " + errorMessage);
                new KhandevanehDialog(ArchiveActivity.this, errorMessage, null).show();
            }
        }, null).send();
    }

    private void sendComment(String comment) {
        if (comment != null && mArchiveId != null) {
            if (comment.isEmpty()) {
                new KhandevanehDialog(this, "بخش نظر خالیه!", null).show();
            } else {
                WebApiHelper.commentOnArchive(mArchiveId, comment, TAG_REQUEST_COMMENT_ARCHIVE, new WebApiRequest.WebApiListener<Object>() {
                    @Override
                    public void onResponse(Object response, final ScoresContainer scoresContainer) {

                        new KhandevanehDialog(ArchiveActivity.this, "نظرت ثبت شد رفیق!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (scoresContainer != null) {
                                    updateScores(scoresContainer.getMelonScore(), scoresContainer.getExperienceScore(), null);
                                }
                            }
                        }).show();
                        mCommentEditText.setText("");
                        mCommentSection.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onErrorResponse(String errorMessage) {
                        LogHelper.logError(TAG_DEBUG, "comment request failed: " + errorMessage);
                        new KhandevanehDialog(ArchiveActivity.this, errorMessage, null).show();
                    }
                }, null).send();
            }
        }
    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityArchive_xeiButton_like:
                like();
                break;

            case R.id.activityArchive_imageView_sendBtn:
                sendComment(mCommentEditText.getText().toString());
                break;

            default:
        }
    }
}
