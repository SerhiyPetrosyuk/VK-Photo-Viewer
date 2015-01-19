package com.mlsdev.serhiy.mlsdevvkphotoviewer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.Constants;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiPhotoAlbum;
import com.vk.sdk.api.model.VKList;

/**
 * Created by android on 17.01.15.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext = null;
    private VKList<VKApiPhotoAlbum> mPhotoAlbumVKList = null;

    public GridViewAdapter(Context mContext, VKList<VKApiPhotoAlbum> mPhotoAlbumVKList) {
        this.mContext = mContext;
        this.mPhotoAlbumVKList = mPhotoAlbumVKList;
    }

    @Override
    public int getCount() {
        return mPhotoAlbumVKList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotoAlbumVKList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_album_item, parent, false);

            MyViewHolder viewHolder = new MyViewHolder();

            viewHolder.albumThumbnail = (ImageView) convertView.findViewById(R.id.iv_photo_albums);
            viewHolder.albumTitle = (TextView) convertView.findViewById(R.id.tv_album_title);
            viewHolder.progressBar= (ProgressBar) convertView.findViewById(R.id.progress_bar_album);

            convertView.setTag(viewHolder);
        }

        final MyViewHolder viewHolder = (MyViewHolder) convertView.getTag();

        viewHolder.albumTitle.setText(mPhotoAlbumVKList.get(position).title);
        Picasso.with(mContext)
                .load(mPhotoAlbumVKList.get(position).thumb_src).into(viewHolder.albumThumbnail, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.d(Constants.VK_LOG_TAG, "ERROR LOAD THUMB FOR ALBUM ID: " + mPhotoAlbumVKList.get(position).getId());
            }
        });

        return convertView;
    }

    private class MyViewHolder {
        public ImageView albumThumbnail;
        public TextView albumTitle;
        public ProgressBar progressBar;
    }
}
