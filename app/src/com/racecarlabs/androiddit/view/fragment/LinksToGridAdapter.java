package com.racecarlabs.androiddit.view.fragment;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.j256.ormlite.dao.Dao;
import com.racecarlabs.androiddit.R;
import com.racecarlabs.androiddit.model.Link;
import com.racecarlabs.androiddit.model.helper.RedditORMHelper;
import com.racecarlabs.androiddit.view.activity.ImageViewActivity;

public class LinksToGridAdapter extends ArrayAdapter<Link> {

    private Dao<Link, Integer> mLinkDao;

    private int mViewSize;

    public LinksToGridAdapter(Context context, RedditORMHelper ormHelper) {
        super(context, R.layout.item_linkgrid);

        try {
            mLinkDao = ormHelper.getLinkDao();
            loadLinks();

        } catch (SQLException e) {
            Log.w(LinksToGridAdapter.class.getSimpleName(), "Can't load all links!");
        }

        mViewSize = context.getResources().getDimensionPixelSize(R.dimen.grid_item);

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
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_linkgrid, null, false);
        }
        Link currLink = getItem(pos);

        String imgUrl = currLink.getImageUrl();

        AQuery aq = new AQuery(view);
        if (!TextUtils.isEmpty(imgUrl)) {
            aq.image(imgUrl, true, true, mViewSize, R.drawable.ic_launcher, null, AQuery.FADE_IN_NETWORK, 1.0f);
            aq.clicked(getImageClickListener(imgUrl));
        } else {
            aq.clear();
            aq.clicked(null);
        }
        return view;
    }

    private OnClickListener getImageClickListener(final String imgUrl) {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent fullPicIntent = new Intent(context, ImageViewActivity.class);
                fullPicIntent.setData(Uri.parse(imgUrl));
                v.getContext().startActivity(fullPicIntent);
            }

        };
    }

}
