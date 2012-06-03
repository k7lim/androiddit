package com.racecarlabs.androiddit.service;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.j256.ormlite.dao.Dao;
import com.racecarlabs.androiddit.model.Link;
import com.racecarlabs.androiddit.model.Subreddit;
import com.racecarlabs.androiddit.model.helper.RedditORMHelper;

public class RedditDownloadService extends IntentService {

    private static final String EXTRA_SUBREDDITNAME = "extras.service.subreddit";

    private static final String TEMPLATE_URL = "http://www.reddit.com/r/%s.json?sort=%s&limit=%d";

    private static final String TEMPLATE_ABOUT_URL = "http://www.reddit.com/r/%s/about.json";

    private static final String PARAM_SORT_HOT = "hot";

    private static final String DEFAULT_SUBREDDIT = "pics";

    private static final long DEFAULT_CACHE_EXPIRY = 1000 * 60 * 60 * 12; // 12 hours in millis
    
    private static final int DEFAULT_PAGESIZE = 100;

    private AQuery mAQ;

    public RedditDownloadService() {
        super("RedditDownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAQ = new AQuery(this);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle data = intent.getExtras();
        String subreddit = (data == null) ? DEFAULT_SUBREDDIT : data.getString(EXTRA_SUBREDDITNAME, DEFAULT_SUBREDDIT);

        String aboutUrl = getAboutUrl(subreddit);
        mAQ.ajax(aboutUrl, JSONObject.class, mAboutCallback);

        String linksUrl = getDownloadUrl(subreddit, PARAM_SORT_HOT, DEFAULT_PAGESIZE);
        mAQ.ajax(linksUrl, JSONObject.class, mLinksCallback);

    }

    private String getDownloadUrl(String subreddit, String sort, int count) {
        return String.format(TEMPLATE_URL, subreddit, sort, count);
    }

    private String getAboutUrl(String subreddit) {
        return String.format(TEMPLATE_ABOUT_URL, subreddit);
    }

    private AjaxCallback<JSONObject> mAboutCallback = new AjaxCallback<JSONObject>() {

        @Override
        public void callback(String url, JSONObject json, AjaxStatus status) {
            if (json != null) {
                JSONObject data = json.optJSONObject("data");
                Subreddit subreddit = new Subreddit(data);

                try {
                    RedditORMHelper helper = RedditORMHelper.getInstance(RedditDownloadService.this);
                    Dao<Subreddit, Integer> srDao = helper.getSubredditDao();
                    srDao.createOrUpdate(subreddit);
                    helper.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private AjaxCallback<JSONObject> mLinksCallback = new AjaxCallback<JSONObject>() {

        @Override
        public void callback(String url, JSONObject json, AjaxStatus status) {
            if (json != null) {
                JSONObject data = json.optJSONObject("data");
                if (data != null) {
                    JSONArray children = data.optJSONArray("children");
                    if (children != null) {
                        RedditORMHelper helper = RedditORMHelper.getInstance(RedditDownloadService.this);

                        for (int i = 0; i < children.length(); i++) {
                            JSONObject linkJson = children.optJSONObject(i);
                            if (linkJson != null) {

                                data = linkJson.optJSONObject("data");

                                if (data != null) {
                                    Link link = new Link(data);

                                    try {
                                        Dao<Link, Integer> linkDao = helper.getLinkDao();
                                        linkDao.createOrUpdate(link);

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                    mAQ.cache(link.getImageUrl(), DEFAULT_CACHE_EXPIRY);
                                }
                            }
                        }

                        helper.close();

                    }
                }
            }
        }

    };

}
