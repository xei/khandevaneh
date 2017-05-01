package ir.tvnasim.khandevaneh.view.bannerslider;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by hamidreza on 4/20/17.
 */

public class SliderView extends ViewPager {

    private SliderAdapter mSliderAdapter;

    private ArrayList<Bundle> mBanners = new ArrayList<>();

    public SliderView(Context context) {
        super(context);
        init(context);
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mSliderAdapter = new SliderAdapter(((AppCompatActivity)context).getSupportFragmentManager(), mBanners);
        setAdapter(mSliderAdapter);
    }

    public void setBanners(ArrayList<Bundle> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        mSliderAdapter.notifyDataSetChanged();
    }

}
