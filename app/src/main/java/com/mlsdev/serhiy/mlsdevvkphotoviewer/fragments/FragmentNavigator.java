package com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.Constants;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.R;

/**
 * Created by android on 17.01.15.
 */
public class FragmentNavigator {



    public static void back(Activity activity){
        ActionBar actionBar = activity.getActionBar();
        FragmentManager manager = activity.getFragmentManager();
        int fragmentsCount = manager.getBackStackEntryCount();

        if (fragmentsCount > 0) {
            Log.d(Constants.VK_LOG_TAG, "Fragments count: " + fragmentsCount);

            manager.popBackStack();

            if (fragmentsCount == 1){
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle(activity.getString(R.string.app_name));
                actionBar.setIcon(R.drawable.ic_launcher);
            } else if (fragmentsCount == 2) {
                actionBar.setTitle(activity.getString(R.string.photo_albums));
            }

//            int fragmentIndexInBackStack = manager.getBackStackEntryAt(fragmentsCount-2).getId();
//            Fragment fragment = manager.findFragmentById(fragmentIndexInBackStack);
//            manager.beginTransaction().show(fragment).commit();

            activity.invalidateOptionsMenu();
        }
    }

    public static void forward(Activity activity, String title){

        ActionBar actionBar = activity.getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
        actionBar.setIcon(android.R.color.transparent);

        activity.invalidateOptionsMenu();
    }

}
