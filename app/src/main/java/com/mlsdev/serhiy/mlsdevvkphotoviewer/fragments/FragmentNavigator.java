package com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;

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

            manager.popBackStack();

            if (fragmentsCount == 1){
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle(activity.getString(R.string.app_name));
                actionBar.setIcon(R.drawable.ic_launcher);
            } else if (fragmentsCount == 2) {
                actionBar.setTitle(activity.getString(R.string.photo_albums));
            }

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
