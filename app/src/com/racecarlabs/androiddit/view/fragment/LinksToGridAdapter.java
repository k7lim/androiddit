package com.racecarlabs.androiddit.view.fragment;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.j256.ormlite.dao.Dao;
import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.model.Link;
import com.racecarlabs.androiddit.model.helper.RedditORMHelper;

public class LinksToGridAdapter extends ArrayAdapter<Link> {

    private Dao<Link, Integer> mLinkDao;

    public LinksToGridAdapter(Context context, RedditORMHelper ormHelper) {
        super(context, R.layout.item_linkgrid);

        try {
            mLinkDao = ormHelper.getLinkDao();
            loadLinks();

        } catch (SQLException e) {
            Log.w(LinksToGridAdapter.class.getSimpleName(), "Can't load all links!");
        }

    }

    private void loadLinks() throws SQLException {
        if (mLinkDao != null) {
            List<Link> linkList = mLinkDao.queryForAll();
            addAll(linkList);
        }
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_linkgrid, null);
        }

        Link currLink = getItem(pos);

        String imgUrl = currLink.getImageUrl();

        if (!TextUtils.isEmpty(imgUrl)) {
            AQuery aq = new AQuery(view);
            aq.id(R.id.pic).image(imgUrl);
        }

        return view;
    }

}
