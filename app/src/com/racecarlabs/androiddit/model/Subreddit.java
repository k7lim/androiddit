package com.racecarlabs.androiddit.model;

import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;

public class Subreddit {

    public static final String REDDIT_THING_PREFIX = "t5";

    private static final String JSON_TITLE = "title";

    private static final String JSON_DISPNAME = "display_name";

    private static final String JSON_HEADERIMG = "header_img";

    private static final String JSON_RELURL = "url";

    private static final String JSON_DESC = "description";

    private static final String JSON_CREATEDUTC = "created_utc";

    private static final String JSON_ISOVER18 = "over18";

    private static final String JSON_SUBSCRIBERS = "subscribers";

    private static final String JSON_REDDITID = "id";

    @DatabaseField(index = true)
    String title;

    @DatabaseField
    String displayName;

    @DatabaseField
    String headerImg;

    @DatabaseField
    String relativeUrl;

    @DatabaseField
    String descMarkDown;

    @DatabaseField
    long createdUtc;

    @DatabaseField
    boolean isOver18;

    @DatabaseField
    int subscribers;

    @DatabaseField(id = true)
    String redditId;

    Subreddit() {
        // needed by ormlite
    }

    public Subreddit(JSONObject data) {
        this.title = data.optString(JSON_TITLE);
        this.displayName = data.optString(JSON_DISPNAME);
        this.headerImg = data.optString(JSON_HEADERIMG);
        this.relativeUrl = data.optString(JSON_RELURL);
        this.descMarkDown = data.optString(JSON_DESC);
        this.createdUtc = data.optLong(JSON_CREATEDUTC);
        this.isOver18 = data.optBoolean(JSON_ISOVER18);
        this.subscribers = data.optInt(JSON_SUBSCRIBERS);
        this.redditId = data.optString(JSON_REDDITID);
    }

    public String getMarkdownDescription() {
        return descMarkDown;
    }

}
