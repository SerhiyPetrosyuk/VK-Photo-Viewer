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
import com.mlsdev.serhiy.mlsdevvkphotoviewer.adapters.PhotoGridAdapter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKList;

/**
 * Created by android on 19.01.15.
 */
public class PhotosFragment extends Fragment {

    public static final String TAG = "photos.fragment";

    private String mAlbumTitle = null;
    private Integer mAlbumId = null;
    private GridView gridView,mPhotosGridView = null;
    private PhotoGridAdapter mPhotoGridAdapter = null;

    public PhotosFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        mAlbumTitle = bundle.getString(Constants.ALBUM_TITLE, "album");
        mAlbumId    = bundle.getInt(Constants.ALBUM_ID, 0);

        FragmentNavigator.forward(getActivity(), mAlbumTitle);

        View rootView = inflater.inflate(R.layout.photos_fragment, container, false);

        mPhotosGridView = (GridView) rootView.findViewById(R.id.gv_photos);
        mPhotosGridView.setOnItemClickListener(new OnPhotoClickListener());
        getPhotos();

        return rootView;

    }

    private void getPhotos(){

        VKRequest request = new VKRequest("photos.get", VKParameters.from(VKApiConst.ALBUM_ID, mAlbumId, VKApiConst.REV, 1, "wall"));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiPhoto> photos = new VKList<VKApiPhoto>(response.json, VKApiPhoto.class);
                mPhotoGridAdapter = new PhotoGridAdapter(getActivity(), photos);
                mPhotosGridView.setAdapter(mPhotoGridAdapter);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(Constants.VK_LOG_TAG, "Request error: "+error.httpError);
            }
        });

    }// end getPhotos

    private class OnPhotoClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            VKApiPhoto photo = (VKApiPhoto) parent.getAdapter().getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.FULL_SCREEN_PHOTO, photo.photo_604);

            PhotoFragment photoFragment = new PhotoFragment();
            photoFragment.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                        .addToBackStack(PhotoFragment.TAG)
                        .add(R.id.main_activity_fragment_holder, photoFragment, PhotosFragment.TAG)
                        .hide(PhotosFragment.this)
//                        .replace(R.id.main_activity_fragment_holder, photoFragment)
                        .commit();
        }
    }
}
