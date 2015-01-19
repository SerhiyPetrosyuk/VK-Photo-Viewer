package com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.Constants;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.api.VKError;


/**
 * Created by android on 16.01.15.
 */
public class LoginFragment extends Fragment {

    public static final String TAG = "login.form.fragment";
    public static final String VK_LOG_TAG = "VK LOG TAG";

    private Button mSingInButton;
    private Button mSingOutButton;
    private Button mAlbumsButton;
    private LinearLayout mSingOutAndAlbumsButtonsHolder;
    private VKSdkListener mVkSdkListener;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login_fragment, container, false);

        // Define needed widgets
        prepareView(rootView);

        // Define VK sdk listener to make the initialization an authorization
        defineVkSdkListener();

        VKSdk.initialize(mVkSdkListener, Constants.APP_ID,
                VKAccessToken.tokenFromSharedPreferences(getActivity(), Constants.TOKEN_KEY));

        /*
        * If a user has logged in we show him the fragment with two buttons, the first one іs
        * "Log out button" and the second one is "show albums" button
        * */
        if (VKSdk.isLoggedIn())
            openPhotoAlbumsFragment();

        // Set retain instance to save fragment data when the is changed
        this.setRetainInstance(true);

        return rootView;
    }

    private void prepareView(View rootView){
        mSingInButton  = (Button) rootView.findViewById(R.id.btn_sing_in);
        mSingOutButton = (Button) rootView.findViewById(R.id.btn_sing_out);
        mAlbumsButton  = (Button) rootView.findViewById(R.id.btn_albums);
        mSingOutAndAlbumsButtonsHolder = (LinearLayout) rootView.findViewById(R.id.ll_sing_out_and_albums);

        mSingInButton.setOnClickListener(new SingInOnclickListener());
        mSingOutButton.setOnClickListener(new SingOutButtonListener());
        mAlbumsButton.setOnClickListener(new AlbumsOnClickListener());
    }

    private class SingOutButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (VKSdk.isLoggedIn())
                VKSdk.logout();

            mSingInButton.setVisibility(View.VISIBLE);
            mSingOutAndAlbumsButtonsHolder.setVisibility(View.GONE);
        }
    }

    private class SingInOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (!VKSdk.isLoggedIn())
                VKSdk.authorize(new String[]{VKScope.PHOTOS}, true, false);
        }

    }

    private class AlbumsOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(PhotoAlbumsFragment.TAG)
                    .replace(R.id.main_activity_fragment_holder, new PhotoAlbumsFragment(), PhotoAlbumsFragment.TAG)
                    .commit();

            Log.d(Constants.VK_LOG_TAG, "Go to " + PhotoAlbumsFragment.TAG);
        }
    }

    private void defineVkSdkListener(){
        mVkSdkListener = new VKSdkListener() {

            @Override
            public void onAcceptUserToken(VKAccessToken token) {
                Log.d(VK_LOG_TAG, "onAcceptUserToken " + token);
                openPhotoAlbumsFragment();
            }

            @Override
            public void onReceiveNewToken(VKAccessToken newToken) {
                Log.d(VK_LOG_TAG, "onReceiveNewToken " + newToken);
                newToken.saveTokenToSharedPreferences(getActivity(), Constants.TOKEN_KEY);
                openPhotoAlbumsFragment();
            }

            @Override
            public void onRenewAccessToken(VKAccessToken token) {
                Log.d(VK_LOG_TAG, "onRenewAccessToken " + token);
                token.saveTokenToSharedPreferences(getActivity(), Constants.TOKEN_KEY);
                openPhotoAlbumsFragment();
            }

            @Override
            public void onCaptchaError(VKError vkError) {
                Toast.makeText(getActivity(), "Wrong code", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTokenExpired(VKAccessToken vkAccessToken) {
                Toast.makeText(getActivity(), "Token expired", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAccessDenied(VKError vkError) {
                Toast.makeText(getActivity(), "Access denied", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void openPhotoAlbumsFragment(){
        mSingInButton.setVisibility(View.GONE);
        mSingOutAndAlbumsButtonsHolder.setVisibility(View.VISIBLE);
    }
}
