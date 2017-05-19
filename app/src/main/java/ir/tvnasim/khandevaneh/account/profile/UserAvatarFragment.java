package ir.tvnasim.khandevaneh.account.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.view.XeiButton;


public class UserAvatarFragment extends Fragment implements View.OnClickListener {

    private static final String TAG_DEBUG = UserAvatarFragment.class.getSimpleName();

    private static final int QUALITY_IMAGE_COMPRESSION = 50;
    private static final int CODE_REQUEST_LOAD_IMAGE_FROM_GALLERY = 11;

    private SimpleDraweeView mAvatarSimpleDraweeView;
    private XeiButton mNextButton;

    private String mAvatarEncodedBitmap;

    public UserAvatarFragment() {
        // Required empty public constructor
    }

    public static UserAvatarFragment newInstance() {
        return new UserAvatarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_default);
        mAvatarEncodedBitmap = encodeBitmap(defaultBitmap);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_avatar, container, false);

        mAvatarSimpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.fragmentUserAvatar_simpleDraweeView_avatar);
        mNextButton = (XeiButton) view.findViewById(R.id.fragmentUserAvatar_xeiButton_next);

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        mAvatarSimpleDraweeView.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "انتخاب عکس"), CODE_REQUEST_LOAD_IMAGE_FROM_GALLERY);
    }

    private String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMAGE_COMPRESSION, byteArrayOutputStream);
        byte[] ba = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(ba, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST_LOAD_IMAGE_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    final Bitmap bmp;
                    try {
                        bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                        mAvatarSimpleDraweeView.setImageBitmap(bmp);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mAvatarEncodedBitmap = encodeBitmap(bmp);
                            }
                        }).start();
                    } catch (IOException ioe) {
                        LogHelper.logError(TAG_DEBUG, ioe.getMessage());
                    }

                }
            }
        }
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.fragmentUserAvatar_simpleDraweeView_avatar:
                // set null value to mAvatarEncodedBitmap means that the user has changed the avatar.
                // changing the avatar may be time consuming and this null value has prevent to send default avatar.
                mAvatarEncodedBitmap = null;
                getImageFromGallery();
                break;

            case R.id.fragmentUserAvatar_xeiButton_next:
                if (mAvatarEncodedBitmap != null) {
                    ((ProfileCompletionActivity)getActivity()).onInfoEnter(UserInfoFragment.WHICH_FRAGMENT_AVATAR, mAvatarEncodedBitmap);
                }
                break;
        }
    }
}
