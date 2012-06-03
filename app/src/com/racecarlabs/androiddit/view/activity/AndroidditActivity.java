package com.racecarlabs.androiddit.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.androidquery.AQuery;
import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.controller.adapter.SubredditFragmentAdapter;
import com.racecarlabs.androiddit.service.RedditDownloadService;
import com.viewpagerindicator.TabPageIndicator;

public class AndroidditActivity extends SherlockFragmentActivity {

    private AQuery mAQ;

    private ViewPager mPager;

    private TabPageIndicator mTabs;

    private SubredditFragmentAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androiddit);

        mAQ = new AQuery(this);
        
        mPager = (ViewPager) mAQ.id(R.id.pager).getView();
        mAdapter = new SubredditFragmentAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        
        mTabs = (TabPageIndicator) mAQ.id(R.id.tabs).getView();
        mTabs.setViewPager(mPager);
        
        Intent updateIntent = new Intent(this, RedditDownloadService.class);
        startService(updateIntent);
    }
}