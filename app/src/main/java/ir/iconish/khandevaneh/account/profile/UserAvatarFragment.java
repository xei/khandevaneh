package ir.iconish.khandevaneh.account.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.helper.LogHelper;
import ir.iconish.khandevaneh.view.XeiButton;


public class UserAvatarFragment extends Fragment implements View.OnClickListener, AvatarsAdapter.OnAvatarSelected {

    private static final String TAG_DEBUG = UserAvatarFragment.class.getSimpleName();

    private static final int QUALITY_IMAGE_COMPRESSION = 50;
    private static final int CODE_REQUEST_LOAD_IMAGE_FROM_GALLERY = 11;

    private ImageView mAvatarSimpleDraweeView;
    private RecyclerView mAvatarsRecyclerView;
    private AvatarsAdapter mAvatarsAdapter;
    private XeiButton mNextButton;

    private String mAvatarEncodedBitmap;
    private String mBackupEncodedBitmap;

    public UserAvatarFragment() {
        // Required empty public constructor
    }

    public static UserAvatarFragment newInstance() {
        return new UserAvatarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_avatar, container, false);

        findViews(view);
        initRecyclerView();
        setOnClickListeners();

        Bitmap defaultAvatar = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_smile);
        loadAvatar(defaultAvatar);

        return view;
    }

    private void findViews(View rootView) {
        mAvatarSimpleDraweeView = (ImageView) rootView.findViewById(R.id.fragmentUserAvatar_simpleDraweeView_avatar);
        mAvatarsRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragmentUserAvatar_recyclerView_avatars);
        mNextButton = (XeiButton) rootView.findViewById(R.id.fragmentUserAvatar_xeiButton_next);
    }

    private void initRecyclerView() {
        mAvatarsRecyclerView.setHasFixedSize(true);
        mAvatarsAdapter = new AvatarsAdapter(this);
        mAvatarsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAvatarsRecyclerView.setAdapter(mAvatarsAdapter);
        mAvatarsRecyclerView.scrollToPosition(8);
    }

    private void setOnClickListeners() {
        mAvatarsRecyclerView.setOnClickListener(this);
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

    private void loadAvatar(final Bitmap avatarBitmap) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAvatarEncodedBitmap = encodeBitmap(avatarBitmap);
                mBackupEncodedBitmap = mAvatarEncodedBitmap;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAvatarSimpleDraweeView.setImageBitmap(avatarBitmap);
                    }
                });
            }
        }).start();

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
                        loadAvatar(bmp);
                    } catch (IOException ioe) {
                        LogHelper.logError(TAG_DEBUG, ioe.getMessage());
                    }

                }
            } else {
                mAvatarEncodedBitmap = mBackupEncodedBitmap;
            }
        }
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.fragmentUserAvatar_xeiButton_next:
                if (mAvatarEncodedBitmap != null) {
                    ((ProfileCompletionActivity)getActivity()).onInfoEnter(UserInfoFragment.WHICH_FRAGMENT_AVATAR, mAvatarEncodedBitmap);
                }
                break;
        }
    }

    @Override
    public void onSelected(int resId) {
        if (resId == android.R.drawable.ic_input_add) {
            // set null value to mAvatarEncodedBitmap means that the user has changed the avatar.
            // changing the avatar may be time consuming and this null value has prevent to send default avatar.
            mAvatarEncodedBitmap = null;
            getImageFromGallery();
        } else {
            Bitmap defaultAvatar = BitmapFactory.decodeResource(getResources(), resId);
            loadAvatar(defaultAvatar);
        }

    }
}
