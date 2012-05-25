package com.racecarlabs.androiddit.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.racecarlabs.androiddit.R;

public class SubredditDescFragment extends AbsSubredditFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_desc, null, false);
        
        return fragView;
    }
}
