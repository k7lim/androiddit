package com.racecarlabs.androiddit.view.activity;

import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.R.layout;

import android.app.Activity;
import android.os.Bundle;

public class AndroidditActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}