package ir.tvnasim.khandevaneh.view.bannerslider;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;


public class BannerFragment extends Fragment {

    public static final String KEY_ARG_IMAGE_URL = "KEY_ARG_IMAGE_URL";

    private SimpleDraweeView mImageSimpleDraweeView;
    private OnBannerClickedListener mOnBannerClickedListener;

    private Bundle mArgument;
    private String mImageUrl;


    public BannerFragment() {
        // Required empty public constructor
    }

    public static BannerFragment newInstance(Bundle banner) {
        BannerFragment fragment = new BannerFragment();
        fragment.setArguments(banner);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArgument = getArguments();
        if (mArgument != null) {
            mImageUrl = getArguments().getString(KEY_ARG_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_banner, container, false);

        mImageSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.fragmentBanner_simpleDraweeView);
        FrescoHelper.setImageUrl(mImageSimpleDraweeView, mImageUrl);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBannerClickedListener.onBannerClick(mArgument);
            }
        });

        return itemView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnBannerClickedListener) {
            mOnBannerClickedListener = (OnBannerClickedListener)context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnBannerClickedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnBannerClickedListener = null;
    }

}
