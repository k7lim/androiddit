package com.racecarlabs.androiddit.view.fragment;

import android.content.Context;

import com.actionbarsherlock.app.SherlockFragment;
import com.racecarlabs.androiddit.model.helper.RedditORMHelper;

public abstract class AbsSubredditFragment extends SherlockFragment {

   
    private RedditORMHelper mORMHelper;

    protected RedditORMHelper getORMHelper(Context context) {
        if(mORMHelper == null) {
            mORMHelper = RedditORMHelper.getInstance(context);
        }
        
        return mORMHelper;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        if(mORMHelper != null) {
            mORMHelper.close();
            mORMHelper = null;
        }
    }
    
}
