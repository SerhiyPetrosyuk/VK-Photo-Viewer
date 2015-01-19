package com.mlsdev.serhiy.mlsdevvkphotoviewer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.Constants;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKList;

import java.util.zip.Inflater;

/**
 * Created by android on 19.01.15.
 */
public class PhotoGridAdapter extends BaseAdapter {

    private Context mContext = null;
    private VKList<VKApiPhoto> photos = null;

    public PhotoGridAdapter(Context mContext, VKList<VKApiPhoto> photos) {
        this.mContext = mContext;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_grid_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder();
            viewHolder.photoThumbnail = (ImageView) convertView.findViewById(R.id.iv_photo_thumbnail);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar_photo);
            convertView.setTag(viewHolder);
        }

        final MyViewHolder viewHolder = (MyViewHolder) convertView.getTag();

        Picasso.with(mContext).load(photos.get(position).photo_604).into(viewHolder.photoThumbnail, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.d(Constants.VK_LOG_TAG, "ERROR LOAD THUMB FOR PHOTO ID: " + photos.get(position).getId());
            }
        });

        return convertView;
    }

    private class MyViewHolder {
        public ImageView photoThumbnail;
        public ProgressBar progressBar;
    }
}
