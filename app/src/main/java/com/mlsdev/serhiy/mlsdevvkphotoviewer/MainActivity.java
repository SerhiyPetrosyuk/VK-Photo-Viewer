package com.mlsdev.serhiy.mlsdevvkphotoviewer;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments.FragmentNavigator;
import com.mlsdev.serhiy.mlsdevvkphotoviewer.fragments.LoginFragment;
import com.vk.sdk.VKUIHelper;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_fragment_holder, new LoginFragment(), LoginFragment.TAG)
                .commit();

        VKUIHelper.onCreate(this);
    }

    /*
    * Override onOptionsItemSelected method to manage the back stack like you want
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                FragmentNavigator.back(this);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }
}
