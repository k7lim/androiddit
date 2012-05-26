package com.racecarlabs.androiddit.model;

import org.json.JSONObject;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.racecarlabs.androiddit.utils.RedditUtils;

public class Link {

    public static final String REDDIT_THING_PREFIX = "t3";

    private static final String JSON_SUBREDDITID = "subreddit_id";

    private static final String JSON_SUBREDDITNAME = "subreddit";

    private static final String JSON_ISSELF = "is_self";

    private static final String JSON_DOMAIN = "domain";

    private static final String JSON_SELFHTML = "selftext_html";

    private static final String JSON_SELFTXT = "selftext";

    private static final String JSON_TITLE = "title";

    private static final String JSON_NUMCOMMENTS = "num_comments";

    private static final String JSON_SCORE = "score";

    private static final String JSON_UPS = "ups";

    private static final String JSON_DOWNS = "downs";

    private static final String JSON_THUMBNAIL = "thumbnail";

    private static final String JSON_PERMALINK = "permalink";

    private static final String JSON_URL = "url";

    private static final String JSON_AUTHOR = "author";

    private static final String JSON_CREATEDUTC = "created_utc";

    private static final String JSON_ISOVER18 = "over18";

    private static final String JSON_REDDITID = "id";

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(index = true)
    String subredditId;

    @DatabaseField(index = true)
    String subredditName;

    @DatabaseField
    boolean isSelf;

    @DatabaseField
    String domain;

    @DatabaseField
    String selfTxtHtml;

    @DatabaseField
    String selfTxt;

    @DatabaseField
    String title;

    @DatabaseField
    int numComments;

    @DatabaseField
    int score;

    @DatabaseField
    int ups;

    @DatabaseField
    int downs;

    @DatabaseField
    String thumbnail;

    @DatabaseField
    String permalink;

    @DatabaseField
    String url;

    @DatabaseField
    String author;

    @DatabaseField
    long createdUtc;

    @DatabaseField
    boolean isOver18;

    @DatabaseField(index = true)
    String redditId;

    Link() {
        // needed by ormlite
    }

    public Link(JSONObject data) {
        this.subredditId = data.optString(JSON_SUBREDDITID);
        this.subredditName = data.optString(JSON_SUBREDDITNAME);
        this.isSelf = data.optBoolean(JSON_ISSELF);
        this.domain = data.optString(JSON_DOMAIN);
        this.selfTxtHtml = data.optString(JSON_SELFHTML);
        this.selfTxt = data.optString(JSON_SELFTXT);
        this.title = data.optString(JSON_TITLE);
        this.numComments = data.optInt(JSON_NUMCOMMENTS);
        this.score = data.optInt(JSON_SCORE);
        this.ups = data.optInt(JSON_UPS);
        this.downs = data.optInt(JSON_DOWNS);
        this.thumbnail = data.optString(JSON_THUMBNAIL);
        this.permalink = data.optString(JSON_PERMALINK);
        this.url = data.optString(JSON_URL);
        this.author = data.optString(JSON_AUTHOR);
        this.createdUtc = data.optLong(JSON_CREATEDUTC);
        this.isOver18 = data.optBoolean(JSON_ISOVER18);
        this.redditId = data.optString(JSON_REDDITID);
    }

    public String getImageUrl() {

        if (!TextUtils.isEmpty(this.url) && RedditUtils.isImageUrl(this.url)) {
            return this.url;
        }

        return this.thumbnail;
    }

}
