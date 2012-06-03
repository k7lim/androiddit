package com.racecarlabs.androiddit.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.controller.adapter.LinksToGridAdapter;

public class SubredditPicsFragment extends AbsSubredditFragment {
    
    
    private AQuery mAQ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_pics, null, false);
        
        mAQ = new AQuery(fragView);
        
        Context context = getActivity();
        
        LinksToGridAdapter adapter = new LinksToGridAdapter(context, getORMHelper(context));
        mAQ.id(R.id.grid).adapter(adapter);

        
        return fragView;
    }
}
