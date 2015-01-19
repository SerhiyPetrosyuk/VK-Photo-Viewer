package com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.Constants;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.R;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.adapters.GridViewAdapter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhotoAlbum;
import com.vk.sdk.api.model.VKList;

/**
 * Created by android on 16.01.15.
 */
public class PhotoAlbumsFragment extends Fragment {

    public static final String TAG = "photo.albums.tag";

    private GridView mPhotoAlbumsGridView = null;

    public PhotoAlbumsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentNavigator.forward(getActivity(), "photo albums");

        VKAccessToken accessToken = VKAccessToken.tokenFromSharedPreferences(getActivity(), Constants.TOKEN_KEY);
        String userId = accessToken.userId;
        Log.d(Constants.VK_LOG_TAG, "user ID: " + userId);
        Log.d(Constants.VK_LOG_TAG, "access token: " + accessToken.accessToken);

        View rootView = inflater.inflate(R.layout.photo_albums_fragment, container, false);

        mPhotoAlbumsGridView = (GridView) rootView.findViewById(R.id.gv_photo_albums);
        mPhotoAlbumsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VKApiPhotoAlbum photoAlbum = (VKApiPhotoAlbum) parent.getAdapter().getItem(position);

                openPhotoAlbum(photoAlbum);

                Log.d(Constants.VK_LOG_TAG, "Album title: " + photoAlbum.title + " ID: " + photoAlbum.getId());
            }
        });
        getPhotoAlbums(userId, rootView);

        this.setRetainInstance(true);

        return rootView;
    }

    /*
    * Get all photo albums from the VK server
    * */
    private void getPhotoAlbums(String userId, View view){
        // The request to download photo albums
        VKRequest request = new VKRequest("photos.getAlbums", VKParameters.from(VKApiConst.OWNER_ID, userId, "need_covers", 1, "need_system", "wall"));
        Log.d(Constants.VK_LOG_TAG, "URL: " + request.getPreparedRequest().getURI().toString());

        /*
        * The VKRequest is work in the own thread and handle the response in the CallBack,
        * that is why we don't need to use "AsyncTask".
        * */
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiPhotoAlbum> photoAlbums = new VKList<VKApiPhotoAlbum>(response.json, VKApiPhotoAlbum.class);

                GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), photoAlbums);
                mPhotoAlbumsGridView.setAdapter(gridViewAdapter);
            }
        });

    }

    /*
    * It opens the selected photo album.
    * */
    private void openPhotoAlbum(VKApiPhotoAlbum photoAlbum) {

        // We need the album id to get photos and title to set up it into the action bar.
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ALBUM_TITLE, photoAlbum.title);
        bundle.putInt(Constants.ALBUM_ID, photoAlbum.getId());

        PhotosFragment photosFragment = new PhotosFragment();
        photosFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                    .addToBackStack(PhotosFragment.TAG)
                    .add(R.id.main_activity_fragment_holder, photosFragment, PhotosFragment.TAG)
                    .show(photosFragment)
                    .hide(this)
                    .commit();
    }

}
