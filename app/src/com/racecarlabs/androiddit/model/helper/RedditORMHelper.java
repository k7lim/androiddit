package com.racecarlabs.androiddit.model.helper;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.racecarlabs.androiddit.model.Link;
import com.racecarlabs.androiddit.model.Subreddit;

public class RedditORMHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "links.db";

    private static final int DATABASE_VERSION = 1;

    private Dao<Link, Integer> mLinkDao = null;

    private Dao<Subreddit, Integer> mSubredditDao = null;

    private static RedditORMHelper sInstance = null;

    private static final AtomicInteger sClientCount = new AtomicInteger(0);

    // singleton
    private RedditORMHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized RedditORMHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RedditORMHelper(context);
        }

        sClientCount.incrementAndGet();

        return sInstance;
    }

    @Override
    public void close() {
        if (sClientCount.decrementAndGet() == 0) {
            super.close();
            mLinkDao = null;
            mSubredditDao = null;
            sInstance = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connSrc) {

        try {
            TableUtils.createTable(connSrc, Subreddit.class);
            TableUtils.createTable(connSrc, Link.class);

        } catch (SQLException e) {
            Log.e(RedditORMHelper.class.getName(), "Can't create database tables", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connSrc, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connSrc, Link.class, true);
            TableUtils.dropTable(connSrc, Subreddit.class, true);

        } catch (SQLException e) {
            Log.e(RedditORMHelper.class.getName(), "Can't drop database tables", e);
        }

        onCreate(db, connSrc);
    }

    public Dao<Link, Integer> getLinkDao() throws SQLException {
        if (mLinkDao == null) {
            mLinkDao = getDao(Link.class);
        }

        return mLinkDao;
    }

    public Dao<Subreddit, Integer> getSubredditDao() throws SQLException {
        if (mSubredditDao == null) {
            mSubredditDao = getDao(Subreddit.class);
        }
        return mSubredditDao;
    }

}
