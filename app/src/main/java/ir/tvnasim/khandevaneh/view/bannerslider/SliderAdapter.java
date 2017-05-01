package ir.tvnasim.khandevaneh.view.bannerslider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by hamidreza on 4/20/17.
 */

class SliderAdapter extends FragmentPagerAdapter {

    private ArrayList<Bundle> mBanners;

    public SliderAdapter(FragmentManager fm, ArrayList<Bundle> banners) {
        super(fm);
        mBanners = banners;
    }

    @Override
    public Fragment getItem(int position) {
        return BannerFragment.newInstance(mBanners.get(position));
    }

    @Override
    public int getCount() {
        return mBanners.size();
    }

}
