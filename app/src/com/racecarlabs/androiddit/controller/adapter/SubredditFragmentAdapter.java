package com.racecarlabs.androiddit.controller.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.view.fragment.SubredditDescFragment;
import com.racecarlabs.androiddit.view.fragment.SubredditPicsFragment;

public class SubredditFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SubredditFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        
        mContext = context;
    }

    @Override
    public Fragment getItem(int pos) {
        SubredditFragmentType type = SubredditFragmentType.values()[pos];

        return Fragment.instantiate(mContext, type.mFragClass.getName(), null);
    }

    @Override
    public int getCount() {
        return SubredditFragmentType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        SubredditFragmentType type = SubredditFragmentType.values()[pos];
        
        return mContext.getText(type.mTitleResId);
    }

    public enum SubredditFragmentType {
        PICS(R.string.tab_pics, SubredditPicsFragment.class),// photo grid tab
        DESCRIPTION(R.string.tab_desc, SubredditDescFragment.class); //about page

        final int mTitleResId;

        final Class<?> mFragClass;

        private SubredditFragmentType(int titleResId, Class<?> fragmentClss) {
            mTitleResId = titleResId;
            mFragClass = fragmentClss;
        }
    }

}

