package com.toddmo.apps.capture.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.toddmo.apps.capture.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String LOG_TAG = SectionsPagerAdapter.class.getCanonicalName();

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public final int TAB_INDEX_HOME = 0;
    public final int TAB_INDEX_CAPTURE = 1;
    public final int TAB_INDEX_PLAYBACK = 2;
    public final int TAB_INDEX_CONFIGURATION = 3;

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case TAB_INDEX_HOME:
                return HomePageFragment.newInstance();
            case TAB_INDEX_CAPTURE:
                return CaptureFragment.newInstance();
            case TAB_INDEX_PLAYBACK:
                return PlaybackFragment.newInstance();
            case TAB_INDEX_CONFIGURATION:
                return ConfigurationFragment.newInstance();
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}