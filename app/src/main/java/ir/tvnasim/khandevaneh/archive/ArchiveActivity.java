package ir.tvnasim.khandevaneh.archive;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.MediaController;
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
import ir.tvnasim.khandevaneh.view.XeiTextView;

public class ArchiveActivity extends BaseActivity {

    private static final String KEY_EXTRA_ARCHIVE_ID = "KEY_EXTRA_ARCHIVE_ID";
    private static final String TAG_REQUEST_GET_ARCHIVE_ITEM = "requestTag_pollingActivity_getArchiveItem";
    private static final String TAG_REQUEST_LIKE_ARCHIVE = "requestTag_pollingActivity_likeArchive";

    private static final String TYPE_IMAGE = "2";
    private static final String TYPE_VIDEO = "1";

    private XeiTextView mTitleTextView;
    private ViewStub mContextViewStub;
    private RelativeLayout mFooterRelativeLayout;
    private XeiButton mLikeButton;
    private XeiButton mCommentButton;

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
        mFooterRelativeLayout = (RelativeLayout) findViewById(R.id.activityArchive_relativeLayout_footer);
        mLikeButton = (XeiButton) findViewById(R.id.activityArchive_xeiButton_like);
        mCommentButton = (XeiButton) findViewById(R.id.activityArchive_xeiButton_comment);
    }

    private void setOnClickListeners() {
        mLikeButton.setOnClickListener(this);
        mCommentButton.setOnClickListener(this);
    }

    private void renderArchive(ArchiveItem archiveItem) {
        mTitleTextView.setText(archiveItem.getTitle());

        switch (archiveItem.getType()) {
            case TYPE_IMAGE:
                mContextViewStub.setLayoutResource(R.layout.layout_archive_context_image);
                SimpleDraweeView archiveContextSimpleDraweeView = (SimpleDraweeView) mContextViewStub.inflate();
                FrescoHelper.setImageUrl(archiveContextSimpleDraweeView, archiveItem.getContent());
                break;

            case TYPE_VIDEO:
                mContextViewStub.setLayoutResource(R.layout.layout_archive_context_video);
                final VideoView archiveContextVideoView = (VideoView) mContextViewStub.inflate();
                archiveContextVideoView.setVideoPath(archiveItem.getContent());
                archiveContextVideoView.setKeepScreenOn(true);

                final MediaController mediaController = new MediaController(this) {
                    @Override
                    public void show(int timeout) {
                        super.show(0);
                    }
                };
                mediaController.getLayoutParams().height = HelperFunctions.dpToPx(this, 42);

                archiveContextVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();

                        mediaController.setAnchorView(archiveContextVideoView);
                        mediaController.setMediaPlayer(archiveContextVideoView);
                        mediaController.show(0);
                        mediaController.requestFocus();
                    }
                });
                break;

            default:
                LogHelper.logError(TAG_DEBUG, "invalid archive type!");
                break;
        }

        mFooterRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void fetchArchiveFromApi() {
        WebApiHelper.getArchiveItem(mArchiveId, TAG_REQUEST_GET_ARCHIVE_ITEM, new WebApiRequest.WebApiListener<ArchiveItem>() {
            @Override
            public void onResponse(ArchiveItem archiveItem, ScoresContainer scoresContainer) {
                if (archiveItem != null) {
                    renderArchive(archiveItem);
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

    private void comment() {
        new KhandevanehDialog(this, getString(R.string.inform_notImplemented), null).show();
    }

    @Override
    public void onClick(View clickedView) {
        super.onClick(clickedView);

        switch (clickedView.getId()) {
            case R.id.activityArchive_xeiButton_like:
                like();
                break;

            case R.id.activityArchive_xeiButton_comment:
                comment();
                break;

            default:
        }
    }
}
