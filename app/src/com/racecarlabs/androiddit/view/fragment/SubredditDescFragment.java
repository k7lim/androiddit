package com.racecarlabs.androiddit.view.fragment;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.commonsware.cwac.anddown.AndDown;
import com.j256.ormlite.dao.Dao;
import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.model.Subreddit;

public class SubredditDescFragment extends AbsSubredditFragment {
    private AQuery mAQ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_desc, null, false);

        mAQ = new AQuery(fragView);

        try {
            Dao<Subreddit, Integer> helper = getORMHelper(getActivity()).getSubredditDao();
            List<Subreddit> subreddits = helper.queryForAll();

            // for now, just one hardcoded subreddit
            if (subreddits != null && subreddits.size() > 0) {
                Subreddit subreddit = subreddits.get(0);

                String raw = subreddit.getMarkdownDescription();
                String htmlDesc = new AndDown().markdownToHtml(raw);
                CharSequence desc = Html.fromHtml(htmlDesc);
                mAQ.id(R.id.desc).text(desc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fragView;
    }
}
