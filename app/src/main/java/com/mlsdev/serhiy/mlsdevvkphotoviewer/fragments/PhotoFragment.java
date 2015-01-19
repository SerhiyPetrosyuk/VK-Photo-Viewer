package com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.Constants;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by android on 19.01.15.
 */
public class PhotoFragment extends Fragment {

    public static final String TAG = "full.screen.photo";

    private ImageView mFullScreenPhoto = null;
    private ProgressBar mProgressBar = null;

    public PhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.photo_fragment, container, false);

        mFullScreenPhoto = (ImageView) rootView.findViewById(R.id.iv_photo_in_full_screen);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_full_photo);

        Bundle bundle = getArguments();
        String photoUrl = bundle.getString(Constants.FULL_SCREEN_PHOTO, "");

        Picasso.with(getActivity()).load(photoUrl).into(mFullScreenPhoto, new Callback() {
            @Override
            public void onSuccess() {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.d(Constants.VK_LOG_TAG, "ERROR LOAD FULL PHOTO" );
            }
        });

        return rootView;
    }
}
